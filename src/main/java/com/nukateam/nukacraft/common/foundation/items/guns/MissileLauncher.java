package com.nukateam.nukacraft.common.foundation.items.guns;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.nukacraft.client.render.animators.MissileLauncherAnimator;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.function.BiFunction;

public class MissileLauncher extends NukaGunItem {
    public MissileLauncher(Properties properties) {
        super(properties);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return MissileLauncherAnimator::new;
    }
}
