package com.minecrafttas.savestatemod;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerRegistry;
import com.minecrafttas.savestatemod.savestates.SavestateHandler;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class ClientSavestateMod implements ClientModInitializer{

	private static ClientSavestateMod instance;
	
	private final KeybindManager keybindManager = new KeybindManager();
	
	private final SavestateHandler handler = new SavestateHandler(SavestateMod.LOGGER);
	
	@Override
	public void onInitializeClient() {
		SavestateMod.LOGGER.info("Initialized ClientSide");
		instance=this;
		KeybindManagerRegistry.registerKeybindManager(keybindManager);
		SavestateMod.getInstance().getTickrateChanger().mc=Minecraft.getInstance();
		SavestateMod.getInstance().getTickAdvance().mc=Minecraft.getInstance();
	}

	public static ClientSavestateMod getInstance() {
		return instance;
	}
	
	public KeybindManager getKeybindManager() {
		return keybindManager;
	}
	
	public SavestateHandler getSavestateHandler() {
		return handler;
	}
}
