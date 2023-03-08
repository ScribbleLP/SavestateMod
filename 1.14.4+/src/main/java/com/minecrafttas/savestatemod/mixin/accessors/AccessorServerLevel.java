package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.level.ServerLevel;
//#1.16.1
//$$import org.spongepowered.asm.mixin.Mutable;
//$$import org.spongepowered.asm.mixin.gen.Accessor;
//$$
//$$import net.minecraft.world.level.storage.ServerLevelData;
//#end

@Mixin(ServerLevel.class)
public interface AccessorServerLevel {
	//#1.16.1
//$$	@Accessor("serverLevelData") @Mutable
//$$	public void setServerLevelData(ServerLevelData serverLevelData);
	//#end
}
