package com.minecrafttas.savestatemod.mixin.client.tickratechanger;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecrafttas.savestatemod.SavestateMod;
import com.minecrafttas.savestatemod.tickratechanger.TickAdvance;
import com.minecrafttas.savestatemod.tickratechanger.TickrateChanger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Timer;

/**
 * This Mixin slows down the integrated Timer making the game run slower
 * @author Pancake
 */
@Mixin(Timer.class)
@Environment(EnvType.CLIENT)
public class MixinTimer {

	@Shadow @Final @Mutable
	public float msPerTick;

	@Inject(method = "advanceTime", at = @At("HEAD"))
	public void onAdvanceTime(CallbackInfoReturnable<Integer> cir) {
		TickrateChanger tickratechanger = SavestateMod.getInstance().getTickrateChanger();
		TickAdvance tickadvance = SavestateMod.getInstance().getTickAdvance();
		
		this.msPerTick = (float) (tickadvance.isTickadvanceEnabled() && !tickadvance.shouldTickClient ? Float.MAX_VALUE : tickratechanger.getMsPerTick());
	}

}
