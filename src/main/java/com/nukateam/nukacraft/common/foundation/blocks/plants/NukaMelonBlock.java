package com.nukateam.nukacraft.common.foundation.blocks.plants;

import com.nukateam.nukacraft.common.registery.ModBlocks;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.StemBlock;

public class NukaMelonBlock extends MelonBlock {
    public NukaMelonBlock(Properties p_54829_) {
        super(p_54829_);
    }

    @Override
    public StemBlock getStem() {
        return (StemBlock) ModBlocks.NUKAMELON_STEM.get();
    }
    @Override
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)ModBlocks.ATTACHED_NUKAMELON_STEM.get();
    }
}
