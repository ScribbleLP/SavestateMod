package com.minecrafttas.savestatemod;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerBase;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

/**
 * Main keybind manager of the mod
 * @author Scribble
 *
 */
@Environment(EnvType.CLIENT)
public class KeybindManager extends KeybindManagerBase{
	
	private KeyMapping savestateKey = new KeyMapping("Create Savestate", GLFW.GLFW_KEY_J, "SavestateMod");
	
	private KeyMapping testingKeyV = new KeyMapping("Testing1", GLFW.GLFW_KEY_V, "SavestateMod");
	private KeyMapping testingKeyB = new KeyMapping("Testing2", GLFW.GLFW_KEY_B, "SavestateMod");
	private KeyMapping testingKeyN = new KeyMapping("Testing3", GLFW.GLFW_KEY_N, "SavestateMod");
	private KeyMapping testingKeyM = new KeyMapping("Testing4", GLFW.GLFW_KEY_M, "SavestateMod");
	private KeyMapping testingKeyF12 = new KeyMapping("Testing5", GLFW.GLFW_KEY_F12, "SavestateMod");

	@Override
	public void onGameLoop(Minecraft mc) {
		if (isKeyDown(mc, savestateKey)) {
			mc.gui.getChat().addMessage(new TextComponent("Savestating..."));
			SavestateMod.getInstance().getSavestateHandler().requestSavestate();
		}
	}

	@Override
	public List<KeyMapping> registerKeyMappings() {
		return List.of(savestateKey, testingKeyV, testingKeyB, testingKeyN, testingKeyM, testingKeyF12);
	}

}
