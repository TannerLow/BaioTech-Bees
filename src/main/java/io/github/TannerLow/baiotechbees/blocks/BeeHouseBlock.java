package io.github.TannerLow.baiotechbees.blocks;

import io.github.TannerLow.baiotechbees.blocks.entities.BeeHouseBlockEntity;
import io.github.TannerLow.baiotechbees.guis.screens.BeeHouseScreen;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class BeeHouseBlock extends BeeBreedingBlock {
    public BeeHouseBlock(Identifier identifier) {
        super(identifier);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new BeeHouseBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.isRemote) {
            return true;
        } else {
            BeeHouseBlockEntity entity = (BeeHouseBlockEntity) world.getBlockEntity(x, y, z);
            ((ClientPlayerEntity)player).minecraft.setScreen(new BeeHouseScreen(player.inventory, entity));
            return true;
        }
    }
}
