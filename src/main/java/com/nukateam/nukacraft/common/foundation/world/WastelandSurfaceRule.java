package com.nukateam.nukacraft.common.foundation.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class WastelandSurfaceRule {
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
    
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);

    private static final SurfaceRules.RuleSource LAND_TOP      = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource LAND_FILL     = makeStateRule(Blocks.DIRT);

    private static final SurfaceRules.RuleSource OCEAN_FLOOR   = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource OCEAN_UNDER   = makeStateRule(Blocks.STONE);

    private static final SurfaceRules.RuleSource RIVER_FLOOR   = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource RIVER_UNDER   = makeStateRule(Blocks.STONE);

    public static SurfaceRules.RuleSource fallout() {
        return falloutWorldLike(true, false, true);
    }

    public static SurfaceRules.RuleSource falloutWorldLike(boolean roofBedrock, boolean ceilingBedrock, boolean floorBedrock) {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();

        if (ceilingBedrock) {
            builder.add(SurfaceRules.ifTrue(
                    SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
                    BEDROCK));
        }
        if (floorBedrock) {
            builder.add(SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                    BEDROCK));
        }

        builder.add(SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                DEEPSLATE));

        SurfaceRules.RuleSource poisonValleySurface = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.POISON_VALLEY),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, LAND_TOP),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, LAND_FILL)
                )
        );

        SurfaceRules.RuleSource irradiatedOceanSurface = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.IRRADIATED_OCEAN),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, OCEAN_FLOOR),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, OCEAN_UNDER)
                )
        );

        SurfaceRules.RuleSource irradiatedRiverSurface = SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.IRRADIATED_RIVER),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, RIVER_FLOOR),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, RIVER_UNDER)
                )
        );

        SurfaceRules.RuleSource applyBiomes = SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                        irradiatedOceanSurface,
                        irradiatedRiverSurface,
                        poisonValleySurface
                )
        );

        builder.add(applyBiomes);

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }
}
