package io.github.TannerLow.baiotechbees.items.colors;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider;

public class BeeColorProvider implements ItemColorProvider {
    public static final int WHITE = 0xFFFFFF;
    public final int BODY1;
    public final int BODY2;

    public BeeColorProvider(int body1, int body2) {
        super();
        this.BODY1 = body1;
        this.BODY2 = body2;
    }

    @Override
    public int getColor(ItemStack stack, int index) {
        if (index == 0) {
            NbtCompound nbtCompound = stack.getStationNbt();
            if(nbtCompound != null) {
                //short id = nbtCompound.getShort("BeeID");
                int id = stack.getDamage();
                switch(id){
                    case 0: return BeeColors.FOREST_OUTLINE;
                    case 1: return BeeColors.MEADOWS_OUTLINE;
                    case 2: return BeeColors.COMMON_OUTLINE;
                    case 3: return BeeColors.CULTIVATED_OUTLINE;
                    case 4: return BeeColors.NOBLE_OUTLINE;
                    case 5: return BeeColors.DILIGENT_OUTLINE;
                    case 6: return BeeColors.MAJESTIC_OUTLINE;
                    case 7: return BeeColors.UNWEARY_OUTLINE;
                }
            }
        } else if (index == 1) {
            return BODY1;
        } else if (index == 2) {
            return BODY2;
        }
        return WHITE;
    }
}
