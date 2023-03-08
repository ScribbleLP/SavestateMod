package com.minecrafttas.savestatemod.savestates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import com.minecrafttas.savestatemod.SavestateMod;
import com.minecrafttas.savestatemod.mixin.accessors.AccessorBlockableEventLoop;
import com.minecrafttas.savestatemod.mixin.accessors.AccessorLevelStorage;
import com.minecrafttas.savestatemod.savestates.duck.RegionFileStorageDuck;
import com.mojang.datafixers.util.Pair;

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
//#1.19.3
//$$import net.minecraft.core.LayeredRegistryAccess;
//$$import net.minecraft.core.Registry;
//$$import net.minecraft.server.RegistryLayer;
//$$import net.minecraft.server.WorldLoader;
//$$import com.minecrafttas.savestatemod.SavestateMod;
//$$import net.minecraft.world.flag.FeatureFlags;
//$$import net.minecraft.world.level.dimension.LevelStem;
//$$import net.minecraft.server.WorldLoader.DataLoadContext;
//$$import net.minecraft.server.WorldLoader.InitConfig;
//$$import net.minecraft.server.dedicated.DedicatedServerProperties;
//$$import net.minecraft.world.level.WorldDataConfiguration;
//$$import net.minecraft.server.packs.resources.CloseableResourceManager;
//$$import net.minecraft.server.dedicated.DedicatedServerSettings;
//$$import net.minecraft.resources.RegistryDataLoader;
//$$import net.minecraft.core.registries.Registries;
//#end
//#1.16.1
//##1.18.2
//$$import net.minecraft.resources.RegistryOps;
//$$import net.minecraft.nbt.Tag;
//##def
//$$import net.minecraft.resources.RegistryReadOps;
//$$import net.minecraft.server.ServerResources;
//##end
//$$import net.minecraft.Util;
//$$import net.minecraft.commands.Commands.CommandSelection;
//$$import java.util.concurrent.CompletableFuture;
//$$import net.minecraft.core.RegistryAccess;
//$$import net.minecraft.nbt.NbtOps;
//$$import net.minecraft.server.packs.repository.FolderRepositorySource;
//$$import net.minecraft.server.packs.repository.PackRepository;
//$$import net.minecraft.server.packs.repository.PackSource;
//$$import net.minecraft.server.packs.repository.ServerPacksSource;
//$$import net.minecraft.world.level.DataPackConfig;
//$$import net.minecraft.world.level.Level;
//$$import net.minecraft.world.level.storage.LevelResource;
//$$import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
//$$import net.minecraft.world.level.storage.ServerLevelData;
//$$import net.minecraft.world.level.storage.WorldData;
//$$import net.minecraft.world.level.storage.DerivedLevelData;
//$$import com.minecrafttas.savestatemod.mixin.accessors.AccessorServerLevel;
//$$import com.minecrafttas.savestatemod.mixin.accessors.AccessorLevel;
//#def
//#end
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelStorageSource;

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
			
			//#1.16.1
//$$			if(level.dimension() == Level.OVERWORLD) {
			//#def
//$$			if(level.dimension.getType() == DimensionType.OVERWORLD) {
			//#end
				ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
				chunkSource.removeRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
			}
		}
		
		((AccessorBlockableEventLoop) server).runRunAllTasks();
	}
	
	public static void loadWorlds(MinecraftServer server) {
		
		Iterator<ServerLevel> levels = server.getAllLevels().iterator();
		
		AccessorLevelStorage storage = (AccessorLevelStorage)server;
		
		/*Loading the world/level data from file*/
		
		//#1.16.1
//$$		LevelStorageAccess access = storage.getStorageSource();
//$$		WorldData worldData;
//$$		try {
			//##1.19.3
//$$			worldData = loadWorldData(access);
			//##1.18.2
//$$			worldData = loadWorldData(access, server);
			//##def
//$$			worldData = loadWorldData(access);
			//##end
//$$		} catch (Exception e) {
//$$			e.printStackTrace();
//$$			return;
//$$		}
//$$		
//$$		storage.setWorldData(worldData);
		//#def
//$$		LevelData worldData = loadWorldData(server);
		//#end
		
		/*Applying the level data*/
		
		//#1.16.1
//$$		while(levels.hasNext()) {
//$$			ServerLevel level = levels.next();
//$$			ServerLevelData data = worldData.overworldData();
//$$			
//$$			if(level.dimension()!=Level.OVERWORLD) {
//$$				data = new DerivedLevelData(worldData, data);
//$$			}
//$$			
//$$			((AccessorServerLevel)level).setServerLevelData(data);
//$$			((AccessorLevel)level).setLevelData(data);
//$$			
//$$			ServerChunkCache chunkSource=level.getChunkSource();
//$$			
//$$			if(level.dimension() == Level.OVERWORLD) {
//$$				ChunkPos chunkPos = new ChunkPos(new BlockPos(level.getLevelData().getXSpawn(), 0, level.getLevelData().getZSpawn()));
//$$				chunkSource.addRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
//$$			}
//$$		}
		//#def
		//#end
	}
	
	public static void loadPlayers(MinecraftServer server) {
		//#1.16.1
//$$		server.getPlayerList().getPlayers().forEach(player -> {
//$$			ServerLevel serverLevel=server.overworld();
//$$			serverLevel.getChunkSource().addEntity(player);
//$$		});
		//#end
	}

// # 1.19.3
//$$	private static WorldData loadWorldData(LevelStorageAccess levelStorageAccess) throws IOException {
//$$		InitConfig initConfig = loadOrCreateConfig();
//$$        Pair<WorldDataConfiguration, CloseableResourceManager> pair = initConfig.packConfig.createResourceManager();
//$$        CloseableResourceManager closeableResourceManager = pair.getSecond();
//$$        LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess = RegistryLayer.createRegistryAccess();
//$$        LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess2 = WorldLoader.loadAndReplaceLayer(closeableResourceManager, layeredRegistryAccess, RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);
//$$        RegistryAccess.Frozen frozen = layeredRegistryAccess2.getAccessForLoading(RegistryLayer.DIMENSIONS);
//$$        RegistryAccess.Frozen frozen2 = RegistryDataLoader.load(closeableResourceManager, frozen, RegistryDataLoader.DIMENSION_REGISTRIES);
//$$        WorldDataConfiguration worldDataConfiguration = pair.getFirst();
//$$		
//$$		DataLoadContext context = new DataLoadContext(closeableResourceManager, worldDataConfiguration, frozen, frozen2);
//$$		Registry<LevelStem> registry = context.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
//$$		
//$$        RegistryOps<Tag> dynamicOps = RegistryOps.create(NbtOps.INSTANCE, context.datapackWorldgen());
//$$        return levelStorageAccess.getDataTag(dynamicOps, levelStorageAccess.getDataConfiguration(), registry, context.datapackWorldgen().allRegistriesLifecycle()).getFirst();
//$$	}
	// # 1.18.2
//$$	private static WorldData loadWorldData(LevelStorageAccess levelStorageAccess, MinecraftServer server) {
//$$        RegistryAccess.Writable writable = RegistryAccess.builtinCopy();
//$$        RegistryOps<Tag> dynamicOps = RegistryOps.createAndLoad(NbtOps.INSTANCE, writable, server.getResourceManager());
//$$        return levelStorageAccess.getDataTag(dynamicOps, levelStorageAccess.getDataPacks(), writable.allElementsLifecycle());
//$$	}
	// # 1.16.1
//$$	private static WorldData loadWorldData(LevelStorageAccess levelStorageAccess) {
//$$        ServerResources serverResources;
//$$		DataPackConfig dataPackConfig = levelStorageAccess.getDataPacks();
		// ## 1.17.1
//$$		PackRepository packRepository = new PackRepository(net.minecraft.server.packs.PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(levelStorageAccess.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD));
//$$        DataPackConfig dataPackConfig2 = MinecraftServer.configurePackRepository(packRepository, dataPackConfig == null ? DataPackConfig.DEFAULT : dataPackConfig, false);
//$$        CompletableFuture<ServerResources> completableFuture = ServerResources.loadResources(packRepository.openAllSelected(), RegistryAccess.builtin(), CommandSelection.DEDICATED, 2, Util.backgroundExecutor(), Runnable::run);
		// ## def
        // ### 1.16.5
//$$		PackRepository packRepository = new PackRepository(new ServerPacksSource(), new FolderRepositorySource(levelStorageAccess.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD));
		// ### def
//$$        PackRepository<net.minecraft.server.packs.repository.Pack> packRepository = new PackRepository<net.minecraft.server.packs.repository.Pack>(net.minecraft.server.packs.repository.Pack::new, new ServerPacksSource(), new FolderRepositorySource(levelStorageAccess.getLevelPath(LevelResource.DATAPACK_DIR).toFile(), PackSource.WORLD));
        // ### end
//$$        DataPackConfig dataPackConfig2 = MinecraftServer.configurePackRepository(packRepository, dataPackConfig == null ? DataPackConfig.DEFAULT : dataPackConfig, false);
//$$        CompletableFuture<ServerResources> completableFuture = ServerResources.loadResources(packRepository.openAllSelected(), CommandSelection.DEDICATED, 2, Util.backgroundExecutor(), Runnable::run);
		// ## end
//$$        try {
//$$            serverResources = completableFuture.get();
//$$        }
//$$        catch (Exception exception) {
//$$            packRepository.close();
//$$            return null;
//$$        }
//$$        serverResources.updateGlobals();
//$$        RegistryAccess.RegistryHolder exception = RegistryAccess.builtin();
//$$        RegistryReadOps<net.minecraft.nbt.Tag> registryReadOps = RegistryReadOps.create(NbtOps.INSTANCE, serverResources.getResourceManager(), exception);
//$$        WorldData worldData = levelStorageAccess.getDataTag(registryReadOps, dataPackConfig2);
//$$        return worldData;
//$$	}
	//# def
//$$	private static LevelData loadWorldData(MinecraftServer server) {
//$$		SavestateHandler handler = SavestateMod.getInstance().getSavestateHandler();
//$$		return LevelStorageSource.getLevelData(new java.io.File(handler.getSavestateDirectory()+".." + File.separator + server.getLevelIdName(), "level.dat"), server.getFixerUpper());
//$$	}
	// # end
	
	//#1.19.3
//$$    private static WorldLoader.InitConfig loadOrCreateConfig() throws IOException {
//$$    	Path path = Paths.get("server.properties", new String[0]);
//$$        DedicatedServerSettings dedicatedServerSettings = new DedicatedServerSettings(path);
//$$        dedicatedServerSettings.forceSave();
//$$        
//$$        File file = new File(".");
//$$        String string = dedicatedServerSettings.getProperties().levelName;
//$$        
//$$        
//$$        LevelStorageSource levelStorageSource = LevelStorageSource.createDefault(file.toPath());
//$$        LevelStorageSource.LevelStorageAccess levelStorageAccess = levelStorageSource.createAccess(string);
//$$        PackRepository packRepository = ServerPacksSource.createPackRepository(levelStorageAccess.getLevelPath(LevelResource.DATAPACK_DIR));
//$$        
//$$        DedicatedServerProperties dedicatedServerProperties = dedicatedServerSettings.getProperties();
//$$        
//$$        WorldDataConfiguration worldDataConfiguration2;
//$$        boolean bl2;
//$$        WorldDataConfiguration worldDataConfiguration = levelStorageAccess.getDataConfiguration();
//$$        if (worldDataConfiguration != null) {
//$$            bl2 = false;
//$$            worldDataConfiguration2 = worldDataConfiguration;
//$$        } else {
//$$            bl2 = true;
//$$            worldDataConfiguration2 = new WorldDataConfiguration(dedicatedServerProperties.initialDataPackConfiguration, FeatureFlags.DEFAULT_FLAGS);
//$$        }
//$$        WorldLoader.PackConfig packConfig = new WorldLoader.PackConfig(packRepository, worldDataConfiguration2, false, bl2);
//$$        return new WorldLoader.InitConfig(packConfig, CommandSelection.DEDICATED, dedicatedServerProperties.functionPermissionLevel);
//$$    }
    //#end
}
