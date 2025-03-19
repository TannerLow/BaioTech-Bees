package io.github.TannerLow.baiotechbees.items;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BeeFrameItem extends TemplateItem {
    public final double productionModifier;


    public BeeFrameItem(Identifier identifier, double productionModifier, int durability) {
        super(identifier);
        this.productionModifier = productionModifier;
        this.setMaxDamage(durability);
    }
}
