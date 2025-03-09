package io.github.TannerLow.baiotechbees.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class BeeItem extends TemplateItem {
    public static String[] names = {
        "forest", "meadows"
    };

    public BeeItem(Identifier identifier) {
        super(identifier);
        setHasSubtypes(true);
    }

    @SubItemProvider
    public List<ItemStack> getSubItems() {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(createBee(0));
        list.add(createBee(1));

        ItemStack bee = new ItemStack(this);
        NbtCompound nbt = bee.getStationNbt();
        nbt.putShort("test", (short)5);
        list.add(bee);

        ItemStack bee2 = new ItemStack(this);
        NbtCompound nbt1 = bee2.getStationNbt();
        nbt1.putShort("test2", (short)6);
        nbt1.putShort("test2", (short)6);
        list.add(bee2);

        return list;
    }

    public ItemStack createBee(int beeId) {
        ItemStack bee = new ItemStack(this, 1, beeId);
//        NbtCompound nbt = bee.getStationNbt();
//        nbt.putShort("BeeID", (short)1);
//        bee.writeNbt(nbt.copy());
        return bee;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + names[stack.getDamage()];
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        System.out.print(stack.getStationNbt().contains("test"));
        System.out.print(stack.getStationNbt().contains("test2"));
        System.out.println();
        return true;
    }
}
