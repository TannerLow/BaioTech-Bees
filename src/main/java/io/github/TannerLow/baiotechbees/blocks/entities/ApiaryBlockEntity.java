package io.github.TannerLow.baiotechbees.blocks.entities;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import io.github.TannerLow.baiotechbees.items.BeeItem;
import io.github.TannerLow.baiotechbees.items.QueenBeeItem;
import io.github.TannerLow.baiotechbees.items.util.BeeProduct;
import io.github.TannerLow.baiotechbees.items.util.BeeProductTable;
import io.github.TannerLow.baiotechbees.items.util.Gene;
import io.github.TannerLow.baiotechbees.items.util.Genome;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ApiaryBlockEntity extends BlockEntity implements Inventory {
    private ItemStack[] inventory = new ItemStack[12];
    public int breedTime = 0;
    public int beeTicks = 0;
    public int lifespan = 0;
    public final double PRODUCTION_MODIFIER = 10.0; // should be 0.1 for apiary
    public Map<ItemStack, Integer> potentialProducts = new HashMap();
    double speed;
    public List<ItemStack> outputBuffer = new LinkedList();

    @Override
    public int size() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory[slot];
    }

    public void updateOutputBuffer() {
        Iterator<ItemStack> iterator = outputBuffer.iterator();

        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();

            int slot = findItemInOutputSlot(stack);
            if (slot != -1) {
                markDirty();
                while(inventory[slot].count < inventory[slot].getMaxCount() && stack.count > 0) {
                    inventory[slot].count++;
                    stack.count--;
                }
                if(stack.count <= 0) {
                    iterator.remove();
                    continue;
                }
            }

            slot = findEmptyOutputSlot(stack);
            if(slot != -1) {
                markDirty();
                inventory[slot] = stack;
                iterator.remove();
            }
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.inventory[slot] != null) {
            if (this.inventory[slot].count <= amount) {
                ItemStack itemStack = this.inventory[slot];

                // if we are removing the queen then save stats to queen and reset apiary state
                if(slot == 0) {
                    if(itemStack.getItem() instanceof QueenBeeItem) {
                        NbtCompound nbt = itemStack.getStationNbt();
                        nbt.putInt("BreedTime", breedTime);
                        nbt.putInt("BeeTicks", beeTicks);
                    }
                    beeTicks = 0;
                    lifespan = 0;
                    breedTime = 0;
                }

                this.inventory[slot] = null;
                updateOutputBuffer();

                markDirty();
                return itemStack;
            }
            else {
                ItemStack splitStack = this.inventory[slot].split(amount);

                markDirty();
                return splitStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null) {
            if(stack.count > this.getMaxCountPerStack()) {
                stack.count = this.getMaxCountPerStack();
            }

            if(slot == 0 || slot == 1) {
                if(hasPrincess() && hasDrone()) {
                    ItemStack queenStack = ItemListener.QUEEN_BEE.fromMating(inventory[0], inventory[1]);
                    removeStack(0, 1);
                    removeStack(1, 1);
                    setStack(0, queenStack);

                    markDirty();
                    return;
                }
            }

            if(inventory[0] != null && inventory[0].getItem() instanceof QueenBeeItem) {
                NbtCompound nbt = inventory[0].getStationNbt();
                breedTime = nbt.getInt("BreedTime");
                beeTicks = nbt.getInt("BeeTicks");
                lifespan = nbt.getInt("Lifespan");

                NbtCompound princessGenomeNbt = nbt.getCompound("PrincessGenome");
                Genome princessGenome = new Genome();
                princessGenome.readNbt(princessGenomeNbt);

                // some preprocessing for byproduct spawning
                potentialProducts = new HashMap<>();
                Gene breedGene = princessGenome.getGene("Breed");
                short breed1 = (short)breedGene.value1;
                short breed2 = (short)breedGene.value2;
                for(BeeProduct beeProduct : BeeProductTable.productTable.get(breed1)) {
                    if(potentialProducts.containsKey(beeProduct.itemStack)) {
                        if(potentialProducts.get(beeProduct.itemStack) < beeProduct.chance) {
                            potentialProducts.put(beeProduct.itemStack, beeProduct.chance);
                        }
                    }
                    else {
                        potentialProducts.put(beeProduct.itemStack, beeProduct.chance);
                    }
                }
                for(BeeProduct beeProduct : BeeProductTable.productTable.get(breed2)) {
                    if(potentialProducts.containsKey(beeProduct.itemStack)) {
                        if(potentialProducts.get(beeProduct.itemStack) < beeProduct.chance) {
                            potentialProducts.put(beeProduct.itemStack, beeProduct.chance);
                        }
                    }
                    else {
                        potentialProducts.put(beeProduct.itemStack, beeProduct.chance);
                    }
                }

                speed = (double)princessGenome.getGene("Speed").value1;
            }
        }
        markDirty();
    }

    @Override
    public void tick() {

        if(inventory[0] == null) {
            return;
        }

        if(inventory[0].getItem() instanceof QueenBeeItem) {
            if(breedTime > 0) {
                breedTime--;
            }
            // Bee tick
            else {
                //ItemStack queenStack = inventory[0];
                //NbtCompound princessGenomeNbt = queenStack.getStationNbt().getCompound("PrincessGenome");
                //Genome princessGenome = new Genome();
                //princessGenome.readNbt(princessGenomeNbt);
                beeTicks++;
                breedTime = QueenBeeItem.TICKS_IN_BEE_TICK;

                // attempt to spawn products
                for(Map.Entry<ItemStack, Integer> entry : potentialProducts.entrySet()) {
                    ItemStack itemStack = entry.getKey();
                    int chance = entry.getValue();

                    double productProbability = speed * PRODUCTION_MODIFIER /* frameModifier*/ * chance / 100.0; // TODO update when adding frames
                    while(productProbability > 0) {
                        if(QueenBeeItem.RNG.nextDouble() < productProbability) {
                            outputBuffer.add(itemStack.copy());
                            updateOutputBuffer();
                        }
                        productProbability -= 1;
                    }
                }

                if(beeTicks >= lifespan) {
                    breedTime = 0;
                    beeTicks = 0;
                    outputBuffer.addAll(ItemListener.QUEEN_BEE.createOffspring(inventory[0]));
                    removeStack(0, 1);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Apiary";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.world.getBlockEntity(this.x, this.y, this.z) != this
                ? false
                : !(player.getSquaredDistance((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) > 64.0);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        NbtList itemsNbt = nbt.getList("Items");
        this.inventory = new ItemStack[this.size()];
        for (int i = 0; i < itemsNbt.size(); i++) {
            NbtCompound var4 = (NbtCompound)itemsNbt.get(i);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }

        NbtList bufferNbt = nbt.getList("Buffer");
        for (int i = 0; i < bufferNbt.size(); i++) {
            NbtCompound stackNbt = (NbtCompound)bufferNbt.get(i);
            outputBuffer.add(new ItemStack(stackNbt));
        }

        NbtList potentialProductsNbt = nbt.getList("PotentialProducts");
        for(int i = 0; i < potentialProductsNbt.size(); i++) {
            NbtCompound entryNbt = (NbtCompound)potentialProductsNbt.get(i);
            ItemStack itemStack = new ItemStack(entryNbt.getCompound("ItemStack"));
            int chance = entryNbt.getInt("Chance");
            potentialProducts.put(itemStack, chance);
        }

        speed = nbt.getDouble("Speed");
        breedTime = nbt.getInt("BreedTime");
        beeTicks = nbt.getInt("BeeTicks");
        lifespan = nbt.getInt("Lifespan");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        NbtList slotsNbt = new NbtList();
        for (int i = 0; i < this.inventory.length; i++) {
            if (this.inventory[i] != null) {
                NbtCompound slotNbt = new NbtCompound();
                slotNbt.putByte("Slot", (byte) i);
                this.inventory[i].writeNbt(slotNbt);
                slotsNbt.add(slotNbt);
            }
        }

        NbtList bufferNbt = new NbtList();
        ItemStack[] outputBufferArray = outputBuffer.toArray(new ItemStack[outputBuffer.size()]);
        for (int i = 0; i < outputBufferArray.length; i++) {
            if (outputBufferArray[i] != null) {
                NbtCompound stackNbt = new NbtCompound();
                outputBufferArray[i].writeNbt(stackNbt);
                //stackNbt.put("ItemStack", outputBufferArray[i].getStationNbt());
                bufferNbt.add(stackNbt);
            }
        }

        NbtList potentialProductsNbt = new NbtList();
        for(Map.Entry<ItemStack, Integer> entry : potentialProducts.entrySet()) {
            NbtCompound entryNbt = new NbtCompound();
            NbtCompound itemStackNbt = new NbtCompound();
            entry.getKey().writeNbt(itemStackNbt);
            entryNbt.put("ItemStack", itemStackNbt);
            entryNbt.putInt("Chance", entry.getValue());
            potentialProductsNbt.add(entryNbt);
        }

        nbt.put("Buffer", bufferNbt);
        nbt.put("Items", slotsNbt);
        nbt.put("PotentialProducts", potentialProductsNbt);
        nbt.putDouble("Speed", speed);
        nbt.putInt("BreedTime", breedTime);
        nbt.putInt("BeeTicks", beeTicks);
        nbt.putInt("Lifespan", lifespan);
    }

    public int findItemInOutputSlot(ItemStack stack) {
        // find if item already present
        for(int i = 2; i <= 8; i++) {
            if(inventory[i] != null && stack.isItemEqual(inventory[i])) {
                return i;
            }
        }
        return -1;
    }

    public int findEmptyOutputSlot(ItemStack stack) {
        // else find an open slot
        for(int i = 2; i <= 8; i++) {
            if(inventory[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasPrincess() {
        return (inventory[0] != null && ((BeeItem)inventory[0].getItem()).isPrincess);
    }

    public boolean hasDrone() {
        return (inventory[1] != null && !((BeeItem)inventory[1].getItem()).isPrincess);
    }
}
