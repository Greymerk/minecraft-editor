package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;



public enum Lantern {

	SOUL, FLAME;
	
	public static void set(IWorldEditor editor, Coord origin) {
		Lantern.set(editor, origin, FLAME, true);
	}
	
	public static void set(IWorldEditor editor, Coord origin, Lantern type, boolean hang) {
		if(!editor.getBlock(origin).isReplaceable()) return;
		MetaBlock.of(fromType(type).defaultBlockState())
			.with(LanternBlock.HANGING, hang)
			.set(editor, origin);
	}
	
	public static Block fromType(Lantern type) {
		switch(type) {
		case FLAME: return Blocks.LANTERN;
		case SOUL: return Blocks.SOUL_LANTERN;
		default: return Blocks.LANTERN;
		
		}
	}
	
}
