package io.github.TannerLow.baiotechbees.guis.handlers;

import io.github.TannerLow.baiotechbees.blocks.entities.BeeHouseBlockEntity;
import io.github.TannerLow.baiotechbees.guis.slots.BeeSlot;
import io.github.TannerLow.baiotechbees.guis.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BeeHouseScreenHandler extends ScreenHandler {
    private BeeHouseBlockEntity beeHouseBlockEntity;

    public BeeHouseScreenHandler(PlayerInventory playerInventory, BeeHouseBlockEntity beeHouseBlockEntity) {
        this.beeHouseBlockEntity = beeHouseBlockEntity;

        // inputs
        this.addSlot(new BeeSlot(beeHouseBlockEntity, 0, 29, 39));
        this.addSlot(new BeeSlot(beeHouseBlockEntity, 1, 29, 65));

        // outputs
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 2, 116, 52));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 3, 95, 39));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 4, 116, 26));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 5, 137, 39));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 6, 137, 65));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 7, 116, 78));
        this.addSlot(new OutputSlot(beeHouseBlockEntity, 8, 95, 65));

        // Player inventory slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 108 + row * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 166));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.beeHouseBlockEntity.canPlayerUse(player);
    }
}
