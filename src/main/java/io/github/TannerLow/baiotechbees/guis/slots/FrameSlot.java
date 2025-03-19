package io.github.TannerLow.baiotechbees.guis.slots;

import io.github.TannerLow.baiotechbees.items.BeeFrameItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FrameSlot extends Slot {

    public FrameSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof BeeFrameItem;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}
