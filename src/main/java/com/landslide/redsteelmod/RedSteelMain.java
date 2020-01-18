package com.landslide.redsteelmod;

import com.landslide.redsteelmod.config.RedSteelConfig;
import com.landslide.redsteelmod.world.gen.OreGeneration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RedSteelMain.MODID)
public final class RedSteelMain {

	public static RedSteelMain instance;
	public static final String MODID = "redsteelmod";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public RedSteelMain() {
		instance = this;

		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, RedSteelConfig.SERVER_SPEC);
	}

}