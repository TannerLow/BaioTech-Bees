package io.github.TannerLow.baiotechbees.items.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Mutation {
    public short breed1;
    public short breed2;
    public short mutationBreed;
    public int chance;

    public boolean shouldOccur(int roll) {
        return roll < chance;
    }
}
