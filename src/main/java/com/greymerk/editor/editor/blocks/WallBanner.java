package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public class WallBanner {
	
	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Cardinal dir) {
		WallBanner.generate(editor, Banner.get(editor.getRegistryManager(), rand), origin, dir);
	}
	
	public static void generate(IWorldEditor editor, ItemStack banner, Coord origin, Cardinal dir) {
		editor.setBlockEntity(origin,
			MetaBlock.of(Blocks.WALL_BANNER.black())
				.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir)), 
			BannerBlockEntity.class).ifPresent(bannerEntity -> {
				bannerEntity.applyComponentsFromItemStack(banner);
				bannerEntity.setChanged();	
			});
	}	
}
