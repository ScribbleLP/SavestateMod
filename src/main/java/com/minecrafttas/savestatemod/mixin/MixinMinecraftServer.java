package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
	
	@Inject(method = "forceSynchronousWrites", at = @At("HEAD"), cancellable = true)
	public void inject_forceSynchronousWrites(CallbackInfoReturnable<Boolean> ci) {
		ci.setReturnValue(false);
		ci.cancel();
	}
}
