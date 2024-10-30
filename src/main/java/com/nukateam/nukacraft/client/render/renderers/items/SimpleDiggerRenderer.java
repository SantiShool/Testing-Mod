package com.nukateam.nukacraft.client.render.renderers.items;

import com.nukateam.nukacraft.client.models.items.SimpleDiggerModel;
import com.nukateam.nukacraft.common.foundation.items.misc.SimpleDiggerItem;
import com.nukateam.nukacraft.common.foundation.items.misc.SimpleMeleeWeapon;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class SimpleDiggerRenderer extends GeoItemRenderer<SimpleDiggerItem> {

    public SimpleDiggerRenderer() {
        super(new SimpleDiggerModel());
    }
}
