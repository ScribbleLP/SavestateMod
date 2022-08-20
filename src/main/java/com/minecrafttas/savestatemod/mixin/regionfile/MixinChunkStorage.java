package com.minecrafttas.savestatemod.mixin.regionfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.IOWorker;

@Mixin(ChunkStorage.class)
public class MixinChunkStorage implements RegionFileStorageDuck  {

	@Shadow @Final
	private IOWorker worker;
	
	@Override
	public void clearRegionFileStorage() {
		((RegionFileStorageDuck)(Object)worker).clearRegionFileStorage();
	}
	
}
