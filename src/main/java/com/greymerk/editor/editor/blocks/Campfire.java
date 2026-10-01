package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;


public enum Campfire {

	NATURAL, SOUL;
	
	public static void generate(IWorldEditor editor, Coord origin, Campfire type) {
		MetaBlock.of(fromType(type)).set(editor, origin);
	}
	
	public static Block fromType(Campfire type) {
		switch(type) {
		case NATURAL: return Blocks.CAMPFIRE;
		case SOUL: return Blocks.SOUL_CAMPFIRE;
		default: return Blocks.CAMPFIRE;
		}
	}
	
}
