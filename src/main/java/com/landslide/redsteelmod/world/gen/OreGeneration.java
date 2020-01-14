package com.landslide.redsteelmod.world.gen;

import com.landslide.redsteelmod.config.OreGenConfig;
import com.landslide.redsteelmod.init.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {
    public static void setupOreGeneration() {
        if (/*OreGenConfig.generate_ores.get()*/ true) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ABERRITE_ORE.getDefaultState(), /*OreGenConfig.aberrite_low_frequency.get()*/5), Placement.COUNT_RANGE, new CountRangeConfig(5, 5, 0, 10)));
            }
        }
    }
}
