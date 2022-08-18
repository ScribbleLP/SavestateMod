package com.minecrafttas.savestatemod.savestates;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import com.minecrafttas.savestatemod.SavestateMod;
import com.minecrafttas.savestatemod.mixin.accessors.AccessorLevelStorage;
import com.minecrafttas.savestatemod.networking.IServerPacketHandler;
import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;
import com.minecrafttas.savestatemod.savestates.exceptions.LoadstateException;
import com.minecrafttas.savestatemod.savestates.exceptions.SavestateException;
import com.minecrafttas.savestatemod.tickratechanger.TickAdvance;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
	private static final ResourceLocation LOADSTATE_RL = new ResourceLocation("savestatemod", "loadstate");

	public SavestateHandler(MinecraftServer server, Logger logger) {
		this.server = server;
		createSavestateDirectory();
		this.logger = logger;
	}

	private void createSavestateDirectory() {
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
		createSavestateDirectory();

		// Enable tickrate 0
		TickAdvance tickadvance = SavestateMod.getInstance().getTickAdvance();
		tickadvance.tickadvance = true;
		
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

		tickadvance.tickadvance = false;
		
		// Unlock savestating
		state = SavestateState.NONE;
		sendMessage(server, new TextComponent(ChatFormatting.GREEN + "Savestate saved!"));
	}

	public void loadstate() throws LoadstateException, IOException{
		if (state == SavestateState.SAVING) {
			throw new LoadstateException("A savestating operation is already being carried out");
		}
		if (state == SavestateState.LOADING) {
			throw new LoadstateException("A loadstate operation is being carried out");
		}
		// Lock savestating and loadstating
		state = SavestateState.LOADING;
		
		// Enable tickrate 0
		TickAdvance tickadvance = SavestateMod.getInstance().getTickAdvance();
		tickadvance.tickadvance = true;
		
		// Create a directory just in case
		createSavestateDirectory();
		
		// Get the current and target directory for copying
		String worldname = getWorldName();
		File currentfolder = new File(savestateDirectory, ".." + File.separator + worldname);
		File targetfolder = new File(savestateDirectory, worldname);
		
		// Unload chunks on the server
		WorldHacks.unloadPlayers();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WorldHacks.unloadWorld();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		FileUtils.deleteDirectory(currentfolder);
		FileUtils.copyDirectory(targetfolder, currentfolder);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WorldHacks.loadWorld();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WorldHacks.loadPlayer();
		
		tickadvance.tickadvance = false;
		
		state = SavestateState.NONE;
		
		sendMessage(server, new TextComponent(ChatFormatting.GREEN + "Savestate loaded"));
	}
	
	@Environment(EnvType.CLIENT)
	public void requestSavestate() {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		Minecraft mc = Minecraft.getInstance();
		mc.getConnection().send(new ServerboundCustomPayloadPacket(SAVESTATE_RL, buf));
	}
	
	@Environment(EnvType.CLIENT)
	public void requestLoadstate() {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		Minecraft mc = Minecraft.getInstance();
		mc.getConnection().send(new ServerboundCustomPayloadPacket(LOADSTATE_RL, buf));
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
		else if(packet.getIdentifier().equals(LOADSTATE_RL)) {
			try {
				logger.info("Loadstating!");
				loadstate();
			} catch (LoadstateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendMessage(MinecraftServer server, Component component) {
		server.getPlayerList().broadcastMessage(component, ChatType.CHAT, Util.NIL_UUID);
	}

}
