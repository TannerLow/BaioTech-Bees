package io.github.TannerLow.baiotechbees.guis.handlers;

import io.github.TannerLow.baiotechbees.blocks.entities.ApiaryBlockEntity;
import io.github.TannerLow.baiotechbees.guis.slots.BeeSlot;
import io.github.TannerLow.baiotechbees.guis.slots.FrameSlot;
import io.github.TannerLow.baiotechbees.guis.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

public class ApiaryScreenHandler extends ScreenHandler {
    private ApiaryBlockEntity apiaryBlockEntity;

    public ApiaryScreenHandler(PlayerInventory playerInventory, ApiaryBlockEntity apiaryBlockEntity) {
        this.apiaryBlockEntity = apiaryBlockEntity;

        // inputs
        this.addSlot(new BeeSlot(apiaryBlockEntity, 0, 29, 39));
        this.addSlot(new BeeSlot(apiaryBlockEntity, 1, 29, 65));

        // outputs
        this.addSlot(new OutputSlot(apiaryBlockEntity, 2, 116, 52));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 3, 95, 39));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 4, 116, 26));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 5, 137, 39));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 6, 137, 65));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 7, 116, 78));
        this.addSlot(new OutputSlot(apiaryBlockEntity, 8, 95, 65));

        // frames
        this.addSlot(new FrameSlot(apiaryBlockEntity, 9, 66, 23));
        this.addSlot(new FrameSlot(apiaryBlockEntity, 10, 66, 52));
        this.addSlot(new FrameSlot(apiaryBlockEntity, 11, 66, 81));

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
        return this.apiaryBlockEntity.canPlayerUse(player);
    }
}
