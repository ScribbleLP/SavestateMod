package com.minecrafttas.savestatemod;

import net.fabricmc.api.ClientModInitializer;

public class ClientSavestateMod implements ClientModInitializer{

	public static final KeybindManager keybindManager = new KeybindManager();
	
	@Override
	public void onInitializeClient() {
		SavestateMod.LOGGER.info("Initialized ClientSide");
	}

}
