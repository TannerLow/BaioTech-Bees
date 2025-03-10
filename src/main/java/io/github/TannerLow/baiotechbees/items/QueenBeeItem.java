package io.github.TannerLow.baiotechbees.items;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import lombok.NonNull;
import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        return list;
    }

    @Override
    public void addNbtTags(ItemStack bee) {
        NbtCompound nbt = bee.getStationNbt();
        nbt.putInt("BreedTime", 100);
        nbt.putInt("MaxBreedTime", 100);
        nbt.putShort("PrincessBreed1", (short)bee.getDamage());
        nbt.putShort("PrincessBreed2", (short)bee.getDamage());
        nbt.putShort("DroneBreed1", (short)bee.getDamage());
        nbt.putShort("DroneBreed2", (short)bee.getDamage());
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        user.sendMessage("BreedTime: " + stack.getStationNbt().getInt("BreedTime"));
        user.sendMessage("MaxBreedTime: " + stack.getStationNbt().getInt("MaxBreedTime"));
        user.sendMessage("Princess Breed #1: " + stack.getStationNbt().getShort("PrincessBreed1"));
        user.sendMessage("Princess Breed #2: " + stack.getStationNbt().getShort("PrincessBreed2"));
        user.sendMessage("Drone Breed #1: " + stack.getStationNbt().getShort("DroneBreed1"));
        user.sendMessage("Drone Breed #2: " + stack.getStationNbt().getShort("DroneBreed2"));
        return super.use(stack, world, user);
    }

    public ItemStack fromMating(@NonNull ItemStack princessStack, @NonNull ItemStack droneStack) {
        NbtCompound princessNbt = princessStack.getStationNbt();
        NbtCompound droneNbt = droneStack.getStationNbt();

        short princessBreed1 = princessNbt.getShort("Breed1");
        short princessBreed2 = princessNbt.getShort("Breed2");
        short droneBreed1 = droneNbt.getShort("Breed1");
        short droneBreed2 = droneNbt.getShort("Breed2");

        ItemStack queenStack = createBee(0);
        NbtCompound queenNbt = queenStack.getStationNbt();
        queenNbt.putShort("PrincessBreed1", princessBreed1);
        queenNbt.putShort("PrincessBreed2", princessBreed2);
        queenNbt.putShort("DroneBreed1", droneBreed1);
        queenNbt.putShort("DroneBreed2", droneBreed2);
        queenStack.setDamage(princessStack.getDamage());

        return queenStack;
    }

    public static List<ItemStack> createOffspring(ItemStack queen) {
        List offspring = new ArrayList<ItemStack>();

        ItemStack princess = new ItemStack(ItemListener.PRINCESS_BEE);
        Map breeds = getOffspringBreed(queen);
        NbtCompound princessNbt = princess.getStationNbt();
        short breed1 = (short)breeds.get("Breed1");
        princessNbt.putShort("Breed1", breed1);
        princessNbt.putShort("Breed2", (short)breeds.get("Breed2"));
        princess.setDamage(breed1);
        offspring.add(princess);

        return offspring;
    }

    public static Map getOffspringBreed(ItemStack queen) {
        boolean fromPrincess = RNG.nextBoolean();
        boolean fromDrone = RNG.nextBoolean();

        NbtCompound nbt = queen.getStationNbt();
        short princessBreed1 = nbt.getShort("PrincessBreed1");
        short princessBreed2 = nbt.getShort("PrincessBreed2");
        short droneBreed1 = nbt.getShort("DroneBreed1");
        short droneBreed2 = nbt.getShort("DroneBreed2");

        Map result = new HashMap<String, Short>();
        result.put("Breed1", fromPrincess? princessBreed1 : princessBreed2);
        result.put("Breed2", fromDrone ? droneBreed1 : droneBreed2);

        return result;
    }
}
