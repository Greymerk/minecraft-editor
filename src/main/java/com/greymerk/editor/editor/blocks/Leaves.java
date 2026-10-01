package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.LeavesBlock;

public class Leaves {
	
	public static MetaBlock get(Wood type, boolean persistent){
		return MetaBlock.of(Wood.getLeaf(type)).with(LeavesBlock.PERSISTENT, persistent);
	}
}
