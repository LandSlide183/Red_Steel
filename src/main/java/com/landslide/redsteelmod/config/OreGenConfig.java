package com.landslide.redsteelmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreGenConfig {
    public static ForgeConfigSpec.IntValue aberrite_low_frequency;
    public static ForgeConfigSpec.BooleanValue generate_ores;
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("OreGen Config");

        aberrite_low_frequency = server
                .comment("The number ore veins of aberrite ore that can spawn in a single chunk. Defaults to 2.")
                .defineInRange("oregen.aberrite_frequency", 2, 0, 10);

        generate_ores = server
                .comment("Decide if you want Red Steel's ores to spawn.")
                .define("oregen.generate_ores", true);
    }
}
