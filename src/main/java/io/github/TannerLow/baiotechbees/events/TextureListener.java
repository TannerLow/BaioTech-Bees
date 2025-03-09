package io.github.TannerLow.baiotechbees.events;

import com.jcraft.jorbis.Block;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class TextureListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.DRONE_BEE.setTexture(NAMESPACE.id("item/drone_bee"));
        ItemListener.PRINCESS_BEE.setTexture(NAMESPACE.id("item/princess_bee"));
        ItemListener.QUEEN_BEE.setTexture(NAMESPACE.id("item/queen_bee"));
    }

//    @EventListener
//    public void registerTextureColors(ItemColorsRegisterEvent event) {
//        for (SpawnEggItem item : SpawnEggs.spawnEggs) {
//            event.itemColors.register((itemInstance, layer) -> {
//                // Im aware this could error if an egg doesnt have colors registered, but since its called per frame checking harms the performance
//                return ColorizationHandler.eggColor.get(item.spawnedEntity)[layer];
//            }, item);
//        }
//    }
}
