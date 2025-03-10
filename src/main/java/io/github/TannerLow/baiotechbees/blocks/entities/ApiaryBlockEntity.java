package io.github.TannerLow.baiotechbees.blocks.entities;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import io.github.TannerLow.baiotechbees.items.BeeItem;
import io.github.TannerLow.baiotechbees.items.QueenBeeItem;
import lombok.NonNull;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class ApiaryBlockEntity extends BlockEntity implements Inventory {
    private ItemStack[] inventory = new ItemStack[12];
    public int breedTime = 0;
    public int maxBreedTime = 0;

    @Override
    public int size() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.inventory[slot] != null) {
            if (this.inventory[slot].count <= amount) {
                ItemStack itemStack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemStack;
            } else {
                ItemStack splitStack = this.inventory[slot].split(amount);
                if (this.inventory[slot].count == 0) {
                    this.inventory[slot] = null;
                }

                return splitStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
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
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtList slotsNbt = new NbtList();

        for (int i = 0; i < this.inventory.length; i++) {
            if (this.inventory[i] != null) {
                NbtCompound slotNbt = new NbtCompound();
                slotNbt.putByte("Slot", (byte)i);
                this.inventory[i].writeNbt(slotNbt);
                slotsNbt.add(slotNbt);
            }
        }

        nbt.put("Items", slotsNbt);
    }

    @Override
    public void tick() {
        // Check if nothing to do
        if(inventory[0] == null) {
            breedTime = 0;
            maxBreedTime = 0;
            return;
        }

        // Handle Queen
        if(inventory[0].getItem() instanceof QueenBeeItem) {
            NbtCompound nbt = inventory[0].getStationNbt();
            breedTime = nbt.getInt("BreedTime");
            // Still breeding
            if(breedTime > 0) {
                breedTime--;
                nbt.putInt("BreedTime", breedTime);
            }
            // Done breeding
            else {
                int emptySlot = findEmptyOutputSlot();
                if(emptySlot != -1) {
                    inventory[emptySlot] = ItemListener.QUEEN_BEE.createOffspring(inventory[0]).get(0);
                    inventory[0] = null;
                    markDirty();
                }
            }
            return;
        }

        // Handle Princess + Drone
        if(hasPrincess() && hasDrone()) {
            ItemStack queenStack = ItemListener.QUEEN_BEE.fromMating(inventory[0], inventory[1]);
            maxBreedTime = queenStack.getStationNbt().getInt("MaxBreedTime");
            queenStack.getStationNbt().putInt("BreedTime", maxBreedTime);
            inventory[0] = queenStack;
            inventory[1].count--;
            if(inventory[1].count <= 0) {
                inventory[1] = null;
            }
            markDirty();
        }
    }

    public int findEmptyOutputSlot() {
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
