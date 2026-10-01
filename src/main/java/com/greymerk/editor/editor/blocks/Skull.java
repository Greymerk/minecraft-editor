package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;



public enum Skull {

	SKELETON, WITHER, ZOMBIE, STEVE, CREEPER;
	
	public static void set(IWorldEditor editor, RandomSource rand, Coord origin, Cardinal dir, Skull type){
		if(!editor.isSupported(origin)) return;
		setRotation(rand, MetaBlock.of(Skull.fromType(type)), dir).set(editor, origin);
	}
	
	public static MetaBlock setRotation(RandomSource rand, MetaBlock skull, Cardinal dir){
		int directionValue = getDirectionValue(dir);
		
		// nudge the skull so that it isn't perfectly aligned.
		directionValue += -1 + rand.nextInt(3);
		
		// make sure the skull direction value is less than 16
		//directionValue = directionValue % 16;
		directionValue = Math.floorMod(directionValue, 16);
		
		skull.with(SkullBlock.ROTATION, directionValue);
		return skull;
	}
	
	public static Block fromType(Skull type){
		switch(type){
		case SKELETON: return Blocks.SKELETON_SKULL;
		case WITHER: return Blocks.WITHER_SKELETON_SKULL;
		case ZOMBIE: return Blocks.ZOMBIE_HEAD;
		case STEVE: return Blocks.PLAYER_HEAD;
		case CREEPER: return Blocks.CREEPER_HEAD;
		default: return Blocks.SKELETON_SKULL;
		}
	}
	
	public static int getDirectionValue(Cardinal dir){
		switch(dir){
		case NORTH: return 0;
		case EAST: return 4;
		case SOUTH: return 8;
		case WEST: return 12;
		default: return 0;
		}
	}	
}
