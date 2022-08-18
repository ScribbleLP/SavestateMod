package com.minecrafttas.savestatemod.keybinds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.minecrafttas.savestatemod.mixin.client.keybinds.KBAccessorKeyMapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeybindManagerRegistry {

	private static List<IKeybindManager> managerRegistry = new ArrayList<>();

	/**
	 * Registers a keybind manager
	 * @param manager One or more keybind managers
	 */
	public static void registerKeybindManager(IKeybindManager... manager) {
		for (IKeybindManager iKeybindManager : manager) {
			managerRegistry.add(iKeybindManager);
		}
	}

	/**
	 * Unregisters a keybind manager
	 * @param manager One or more keybind managers
	 */
	public static void unregisterKeybindManager(IKeybindManager... manager) {
		for (IKeybindManager iKeybindManager : manager) {
			managerRegistry.add(iKeybindManager);
		}
	}
	
	/**
	 * Fires registering keybinds, do not use
	 * @param keyMappings
	 * @return modified keyMappings
	 */
	public static KeyMapping[] fireRegisterKeybinds(KeyMapping[] keyMappings) {
		List<KeyMapping> modded = new ArrayList<>();
		
		for (IKeybindManager keyManager : managerRegistry) {
			modded.addAll(keyManager.registerKeyMappings());
		}

		addCategories(modded);

		List<KeyMapping> newKeyMappings = Lists.newArrayList(keyMappings);
		newKeyMappings.removeAll(modded);
		newKeyMappings.addAll(modded);

		return newKeyMappings.toArray(new KeyMapping[0]);
	}
	
	private static void addCategories(List<KeyMapping> modded) {
		modded.forEach(key -> {
			Map<String, Integer> map = KBAccessorKeyMapping.getCategorySorting();
			String categoryName = key.getCategory();
			if (map.containsKey(categoryName)) {
				return;
			}

			Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
			int largestInt = largest.orElse(0);
			map.put(categoryName, largestInt + 1);
		});
	}

	public static void fireOnGameLoop(Minecraft minecraft) {
		for (IKeybindManager keyManager : managerRegistry) {
			keyManager.onGameLoop(minecraft);
		}
	}
	
	
}
