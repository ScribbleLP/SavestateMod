package com.minecrafttas.savestatemod;

import net.fabricmc.api.ClientModInitializer;

public class ClientSavestateMod implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		SavestateMod.LOGGER.info("Initialized ClientSide");
	}

}
