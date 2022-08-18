package com.minecrafttas.savestatemod.keybinds;

import java.util.List;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public interface IKeybindManager {
	
	/**
	 * Add key mappings to this list, to register them
	 * @return An array of keymappings
	 */
	public List<KeyMapping> registerKeyMappings();
	
	/**
	 * Runs every tick.
	 * @param mc Instance of minecraft
	 */
	public void onGameLoop(Minecraft mc);
}
