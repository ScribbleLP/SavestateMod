package com.minecrafttas.savestatemod.savestates;

import java.util.Iterator;
import java.util.List;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.dimension.DimensionType;

public class WorldHacks {
	
	public static void unloadPlayers(MinecraftServer server) {

		Iterator<ServerLevel> levels = server.getAllLevels().iterator();
		
		while(levels.hasNext()) {
			ServerLevel level = levels.next();
			List<ServerPlayer> players = server.getPlayerList().getPlayers();
			
			for (ServerPlayer player : players) {
				level.getChunkSource().removeEntity(player);
			}
		}
	}
	
	public static void unloadWorlds(MinecraftServer server) {
		
		Iterator<ServerLevel> levels = server.getAllLevels().iterator();
		
		while(levels.hasNext()) {
			ServerLevel level = levels.next();
			ServerChunkCache chunkSource=level.getChunkSource();
			
//			level.save(null, true, false);
			
			if(level.dimensionType() == DimensionType.defaultOverworld()) {
				ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
				chunkSource.removeRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
			}
		}
		
		server.runAllTasks();
	}
	
	public static void loadWorlds(MinecraftServer server) {
		
		Iterator<ServerLevel> levels = server.getAllLevels().iterator();
		
		while(levels.hasNext()) {
			ServerLevel level = levels.next();
			ServerChunkCache chunkSource=level.getChunkSource();
			
			((RegionFileStorageDuck)(ChunkStorage)chunkSource.chunkMap).clearRegionFileStorage();
			
			if(level.dimensionType() == DimensionType.defaultOverworld()) {
				ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
				chunkSource.addRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
			}
		}
	}
	
	public static void loadPlayers(MinecraftServer server) {
		server.getPlayerList().getPlayers().forEach(player -> {
			ServerLevel serverLevel=server.overworld();
			serverLevel.getChunkSource().addEntity(player);
		});
	}

}