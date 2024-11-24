package com.nukateam.nukacraft.common.foundation.blocks.fluids;

import com.nukateam.nukacraft.common.registery.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
public class AcidFluidBlock extends LiquidBlock {
    public AcidFluidBlock(RegistryObject<FlowingFluid> flowingFluid, BlockBehaviour.Properties properties) {
        super(flowingFluid, properties);
    }




    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        BlockPos blockpos = pPos.above();
        if (pLevel.getBlockState(blockpos).isAir() && !pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
            if (pRandom.nextInt(5) == 0) {
                double d0 = (double)pPos.getX() + pRandom.nextDouble();
                double d1 = (double)pPos.getY() + 1.0;
                double d2 = (double)pPos.getZ() + pRandom.nextDouble();
                pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.1, 0.0);
                //pLevel.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }

            if (pRandom.nextInt(200) == 0) {
                pLevel.playLocalSound((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.05F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }
        }

    }

    @Override
    public void entityInside(BlockState pState, Level level, BlockPos pPos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            living.hurt(level.damageSources().generic(), 1);
        }
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (shouldSpreadLiquid(pLevel, pPos, pState)) {
            pLevel.scheduleTick(pPos, pState.getFluidState().getType(), getFluid().getTickDelay(pLevel));
        }

    }

    private boolean shouldSpreadLiquid(Level pLevel, BlockPos acidPos, BlockState pState) {
        boolean flag = pLevel.getBlockState(acidPos.below()).is(Blocks.SOUL_SOIL);

//        for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
//            var lavaPos = acidPos.relative(direction.getOpposite());
//            if (pLevel.getFluidState(lavaPos).is(FluidTags.LAVA)) {
//                var block = pLevel.getFluidState(lavaPos).isSource() ? Blocks.CRYING_OBSIDIAN : Blocks.TUFF;
//                pLevel.setBlockAndUpdate(lavaPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel,
//                        lavaPos, lavaPos, block.defaultBlockState()));
////                this.fizz(pLevel, pPos);
//                return false;
//            }
//
//            if (flag && pLevel.getBlockState(lavaPos).is(Blocks.GRASS_BLOCK)) {
//                pLevel.setBlockAndUpdate(acidPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, acidPos, acidPos, ModBlocks.ACID_DIRT.get().defaultBlockState()));
////                this.fizz(pLevel, pPos);
//                return false;
//            }
//        }

        return true;
    }
}
