package io.github.TannerLow.baiotechbees.events;

import io.github.TannerLow.baiotechbees.blocks.ApiaryBlock;
import io.github.TannerLow.baiotechbees.blocks.BeeHouseBlock;
import io.github.TannerLow.baiotechbees.blocks.entities.ApiaryBlockEntity;
import io.github.TannerLow.baiotechbees.blocks.entities.BeeHouseBlockEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class BlockListener {
    // Namespace Utility Field
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    // A static object holding our bock
    public static Block APIARY;
    public static Block BEE_HOUSE;

    // An event listener listening to the BlockRegistryEvent
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        BlockEntity.create(ApiaryBlockEntity.class, "Apiary");
        BlockEntity.create(BeeHouseBlockEntity.class, "BeeHouse");
        APIARY = new ApiaryBlock(NAMESPACE.id("apiary_block")).setTranslationKey(NAMESPACE, "apiary_block");
        BEE_HOUSE = new BeeHouseBlock(NAMESPACE.id("bee_house_block")).setTranslationKey(NAMESPACE, "bee_house_block");
    }
}
