package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.storage.DimensionDataStorage;

@Mixin(ServerChunkCache.class)
public interface AccessorServerChunkCache {
	@Accessor("dataStorage")
	public DimensionDataStorage getDimensionDataStorage();
	@Invoker("clearCache")
	public void invokeClearCache();
	@Accessor
	public DistanceManager getDistanceManager();
}
