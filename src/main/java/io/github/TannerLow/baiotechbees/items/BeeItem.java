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
    public final boolean isPrincess;

    public static String[] names = {
        "forest", "meadows"
    };

//    public static Map idToString = new HashMap();
//    public static Map stringToId = new HashMap();

    public BeeItem(Identifier identifier) {
        super(identifier);
        this.isPrincess = false;
        setHasSubtypes(true);
    }

    public BeeItem(Identifier identifier, boolean isPrincess) {
        super(identifier);
        this.isPrincess = isPrincess;
        setHasSubtypes(true);
        if(isPrincess) {
            setMaxCount(1);
        }
    }

    @SubItemProvider
    public List<ItemStack> getSubItems() {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(createBee(0));
        list.add(createBee(1));

        return list;
    }

    public ItemStack createBee(int beeId) {
        ItemStack bee = new ItemStack(this, 1, beeId);
        addNbtTags(bee);
        return bee;
    }

    public void addNbtTags(ItemStack bee) {
        NbtCompound nbt = bee.getStationNbt();
        nbt.putShort("Breed1", (short)bee.getDamage());
        nbt.putShort("Breed2", (short)bee.getDamage());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + names[stack.getDamage()];
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if(stack.getStationNbt().contains("Breed1") && stack.getStationNbt().contains("Breed2")) {
            user.sendMessage("Breed #1: " + names[stack.getStationNbt().getShort("Breed1")]);
            user.sendMessage("Breed #2: " + names[stack.getStationNbt().getShort("Breed2")]);
        }
        return super.use(stack, world, user);
    }

}
