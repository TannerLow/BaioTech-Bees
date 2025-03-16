package io.github.TannerLow.baiotechbees.items;

import io.github.TannerLow.baiotechbees.items.colors.BeeColors;
import io.github.TannerLow.baiotechbees.items.util.Breed;
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
import net.modificationstation.stationapi.api.util.Formatting;
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

        list.add(createBee(Breed.Forest.id()));
        list.add(createBee(Breed.Meadows.id()));
        list.add(createBee(Breed.Common.id()));
        list.add(createBee(Breed.Cultivated.id()));
        list.add(createBee(Breed.Noble.id()));
        list.add(createBee(Breed.Majestic.id()));
        list.add(createBee(Breed.Diligent.id()));
        list.add(createBee(Breed.Unweary.id()));

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

    public static Genome createGenome(Breed breed) {
        return createGenome(breed.id(), breed.fertility(), breed.lifespan(), breed.speed());
    }

    // For creation of genomes for which a Breed enum doesn't exist
    public static Genome createGenome(short breedId, byte fertility, int lifespan, double speed) {
        Genome genome = new Genome();
        genome.genes.add(new Gene("Breed", Gene.Type.SHORT, breedId, breedId));
        genome.genes.add(new Gene("Fertility", Gene.Type.BYTE, fertility, fertility));
        genome.genes.add(new Gene("Lifespan", Gene.Type.INT, lifespan, lifespan));
        genome.genes.add(new Gene("Speed", Gene.Type.DOUBLE, speed, speed));
        return genome;
    }

    public static void registerBreed(Breed breed) {
        registerBreed(breed.name(), breed.id());
    }

    // For registration of breeds for which a Breed enum doesn't exist
    public static void registerBreed(String breedName, short breedId) {
        names.add(breedName);
        breedIds.put(breedName, breedId);
    }

    public static String getColoredBreedText(Gene breedGene) {
        Formatting color1 = BeeColors.breedTextColor.get((short)breedGene.value1);
        Formatting color2 = BeeColors.breedTextColor.get((short)breedGene.value2);
        return color1 + names.get((short)breedGene.value1) + Formatting.WHITE +
               " - " +
               color2 + names.get((short)breedGene.value2);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        Genome genome = new Genome();
        genome.readNbt(stack.getStationNbt());
        Gene breeds = genome.getGene("Breed");
        return new String[]{originalTooltip, getColoredBreedText(breeds)};
    }

    static {
        BASE_GENOMES.put(Breed.Forest.id(), createGenome(Breed.Forest));
        BASE_GENOMES.put(Breed.Meadows.id(), createGenome(Breed.Meadows));
        BASE_GENOMES.put(Breed.Common.id(), createGenome(Breed.Common));
        BASE_GENOMES.put(Breed.Cultivated.id(), createGenome(Breed.Cultivated));
        BASE_GENOMES.put(Breed.Noble.id(), createGenome(Breed.Noble));
        BASE_GENOMES.put(Breed.Diligent.id(), createGenome(Breed.Diligent));
        BASE_GENOMES.put(Breed.Majestic.id(), createGenome(Breed.Majestic));
        BASE_GENOMES.put(Breed.Unweary.id(), createGenome(Breed.Unweary));

        registerBreed(Breed.Forest);
        registerBreed(Breed.Meadows);
        registerBreed(Breed.Common);
        registerBreed(Breed.Cultivated);
        registerBreed(Breed.Noble);
        registerBreed(Breed.Diligent);
        registerBreed(Breed.Majestic);
        registerBreed(Breed.Unweary);
    }
}
