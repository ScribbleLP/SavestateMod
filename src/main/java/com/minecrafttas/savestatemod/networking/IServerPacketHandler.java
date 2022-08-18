package com.minecrafttas.savestatemod.networking;

import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;

public interface IServerPacketHandler {
	public void onServerPacket(ServerboundCustomPayloadPacketDuck packet);
}
