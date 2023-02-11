package com.minecrafttas.savestatemod.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;

/**
 * Implements an onClientPacket method to this class.
 * <p>
 * This class needs to be registered in your initialization class with {@linkplain NetworkRegistry#registerClient(IClientPacketHandler...)}
 * 
 * @author Scribble
 *
 */
public interface IClientPacketHandler {
	/**
	 * Fired when a {@link ClientboundCustomPayloadPacket} reaches the client.
	 * <p>
	 * Example:
	 * <p>
	 * <blockquote>
	 * <pre>
	 * // Check the packet identifier 
	 * if (ResourceLocation.equals(packet.getIdentifier())) {
	 * 
	 *	// Check if the current thread is the client thread
	 *	PacketUtils.ensureRunningOnSameThread(packet, 
	 *						clientPacketListener,
	 *						minecraft);
	 *	// Read data from the packet
	 *	packet.getData().readDouble();
	 * }
	 * </pre>
	 * </blockquote>
	 * 
	 * @param packet The sent packet
	 * @param clientPacketListener The packet listener
	 * @param minecraft The current Minecraft instance from the client
	 */
	public void onClientPacket(ClientboundCustomPayloadPacket packet, ClientPacketListener clientPacketListener, Minecraft minecraft);
}
