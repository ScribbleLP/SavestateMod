package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

@Mixin(ServerLevel.class)
public interface AccessorServerLevel {
	@Accessor("serverLevelData") @Mutable
	public void setServerLevelData(ServerLevelData serverLevelData);
	
}
