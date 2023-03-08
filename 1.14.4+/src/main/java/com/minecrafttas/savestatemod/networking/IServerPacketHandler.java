package com.minecrafttas.savestatemod.networking;

import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;

import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Implements an onServerPacket method to this class.
 * <p>
 * This class needs to be registered in your initialization class with {@linkplain NetworkRegistry#registerServer(IServerPacketHandler...)}
 * 
 * @author Scribble
 *
 */
public interface IServerPacketHandler {
	/**
	 * Fired when a {@link ServerboundCustomPayloadPacket} reaches the server.
	 * <p>
	 * Example:
	 * <p>
	 * <blockquote>
	 * <pre>
	 * // Check the packet identifier 
	 * if (ResourceLocation.equals(packet.getIdentifier())) {
	 * 
	 *	// Check if the current thread is the server thread
	 *	PacketUtils.ensureRunningOnSameThread((ServerboundCustomPayloadPacket) packet, 
	 *						serverGamePacketListenerImpl,
	 *						player.getLevel());
	 *	// Read data from the packet
	 *	packet.getData().readDouble();
	 * }
	 * </pre>
	 * </blockquote>
	 * @param packet The sent packet
	 * @param serverGamePacketListenerImpl The packet listener
	 * @param player The Server player where this packet came from
	 */
	public void onServerPacket(ServerboundCustomPayloadPacketDuck packet, ServerGamePacketListenerImpl serverGamePacketListenerImpl, ServerPlayer player);
}
