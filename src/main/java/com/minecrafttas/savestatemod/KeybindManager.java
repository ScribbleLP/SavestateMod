package com.minecrafttas.savestatemod;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import com.minecrafttas.savestatemod.mixin.client.accessors.AccessorKeyMapping;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Manages keybinds and their categories.
 * @author Pancake
 */
@Environment(EnvType.CLIENT)
public class KeybindManager {
	
	private KeyMapping testingKey = new KeyMapping("Testing", GLFW.GLFW_KEY_V, "Savestate Keybinds");
	private KeyMapping testingKey2 = new KeyMapping("Testing", GLFW.GLFW_KEY_B, "Savestate Keybinds");
	private KeyMapping testingKey3 = new KeyMapping("Testing", GLFW.GLFW_KEY_N, "Savestate Keybinds");
	private KeyMapping testingKey4 = new KeyMapping("Testing", GLFW.GLFW_KEY_M, "Savestate Keybinds");

	/**
	 * Categories for all key binds used.
	 */
	private String[] keybindCategories = {"Savestate Keybinds"};

	/**
	 * Initializes the key bind Manager, registers categories and key binds.
	 */
	public KeyMapping[] onKeybindInitialize(KeyMapping[] keyMappings) {
		// Initialize Categories first
		final Map<String, Integer> categories = AccessorKeyMapping.getCategorySorting();
		for (int i = 0; i < this.keybindCategories.length; i++) categories.put(this.keybindCategories[i], i+8);
		// Finish by adding Keybinds
		return ArrayUtils.addAll(keyMappings, testingKey, testingKey2, testingKey3, testingKey4);
	}

	/**
	 * Watches out for key presses and triggers sub events.
	 * @param mc Instance of minecraft
	 */
	public void onGameLoop(Minecraft mc) {
		if (this.isKeyDown(mc, testingKey)) {
			WorldHacks.unloadPlayers();
		}
		else if (this.isKeyDown(mc, testingKey2)) {
			WorldHacks.unloadWorld();
		}
		else if (this.isKeyDown(mc, testingKey3)) {
			WorldHacks.loadWorld();
		}
		else if (this.isKeyDown(mc, testingKey4)) {
			WorldHacks.loadPlayer();
		}
	}

	/**
	 * Map of pressed/non-pressed Keys.
	 */
	private Map<KeyMapping, Boolean> keys = new HashMap<>();

	/**
	 * Checks whether a key has been pressed recently.
	 * @param mc Instance of minecraft
	 * @param map Key mappings to check
	 * @return Key has been pressed recently
	 */
	private boolean isKeyDown(Minecraft mc, KeyMapping map) {
		boolean wasPressed = this.keys.containsKey(map) ? this.keys.get(map) : false;
		boolean isPressed = GLFW.glfwGetKey(mc.getWindow().getWindow(), ((AccessorKeyMapping) map).getKey().getValue()) == GLFW.GLFW_PRESS;
		this.keys.put(map, isPressed);
		return !wasPressed && isPressed;
	}

}
