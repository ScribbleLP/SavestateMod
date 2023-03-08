package com.minecrafttas.savestatemod.mixin.regionfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

//#1.16.1
//$$import org.spongepowered.asm.mixin.Mixin;
//$$import net.minecraft.world.level.chunk.storage.IOWorker;
//#end
import net.minecraft.world.level.chunk.storage.RegionFileStorage;

//#1.16.1
//$$@Mixin(IOWorker.class)
//#end
public class MixinIOWorker implements RegionFileStorageDuck{

	@Shadow @Final
	private RegionFileStorage storage;
	
	@Override
	public void clearRegionFileStorage() {
		((RegionFileStorageDuck)(Object)storage).clearRegionFileStorage();
	}

}
