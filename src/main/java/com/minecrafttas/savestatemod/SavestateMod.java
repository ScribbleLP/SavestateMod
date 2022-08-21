package com.minecrafttas.savestatemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minecrafttas.savestatemod.networking.NetworkRegistry;
import com.minecrafttas.savestatemod.savestates.SavestateHandler;
import com.minecrafttas.savestatemod.tickratechanger.TickAdvance;
import com.minecrafttas.savestatemod.tickratechanger.TickrateChanger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

public class SavestateMod implements ModInitializer {

	private static SavestateMod instance;
	
	public static final Logger LOGGER=LogManager.getFormatterLogger("SavestateMod");
	
	private SavestateHandler savestateHandler;
	private TickrateChanger tickratechanger = new TickrateChanger();
	private TickAdvance tickadvance =  new TickAdvance();
	
	public SavestateMod() {
		instance=this;
	}
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized");
		NetworkRegistry.registerClient(tickratechanger);
		NetworkRegistry.registerClient(tickadvance);
		
		NetworkRegistry.registerServer(tickratechanger);
		NetworkRegistry.registerServer(tickadvance);
	}

	public static SavestateMod getInstance() {
		return instance;
	}
	
	public void onServerStart(MinecraftServer server) {
		savestateHandler=new SavestateHandler(server, LOGGER);
		NetworkRegistry.registerServer(savestateHandler);
		tickratechanger.mcserver=server;
		tickadvance.mcserver=server;
	}
	
	public void onServerStop(MinecraftServer server) {
		NetworkRegistry.unregisterServer(savestateHandler);
		savestateHandler=null;
	}
	
	public SavestateHandler getSavestateHandler() {
		return savestateHandler;
	}

	public TickrateChanger getTickrateChanger() {
		return tickratechanger;
	}

	public TickAdvance getTickAdvance() {
		return tickadvance;
	}
}
