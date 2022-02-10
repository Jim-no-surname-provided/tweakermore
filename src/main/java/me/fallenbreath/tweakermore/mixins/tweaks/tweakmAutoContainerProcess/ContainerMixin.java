package me.fallenbreath.tweakermore.mixins.tweaks.tweakmAutoContainerProcess;

import me.fallenbreath.tweakermore.impl.tweakmAutoContainerProcess.ContainerProcessor;
import me.fallenbreath.tweakermore.util.ModIds;
import me.fallenbreath.tweakermore.util.mixin.Condition;
import me.fallenbreath.tweakermore.util.mixin.ModRequire;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ModRequire(enableWhen = @Condition(ModIds.itemscroller))
@Mixin(ScreenHandler.class)
public abstract class ContainerMixin
{
	@Inject(method = "updateSlotStacks", at = @At("TAIL"))
	private void tweakmAutoFillContainer(CallbackInfo ci)
	{
		ContainerProcessor.process((ScreenHandler)(Object)this);
	}
}
