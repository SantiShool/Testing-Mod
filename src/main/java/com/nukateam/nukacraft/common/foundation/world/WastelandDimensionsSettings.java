package com.nukateam.nukacraft.common.foundation.world;

import com.mojang.serialization.DataResult;
import com.nukateam.nukacraft.common.registery.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

import java.util.OptionalLong;

import static com.nukateam.nukacraft.common.data.utils.Resources.nukaResource;

public class WastelandDimensionsSettings {

    public static final ResourceLocation WASTELAND = nukaResource("fallout_wasteland");

    public static final ResourceKey<Level> WASTELAND_DIMENSION = ResourceKey.create(Registries.DIMENSION, WASTELAND);
    public static final ResourceKey<DimensionType> WASTELAND_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, WASTELAND);

    static final NoiseSettings WASTELAND_NOISE_SETTINGS = create(-64, 384, 1, 2);

    public static final ResourceKey<NoiseGeneratorSettings> WASTELAND_NOISE_GEN =
            ResourceKey.create(Registries.NOISE_SETTINGS, nukaResource("fallout_wasteland_noise"));

    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(WASTELAND_NOISE_GEN, falloutNoise(context));
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(WASTELAND_DIM_TYPE, falloutDimensionType());
    }

    public static NoiseGeneratorSettings falloutNoise(BootstapContext<NoiseGeneratorSettings> context) {
        var falloutNoise = WastelandNoiseRouterData.falloutNoise(
                context.lookup(Registries.DENSITY_FUNCTION),
                context.lookup(Registries.NOISE),
                false, false
        );

        return new NoiseGeneratorSettings(
                WASTELAND_NOISE_SETTINGS,
                // --- Base Block (use stone to avoid grass generating underground) ---
                Blocks.STONE.defaultBlockState(),
                // --- Default Fluid for oceans/rivers (your custom dirty/irradiated water) ---
                ModFluids.DIRTY_WATER_BLOCK.get().defaultBlockState(),
                falloutNoise,
                WastelandSurfaceRule.fallout(),
                (new WastelandBiomeBuilder()).spawnTarget(),
                64, false, false, false, false
        );
    }

    private static DimensionType falloutDimensionType() {
        return new DimensionType(
                OptionalLong.empty(),
                true, false, false, true, 1.0D, true, false,
                -64,
                384,
                384,       // Logical Height
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0f,        // ambient light
                new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 7)
        );
    }

    private static DataResult<NoiseSettings> guardY(NoiseSettings settings) {
        if (settings.minY() + settings.height() > DimensionType.MAX_Y + 1) {
            return DataResult.error(() -> "min_y + height cannot be higher than: " + (DimensionType.MAX_Y + 1));
        } else if (settings.height() % 16 != 0) {
            return DataResult.error(() -> "height has to be a multiple of 16");
        } else {
            return settings.minY() % 16 != 0
                    ? DataResult.error(() -> "min_y has to be a multiple of 16")
                    : DataResult.success(settings);
        }
    }

    public static NoiseSettings create(int minY, int height, int noiseSizeHorizontal, int noiseSizeVertical) {
        NoiseSettings settings = new NoiseSettings(minY, height, noiseSizeHorizontal, noiseSizeVertical);
        guardY(settings).error().ifPresent((err) -> {
            throw new IllegalStateException(err.message());
        });
        return settings;
    }
}
