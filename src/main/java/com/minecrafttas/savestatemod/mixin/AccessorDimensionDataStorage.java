package com.minecrafttas.savestatemod.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

@Mixin(DimensionDataStorage.class)
public interface AccessorDimensionDataStorage {
	@Accessor("cache")
	public Map<String, SavedData> getCache();
}
