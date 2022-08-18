package com.minecrafttas.savestatemod.networking;

import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;

public interface IClientPacketHandler {
	public void onClientPacket(ClientboundCustomPayloadPacket packet);
}
