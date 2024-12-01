package com.nukateam.nukacraft.common.foundation.blocks;

import com.nukateam.nukacraft.NukaCraftMod;
import com.nukateam.nukacraft.common.registery.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SlagSludgeBlock extends SnowLayerBlock {
    public static final int MAX_HEIGHT = 4;
    public static final IntegerProperty LAYERS;
    protected static final VoxelShape[] SHAPE_BY_LAYER;
    public static final int HEIGHT_IMPASSABLE = 5;

    public SlagSludgeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LAYERS, 1));
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        switch (pType) {
            case LAND:
                return pState.getValue(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos);
    }



    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return super.canBeReplaced(pState, pUseContext);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if ((pEntity instanceof Player)) {
            this.destroyLayer(pLevel, pState, pPos, 15);
            super.stepOn(pLevel, pPos, pState, pEntity);
        }
    }




    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        int i = pState.getValue(LAYERS);
        if (i == 1) {
            pLevel.destroyBlock(pPos, false);}
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {

    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private void destroyLayer(Level pLevel, BlockState pState, BlockPos pPos, int pChance) {
        pLevel.addParticle(ParticleTypes.LARGE_SMOKE, 1, 1, 1, 2.0D, 2.0D, 2.0D);
        if (!pLevel.isClientSide && pLevel.random.nextInt(pChance) == 0 && pState.is(ModBlocks.ASHDIRT.get())) {
            pLevel.playSound(null, pPos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 0.7F, 0.9F + pLevel.random.nextFloat() * 0.2F);
            int i = pState.getValue(LAYERS);

            pLevel.setBlock(pPos, pState.setValue(LAYERS, i - 1), 2);
            pLevel.gameEvent(GameEvent.BLOCK_DESTROY, pPos, GameEvent.Context.of(pState));
            pLevel.levelEvent(2001, pPos, Block.getId(pState));

        }

    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState $$1 = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if ($$1.is(this)) {
            int $$2 = $$1.getValue(LAYERS);
            return $$1.setValue(LAYERS, Math.min(8, $$2 + 1));
        } else {
            return super.getStateForPlacement(pContext);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{LAYERS});
    }

    static {
        LAYERS = BlockStateProperties.LAYERS;
        SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(), Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};
    }

}
