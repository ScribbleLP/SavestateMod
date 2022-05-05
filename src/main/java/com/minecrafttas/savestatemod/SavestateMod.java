package com.minecrafttas.savestatemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class SavestateMod implements ModInitializer{

	public static final Logger LOGGER=LogManager.getFormatterLogger("SavestateMod");
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized");
	}

}
