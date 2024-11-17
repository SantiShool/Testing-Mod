package com.nukateam.nukacraft.common.foundation.items.misc;

import com.nukateam.example.common.util.interfaces.IMeleeWeapon;
import com.nukateam.example.common.util.utils.ResourceUtils;
import com.nukateam.nukacraft.client.render.renderers.items.SimpleDiggerRenderer;
import com.nukateam.nukacraft.client.render.renderers.items.SimpleMeleeRenderer;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleDiggerItem extends AxeItem implements GeoItem, IMeleeWeapon {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Lazy<String> name = Lazy.of(() -> ResourceUtils.getResourceName(ForgeRegistries.ITEMS.getKey(this)));
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public SimpleDiggerItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    public String getName() {
        return name.get();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private SimpleDiggerRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new SimpleDiggerRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
