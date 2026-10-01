package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.util.Color;

import net.minecraft.world.level.block.GlazedTerracottaBlock;

public class Terracotta {
	public static MetaBlock get(Color color, Cardinal dir){
		return ColorBlock.get(ColorBlock.GLAZED, color)
				.with(GlazedTerracottaBlock.FACING, Cardinal.facing(dir));
	}
}
