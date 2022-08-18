package com.minecrafttas.savestatemod.mixin.client.networking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;

/**
 * Increases Minecrafts 32k bytes packet size limit.
 * 
 * @author Pancake
 */
@Mixin(ClientboundCustomPayloadPacket.class)
public class MixinClientboundCustomPayloadPacket {
	@ModifyConstant(method = "Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 0x100000))
	public int redirect_readableBytesButCool(int original) {
		return Integer.MAX_VALUE;
	}

	@ModifyConstant(method = "Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 0x100000))
	public int redirect_readableBytes(int original) {
		return Integer.MAX_VALUE;
	}
}
