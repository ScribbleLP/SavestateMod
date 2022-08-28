/**
 * Here is the logic of the tickrate changer:
 *
 * As noted by the @Environment annotations in front of methods, this code works on both client and server.
 *
 * Every time the clients wants to change the tickrate it sends a request tickrate change packet to the server. #requestTickrateUpdate ~~> #requestTickrateUpdate
 * The server proceeds by changing it's tickrate and sends a tickrate change packet to the client from it's listener. #onServerPacket -> #updateTickrate
 * The clients listener finally updates the client tickrate too. #onClientPacket -> #internallyUpdateTickrate
 */
package com.minecrafttas.savestatemod.tickratechanger;

import com.minecrafttas.savestatemod.networking.IClientPacketHandler;
import com.minecrafttas.savestatemod.networking.IServerPacketHandler;
import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Main tickrate changer
 * @author Pancake
 */
public class TickrateChanger implements IClientPacketHandler, IServerPacketHandler{

	static final ResourceLocation TICKRATE_CHANGER_RL = new ResourceLocation("lotas", "tickratechanger");
	@Environment(EnvType.CLIENT)
	public Minecraft mc;
	public MinecraftServer mcserver;

	// Tickrate Variables in various formats
	private double tickrate = 20.0;
	private double msPerTick = 50.0;
	private double gamespeed = 1.0;

	// Recommended tickrate
	@Environment(EnvType.CLIENT)
	private double[] tickrates = new double[] {
		0.5f, 1.0f, 2.0f, 4.0f, 5.0f, 10.0f, 20.0f	
	};
	
	// Tickrate Changer Millisecond
	@Environment(EnvType.CLIENT)
	private long timeSinceTC = System.currentTimeMillis();
	@Environment(EnvType.CLIENT)
	private long fakeTimeSinceTC = System.currentTimeMillis();

	/**
	 * Updates the Client tickrate when receiving a packet
	 * @param p Incoming Packet
	 */
	@Override
	@Environment(EnvType.CLIENT)
	public void onClientPacket(ClientboundCustomPayloadPacket p, ClientPacketListener clientPacketListener, Minecraft minecraft) {
		if (TICKRATE_CHANGER_RL.equals(p.getIdentifier())) {
			this.internallyUpdateTickrate(p.getData().readDouble());
			// Update the local time
			long time = System.currentTimeMillis() - this.timeSinceTC;
			this.fakeTimeSinceTC += time * this.gamespeed;
			this.timeSinceTC = System.currentTimeMillis();
			// Update the Tickrate Changer LoWidget
		}
	}


	@Override
	public void onServerPacket(ServerboundCustomPayloadPacketDuck packet, ServerGamePacketListenerImpl serverGamePacketListenerImpl, ServerPlayer player) {
		if (TICKRATE_CHANGER_RL.equals(packet.getIdentifier())) this.updateTickrate(packet.getData().readDouble());
	}

	/**
	 * Client-Side only tickrate update request. Sends a packet to the server updating the tickrate.
	 * @param tickrate Tickrate to update to
	 */
	@Environment(EnvType.CLIENT)
	public void requestTickrateUpdate(double tickrate) {
		// Request tickrate update
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeDouble(tickrate);
		ServerboundCustomPayloadPacket p = new ServerboundCustomPayloadPacket(TICKRATE_CHANGER_RL, buf);
		this.mc.getConnection().send(p);
		// Send notification
	}

	/**
	 * Server-Side only tickrate update. Sends a packet to all players
	 * @param tickrate Tickrate to update to
	 */
	public void updateTickrate(double tickrate) {
		if (tickrate < 0.1) return;
		this.internallyUpdateTickrate(tickrate);
		// Update Tickrate for all Clients
		this.mcserver.getPlayerList().getPlayers().forEach(c -> {
			FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
			buf.writeDouble(tickrate);
			ClientboundCustomPayloadPacket p = new ClientboundCustomPayloadPacket(TICKRATE_CHANGER_RL, buf);
			c.connection.send(p);
		});
	}

	/**
	 * Internally update the tickrate for the game
	 * @param tickrate Tickrate to update to
	 */
	private void internallyUpdateTickrate(double tickrate) {
		this.tickrate = tickrate;
		this.msPerTick = 1000.0 / tickrate;
		this.gamespeed = tickrate / 20;
	}

	/**
	 * Client-side only slowed down millisecond counter
	 * @return
	 */
	@Environment(EnvType.CLIENT)
	public long getMilliseconds() {
		long time = System.currentTimeMillis() - this.timeSinceTC;
		time *= this.gamespeed;
		return this.fakeTimeSinceTC + time;
	}

	/**
	 * Clears local data on disconnect
	 */
	@Environment(EnvType.CLIENT)
	public void onDisconnect() {
		this.internallyUpdateTickrate(20.0);
	}

	/**
	 * Updates client data on connect
	 */
	public void onConnect(ServerPlayer c) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeDouble(this.tickrate);
		ClientboundCustomPayloadPacket p = new ClientboundCustomPayloadPacket(TICKRATE_CHANGER_RL, buf);
		c.connection.send(p);
	}

	/**
	 * Decreases the tickrate to the nearest recommended value
	 */
	@Environment(EnvType.CLIENT)
	public void decreaseTickrate() {
		double newTickrate = this.tickrate;
		for (int i = this.tickrates.length-1; i >= 0; i--)
			if ((newTickrate = this.tickrates[i]) < this.tickrate)
				break;
		
		this.requestTickrateUpdate(newTickrate);
	}

	/**
	 * Increases the tickrate to the nearest recommended value
	 */
	@Environment(EnvType.CLIENT)
	public void increaseTickrate() {
		double newTickrate = this.tickrate;
		for (int i = 0; i < this.tickrates.length; i++)
			if ((newTickrate = this.tickrates[i]) > this.tickrate)
				break;
		
		this.requestTickrateUpdate(newTickrate);
	}
	
	// Place getters here to not confuse with public variables that shall not be set

	public double getTickrate() {
		return this.tickrate;
	}

	public double getMsPerTick() {
		return this.msPerTick;
	}

	public double getGamespeed() {
		return this.gamespeed;
	}

}