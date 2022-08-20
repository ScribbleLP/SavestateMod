package com.minecrafttas.savestatemod;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerBase;
import com.minecrafttas.savestatemod.savestates.WorldHacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Main keybind manager of the mod
 * @author Scribble
 *
 */
@Environment(EnvType.CLIENT)
public class KeybindManager extends KeybindManagerBase{
	
	private KeyMapping savestateKey = new KeyMapping("Create Savestate", GLFW.GLFW_KEY_J, "SavestateMod");
	private KeyMapping loadstateKey = new KeyMapping("Load Savestate", GLFW.GLFW_KEY_K, "SavestateMod");
	
	private KeyMapping tickadvanceToggleKey = new KeyMapping("Toggle Tickadvance", GLFW.GLFW_KEY_F8, "SavestateMod");
	private KeyMapping tickadvanceAdvance = new KeyMapping("Advance a tick", GLFW.GLFW_KEY_F9, "SavestateMod");
	
	private KeyMapping testingKeyV = new KeyMapping("Testing1", GLFW.GLFW_KEY_V, "SavestateMod");
	private KeyMapping testingKeyB = new KeyMapping("Testing2", GLFW.GLFW_KEY_B, "SavestateMod");
	private KeyMapping testingKeyN = new KeyMapping("Testing3", GLFW.GLFW_KEY_N, "SavestateMod");
	private KeyMapping testingKeyM = new KeyMapping("Testing4", GLFW.GLFW_KEY_M, "SavestateMod");
//	private KeyMapping testingKeyF12 = new KeyMapping("Testing5", GLFW.GLFW_KEY_F12, "SavestateMod");

	@Override
	public void onGameLoop(Minecraft mc) {
		if (isKeyDown(mc, savestateKey)) {
			SavestateMod.getInstance().getSavestateHandler().requestSavestate();
		}
		else if(isKeyDown(mc, loadstateKey)) {
			SavestateMod.getInstance().getSavestateHandler().requestLoadstate();
		}
		else if(isKeyDown(mc, tickadvanceToggleKey)) {
			SavestateMod.getInstance().getTickAdvance().requestTickadvanceToggle();
		}
		else if(isKeyDown(mc, tickadvanceAdvance)) {
			SavestateMod.getInstance().getTickAdvance().requestTickadvance();
		}
		
		else if(isKeyDown(mc, testingKeyV)) {
			WorldHacks.unloadPlayers();
		}
		else if(isKeyDown(mc, testingKeyB)) {
			WorldHacks.unloadWorld();
		}
		else if(isKeyDown(mc, testingKeyN)) {
			WorldHacks.loadWorld();
		}
		else if(isKeyDown(mc, testingKeyM)) {
			WorldHacks.loadPlayer();
		}
	}

	@Override
	public List<KeyMapping> registerKeyMappings() {
		return List.of(savestateKey, loadstateKey, tickadvanceToggleKey, tickadvanceAdvance, testingKeyV, testingKeyB, testingKeyN, testingKeyM);
	}

}
