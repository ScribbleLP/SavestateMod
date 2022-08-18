package com.minecrafttas.savestatemod.mixin.client.tickratechanger;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecrafttas.savestatemod.SavestateMod;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	/**
	 * Executed every tick of the game.
	 * @param mc Instance of minecraft
	 */
	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		// Tick Tick Advance
		SavestateMod.getInstance().getTickAdvance().onTick((Minecraft)(Object)this);
	}
}
