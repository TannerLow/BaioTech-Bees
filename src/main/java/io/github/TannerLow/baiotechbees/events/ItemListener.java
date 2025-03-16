package io.github.TannerLow.baiotechbees.events;

import io.github.TannerLow.baiotechbees.items.BeeItem;
import io.github.TannerLow.baiotechbees.items.QueenBeeItem;
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
    public static BeeItem DRONE_BEE;
    public static BeeItem PRINCESS_BEE;
    public static QueenBeeItem QUEEN_BEE;

    public static Item HONEY_COMB;
    public static Item DRIPPING_COMB;
    public static Item STRINGY_COMB;

    public static Item HONEY_DROP;
    public static Item HONEYDEW;
    public static Item PROPOLIS;

    // An event listener listening to the BlockRegistryEvent
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        DRONE_BEE = (BeeItem)new BeeItem(NAMESPACE.id("drone_bee")).setTranslationKey(NAMESPACE, "drone_bee");
        PRINCESS_BEE = (BeeItem)new BeeItem(NAMESPACE.id("princess_bee"), true).setTranslationKey(NAMESPACE, "princess_bee");
        QUEEN_BEE = (QueenBeeItem)new QueenBeeItem(NAMESPACE.id("queen_bee")).setTranslationKey(NAMESPACE, "queen_bee");

        HONEY_COMB = new TemplateItem(NAMESPACE.id("honey_comb")).setTranslationKey(NAMESPACE, "honey_comb");
        DRIPPING_COMB = new TemplateItem(NAMESPACE.id("dripping_comb")).setTranslationKey(NAMESPACE, "dripping_comb");
        STRINGY_COMB = new TemplateItem(NAMESPACE.id("stringy_comb")).setTranslationKey(NAMESPACE, "stringy_comb");

        HONEY_DROP = new TemplateItem(NAMESPACE.id("honey_drop")).setTranslationKey(NAMESPACE, "honey_drop");
        HONEYDEW = new TemplateItem(NAMESPACE.id("honeydew")).setTranslationKey(NAMESPACE, "honeydew");
        PROPOLIS = new TemplateItem(NAMESPACE.id("propolis")).setTranslationKey(NAMESPACE, "propolis");
    }
}
