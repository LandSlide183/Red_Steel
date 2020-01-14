package com.landslide.redsteelmod;

import com.landslide.redsteelmod.config.Config;
import com.landslide.redsteelmod.world.gen.OreGeneration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RedSteelMain.MODID)
public final class RedSteelMain {

	public static RedSteelMain instance;
	public static final String MODID = "redsteelmod";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public RedSteelMain() {
		instance = this;

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("redsteelmod-client.toml").toString());
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("redsteelmod-server.toml").toString());
	}
	private void commonSetup(final FMLCommonSetupEvent event) {
		OreGeneration.setupOreGeneration();
		LOGGER.info("Red Steel's ores have been added to generation.");
	}
}