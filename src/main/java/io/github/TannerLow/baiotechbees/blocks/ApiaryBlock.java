package io.github.TannerLow.baiotechbees.blocks;


import io.github.TannerLow.baiotechbees.blocks.entities.ApiaryBlockEntity;
import io.github.TannerLow.baiotechbees.guis.screens.ApiaryScreen;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class ApiaryBlock extends TemplateBlockWithEntity {
    public static final DirectionProperty FACING = DirectionProperty.of(
            "facing", new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}
    );

    public ApiaryBlock(Identifier identifier) {
        super(identifier, Material.WOOD);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ApiaryBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.isRemote) {
            return true;
        } else {
            ApiaryBlockEntity entity = (ApiaryBlockEntity) world.getBlockEntity(x, y, z);
            ((ClientPlayerEntity)player).minecraft.setScreen(new ApiaryScreen(player.inventory, entity));
            return true;
        }
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        //System.out.println(context.getHorizontalPlayerFacing().getOpposite().name());
        return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
    }
}
