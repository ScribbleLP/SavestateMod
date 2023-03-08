package com.minecrafttas.savestatemod.keybinds;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.minecrafttas.savestatemod.mixin.client.keybinds.KBAccessorKeyMapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;

public abstract class KeybindManagerBase implements IKeybindManager {
	
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
	protected boolean isKeyDown(Minecraft mc, KeyMapping map) {
		// Check if in a text field
		Screen screen = mc.screen;
		if (!(screen == null || !(screen.getFocused() instanceof EditBox) || !((EditBox) screen.getFocused()).canConsumeInput()))
			return false;
		
		boolean wasPressed = this.keys.containsKey(map) ? this.keys.get(map) : false;
		boolean isPressed = GLFW.glfwGetKey(mc.getWindow().getWindow(), ((KBAccessorKeyMapping) map).getKey().getValue()) == GLFW.GLFW_PRESS; //@GetWindow;
		this.keys.put(map, isPressed);
		return !wasPressed && isPressed;
	}
}
