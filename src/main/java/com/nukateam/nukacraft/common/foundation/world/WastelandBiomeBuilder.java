package com.nukateam.nukacraft.common.foundation.world;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.function.Consumer;

public class WastelandBiomeBuilder {
    private static final float VALLEY_SIZE = 0.05F;
    private static final float LOW_START = 0.26666668F;
    public static final float HIGH_START = 0.4F;
    private static final float HIGH_END = 0.93333334F;
    private static final float PEAK_SIZE = 0.1F;
    public static final float PEAK_START = 0.56666666F;
    private static final float PEAK_END = 0.7666667F;
    public static final float NEAR_INLAND_START = -0.11F;
    public static final float MID_INLAND_START = 0.03F;
    public static final float FAR_INLAND_START = 0.3F;
    public static final float EROSION_INDEX_1_START = -0.78F;
    public static final float EROSION_INDEX_2_START = -0.375F;

    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private final Climate.Parameter[] humidities = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.35F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)
    };
    private final Climate.Parameter[] erosions = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
    private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);

    // Continentalness bands
    private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
    private final Climate.Parameter deepOceanContinentalness      = Climate.Parameter.span(-1.05F, -0.455F);
    private final Climate.Parameter oceanContinentalness          = Climate.Parameter.span(-0.455F, -0.19F);
    private final Climate.Parameter coastContinentalness          = Climate.Parameter.span(-0.19F, -0.11F);
    private final Climate.Parameter inlandContinentalness         = Climate.Parameter.span(-0.11F, 0.55F);
    private final Climate.Parameter nearInlandContinentalness     = Climate.Parameter.span(-0.11F, 0.03F);
    private final Climate.Parameter midInlandContinentalness      = Climate.Parameter.span(0.03F, 0.3F);
    private final Climate.Parameter farInlandContinentalness      = Climate.Parameter.span(0.3F, 1.0F);

    private final ResourceKey<Biome>[][] MIDDLE_BIOMES =
            new ResourceKey[][]{
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY }
            };

    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT =
            new ResourceKey[][]{
                    { null, null, null, null, null },
                    { null, null, null, null, null },
                    { null, null, null, null, null },
                    { null, null, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { null, null, null, null, null }
            };

    private final ResourceKey<Biome>[][] PLATEAU_BIOMES =
            new ResourceKey[][]{
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY }
            };

    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT =
            new ResourceKey[][]{
                    { null, null, null, null, null },
                    { null, null, null, null, null },
                    { null, null, null, null, null },
                    { null, null, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, null },
                    { null, null, null, null, null }
            };

    private final ResourceKey<Biome>[][] EXTREME_HILLS =
            new ResourceKey[][]{
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY, ModBiomes.POISON_VALLEY },
                    { null, null, null, null, null },
                    { null, null, null, null, null }
            };

    public List<Climate.ParameterPoint> spawnTarget() {
        Climate.Parameter zero = Climate.Parameter.point(0.0F);
        float f = 0.16F;
        return List.of(
                new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, zero, Climate.Parameter.span(-1.0F, -0.16F), 0L),
                new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, zero, Climate.Parameter.span(0.16F, 1.0F), 0L)
        );
    }

    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        this.addOffCoastBiomes(out);
        this.addInlandBiomes(out);
        this.addUndergroundBiomes(out);
        this.addRiverBiomes(out); // <— route rivers to our custom river biome
    }

    private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        // Offshore shelf/islands (leave as Poison Valley for now)
        this.addSurfaceBiome(out, FULL_RANGE, FULL_RANGE, mushroomFieldsContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.POISON_VALLEY);

        // Deep ocean and ocean bands — use our toxic ocean
        this.addSurfaceBiome(out, FULL_RANGE, FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.TOXIC_OCEAN);
        this.addSurfaceBiome(out, FULL_RANGE, FULL_RANGE, oceanContinentalness,     FULL_RANGE, FULL_RANGE, 0.0F, ModBiomes.TOXIC_OCEAN);
    }

    private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> c) {
        this.addMidSlice(c, Climate.Parameter.span(-1.0F, -0.93333334F));
        this.addHighSlice(c, Climate.Parameter.span(-0.93333334F, -0.7666667F));
        this.addPeaks(c, Climate.Parameter.span(-0.7666667F, -0.56666666F));
        this.addHighSlice(c, Climate.Parameter.span(-0.56666666F, -0.4F));
        this.addMidSlice(c, Climate.Parameter.span(-0.4F, -0.26666668F));
        this.addLowSlice(c, Climate.Parameter.span(-0.26666668F, -0.05F));
        this.addValleys(c, Climate.Parameter.span(-0.05F, 0.05F));
        this.addLowSlice(c, Climate.Parameter.span(0.05F, 0.26666668F));
        this.addMidSlice(c, Climate.Parameter.span(0.26666668F, 0.4F));
        this.addHighSlice(c, Climate.Parameter.span(0.4F, 0.56666666F));
        this.addPeaks(c, Climate.Parameter.span(0.56666666F, 0.7666667F));
        this.addHighSlice(c, Climate.Parameter.span(0.7666667F, 0.93333334F));
        this.addMidSlice(c, Climate.Parameter.span(0.93333334F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter pvBand) {
        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temp = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter hum = this.humidities[j];
                ResourceKey<Biome> mid        = this.pickMiddleBiome(i, j, pvBand);
                ResourceKey<Biome> midHot     = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pvBand);
                ResourceKey<Biome> midHotCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pvBand);
                ResourceKey<Biome> plateau    = this.pickPlateauBiome(i, j, pvBand);
                ResourceKey<Biome> hills      = this.pickExtremeHillsBiome(i, j, pvBand);
                ResourceKey<Biome> shattered  = this.maybePickShatteredBiome(i, j, pvBand, hills);
                ResourceKey<Biome> peak       = this.pickPeakBiome(i, j, pvBand);

                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], pvBand, 0.0F, peak);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], pvBand, 0.0F, midHotCold);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], pvBand, 0.0F, peak);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pvBand, 0.0F, plateau);
                this.addSurfaceBiome(out, temp, hum, this.midInlandContinentalness, this.erosions[3], pvBand, 0.0F, midHot);
                this.addSurfaceBiome(out, temp, hum, this.farInlandContinentalness, this.erosions[3], pvBand, 0.0F, plateau);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], pvBand, 0.0F, shattered);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pvBand, 0.0F, hills);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pvBand, 0.0F, mid);
            }
        }
    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter pvBand) {
        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temp = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter hum = this.humidities[j];
                ResourceKey<Biome> mid        = this.pickMiddleBiome(i, j, pvBand);
                ResourceKey<Biome> midHot     = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pvBand);
                ResourceKey<Biome> midHotCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pvBand);
                ResourceKey<Biome> plateau    = this.pickPlateauBiome(i, j, pvBand);
                ResourceKey<Biome> hills      = this.pickExtremeHillsBiome(i, j, pvBand);
                ResourceKey<Biome> shattered  = this.maybePickShatteredBiome(i, j, pvBand, mid);
                ResourceKey<Biome> slope      = this.pickSlopeBiome(i, j, pvBand);
                ResourceKey<Biome> peak       = this.pickPeakBiome(i, j, pvBand);

                this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, this.erosions[0], pvBand, 0.0F, slope);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], pvBand, 0.0F, peak);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, this.erosions[1], pvBand, 0.0F, midHotCold);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], pvBand, 0.0F, slope);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pvBand, 0.0F, plateau);
                this.addSurfaceBiome(out, temp, hum, this.midInlandContinentalness, this.erosions[3], pvBand, 0.0F, midHot);
                this.addSurfaceBiome(out, temp, hum, this.farInlandContinentalness, this.erosions[3], pvBand, 0.0F, plateau);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], pvBand, 0.0F, shattered);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pvBand, 0.0F, hills);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pvBand, 0.0F, mid);
            }
        }
    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter pvBand) {
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), pvBand, 0.0F, ModBiomes.POISON_VALLEY);

        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temp = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter hum = this.humidities[j];
                ResourceKey<Biome> mid        = this.pickMiddleBiome(i, j, pvBand);
                ResourceKey<Biome> midHot     = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pvBand);
                ResourceKey<Biome> midHotCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pvBand);
                ResourceKey<Biome> hills      = this.pickExtremeHillsBiome(i, j, pvBand);
                ResourceKey<Biome> plateau    = this.pickPlateauBiome(i, j, pvBand);
                ResourceKey<Biome> beach      = this.pickBeachBiome(i, j);
                ResourceKey<Biome> shattered  = this.maybePickShatteredBiome(i, j, pvBand, mid);
                ResourceKey<Biome> shCoast    = this.pickShatteredCoastBiome(i, j, pvBand);
                ResourceKey<Biome> slope      = this.pickSlopeBiome(i, j, pvBand);

                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], pvBand, 0.0F, slope);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], pvBand, 0.0F, midHotCold);
                this.addSurfaceBiome(out, temp, hum, this.farInlandContinentalness, this.erosions[1], pvBand, 0.0F, i == 0 ? slope : plateau);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, this.erosions[2], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, this.midInlandContinentalness, this.erosions[2], pvBand, 0.0F, midHot);
                this.addSurfaceBiome(out, temp, hum, this.farInlandContinentalness, this.erosions[2], pvBand, 0.0F, plateau);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], pvBand, 0.0F, midHot);
                if (pvBand.max() < 0L) {
                    this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[4], pvBand, 0.0F, beach);
                    this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pvBand, 0.0F, mid);
                } else {
                    this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pvBand, 0.0F, mid);
                }

                this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[5], pvBand, 0.0F, shCoast);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, this.erosions[5], pvBand, 0.0F, shattered);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pvBand, 0.0F, hills);
                if (pvBand.max() < 0L) {
                    this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[6], pvBand, 0.0F, beach);
                } else {
                    this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[6], pvBand, 0.0F, mid);
                }

                if (i == 0) {
                    this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pvBand, 0.0F, mid);
                }
            }
        }
    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter pvBand) {
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), pvBand, 0.0F, ModBiomes.POISON_VALLEY);

        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temp = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter hum = this.humidities[j];
                ResourceKey<Biome> mid        = this.pickMiddleBiome(i, j, pvBand);
                ResourceKey<Biome> midHot     = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pvBand);
                ResourceKey<Biome> midHotCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pvBand);
                ResourceKey<Biome> beach      = this.pickBeachBiome(i, j);
                ResourceKey<Biome> shattered  = this.maybePickShatteredBiome(i, j, pvBand, mid);
                ResourceKey<Biome> shCoast    = this.pickShatteredCoastBiome(i, j, pvBand);

                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, midHot);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, midHotCold);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pvBand, 0.0F, midHot);
                this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), pvBand, 0.0F, beach);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[5], pvBand, 0.0F, shCoast);
                this.addSurfaceBiome(out, temp, hum, this.nearInlandContinentalness, this.erosions[5], pvBand, 0.0F, shattered);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pvBand, 0.0F, mid);
                this.addSurfaceBiome(out, temp, hum, this.coastContinentalness, this.erosions[6], pvBand, 0.0F, beach);
                if (i == 0) {
                    this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pvBand, 0.0F, mid);
                }
            }
        }
    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter pvBand) {
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, ModBiomes.POISON_VALLEY);
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, ModBiomes.POISON_VALLEY);

        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), pvBand, 0.0F, ModBiomes.POISON_VALLEY);
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], pvBand, 0.0F, ModBiomes.POISON_VALLEY);
        this.addSurfaceBiome(out, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], pvBand, 0.0F, ModBiomes.POISON_VALLEY);

        for (int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temp = this.temperatures[i];
            for (int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter hum = this.humidities[j];
                ResourceKey<Biome> midHot = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pvBand);
                this.addSurfaceBiome(out, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), pvBand, 0.0F, midHot);
            }
        }
    }

    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        // If you want underground biomes later, add them here with addUndergroundBiome(...)
    }

    // NEW — route rivers to a single custom biome everywhere rivers can appear.
    private void addRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        this.addSurfaceBiome(
                out,
                this.FULL_RANGE,  // temperature
                this.FULL_RANGE,  // humidity
                Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), // continentalness where rivers run
                this.FULL_RANGE,  // erosion
                this.FULL_RANGE,  // peaks/valleys band
                0.0F,
                ModBiomes.IRRADIATED_RIVER
        );
    }

    private ResourceKey<Biome> pickMiddleBiome(int ti, int hi, Climate.Parameter pvBand) {
        if (pvBand.max() < 0L) {
            return this.MIDDLE_BIOMES[ti][hi];
        } else {
            ResourceKey<Biome> alt = this.MIDDLE_BIOMES_VARIANT[ti][hi];
            return alt == null ? this.MIDDLE_BIOMES[ti][hi] : alt;
        }
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int ti, int hi, Climate.Parameter pvBand) {
        return this.pickMiddleBiome(ti, hi, pvBand);
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int ti, int hi, Climate.Parameter pvBand) {
        return ti == 0 ? this.pickSlopeBiome(ti, hi, pvBand) : this.pickMiddleBiomeOrBadlandsIfHot(ti, hi, pvBand);
    }

    private ResourceKey<Biome> maybePickShatteredBiome(int ti, int hi, Climate.Parameter pvBand, ResourceKey<Biome> base) {
        return base;
    }

    private ResourceKey<Biome> pickShatteredCoastBiome(int ti, int hi, Climate.Parameter pvBand) {
        ResourceKey<Biome> coast = pvBand.max() >= 0L ? this.pickMiddleBiome(ti, hi, pvBand) : this.pickBeachBiome(ti, hi);
        return this.maybePickShatteredBiome(ti, hi, pvBand, coast);
    }

    private ResourceKey<Biome> pickBeachBiome(int ti, int hi) {
        return ModBiomes.POISON_VALLEY; // keep beaches as land/dry for now
    }

    private ResourceKey<Biome> pickPlateauBiome(int ti, int hi, Climate.Parameter pvBand) {
        if (pvBand.max() < 0L) {
            return this.PLATEAU_BIOMES[ti][hi];
        } else {
            ResourceKey<Biome> alt = this.PLATEAU_BIOMES_VARIANT[ti][hi];
            return alt == null ? this.PLATEAU_BIOMES[ti][hi] : alt;
        }
    }

    private ResourceKey<Biome> pickPeakBiome(int ti, int hi, Climate.Parameter pvBand) {
        return this.pickPlateauBiome(ti, hi, pvBand);
    }

    private ResourceKey<Biome> pickSlopeBiome(int ti, int hi, Climate.Parameter pvBand) {
        return this.pickPlateauBiome(ti, hi, pvBand);
    }

    private ResourceKey<Biome> pickExtremeHillsBiome(int ti, int hi, Climate.Parameter pvBand) {
        ResourceKey<Biome> key = this.EXTREME_HILLS[ti][hi];
        return key == null ? this.pickMiddleBiome(ti, hi, pvBand) : key;
    }

    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out,
                                 Climate.Parameter temperature,
                                 Climate.Parameter humidity,
                                 Climate.Parameter continentalness,
                                 Climate.Parameter erosion,
                                 Climate.Parameter pvBand,
                                 float weirdness,
                                 ResourceKey<Biome> biome) {
        out.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0F), pvBand, weirdness), biome));
        out.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), pvBand, weirdness), biome));
    }

    private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out,
                                     Climate.Parameter temperature,
                                     Climate.Parameter humidity,
                                     Climate.Parameter continentalness,
                                     Climate.Parameter erosion,
                                     Climate.Parameter pvBand,
                                     float weirdness,
                                     ResourceKey<Biome> biome) {
        out.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.2F, 0.9F), pvBand, weirdness), biome));
    }
}
