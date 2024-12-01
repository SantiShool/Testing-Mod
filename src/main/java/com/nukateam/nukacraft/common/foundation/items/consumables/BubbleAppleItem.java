package com.nukateam.nukacraft.common.foundation.items.consumables;

import com.nukateam.nukacraft.common.registery.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BubbleAppleItem extends RadItem {
    public BubbleAppleItem(float radiation, Item.Properties properties) {
        super(radiation, properties);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Random rand = new Random();
        int sound_id = rand.nextInt(2);
        int text_id = rand.nextInt(9);

        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 0, false, false));
                entity.sendSystemMessage(Component.translatable("text.nukacraft.blank").append(Component.translatable("text.nukacraft.funnies_" + text_id)));
            }
            switch (sound_id) {
                case 0: level.playSound(player, entity, ModSounds.BUBBLEGUM_USE_0.get(), SoundSource.PLAYERS, 1, 1);

                    break;
                case 1: level.playSound(player, entity, ModSounds.BUBBLEGUM_USE_1.get(), SoundSource.PLAYERS, 1, 1);

                    break;
                default: level.playSound(player, entity, ModSounds.BUBBLEGUM_USE_2.get(), SoundSource.PLAYERS, 1, 1);

            }

        }
        return super.finishUsingItem(stack, level, entity);

    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return 22;
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(item, level, list, flag);
        list.add(Component.translatable("tooltip.nukacraft.jumpboost").append("§9(0:05)"));
    }
}