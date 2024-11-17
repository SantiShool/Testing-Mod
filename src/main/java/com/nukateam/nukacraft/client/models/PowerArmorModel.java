package com.nukateam.nukacraft.client.models;

import com.jetug.chassis_core.client.model.ChassisModel;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.nukacraft.common.foundation.entities.misc.PowerArmorFrame;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import net.minecraft.world.InteractionHand;

import static com.nukateam.ntgl.common.util.util.GunModifierHelper.canRenderInOffhand;

public class PowerArmorModel extends ChassisModel<PowerArmorFrame> {
    public static void setRotations(CoreGeoBone bone, float rot) {
        bone.setRotX(rot);
        bone.setRotY(rot);
        bone.setRotZ(rot);
    }

    @Override
    public void setCustomAnimations(PowerArmorFrame animatable, long instanceId, AnimationState<PowerArmorFrame> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        var animationProcessor = getAnimationProcessor();
        var rightArm = animationProcessor.getBone("right_arm");
        var leftArm = animationProcessor.getBone("left_arm");
        var head = animationProcessor.getBone("head");

        if (animatable.hasPassenger()){
            var mainHandItem = animatable.getControllingPassenger().getMainHandItem();
            var offHandItem = animatable.getControllingPassenger().getOffhandItem();

            if(mainHandItem.getItem() instanceof GunItem){
                GunModifierHelper
                        .getGripType(mainHandItem)
                        .getHeldAnimation()
                        .applyGeoModelRotation(animatable, rightArm, leftArm, head, InteractionHand.MAIN_HAND);
            }

            if(offHandItem.getItem() instanceof GunItem && canRenderInOffhand(offHandItem) && canRenderInOffhand(mainHandItem)) {
                GunModifierHelper
                        .getGripType(mainHandItem)
                        .getHeldAnimation()
                        .applyGeoModelRotation(animatable, rightArm, leftArm, head, InteractionHand.OFF_HAND);
            }
        } else if (animatable.armsAnimation == null) {
            setRotations(rightArm, 0);
            setRotations(leftArm, 0);
        }
    }
}
