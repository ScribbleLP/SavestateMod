package com.minecrafttas.savestatemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minecrafttas.savestatemod.networking.NetworkRegistry;
import com.minecrafttas.savestatemod.savestates.SavestateHandler;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

public class SavestateMod implements ModInitializer {

	private static SavestateMod instance;
	
	public static final Logger LOGGER=LogManager.getFormatterLogger("SavestateMod");
	
	private KeybindManager keybindManager = new KeybindManager();
	private SavestateHandler savestateHandler;
	
	public SavestateMod() {
		instance=this;
	}
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized");
	}

	public static SavestateMod getInstance() {
		return instance;
	}
	
	public void onServerStart(MinecraftServer server) {
		savestateHandler=new SavestateHandler(server, LOGGER);
		NetworkRegistry.registerServer(savestateHandler);
	}
	
	public void onServerStop(MinecraftServer server) {
		NetworkRegistry.unregisterServer(savestateHandler);
		savestateHandler=null;
	}
	
	public SavestateHandler getSavestateHandler() {
		return savestateHandler;
	}

	public KeybindManager getKeybindManager() {
		return keybindManager;
	}
}
