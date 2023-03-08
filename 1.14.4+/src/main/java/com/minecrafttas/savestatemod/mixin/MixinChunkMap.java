package com.minecrafttas.savestatemod.mixin;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Either;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.ChunkAccess;

@Mixin(ChunkMap.class)
public class MixinChunkMap{

	@Shadow
	LongSet toDrop;
	
	@Shadow
	Long2ObjectLinkedOpenHashMap<ChunkHolder> updatingChunkMap;
	
	@Inject(method = "processUnloads", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkMap;scheduleUnload(JLnet/minecraft/server/level/ChunkHolder;)V"))
	public void inject_processUnloads(CallbackInfo ci) {
		//System.out.println("Unload!");
	}
	
	@Inject(method = "scheduleChunkLoad", at = @At(value = "RETURN", ordinal = 0))
	public void injectLoad(CallbackInfoReturnable<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> ci) {
		//System.out.println("Load!");
	}
	
	
	@Inject(method = "scheduleChunkGeneration", at = @At("RETURN"))
	public void inject_scheduleChunkGeneration(CallbackInfoReturnable<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> ci) {
		//System.out.println("Generate!");
	}
}
