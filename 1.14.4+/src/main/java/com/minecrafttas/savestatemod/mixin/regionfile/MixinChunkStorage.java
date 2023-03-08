package com.minecrafttas.savestatemod.mixin.regionfile;

import org.spongepowered.asm.mixin.Mixin;
//#1.16.1
//$$import org.spongepowered.asm.mixin.Final;
//$$import org.spongepowered.asm.mixin.Shadow;
//#end

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import net.minecraft.world.level.chunk.storage.ChunkStorage;
//#1.16.1
//$$import net.minecraft.world.level.chunk.storage.IOWorker;
//#end

@Mixin(ChunkStorage.class)
public class MixinChunkStorage implements RegionFileStorageDuck {

	// #1.16.1
//$$	@Shadow @Final
//$$	private IOWorker worker;
	// #end

	@Override
	public void clearRegionFileStorage() {
		// #1.16.1
//$$		((RegionFileStorageDuck)(Object)worker).clearRegionFileStorage();
		// #end
	}
}
