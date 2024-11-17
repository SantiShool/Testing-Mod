package com.nukateam.nukacraft.client.render.animators;

import com.nukateam.geo.interfaces.IResourceProvider;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.geo.render.ItemAnimator;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechnicAnimator extends ItemAnimator implements IResourceProvider {
    public TechnicAnimator(ItemDisplayContext transformType) {
        super(transformType);
    }

    public TechnicAnimator(ItemDisplayContext itemDisplayContext, DynamicGeoItemRenderer<TechnicAnimator> technicAnimatorGeoDynamicItemRenderer) {
        super(itemDisplayContext);
    }

    public String getName() {
        return "flamer";
    }

    public String getNamespace() {
        return "nukacraft";
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
}

