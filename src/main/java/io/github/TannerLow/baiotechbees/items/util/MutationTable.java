package io.github.TannerLow.baiotechbees.items.util;

import com.mojang.datafixers.kinds.IdF;
import io.github.TannerLow.baiotechbees.items.BeeItem;

import java.util.ArrayList;
import java.util.List;

public class MutationTable {
    public static List<Mutation> mutations = new ArrayList();

    public static List<Integer> getPossibleMutations(short breed1, short breed2) {
        List<Integer> indeces = new ArrayList();
        for(int i = 0; i < mutations.size(); i++) {
            Mutation mutation = mutations.get(i);
            if(mutation.breed1 == breed1 && mutation.breed2 == breed2) {
                indeces.add(i);
            }
        }
        return indeces;
    }

    public static void addMutation(String breed1, String breed2, String mutationBreed, int chance) {
        short breedId1 = BeeItem.breedIds.get(breed1);
        short breedId2 = BeeItem.breedIds.get(breed2);
        short breedId3 = BeeItem.breedIds.get(mutationBreed);
        mutations.add(new Mutation(breedId1, breedId2, breedId3, chance));
        mutations.add(new Mutation(breedId2, breedId1, breedId3, chance));
    }

    static {
        addMutation("Forest", "Meadows", "Common", 15);
        addMutation("Common", "Forest", "Cultivated", 12);
        addMutation("Common", "Meadows", "Cultivated", 12);
        addMutation("Cultivated", "Common", "Noble", 10);
        addMutation("Cultivated", "Common", "Diligent", 10);
        addMutation("Noble", "Cultivated", "Majestic", 8);
        addMutation("Diligent", "Cultivated", "Unweary", 8);
    }
}
