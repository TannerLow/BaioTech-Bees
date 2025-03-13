package io.github.TannerLow.baiotechbees.items;

import io.github.TannerLow.baiotechbees.items.util.Gene;
import io.github.TannerLow.baiotechbees.items.util.Genome;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeItem extends TemplateItem {
    public final boolean isPrincess;

    public static String[] names = {
        "forest", "meadows", "common"
    };
    public static final Map<Integer, Genome> BASE_GENOMES = new HashMap();

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
        list.add(createBee(2));

        return list;
    }

    public ItemStack createBee(int beeId) {
        ItemStack bee = new ItemStack(this, 1, beeId);
        addNbtTags(bee);
        return bee;
    }

    public void addNbtTags(ItemStack bee) {
        NbtCompound nbt = bee.getStationNbt();

        Short beeBreedId = (short)bee.getDamage();
        System.out.println("copy of genom for bee id: " + beeBreedId);
        Genome genome = BASE_GENOMES.get(beeBreedId.intValue());
        genome = genome.copy();

        genome.writeNbt(nbt);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + names[stack.getDamage()];
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        Genome.print(stack.getStationNbt());
        return super.use(stack, world, user);
    }

    public static Genome createGenome(short breedId, byte fertility) {
        Genome genome = new Genome();
        genome.genes.add(new Gene("Breed", Gene.Type.SHORT, breedId, breedId));
        genome.genes.add(new Gene("Fertility", Gene.Type.BYTE, fertility, fertility));
        return genome;
    }

    static {
        BASE_GENOMES.put(0, createGenome((short) 0, (byte) 2));
        BASE_GENOMES.put(1, createGenome((short) 1, (byte) 2));
        BASE_GENOMES.put(2, createGenome((short) 2, (byte) 2));
    }
}
