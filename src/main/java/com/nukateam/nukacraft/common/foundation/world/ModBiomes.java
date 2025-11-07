package com.nukateam.nukacraft.common.foundation.world;

import com.nukateam.nukacraft.common.foundation.world.features.ModDefaultFeatures;
import com.nukateam.nukacraft.common.foundation.world.features.placed.ModVegetationPlacements;
import com.nukateam.nukacraft.common.registery.EntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;
import java.util.HashMap;

import static com.nukateam.nukacraft.common.data.utils.Resources.nukaResource;

public class ModBiomes {
    public static final ResourceKey<Biome> POISON_VALLEY = createKey("poison_valley");
    public static final ResourceKey<Biome> GLOW_SEA = createKey("glow_sea");
    public static final ResourceKey<Biome> ASH_HEAP = createKey("ash_heap");
    public static final ResourceKey<Biome> CRANBERRY_BOG = createKey("cranberry_bog");
    public static final ResourceKey<Biome> SAVAGE_DIVIDE = createKey("savage_divide");
    public static final ResourceKey<Biome> TOXIC_OCEAN = createKey("toxic_ocean");
    public static final ResourceKey<Biome> GLOWING_DEPTHS = createKey("glowing_depths");

    private static final HashMap<ResourceKey<Biome>, BiomeSettings> biomeSettings = new HashMap<>();

    public static void bootstrap(BootstapContext<Biome> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(POISON_VALLEY  , createPoisonValley(placedFeatures, worldCarvers));
        context.register(CRANBERRY_BOG  , createCranberryBog(placedFeatures, worldCarvers));
        context.register(ASH_HEAP       , createAshHeap(placedFeatures, worldCarvers));
        context.register(GLOW_SEA       , createGlowSea(placedFeatures, worldCarvers));
        context.register(SAVAGE_DIVIDE  , createSavageDivide(placedFeatures, worldCarvers));
        context.register(TOXIC_OCEAN    , createToxicOcean(placedFeatures, worldCarvers));
        context.register(GLOWING_DEPTHS , createGlowingDepths(placedFeatures, worldCarvers));
//        BiomeSettings
    }

    public static void setupBiomeSettings() {
        biomeSettings.put(POISON_VALLEY , new BiomeSettings().setFogDensity(1.0f));
        biomeSettings.put(CRANBERRY_BOG , new BiomeSettings().setFogDensity(1.0f));
        biomeSettings.put(ASH_HEAP      , new BiomeSettings().setFogDensity(0.5f));
        biomeSettings.put(GLOW_SEA      , new BiomeSettings().setFogDensity(0.05f));
        biomeSettings.put(SAVAGE_DIVIDE , new BiomeSettings().setFogDensity(1.0f));
        biomeSettings.put(TOXIC_OCEAN   , new BiomeSettings().setFogDensity(0.2f));
        biomeSettings.put(GLOWING_DEPTHS, new BiomeSettings().setFogDensity(0.1f));
    }

    @Nullable
    public static BiomeSettings getBiomeSettings(Holder<Biome> biome){
        for (var key: biomeSettings.keySet()) {
            if(biome.is(key)){
                return biomeSettings.get(key);
            }
        }
        return null;
    }

    private static Biome createPoisonValley(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        var mobBuilder = new MobSpawnSettings.Builder();

        var biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        var effects = new BiomeSpecialEffects.Builder()
                .fogColor(-5399162)
                .waterColor(-9547964)
                .waterFogColor(11648455)
                .skyColor(-7964315)
                .foliageColorOverride(1783388)
                .grassColorOverride(-861768)
                .build();

        @@ -191,50 +198,123 @@ public class ModBiomes {
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GRASS_ASH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GLOW_GRASS);

        ModDefaultFeatures.addGlowSeaOres(biomeBuilder);

        ModDefaultFeatures.addGlowTrees(biomeBuilder);

        mobBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityTypes.DEATHCLAW.get(), 2, 1, 1));
        mobBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypes.RADROACH.get(), 1, 1, 1));
        mobBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypes.BLOATFLY.get(), 1, 1, 1));

        ModDefaultFeatures.addGlowSeaPlants(biomeBuilder);


        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
//                .biomeCategory(Biome.BiomeCategory.DESERT)
                .temperature(1.5f)
                .downfall(0.9f).specialEffects(effects)
                .mobSpawnSettings(mobBuilder.build())
                    .generationSettings(biomeBuilder.build())
                    .build();
        }


        private static Biome createToxicOcean(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
            var mobBuilder = new MobSpawnSettings.Builder();
            mobBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 10, 1, 4));
            mobBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 8, 3, 6));
            mobBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
            mobBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
            var biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
            var effects = new BiomeSpecialEffects.Builder()
                    .fogColor(14016607)
                    .waterColor(6040415)
                    .waterFogColor(2697513)
                    .skyColor(11965448)
                    .foliageColorOverride(4076355)
                    .grassColorOverride(4867391)
                    .build();

            BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
            BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
            BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
            BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
            BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
            BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
            BiomeDefaultFeatures.addDefaultSeagrass(biomeBuilder);
            BiomeDefaultFeatures.addOceanKelp(biomeBuilder);
            ModDefaultFeatures.addGlowSeaOres(biomeBuilder);

            return (new Biome.BiomeBuilder())
                    .hasPrecipitation(true)
                    .temperature(0.5f)
                    .downfall(0.8f)
                    .biomeCategory(Biome.BiomeCategory.OCEAN)
                    .specialEffects(effects)
                    .mobSpawnSettings(mobBuilder.build())
                    .generationSettings(biomeBuilder.build())
                    .build();
        }

        private static Biome createGlowingDepths(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
            var mobBuilder = new MobSpawnSettings.Builder();
            mobBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 8, 1, 5));
            mobBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 3, 1, 2));
            mobBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 1, 1));
            var biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
            var effects = new BiomeSpecialEffects.Builder()
                    .fogColor(10592377)
                    .waterColor(2109727)
                    .waterFogColor(1441535)
                    .skyColor(6579300)
                    .foliageColorOverride(2368548)
                    .grassColorOverride(3410186)
                    .ambientParticle(new AmbientParticleSettings(ParticleTypes.GLOW, 0.002f))
                    .build();

            BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
            BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
            BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
            BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
            BiomeDefaultFeatures.addDefaultSeagrass(biomeBuilder);
            BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
            BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
            ModDefaultFeatures.addGlowSeaOres(biomeBuilder);

            return (new Biome.BiomeBuilder())
                    .hasPrecipitation(true)
                    .temperature(0.5f)
                    .downfall(0.6f)
                    .biomeCategory(Biome.BiomeCategory.RIVER)
                    .specialEffects(effects)
                    .mobSpawnSettings(mobBuilder.build())
                    .generationSettings(biomeBuilder.build())
                    .build();
        }

        private static Biome createAshHeap(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
            var mobBuilder = new MobSpawnSettings.Builder();
            var biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
            var effects = new BiomeSpecialEffects.Builder()
                    .fogColor(-10990522)
                    .waterColor(-9551310)
                    .waterFogColor(11648455)
                    .skyColor(-10990522)
                    .foliageColorOverride(-10465466)
                    .grassColorOverride(-11187642)
                    .ambientParticle(new AmbientParticleSettings(ParticleTypes.SMOKE, 0.0219f))
                    .build();

            ModDefaultFeatures.addAshHeapOres(biomeBuilder);

            biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GRASS_ASH);
            ModDefaultFeatures.addAshHeapTrees(biomeBuilder);

            mobBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypes.RADROACH.get(), 1, 1, 1));
            mobBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypes.BLOATFLY.get(), 1, 1, 1));

            biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.HEAP_GRASS);
            biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.RUSTY_BUSH);


        ModDefaultFeatures.addAshHeapDisks(biomeBuilder);
        ModDefaultFeatures.addAshHeapPlants(biomeBuilder);

        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
//                .biomeCategory(Biome.BiomeCategory.DESERT)
                .temperature(1.2f)
                .downfall(0.5f).specialEffects(effects)
                .mobSpawnSettings(mobBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, nukaResource(name));
    }
}
