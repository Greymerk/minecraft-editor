package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum Anvil {

	ANVIL, CHIPPED, DAMAGED;
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
		return MetaBlock.of(getFromDamage(damage)).with(AnvilBlock.FACING, Cardinal.facing(dir));
	}
	
	private static Block getFromDamage(Anvil damage) {
		switch(damage){
		case ANVIL: return Blocks.ANVIL;
		case CHIPPED: return Blocks.CHIPPED_ANVIL;
		case DAMAGED: return Blocks.DAMAGED_ANVIL;
		default: return Blocks.ANVIL;
		}
	}
	
}
