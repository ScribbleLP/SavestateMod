package com.minecrafttas.savestatemod.mixin.accessors;

import java.util.List;
import java.util.Queue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.level.ServerLevel;
//#1.16.1
//$$import org.spongepowered.asm.mixin.Mutable;
//$$import org.spongepowered.asm.mixin.gen.Accessor;
//$$
//$$import net.minecraft.world.entity.Entity;
//$$import net.minecraft.world.level.storage.ServerLevelData;
//#end
import net.minecraft.world.entity.Entity;

@Mixin(ServerLevel.class)
public interface AccessorServerLevel {
	//#1.16.1
//$$	@Accessor("serverLevelData") @Mutable
//$$	public void setServerLevelData(ServerLevelData serverLevelData);
	//#end
	
	@Accessor
	public Queue<Entity> getToAddAfterTick();
	
	//#1.16.1
	//#def
//$$	@Accessor
//$$	public List<Entity> getGlobalEntities();
	//#end
}
