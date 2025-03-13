package io.github.TannerLow.baiotechbees.items.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Genome {
    public static final Random RNG = new Random();

    public List<Gene> genes = new ArrayList();

    public void writeNbt(NbtCompound nbt){
        NbtList genesNbt = new NbtList();

        for(Gene gene : genes) {
            NbtCompound geneNbt = new NbtCompound();
            gene.writeNbt(geneNbt);
            genesNbt.add(geneNbt);
        }

        nbt.put("Genome", genesNbt);
    }

    public void readNbt(NbtCompound nbt){
        genes.clear();

        NbtList genesNbt = nbt.getList("Genome");

        for(int i = 0; i < genesNbt.size(); i++) {
            NbtCompound geneNbt = (NbtCompound) genesNbt.get(i);
            Gene gene = new Gene(geneNbt);
            genes.add(gene);
        }
    }

    public Gene getGene(String geneName) {
        for(Gene gene : genes) {
            if(gene.name.equals(geneName)) {
                return gene;
            }
        }
        return null;
    }

    public Genome copy() {
        Genome copied = new Genome();
        for (Gene gene : genes) {
            copied.genes.add(gene.copy());
        }
        return copied;
    }

    public static Genome crossGenomes(Genome genomeA, Genome genomeB) {
        Genome result = new Genome();
        for(Gene geneA : genomeA.genes) {
            for(Gene geneB : genomeB.genes) {
                if(geneA.name.equals(geneB.name)) {
                    boolean useFirstInA = RNG.nextBoolean();
                    boolean useFirstInB = RNG.nextBoolean();
                    Object fromA = useFirstInA ? geneA.value1 : geneA.value2;
                    Object fromB = useFirstInB ? geneB.value1 : geneB.value2;
                    Gene gene = new Gene(geneA.name, geneA.type, fromA, fromB);
                    result.genes.add(gene);
                }
            }
        }
        return result;
    }

    public static void print(NbtCompound nbt) {
        System.out.println("Genome:");
        NbtList genes = nbt.getList("Genome");
        for(int i = 0; i < genes.size(); i++) {
            Gene.print(((NbtCompound)genes.get(i)));
        }
    }
}
