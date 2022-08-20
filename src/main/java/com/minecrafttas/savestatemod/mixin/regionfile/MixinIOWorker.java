package com.minecrafttas.savestatemod.mixin.regionfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import net.minecraft.world.level.chunk.storage.IOWorker;
import net.minecraft.world.level.chunk.storage.RegionFileStorage;

@Mixin(IOWorker.class)
public class MixinIOWorker implements RegionFileStorageDuck{

	@Shadow @Final
	private RegionFileStorage storage;
	
	@Override
	public void clearRegionFileStorage() {
		((RegionFileStorageDuck)(Object)storage).clearRegionFileStorage();
	}

}
