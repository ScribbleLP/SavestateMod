package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerChunkCache;

@Mixin(ServerChunkCache.class)
public interface AccessorServerChunkCache {
	@Accessor
	public DistanceManager getDistanceManager();
}
