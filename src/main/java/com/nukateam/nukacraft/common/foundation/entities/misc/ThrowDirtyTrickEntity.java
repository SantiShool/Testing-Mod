package com.nukateam.nukacraft.common.foundation.entities.misc;

import com.nukateam.ntgl.common.foundation.entity.ThrowableGrenadeEntity;
import com.nukateam.ntgl.common.foundation.entity.ThrowableItemEntity;
import com.nukateam.nukacraft.common.foundation.entities.grenades.GrenadeUtils;
import com.nukateam.nukacraft.common.registery.EntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import static com.nukateam.nukacraft.common.registery.items.ModWeapons.BRAHMIN_DIRTY_TRICK;
import static com.nukateam.nukacraft.common.registery.items.ModWeapons.GRENADE_FIRE;

public class ThrowDirtyTrickEntity extends ThrowableGrenadeEntity {
    public float rotation;
    public float prevRotation;

    public ThrowDirtyTrickEntity(EntityType<? extends ThrowableItemEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public ThrowDirtyTrickEntity(Level world, LivingEntity entity, int timeLeft) {
        super(EntityTypes.DIRTY_TRICK_ENTITY.get(), world, entity);
        this.setShouldBounce(true);
        this.setGravityVelocity(0.05F);
        this.setItem(new ItemStack(BRAHMIN_DIRTY_TRICK.get()));
        this.setMaxLife(timeLeft);
    }

    @Override
    protected void onHit(HitResult result) {
        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.addParticle(ParticleTypes.MYCELIUM,
                this.getX(), this.getY(), this.getZ(),
                1.0D, 0.0D, 0.0D);
        this.remove(Entity.RemovalReason.KILLED);
        onDeath();
    }

//    @Override
//    protected void onHitEntity(EntityHitResult pResult) {
//        Entity target = pResult.getEntity();
//        if (target instanceof LivingEntity entity) {
//            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10, 1, true, true));
//        }
//        this.remove(Entity.RemovalReason.KILLED);
//        super.onHitEntity(pResult);
//    }

    public void tick() {
        super.tick();
        this.prevRotation = this.rotation;
        double speed = this.getDeltaMovement().length();
        if (speed > 0.1) {
            this.rotation = (float)((double)this.rotation + speed * 50.0);
        }

        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.EFFECT, true, this.getX(), this.getY() + 0.25, this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }


    @Override
    public void onDeath(){
        level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.HONEY_BLOCK_PLACE, SoundSource.PLAYERS, 1.0F, -1.0F);
        createPoppyCloud(1.0F);
    }
    private void createPoppyCloud(float radius)
    {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        cloud.setRadius(radius + 1.0F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 2));
        this.level().addFreshEntity(cloud);
    }
}
