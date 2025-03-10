package io.github.TannerLow.baiotechbees.guis.screens;

import io.github.TannerLow.baiotechbees.blocks.entities.ApiaryBlockEntity;
import io.github.TannerLow.baiotechbees.guis.handlers.ApiaryScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class ApiaryScreen extends HandledScreen {
    private ApiaryBlockEntity apiaryBlockEntity;

    public ApiaryScreen(PlayerInventory inventory, ApiaryBlockEntity apiaryBlockEntity) {
        super(new ApiaryScreenHandler(inventory, apiaryBlockEntity));
        this.apiaryBlockEntity = apiaryBlockEntity;
        this.backgroundHeight = 190;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw("Apiary", 8, 6, 4210752);
        // this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int texture = this.minecraft.textureManager.getTextureId("assets/baiotechbees/gui/apiary.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(texture);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        if(apiaryBlockEntity.maxBreedTime > 0) {
            int height = apiaryBlockEntity.breedTime * 46 / apiaryBlockEntity.maxBreedTime;
            this.drawTexture(x + 20, y + 37, 192, 0, 4, height);
        }

//        if (this.furnaceBlockEntity.isBurning()) {
//            int var5 = this.furnaceBlockEntity.getFuelTimeDelta(12);
//            this.drawTexture(var3 + 56, var4 + 36 + 12 - var5, 176, 12 - var5, 14, var5 + 2);
//        }

//        int var6 = this.furnaceBlockEntity.getCookTimeDelta(24);
//        this.drawTexture(var3 + 79, var4 + 34, 176, 14, var6 + 1, 16);
    }
}
