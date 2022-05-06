package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;

@Mixin(ChunkMap.class)
public interface AccessorChunkMap {
	@Accessor("modified")
	public void setModified(boolean newval);
	
	@Accessor("updatingChunkMap")
	public Long2ObjectLinkedOpenHashMap<ChunkHolder> getChunkMap();
}
