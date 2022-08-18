package com.minecrafttas.savestatemod.networking.duck;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>Adds methods to the ServerboundCustomPayloadPacket.
 * <p>
 * <p>These methods are missing <i>for some reason</i> in the server packet, while they are present in the client packet.
 * <p>
 * <p>You can work around this by using accessors, but getData would have to be handled seperately everytime you call the accessor...
 * <p>This way, you can have the same methods as the ClientPacket without much headaches...
 * 
 * @author Scribble
 *
 */
public interface ServerboundCustomPayloadPacketDuck {
	/**
	 * @return The identifier of the packet as a resource location
	 */
	public ResourceLocation getIdentifier();
	
	/**
	 * @return Copy of the data from the packet
	 */
	public FriendlyByteBuf getData();
}
