package com.minecrafttas.savestatemod.mixin.networking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.minecrafttas.savestatemod.networking.duck.ServerboundCustomPayloadPacketDuck;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

@Mixin(ServerboundCustomPayloadPacket.class)
public class AccessorServerBoundCustomPayloadPacket implements ServerboundCustomPayloadPacketDuck {

	@Shadow
	private ResourceLocation identifier;
	
	@Shadow
	private FriendlyByteBuf data;
	
	@Override
	public ResourceLocation getIdentifier() {
		return identifier;
	}

	@Override
	public FriendlyByteBuf getData() {
		return new FriendlyByteBuf(this.data.copy());
	}

}
