package com.nukateam.nukacraft.client.render.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.event.InputEvents;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.nukacraft.client.models.items.TechnicGunModel;
import com.nukateam.nukacraft.client.render.animators.MissileLauncherAnimator;
import com.nukateam.nukacraft.client.render.animators.TechnicAnimator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MissileLauncherRenderer extends DynamicGunRenderer<MissileLauncherAnimator> {
    public MissileLauncherRenderer() {
        super(new GeoGunModel<>());
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, /*InputEvents.Y / 16D*/ - 6 / 16D,0);
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }
}
