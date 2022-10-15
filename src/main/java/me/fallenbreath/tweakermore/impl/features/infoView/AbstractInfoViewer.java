package me.fallenbreath.tweakermore.impl.features.infoView;

import fi.dy.masa.malilib.config.IConfigBoolean;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigOptionList;
import me.fallenbreath.tweakermore.config.options.listentries.InfoViewStrategy;
import me.fallenbreath.tweakermore.util.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public abstract class AbstractInfoViewer
{
	private final IConfigBoolean switchConfig;
	private final Supplier<InfoViewStrategy> strategySupplier;

	public AbstractInfoViewer(IConfigBoolean switchConfig, Supplier<InfoViewStrategy> strategySupplier)
	{
		this.switchConfig = switchConfig;
		this.strategySupplier = strategySupplier;
	}
	public AbstractInfoViewer(IConfigBoolean switchConfig, TweakerMoreConfigOptionList strategyOption)
	{
		this(switchConfig, () -> (InfoViewStrategy)strategyOption.getOptionListValue());
	}

	public abstract void render(RenderContext context, World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity);

	/**
	 * If this viewer works for and should render for given context
	 * @param world The current world get from {@link fi.dy.masa.malilib.util.WorldUtils#getBestWorld}
	 * @param blockPos The block pos the player looking at
	 * @param blockState The block state the player looking at
	 * @param blockEntity The block entity the player looking at
	 */
	public abstract boolean shouldRenderFor(World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity);

	protected boolean isEnabled()
	{
		if (this.switchConfig.getBooleanValue())
		{
			switch (this.strategySupplier.get())
			{
				case HOTKEY_HELD:
					return TweakerMoreConfigs.INFO_VIEW_RENDERING_KEY.isKeybindHeld();
				case ALWAYS:
					return true;
			}
		}
		return false;
	}
}
