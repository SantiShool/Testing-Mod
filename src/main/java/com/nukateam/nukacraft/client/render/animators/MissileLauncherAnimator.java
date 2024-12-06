package com.nukateam.nukacraft.client.render.animators;

import com.nukateam.geo.interfaces.IResourceProvider;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.geo.render.ItemAnimator;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.nukateam.nukacraft.common.registery.items.WeaponAttachments;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.nukateam.example.common.util.constants.Animations.RELOAD;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;

@OnlyIn(Dist.CLIENT)
public class MissileLauncherAnimator extends GunAnimator implements IResourceProvider {

    public static final String RELOAD_MAGAZINE = "reload_magazine";
    public static final String RELOAD_DRUM = "reload_drum";

    public MissileLauncherAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
//        GunModifierHelper
        var stack = getStack();
        var magazine = Gun.getAttachmentItem(AttachmentType.BARREL, stack);
        var animation = RawAnimation.begin();

        if(magazine.getItem() == WeaponAttachments.MISSILE_MAGAZINE.get()){
            animation.then(RELOAD_MAGAZINE, PLAY_ONCE);
            animationHelper.syncAnimation(event, RELOAD_MAGAZINE, GunModifierHelper.getReloadTime(getStack()));
        }
        else if(magazine.getItem() == WeaponAttachments.MISSILE_DRUM.get()){
            animation.then(RELOAD_DRUM, PLAY_ONCE);
            animationHelper.syncAnimation(event, RELOAD_DRUM, GunModifierHelper.getReloadTime(getStack()));
        }
        else {
            return super.getReloadingAnimation(event);
        }

        return animation;
    }
}

