package io.github.TannerLow.baiotechbees.items.colors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.BakedModelRendererImpl;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BeeColors {
    public static final int BASIC_BODY1 = 0xFEDB16;
    public static final int BASIC_BODY2 = 0xFFFFFF;
    public static final int FOREST_OUTLINE = 0x19CFEB;
    public static final int MEADOWS_OUTLINE = 0xEF131E;
    public static final int COMMON_OUTLINE = 0xB1B1B1;
    public static final int CULTIVATED_OUTLINE = 0x5734EB;
    public static final int NOBLE_OUTLINE = 0xEB9919;
    public static final int DILIGENT_OUTLINE = 0xC119EB;
    public static final int MAJESTIC_OUTLINE = 0x7F0000;
    public static final int UNWEARY_OUTLINE = 0x19EB5A;

    public static Map<Short, Formatting> breedTextColor = new HashMap();

    static {
        breedTextColor.put((short)0, Formatting.AQUA);
        breedTextColor.put((short)1, Formatting.RED);
        breedTextColor.put((short)2, Formatting.GRAY);
        breedTextColor.put((short)3, Formatting.BLUE);
        breedTextColor.put((short)4, Formatting.GOLD);
        breedTextColor.put((short)5, Formatting.LIGHT_PURPLE);
        breedTextColor.put((short)6, Formatting.DARK_RED);
        breedTextColor.put((short)7, Formatting.GREEN);
    }
}
