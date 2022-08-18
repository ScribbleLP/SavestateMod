package com.minecrafttas.savestatemod.mixin.client.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

/**
 * @author Pancake
 */
@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public class HookMinecraft {

	@Inject(method = "run", at = @At("HEAD"))
	public void hookGameInitEvent(CallbackInfo ci) {
	}

	@Inject(method = "close", at = @At("RETURN"))
	public void hookGameCloseEvent(CallbackInfo ci) {
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void hookTickEvent(CallbackInfo ci) {
	}

	@Inject(method = "runTick", at = @At("HEAD"))
	public void hookGameLoopEvent(CallbackInfo ci) {
	}

	@Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
	public void hookScreenUpdateEvent(Screen screen, CallbackInfo ci) {
	}

	@Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At("HEAD"))
	public void hookDisconnectEvent(CallbackInfo ci) {
	}

}
