package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecrafttas.savestatemod.SavestateMod;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
	
	//#1.16.1
//$$	@Inject(method = "forceSynchronousWrites", at = @At("HEAD"), cancellable = true)
//$$	public void inject_forceSynchronousWrites(CallbackInfoReturnable<Boolean> ci) {
//$$		ci.setReturnValue(false);
//$$		ci.cancel();
//$$	}
	//#end
	

	@Inject(
	//#1.16.1
//$$	method = "runServer", 
	//#def
//$$	method = "run", 
	//#end
	at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;updateStatusIcon(Lnet/minecraft/network/protocol/status/ServerStatus;)V"))
	public void inject_runServer(CallbackInfo ci) {
		SavestateMod.getInstance().onServerStart((MinecraftServer)(Object)this);
	}
	
	@Inject(method = "stopServer", at = @At("HEAD"))
	public void inject_stopServer(CallbackInfo ci) {
		SavestateMod.getInstance().onServerStop((MinecraftServer)(Object)this);
	}
}
