package io.github.TannerLow.baiotechbees.events;

import io.github.TannerLow.baiotechbees.items.colors.BeeColors;
import io.github.TannerLow.baiotechbees.items.colors.BeeColorProvider;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

public class ColorListener {
    @EventListener
    public static void registerItemColors(ItemColorsRegisterEvent event) {
        event.itemColors.register(
                new BeeColorProvider(BeeColors.BASIC_BODY1, BeeColors.BASIC_BODY2),
                ItemListener.DRONE_BEE
        );
        event.itemColors.register(
                new BeeColorProvider(BeeColors.BASIC_BODY1, BeeColors.BASIC_BODY2),
                ItemListener.PRINCESS_BEE
        );
        event.itemColors.register(
                new BeeColorProvider(BeeColors.BASIC_BODY1, BeeColors.BASIC_BODY2),
                ItemListener.QUEEN_BEE
        );
    }
}
