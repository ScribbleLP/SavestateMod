package com.minecrafttas.savestatemod.mixin.client.keybinds;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecrafttas.savestatemod.keybinds.KeybindManagerRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

/**
 * Hooks into the tick function
 * @author Scribble
 */
@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public class KBHookMinecraft {

	@Inject(method = "runTick", at = @At("HEAD"))
	public void hookGameLoopEvent(CallbackInfo ci) {
		KeybindManagerRegistry.fireOnGameLoop((Minecraft)(Object)this);
	}

}
