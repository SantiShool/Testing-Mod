package com.nukateam.nukacraft.common.foundation.items.guns;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.render.renderers.gun.DefaultGunRendererGeo;
import net.minecraftforge.common.util.Lazy;

public class TeslaGun extends NukaGunItem {
    public TeslaGun(Properties properties) {
        super(properties);
    }

    private final Lazy<DefaultGunRendererGeo> GUN_RENDERER = Lazy.of(() -> {
        return new DefaultGunRendererGeo();
    });

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return super.getRenderer();
    }
}
