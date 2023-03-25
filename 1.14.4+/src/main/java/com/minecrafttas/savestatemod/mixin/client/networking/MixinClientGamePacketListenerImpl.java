package com.minecrafttas.savestatemod.mixin.client.networking;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecrafttas.savestatemod.networking.NetworkRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;

/**
 * Firing events for the NetworkRegistry
 * @author Pancake
 */
@Mixin(ClientPacketListener.class)
@Environment(EnvType.CLIENT)
public class MixinClientGamePacketListenerImpl {
	
	@Shadow
	private Minecraft minecraft;
	
	@Inject(method = "handleCustomPayload", at = @At("HEAD"))
	public void hookCustomPayloadEvent(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
		NetworkRegistry.fireClientPackets(packet, (ClientPacketListener)(Object)this, minecraft);
	}
	
	@Redirect(method = "handleCustomPayload", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"))
	public void redirect_handleCustomPayload(Logger logger, String msg, Object obj) {
	}
}
