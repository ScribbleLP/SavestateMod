package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.Level;
//#1.16.1
//$$import net.minecraft.world.level.storage.WritableLevelData;
//#def
//$$import net.minecraft.world.level.storage.LevelData;
//#end

@Mixin(Level.class)
public interface AccessorLevel {
	@Accessor("levelData") @Mutable
	//#1.16.1
//$$	public void setLevelData(WritableLevelData levelData);
	//#def
//$$	public void setLevelData(LevelData levelData);
	//#end
}
