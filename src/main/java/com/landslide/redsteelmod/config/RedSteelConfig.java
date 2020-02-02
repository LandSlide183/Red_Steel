package com.landslide.redsteelmod.config;

import com.landslide.redsteelmod.RedSteelMain;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;


public class RedSteelConfig {
    public static final Logger LOGGER = RedSteelMain.LOGGER;
    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }



    public static void bakeServerConfig() {
        generateOresOverworld = SERVER.generateOresOverworld.get();
        aberriteLowerFrequency = SERVER.aberriteLowerFrequency.get();
        aberriteHigherFrequency = SERVER.aberriteHigherFrequency.get();
        LOGGER.info("Red Steel's server config has been baked");
    }

    private static boolean generateOresOverworld;
    private static int aberriteLowerFrequency;
    private static int aberriteHigherFrequency;

    public static int getAberriteHigherFrequency() {
        return aberriteHigherFrequency;
    }

    public static boolean getGenerateOresOverworld() {
        return generateOresOverworld;
    }

    public static int getAberriteLowerFrequency() {
        return aberriteLowerFrequency;
    }
    public static class ServerConfig {
        public final ForgeConfigSpec.BooleanValue generateOresOverworld;
        public final ForgeConfigSpec.IntValue aberriteLowerFrequency;
        public final ForgeConfigSpec.IntValue aberriteHigherFrequency;
        public ServerConfig(ForgeConfigSpec.Builder builder) {
            String translation = RedSteelMain.MODID + ".config.";
            generateOresOverworld = builder
                    .comment("generateOresOverworld is a boolean that determines if Red Steel's ores are generated. Defaults to true.")
                    .translation(translation + "generateOresOverworld")
                    .define("generateOresOverworld", true);
            aberriteLowerFrequency = builder
                    .comment("The amount of lower aberrite ore per chunk, defaults to four.")
                    .translation(translation + " aberriteLowerFrequency")
                    .defineInRange("aberriteLowerFrequency", 4, 0, 50);
            aberriteHigherFrequency = builder
                    .comment("The amount of higher aberrite ore per chunk, defaults to one.")
                    .translation(translation + " aberriteLowerFrequency")
                    .defineInRange("aberriteHigherFrequency", 1, 0, 50);
        }
    }
}
