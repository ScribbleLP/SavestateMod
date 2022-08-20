package com.minecrafttas.savestatemod.savestates;

import java.util.List;

import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.storage.ChunkStorage;

public class WorldHacks {
	
	public static void unloadPlayers() {
		MinecraftServer server=Minecraft.getInstance().getSingleplayerServer();
		ServerLevel world=server.overworld();
		List<ServerPlayer> players = server.getPlayerList().getPlayers();
		for (ServerPlayer player : players) {
			world.getChunkSource().removeEntity(player);
		}
	}
	
	public static void unloadWorld() {
		MinecraftServer mcserver = Minecraft.getInstance().getSingleplayerServer();
		ServerLevel level=mcserver.overworld();
		ServerChunkCache chunkSource=level.getChunkSource();
		
		ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
		
		level.save(null, true, false);
		
		chunkSource.removeRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
		
	}
	
	public static void loadWorld() {
		MinecraftServer mcserver = Minecraft.getInstance().getSingleplayerServer();
		ServerLevel level=mcserver.overworld();
		ServerChunkCache chunkSource=level.getChunkSource();
		
		ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
		
		((RegionFileStorageDuck)(ChunkStorage)chunkSource.chunkMap).clearRegionFileStorage();
		chunkSource.addRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
		
	}
	
	public static void loadPlayer() {
		Minecraft.getInstance().getSingleplayerServer().getPlayerList().getPlayers().forEach(player -> {
//			((AccessorEntity)player).invokeUnsetRemoved();
			MinecraftServer server =Minecraft.getInstance().getSingleplayerServer();
			ServerLevel serverLevel=server.overworld();
			serverLevel.getChunkSource().addEntity(player);
		});
	}

}