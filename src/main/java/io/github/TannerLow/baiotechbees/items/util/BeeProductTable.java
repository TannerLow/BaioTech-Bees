package io.github.TannerLow.baiotechbees.items.util;

import io.github.TannerLow.baiotechbees.events.ItemListener;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeProductTable {
    public static Map<Short, List<BeeProduct>> productTable = new HashMap();

    static {
        List<BeeProduct> products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.HONEY_COMB), 30));
        productTable.put(Breed.Forest.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.HONEY_COMB), 30));
        productTable.put(Breed.Meadows.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.HONEY_COMB), 35));
        productTable.put(Breed.Common.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.HONEY_COMB), 40));
        productTable.put(Breed.Cultivated.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.DRIPPING_COMB), 20));
        productTable.put(Breed.Noble.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.DRIPPING_COMB), 30));
        productTable.put(Breed.Majestic.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.STRINGY_COMB), 20));
        productTable.put(Breed.Diligent.id(), products);

        products = new ArrayList<>();
        products.add(new BeeProduct(new ItemStack(ItemListener.STRINGY_COMB), 30));
        productTable.put(Breed.Unweary.id(), products);
    }
}
