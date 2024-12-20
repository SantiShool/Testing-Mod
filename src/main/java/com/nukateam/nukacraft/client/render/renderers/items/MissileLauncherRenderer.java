package com.nukateam.nukacraft.client.render.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.event.InputEvents;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.nukacraft.client.models.items.TechnicGunModel;
import com.nukateam.nukacraft.client.render.animators.MissileLauncherAnimator;
import com.nukateam.nukacraft.client.render.animators.TechnicAnimator;
import mod.azure.azurelib.cache.object.GeoBone;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class MissileLauncherRenderer extends DynamicGunRenderer<MissileLauncherAnimator> {
    int currentAmmo = 0;

    public MissileLauncherRenderer() {
        super(new GeoGunModel<>());
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, int packedLight) {
        currentAmmo = Gun.getAmmo(stack);
        poseStack.pushPose();
        poseStack.translate(0, /*InputEvents.Y / 16D*/ - 6 / 16D,0);
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, MissileLauncherAnimator animatable, GeoBone bone,
                                  RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                                  boolean isReRender, float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {
        var bonesToHide = new ArrayList<String>();

        switch (currentAmmo){
            case 3 -> {
                bonesToHide.add("missile1");
            }
            case 2 -> {
                bonesToHide.addAll(Arrays.asList("missile1", "missile2"));
            }
            case 1 -> {
                bonesToHide.addAll(Arrays.asList("missile1", "missile2", "missile3"));
            }
            case 0 -> {
                bonesToHide.addAll(Arrays.asList("missile1", "missile2", "missile3", "missile4"));
            }
        }

        for (var name : bonesToHide) {
            if (bone.getName().equals(name)) {
                bone.setHidden(true);
            }
        }

//        hide(bone, bonesToHide);

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
                partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private static void hide(GeoBone bone, ArrayList names) {
        for (var name : names) {
            if (bone.getName().equals(name)) {
                bone.setHidden(true);
            }
        }
    }
}
