package com.landslide.redsteelmod.world.gen;

import com.landslide.redsteelmod.RedSteelMain;
import com.landslide.redsteelmod.config.RedSteelConfig;
import com.landslide.redsteelmod.init.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import static com.landslide.redsteelmod.RedSteelMain.LOGGER;

public class OreGeneration {
    public static void setupOreGeneration() {
        if (RedSteelConfig.getGenerateOresOverworld()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ABERRITE_ORE.getDefaultState(), 7), Placement.COUNT_RANGE, new CountRangeConfig(RedSteelConfig.getAberriteLowerFrequency(), 5, 0, 15)));
                biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ABERRITE_ORE.getDefaultState(), 3), Placement.COUNT_RANGE, new CountRangeConfig(RedSteelConfig.getAberriteHigherFrequency(), 40, 0, 55)));
            }
        }
        LOGGER.info("Red Steel's ores have been added to generation.");
    }
}
