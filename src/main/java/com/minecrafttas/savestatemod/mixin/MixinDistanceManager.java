package com.minecrafttas.savestatemod.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.Ticket;
import net.minecraft.util.SortedArraySet;

@Mixin(DistanceManager.class)
public abstract class MixinDistanceManager {
	
	@Shadow @Final
	private Set<ChunkHolder> chunksToUpdateFutures;
	@Shadow @Final
	private Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets;
	
	@Inject(method = "runAllUpdates", at = @At("HEAD"))
	public void runAllUpdatesC(CallbackInfoReturnable<Boolean> b) {
		chunksToUpdateFutures.clear();
	}
	
}
