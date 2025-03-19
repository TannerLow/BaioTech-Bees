package io.github.TannerLow.baiotechbees.guis.screens;

import io.github.TannerLow.baiotechbees.blocks.entities.BeeHouseBlockEntity;
import io.github.TannerLow.baiotechbees.guis.handlers.BeeHouseScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class BeeHouseScreen extends HandledScreen {
    private BeeHouseBlockEntity beeHouseBlockEntity;

    public BeeHouseScreen(PlayerInventory inventory, BeeHouseBlockEntity beeHouseBlockEntity) {
        super(new BeeHouseScreenHandler(inventory, beeHouseBlockEntity));
        this.beeHouseBlockEntity = beeHouseBlockEntity;
        this.backgroundHeight = 190;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw("Bee House", 8, 6, 4210752);
        // this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int texture = this.minecraft.textureManager.getTextureId("assets/baiotechbees/gui/alveary.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(texture);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        if(beeHouseBlockEntity.lifespan > 0) {
            int height = (int)(((float)(beeHouseBlockEntity.lifespan - beeHouseBlockEntity.beeTicks) / beeHouseBlockEntity.lifespan) * 46);
            //int height = apiaryBlockEntity.breedTime * 46 / apiaryBlockEntity.maxBreedTime;
            float ratio = (float)height / 46.0001f;
            this.drawTexture(x + 20, y + 37 + (46 - height), 176 + 4 * (int)(5*ratio), 0 + (46 - height), 4, height);
        }
    }
}
