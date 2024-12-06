package com.nukateam.nukacraft.common.foundation.items.guns;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.nukacraft.client.render.animators.MissileLauncherAnimator;
import com.nukateam.nukacraft.client.render.renderers.items.MissileLauncherRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class MissileLauncher extends NukaGunItem {
    private final Lazy<MissileLauncherRenderer> GUN_RENDERER = Lazy.of(() -> new MissileLauncherRenderer());

    public MissileLauncher(Properties properties) {
        super(properties);
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return GUN_RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return MissileLauncherAnimator::new;
    }
}
