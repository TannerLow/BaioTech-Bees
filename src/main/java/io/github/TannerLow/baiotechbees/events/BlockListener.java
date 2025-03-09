package io.github.TannerLow.baiotechbees.events;

import io.github.TannerLow.baiotechbees.blocks.ApiaryBlock;
import io.github.TannerLow.baiotechbees.blocks.entities.ApiaryBlockEntity;
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

    // An event listener listening to the BlockRegistryEvent
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        BlockEntity.create(ApiaryBlockEntity.class, "Apiary");
        APIARY = new ApiaryBlock(NAMESPACE.id("apiary_block")).setTranslationKey(NAMESPACE, "apiary_block");
    }
}
