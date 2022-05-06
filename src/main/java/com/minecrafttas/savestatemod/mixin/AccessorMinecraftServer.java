package com.minecrafttas.savestatemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public interface AccessorMinecraftServer {
	@Invoker("loadLevel")
	public void invokeLoadLevel();
}
