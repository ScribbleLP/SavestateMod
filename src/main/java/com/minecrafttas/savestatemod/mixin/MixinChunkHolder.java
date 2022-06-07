package com.minecrafttas.savestatemod.mixin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.datafixers.util.Either;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkHolder.ChunkLoadingFailure;
import net.minecraft.world.level.chunk.ChunkAccess;

@Mixin(ChunkHolder.class)
public abstract class MixinChunkHolder {
	
	@Shadow @Final public AtomicReferenceArray<CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>> futures;
	
//	@Inject(method = "updateFutures", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;complete(Ljava/lang/Object;)Z"))
	@Inject(method = "updateFutures", at = @At(value = "HEAD"))
	public void onComplete(CallbackInfo ci) {
		for (int i = 0; i < futures.length(); i++) {
			futures.set(i, null);
		}
	}
	
}
