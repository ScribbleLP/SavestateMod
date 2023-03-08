package com.minecrafttas.savestatemod.networking;

import java.util.ArrayList;
import java.util.List;

import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Simple network packet registry for adding handlers to classes.
 * <p>
 * <h2>Registering</h2>
 * <ol>
 * <li>Implement IClientPacketHandler or IServerPacketHandler or both.</li>
 * <li>Register in initialization class with either {@linkplain #registerClient(IClientPacketHandler...)} or {@linkplain #registerServer(IServerPacketHandler...)}</li>
 * </ol>
 * 
 * 
 * @author Scribble
 *
 */
public class NetworkRegistry {
	private static final List<IClientPacketHandler> clientRegistry = new ArrayList<>();
	private static final List<IServerPacketHandler> serverRegistry = new ArrayList<>();

	/**
	 * Register one or more packet handlers on the client side
	 * @param clientPacketHandlers One or more client packet handlers
	 */
	public static void registerClient(IClientPacketHandler... clientPacketHandlers) {
		for (IClientPacketHandler iClientPacket : clientPacketHandlers) {
			clientRegistry.add(iClientPacket);
		}
	}

	/**
	 * Register one or more packet handlers on the server side
	 * @param serverPacketHandlers One or more server packet handlers
	 */
	public static void registerServer(IServerPacketHandler... serverPacketHandlers) {
		for (IServerPacketHandler iServerPacket : serverPacketHandlers) {
			serverRegistry.add(iServerPacket);
		}
	}

	/**
	 * Unregister one or more packet handlers on the client side
	 * @param clientPacketHandlers One or more client packet handlers
	 */
	public static void unregisterClient(IClientPacketHandler... clientPacketHandlers) {
		for (IClientPacketHandler iClientPacket : clientPacketHandlers) {
			clientRegistry.remove(iClientPacket);
		}
	}

	/**
	 * Unregister one or more packet handlers on the server side
	 * @param serverPacketHandlers One or more server packet handlers
	 */
	public static void unregisterServer(IServerPacketHandler... serverPacketHandlers) {
		for (IServerPacketHandler iServerPacket : serverPacketHandlers) {
			serverRegistry.remove(iServerPacket);
		}
	}

	/**
	 * Fires an event when a packet is received on the client side. Do not use!
	 * @param packet
	 * @param minecraft 
	 * @param clientPacketListener 
	 */
	public static void fireClientPackets(ClientboundCustomPayloadPacket packet, ClientPacketListener clientPacketListener, Minecraft minecraft) {
		for (IClientPacketHandler handler : clientRegistry) {
			handler.onClientPacket(packet, clientPacketListener, minecraft);
		}
	}

	/**
	 * Fires an event when a packet is received on the server side. Do not use!
	 * @param packet
	 * @param player 
	 * @param serverGamePacketListenerImpl 
	 */
	public static void fireServerPackets(ServerboundCustomPayloadPacket packet, ServerGamePacketListenerImpl serverGamePacketListenerImpl, ServerPlayer player) {
		for (IServerPacketHandler handler : serverRegistry) {
			handler.onServerPacket((ServerboundCustomPayloadPacketDuck) packet, serverGamePacketListenerImpl, player);
		}
	}

}
