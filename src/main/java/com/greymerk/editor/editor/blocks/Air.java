package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.Blocks;

public class Air {

	public static MetaBlock get() {
		return MetaBlock.of(Blocks.CAVE_AIR);
	}
	
}
