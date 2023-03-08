package com.minecrafttas.savestatemod.mixin.regionfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.chunk.storage.RegionFileStorage;

@Mixin(RegionFileStorage.class)
public class MixinRegionFileStorage implements RegionFileStorageDuck{

	@Shadow @Final
	private Long2ObjectLinkedOpenHashMap<RegionFile> regionCache;
	
	@Override
	public void clearRegionFileStorage() {
		regionCache.clear();
	}
	
}
