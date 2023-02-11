package com.minecrafttas.savestatemod.savestates;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.minecrafttas.savestatemod.mixin.accessors.AccessorLevelStorage;
import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;
import com.mojang.serialization.Lifecycle;

import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.server.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.util.Unit;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;

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
			((RegionFileStorageDuck)(ChunkStorage)chunkSource.chunkMap).clearRegionFileStorage();
			
			if(level.dimensionType() == DimensionType.defaultOverworld()) {
				ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
				chunkSource.removeRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
			}
		}
		
		server.runAllTasks();
	}
	
	public static void loadWorlds(MinecraftServer server) {
		
		Iterator<ServerLevel> levels = server.getAllLevels().iterator();
		
		AccessorLevelStorage storage = (AccessorLevelStorage)server;
		LevelStorageAccess access = storage.getStorageSource();
		WorldData worldData = access.getDataTag(null, null);
		storage.setWorldData(worldData);
		
		
		while(levels.hasNext()) {
			ServerLevel level = levels.next();
			ServerChunkCache chunkSource=level.getChunkSource();
			
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

	
	private WorldData loadWorldData(LevelStorageAccess levelStorageAccess) {
		Object dedicatedServerProperties;
        Object worldGenSettings;
        Object levelSettings;
        ServerResources serverResources;
		DataPackConfig dataPackConfig = levelStorageAccess.getDataPacks();
        PackRepository<Pack> packRepository = new PackRepository<Pack>(Pack::new, new ServerPacksSource(), new FolderRepositorySource(levelStorageAccess.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD));
        DataPackConfig dataPackConfig2 = MinecraftServer.configurePackRepository(packRepository, dataPackConfig == null ? DataPackConfig.DEFAULT : dataPackConfig, bl);
        CompletableFuture<ServerResources> completableFuture = ServerResources.loadResources(packRepository.openAllSelected(), Commands.CommandSelection.DEDICATED, dedicatedServerSettings.getProperties().functionPermissionLevel, Util.backgroundExecutor(), Runnable::run);
        try {
            serverResources = completableFuture.get();
        }
        catch (Exception exception) {
            LOGGER.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)exception);
            packRepository.close();
            return null;
        }
        serverResources.updateGlobals();
        RegistryAccess.RegistryHolder exception = RegistryAccess.builtin();
        RegistryReadOps<Tag> registryReadOps = RegistryReadOps.create(NbtOps.INSTANCE, serverResources.getResourceManager(), exception);
        WorldData worldData = levelStorageAccess.getDataTag(registryReadOps, dataPackConfig2);
        if (worldData == null) {
            if (optionSet.has(optionSpec3)) {
                levelSettings = MinecraftServer.DEMO_SETTINGS;
                worldGenSettings = WorldGenSettings.DEMO_SETTINGS;
            } else {
                dedicatedServerProperties = dedicatedServerSettings.getProperties();
                levelSettings = new LevelSettings(((DedicatedServerProperties)dedicatedServerProperties).levelName, ((DedicatedServerProperties)dedicatedServerProperties).gamemode, ((DedicatedServerProperties)dedicatedServerProperties).hardcore, ((DedicatedServerProperties)dedicatedServerProperties).difficulty, false, new GameRules(), dataPackConfig2);
                worldGenSettings = optionSet.has(optionSpec4) ? ((DedicatedServerProperties)dedicatedServerProperties).worldGenSettings.withBonusChest() : ((DedicatedServerProperties)dedicatedServerProperties).worldGenSettings;
            }
            worldData = new PrimaryLevelData((LevelSettings)levelSettings, (WorldGenSettings)worldGenSettings, Lifecycle.stable());
        }
        if (optionSet.has(optionSpec5)) {
            Main.forceUpgrade(levelStorageAccess, DataFixers.getDataFixer(), optionSet.has(optionSpec6), () -> true, worldData.worldGenSettings().levels());
        }
        levelStorageAccess.saveDataTag(exception, worldData);
        return worldData;
	}
}