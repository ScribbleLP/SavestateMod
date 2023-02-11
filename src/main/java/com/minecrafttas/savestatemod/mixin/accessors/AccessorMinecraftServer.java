package com.minecrafttas.savestatemod.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public interface AccessorMinecraftServer {
	@Invoker("waitUntilNextTick")
	public void invokeWaitUntilNextTick();
	
}
