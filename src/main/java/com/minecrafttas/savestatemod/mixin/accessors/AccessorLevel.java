package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(Level.class)
public interface AccessorLevel {
	@Accessor("levelData") @Mutable
	public void setLevelData(WritableLevelData levelData);
}
