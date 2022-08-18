package com.minecrafttas.savestatemod.savestates;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import com.minecrafttas.savestatemod.mixin.accessors.AccessorLevelStorage;
import com.minecrafttas.savestatemod.networking.IServerPacketHandler;
import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;
import com.minecrafttas.savestatemod.savestates.exceptions.SavestateException;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;

/**
 * Handles savestating and loadstating
 * 
 * @author Scribble
 *
 */
public class SavestateHandler implements IServerPacketHandler {

	private MinecraftServer server;
	private File savestateDirectory;

	private SavestateState state = SavestateState.NONE;

	private final Logger logger;

	private static final ResourceLocation SAVESTATE_RL = new ResourceLocation("savestatemod", "savestate");

	public SavestateHandler(MinecraftServer server, Logger logger) {
		this.server = server;
		createSaveStateDirectory();
		this.logger = logger;
	}

	private void createSaveStateDirectory() {
		if (!server.isDedicatedServer()) {
			savestateDirectory = new File(server.getServerDirectory() + File.separator + "saves" + File.separator + "savestates" + File.separator);
		} else {
			savestateDirectory = new File(server.getServerDirectory() + File.separator + "savestates" + File.separator);
		}
		if (!savestateDirectory.exists()) {
			savestateDirectory.mkdir();
		}
	}

	private String getWorldName() {
		LevelStorageAccess levelStorage = ((AccessorLevelStorage) server).getStorageSource();
		return levelStorage.getLevelId();
	}

	public void savestate() throws SavestateException, IOException {
		// Don't continue if stuff is already executing
		if (state == SavestateState.SAVING) {
			throw new SavestateException("A savestating operation is already being carried out");
		}
		if (state == SavestateState.LOADING) {
			throw new SavestateException("A loadstate operation is being carried out");
		}

		// Lock savestating and loadstating
		state = SavestateState.SAVING;

		// Create a directory just in case
		createSaveStateDirectory();

		// Request motion from client
		// TODO

		// Save the world to the file system
		server.getPlayerList().saveAll();
		server.saveAllChunks(false, true, true);

		// Get the current and target directory for copying
		String worldname = getWorldName();
		File currentfolder = new File(savestateDirectory, ".." + File.separator + worldname);
		File targetfolder = new File(savestateDirectory, worldname);

		// Overwriting logic
		if (targetfolder.exists()) {
			logger.warn("WARNING! Overwriting the savestate with the index {}", "DefaultIndex");
			FileUtils.deleteDirectory(targetfolder);
		}

		// Copy the directory
		FileUtils.copyDirectory(currentfolder, targetfolder);

		// Tracker file
		// TODO

		// Savestate screen closing
		// TODO

		// Unlock savestating
		state = SavestateState.NONE;
	}

	@Environment(EnvType.CLIENT)
	public void requestSavestate() {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		Minecraft.getInstance().getConnection().send(new ServerboundCustomPayloadPacket(SAVESTATE_RL, buf));
	}


	@Override
	public void onServerPacket(ServerboundCustomPayloadPacketDuck packet) {
		if (packet.getIdentifier().equals(SAVESTATE_RL)) {
			try {
				logger.info("Savestating!");
				savestate();
			} catch (SavestateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
