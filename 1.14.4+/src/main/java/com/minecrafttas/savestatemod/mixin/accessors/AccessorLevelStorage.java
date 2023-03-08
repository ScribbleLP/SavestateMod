package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
//#1.16.1
//$$import org.spongepowered.asm.mixin.Mutable;
//$$import net.minecraft.world.level.storage.WorldData;
//#end

@Mixin(MinecraftServer.class)
public interface AccessorLevelStorage {
	@Accessor
	//#1.16.1
//$$	public LevelStorageSource.LevelStorageAccess getStorageSource();
//$$	
//$$	@Accessor("worldData") @Mutable
//$$	public void setWorldData(WorldData data);
	//#def
//$$	public LevelStorageSource getStorageSource();
	//#end
	
}
