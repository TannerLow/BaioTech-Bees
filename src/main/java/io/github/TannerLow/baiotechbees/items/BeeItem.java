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
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeItem extends TemplateItem implements CustomTooltipProvider {
    public final boolean isPrincess;

    public static List<String> names = new ArrayList();
    public static Map<String, Short> breedIds = new HashMap();
    public static final Map<Short, Genome> BASE_GENOMES = new HashMap();

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
        Genome genome = BASE_GENOMES.get(beeBreedId);
        genome = genome.copy();

        genome.writeNbt(nbt);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + names.get(stack.getDamage());
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

    public static void registerBreed(String breedName, short breedId) {
        names.add(breedName);
        breedIds.put(breedName, breedId);
    }

    static {
        BASE_GENOMES.put((short)0, createGenome((short) 0, (byte) 2));
        BASE_GENOMES.put((short)1, createGenome((short) 1, (byte) 2));
        BASE_GENOMES.put((short)2, createGenome((short) 2, (byte) 2));

        registerBreed("Forest", (short)0);
        registerBreed("Meadows", (short)1);
        registerBreed("Common", (short)2);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        Genome genome = new Genome();
        genome.readNbt(stack.getStationNbt());
        Gene breeds = genome.getGene("Breed");
        return new String[]{originalTooltip, names.get((short)breeds.value1) + "-" + names.get((short)breeds.value2)};
    }
}
