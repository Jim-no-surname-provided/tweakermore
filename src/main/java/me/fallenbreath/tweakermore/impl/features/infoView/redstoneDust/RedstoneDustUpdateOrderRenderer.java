/*
 * This file is part of the TweakerMore project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * TweakerMore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TweakerMore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TweakerMore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.tweakermore.impl.features.infoView.redstoneDust;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.config.options.listentries.InfoViewTargetStrategy;
import me.fallenbreath.tweakermore.impl.features.infoView.AbstractInfoViewer;
import me.fallenbreath.tweakermore.util.render.RenderContext;
import me.fallenbreath.tweakermore.util.render.TextRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RedstoneDustUpdateOrderRenderer extends AbstractInfoViewer
{
	public RedstoneDustUpdateOrderRenderer()
	{
		super(
				TweakerMoreConfigs.INFO_VIEW_REDSTONE_DUST_UPDATE_ORDER,
				TweakerMoreConfigs.INFO_VIEW_REDSTONE_DUST_UPDATE_ORDER_RENDER_STRATEGY,
				() -> InfoViewTargetStrategy.POINTED
		);
	}

	@Override
	public boolean shouldRenderFor(World world, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity)
	{
		return blockState.getBlock() instanceof RedstoneWireBlock;
	}

	@Override
	public boolean requireBlockEntitySyncing()
	{
		return false;
	}

	@Override
	public void render(RenderContext context, World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity)
	{
		int alphaBits = (int)Math.round(255 * TweakerMoreConfigs.INFO_VIEW_REDSTONE_DUST_UPDATE_ORDER_TEXT_ALPHA.getDoubleValue());
		if (alphaBits == 0)
		{
			return;
		}
		int color = Objects.requireNonNull(Formatting.RED.getColorValue()) | ((alphaBits & 0xFF) << 24);

		List<BlockPos> order = getDustUpdateOrderAt(blockPos);
		for (int i = 0; i < order.size(); i++)
		{
			this.renderTextAtPos(context, order.get(i), String.valueOf(i + 1), color);
		}
	}

	private void renderTextAtPos(RenderContext context, BlockPos pos, String text, int color)
	{
		TextRenderer.create().
				text(text).atCenter(pos).
				color(color).
				shadow().seeThrough().
				render();
	}

	/**
	 * Yeah, that's how dust generate the update emitting order
	 */
	private static List<BlockPos> getDustUpdateOrderAt(BlockPos pos)
	{
		Set<BlockPos> set = Sets.newHashSet();
		set.add(pos);
		for (Direction direction : Direction.values())
		{
			set.add(pos.offset(direction));
		}
		return Lists.newArrayList(set);
	}
}
