package io.github.TannerLow.baiotechbees.blocks.entities;

import io.github.TannerLow.baiotechbees.items.BeeItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BeeBreedingBlockEntity extends BlockEntity implements Inventory {
    public ItemStack[] inventory = new ItemStack[12];
    public int breedTime = 0;
    public int beeTicks = 0;
    public int lifespan = 0;
    public double frameProductionModifier = 1.0;
    public Map<ItemStack, Integer> potentialProducts = new HashMap();
    public double speed;
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
                while(inventory[slot].count < inventory[slot].getMaxCount() && stack.count > 0) {
                    inventory[slot].count++;
                    stack.count--;
                    markDirty();
                }
                if(stack.count <= 0) {
                    iterator.remove();
                    continue;
                }
            }

            slot = findEmptyOutputSlot(stack);
            if(slot != -1) {
                inventory[slot] = stack;
                markDirty();
                iterator.remove();
            }
        }
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

    public int findItemInOutputSlot(ItemStack stack) {
        // find if item already present in output
        for(int i = 2; i <= 8; i++) {
            ItemStack inventorySlot = inventory[i];
            if(inventorySlot != null && inventorySlot.count < inventorySlot.getMaxCount() && stack.isItemEqual(inventorySlot)) {
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
