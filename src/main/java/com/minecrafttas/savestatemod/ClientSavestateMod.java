package com.minecrafttas.savestatemod;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class ClientSavestateMod implements ClientModInitializer{

	private final KeybindManager keybindManager = new KeybindManager();
	
	@Override
	public void onInitializeClient() {
		SavestateMod.LOGGER.info("Initialized ClientSide");
		KeybindManagerRegistry.registerKeybindManager(keybindManager);
		SavestateMod.getInstance().getTickrateChanger().mc=Minecraft.getInstance();
		SavestateMod.getInstance().getTickAdvance().mc=Minecraft.getInstance();
	}

}
