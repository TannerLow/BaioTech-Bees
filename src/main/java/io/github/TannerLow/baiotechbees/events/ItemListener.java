package io.github.TannerLow.baiotechbees.events;

import io.github.TannerLow.baiotechbees.items.BeeItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

public class ItemListener {
    // Namespace Utility Field
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    // A static object holding our bock
    public static Item DRONE_BEE;
    public static Item PRINCESS_BEE;
    public static Item QUEEN_BEE;

    // An event listener listening to the BlockRegistryEvent
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        DRONE_BEE = new BeeItem(NAMESPACE.id("drone_bee")).setTranslationKey(NAMESPACE, "drone_bee");
        PRINCESS_BEE = new BeeItem(NAMESPACE.id("princess_bee")).setTranslationKey(NAMESPACE, "princess_bee");
        QUEEN_BEE = new BeeItem(NAMESPACE.id("queen_bee")).setTranslationKey(NAMESPACE, "queen_bee");
    }
}
