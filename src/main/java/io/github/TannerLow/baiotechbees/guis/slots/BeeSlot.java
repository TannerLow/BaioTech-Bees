package io.github.TannerLow.baiotechbees.guis.slots;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BeeSlot extends Slot {

    public BeeSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return  stack.itemId == ItemListener.DRONE_BEE.id ||
                stack.itemId == ItemListener.PRINCESS_BEE.id ||
                stack.itemId == ItemListener.QUEEN_BEE.id;
    }
}
