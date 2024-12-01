package com.nukateam.nukacraft.common.foundation.items.misc;

import com.nukateam.ntgl.common.foundation.entity.ThrowableGrenadeEntity;
import com.nukateam.ntgl.common.foundation.init.ModSounds;
import com.nukateam.ntgl.common.foundation.item.GrenadeItem;
import com.nukateam.nukacraft.common.data.interfaces.GrenadeFactory;
import com.nukateam.nukacraft.common.foundation.items.guns.BaseGrenadeItem;
import com.nukateam.nukacraft.common.registery.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DirtyTrickItem <T extends ThrowableGrenadeEntity> extends GrenadeItem {
    private final GrenadeFactory<T> grenadeFactory;



    public DirtyTrickItem(Properties properties, int maxCookTime, GrenadeFactory<T> factory) {
        super(properties, maxCookTime);
        this.grenadeFactory = factory;
    }
    public DirtyTrickItem(Properties properties, int maxCookTime, GrenadeFactory<T> factory, @Nullable Supplier<SoundEvent> soundEvent) {
        this(properties, maxCookTime, factory);
    }


    
//    @Override
//    public boolean onDroppedByPlayer(ItemStack item, Player player) {
//        Level level = player.level();
//        if (!level.isClientSide) {
//            level.setBlock(new BlockPos((int)player.getX(), (int)player.getY(), (int)player.getZ()), ModBlocks.ACID_DIRT.get().defaultBlockState(), 1, 1);
//        }
//        if (!level.isClientSide()) {
//            level.setBlock(new BlockPos((int)player.getX(), (int)player.getY(), (int)player.getZ()), ModBlocks.ACID_DIRT.get().defaultBlockState(), 1, 1);
//        }
//        item.shrink(1);
//        return super.onDroppedByPlayer(item, player);
//    }

    @Override
    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
        if (!this.canCook()) return;
        int duration = this.getUseDuration(stack) - count;
        if (duration == 10)
            player.level().playLocalSound(
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.HONEY_BLOCK_BREAK,
                    SoundSource.PLAYERS,
                    1.0F, 1.0F, false);

    }

    @Override
    public ThrowableGrenadeEntity create(Level world, LivingEntity entity, int timeLeft) {
        return grenadeFactory.get(world, entity, timeLeft);
    }
}
