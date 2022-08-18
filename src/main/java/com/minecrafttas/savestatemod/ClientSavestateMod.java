package com.minecrafttas.savestatemod;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerRegistry;

import net.fabricmc.api.ClientModInitializer;

public class ClientSavestateMod implements ClientModInitializer{

	private final KeybindManager keybindManager = new KeybindManager();
	
	@Override
	public void onInitializeClient() {
		SavestateMod.LOGGER.info("Initialized ClientSide");
		KeybindManagerRegistry.registerKeybindManager(keybindManager);
	}

}
