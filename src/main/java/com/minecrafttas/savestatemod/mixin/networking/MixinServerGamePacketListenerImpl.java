package com.minecrafttas.savestatemod.mixin.networking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecrafttas.savestatemod.networking.NetworkRegistry;

import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {
	/**
	 * Triggers an Event in {@link LoTAS#onServerPayload(ServerboundCustomPayloadPacket)} before the game enters the game loop
	 * @param ci Callback Info
	 */
	@Inject(method = "handleCustomPayload", at = @At("HEAD"))
	public void hookCustomPayloadEvent(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
		NetworkRegistry.fireServerPackets(packet);
	}
}
