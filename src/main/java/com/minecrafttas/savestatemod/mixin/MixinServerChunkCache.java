package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecrafttas.savestatemod.SavestateMod;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.chunk.ChunkSource;

@Mixin(ServerChunkCache.class)
@Deprecated
/*Unused*/
public abstract class MixinServerChunkCache extends ChunkSource {
	
	@Inject(method = "runDistanceManagerUpdates", at = @At("HEAD"), cancellable = true)
	public void inject_runDistanceManagerUpdates(CallbackInfoReturnable<Boolean> ci) {
		if(SavestateMod.getInstance().getTickAdvance().isTickadvanceEnabled()) {
			ci.cancel();
		}
	}
}
