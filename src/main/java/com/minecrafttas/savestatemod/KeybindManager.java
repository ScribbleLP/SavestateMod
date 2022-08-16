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
	
	private KeyMapping testingKeyV = new KeyMapping("Testing", GLFW.GLFW_KEY_V, "Savestate Keybinds");
	private KeyMapping testingKeyB = new KeyMapping("Testing", GLFW.GLFW_KEY_B, "Savestate Keybinds");
	private KeyMapping testingKeyN = new KeyMapping("Testing", GLFW.GLFW_KEY_N, "Savestate Keybinds");
	private KeyMapping testingKeyM = new KeyMapping("Testing", GLFW.GLFW_KEY_M, "Savestate Keybinds");
	private KeyMapping testingKeyF12 = new KeyMapping("Testing", GLFW.GLFW_KEY_F12, "Savestate Keybinds");

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
		return ArrayUtils.addAll(keyMappings, testingKeyV, testingKeyB, testingKeyN, testingKeyM);
	}

	/**
	 * Watches out for key presses and triggers sub events.
	 * @param mc Instance of minecraft
	 */
	public void onGameLoop(Minecraft mc) {
		if (this.isKeyDown(mc, testingKeyV)) {
			WorldHacks.unloadPlayers();
		}
		else if (this.isKeyDown(mc, testingKeyB)) {
			WorldHacks.unloadWorld();
		}
		else if (this.isKeyDown(mc, testingKeyN)) {
			WorldHacks.loadWorld();
		}
		else if (this.isKeyDown(mc, testingKeyM)) {
			WorldHacks.loadPlayer();
		}
		else if (this.isKeyDown(mc, testingKeyF12)) {
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
