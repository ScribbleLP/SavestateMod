package com.minecrafttas.savestatemod.mixin.client.keybinds;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;

/**
 * Loads keybinds manually into the keymappings
 * @author Pancake
 */
@Mixin(Options.class)
@Environment(EnvType.CLIENT)
public class KBHookOptions {

	/**
	 * List of Key Mappings that are being registered once loaded
	 */
	@Mutable @Final @Shadow
	public KeyMapping[] keyMappings;

	/**
	 * Loads keybinds into the keymappings
	 * @param ci Callback Info
	 */
	@Inject(method = "load", at = @At("HEAD"))
	public void hookLoadEvent(CallbackInfo ci) {
		this.keyMappings = KeybindManagerRegistry.fireRegisterKeybinds(this.keyMappings);
	}

}