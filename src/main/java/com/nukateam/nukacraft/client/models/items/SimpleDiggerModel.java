package com.nukateam.nukacraft.client.models.items;

import com.nukateam.nukacraft.common.foundation.items.misc.SimpleDiggerItem;
import com.nukateam.nukacraft.common.foundation.items.misc.SimpleMeleeWeapon;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;

import static com.nukateam.nukacraft.common.data.utils.Resources.nukaResource;

public class SimpleDiggerModel extends GeoModel<SimpleDiggerItem> {
    @Override
    public ResourceLocation getModelResource(SimpleDiggerItem model) {
        return nukaResource("geo/items/" + model.getName() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SimpleDiggerItem model) {
        return nukaResource("textures/item/melee/" + model.getName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(SimpleDiggerItem model) {
        return null;
    }
}
