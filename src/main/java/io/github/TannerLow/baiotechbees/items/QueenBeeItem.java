package io.github.TannerLow.baiotechbees.items;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import io.github.TannerLow.baiotechbees.items.util.Gene;
import io.github.TannerLow.baiotechbees.items.util.Genome;
import lombok.NonNull;
import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class QueenBeeItem extends BeeItem {
    public static final Random RNG = new Random();

    public QueenBeeItem(Identifier identifier) {
        super(identifier);
        setMaxCount(1);
    }

    @SubItemProvider
    public List<ItemStack> getSubItems() {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(createBee(0));
        list.add(createBee(1));
        list.add(createBee(2));

        return list;
    }

    @Override
    public void addNbtTags(ItemStack bee) {
        Short beeBreedId = (short)bee.getDamage();

        ItemStack drone = ItemListener.DRONE_BEE.createBee(beeBreedId);
        ItemStack princess = ItemListener.PRINCESS_BEE.createBee(beeBreedId);
        ItemStack queen = fromMating(princess, drone);

        bee.writeNbt(queen.getStationNbt());
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        user.sendMessage("BreedTime: " + stack.getStationNbt().getInt("BreedTime"));
        user.sendMessage("MaxBreedTime: " + stack.getStationNbt().getInt("MaxBreedTime"));
        System.out.println("--- Princess Genome ---");
        Genome.print(stack.getStationNbt().getCompound("PrincessGenome"));
        System.out.println("--- Drone Genome ---");
        Genome.print(stack.getStationNbt().getCompound("DroneGenome"));
        return stack;
    }

    public ItemStack fromMating(@NonNull ItemStack princessStack, @NonNull ItemStack droneStack) {
        Genome princessGenome = new Genome();
        Genome droneGenome = new Genome();
        princessGenome.readNbt(princessStack.getStationNbt());
        droneGenome.readNbt(droneStack.getStationNbt());

        if(princessGenome.genes.size() != droneGenome.genes.size()) {
            return null;
        }

        ItemStack queenStack = new ItemStack(ItemListener.QUEEN_BEE, 1, princessStack.getDamage());

        NbtCompound nbt = queenStack.getStationNbt();

        NbtCompound princessGenomeNbt = new NbtCompound();
        princessGenome.writeNbt(princessGenomeNbt);
        nbt.put("PrincessGenome", princessGenomeNbt);

        NbtCompound droneGenomeNbt = new NbtCompound();
        droneGenome.writeNbt(droneGenomeNbt);
        nbt.put("DroneGenome", droneGenomeNbt);

        nbt.putInt("BreedTime", 200);
        nbt.putInt("MaxBreedTime", 200);

        return queenStack;
    }

    public static Queue<ItemStack> createOffspring(ItemStack queen) {
        Queue offspring = new LinkedList<ItemStack>();

        Genome parentPrincessGenome = new Genome();
        parentPrincessGenome.readNbt(queen.getStationNbt().getCompound("PrincessGenome"));
        Genome parentDroneGenome = new Genome();
        parentDroneGenome.readNbt(queen.getStationNbt().getCompound("DroneGenome"));

        // Create princess
        ItemStack princess = new ItemStack(ItemListener.PRINCESS_BEE);
        Genome newPrincessGenome = Genome.crossGenomes(parentPrincessGenome, parentDroneGenome);
        newPrincessGenome.writeNbt(princess.getStationNbt());
        //Genome.print(princess.getStationNbt());
        princess.setDamage((short)newPrincessGenome.getGene("Breed").value1);
        offspring.add(princess);

        // Create drones
        Gene fertilityGene = parentPrincessGenome.getGene("Fertility");
        byte offspringCount = RNG.nextBoolean() ? (byte)fertilityGene.value1 : (byte)fertilityGene.value2;

        for(int i = 0; i < offspringCount; i++) {
            ItemStack drone = new ItemStack(ItemListener.DRONE_BEE);
            Genome newDroneGenome = Genome.crossGenomes(parentPrincessGenome, parentDroneGenome);
            newDroneGenome.writeNbt(drone.getStationNbt());
            //Genome.print(drone.getStationNbt());
            drone.setDamage((short)newDroneGenome.getGene("Breed").value1);
            offspring.add(drone);
        }

        return offspring;
    }
}
