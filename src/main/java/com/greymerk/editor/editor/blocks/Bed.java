package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.util.Color;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BedPart;


public class Bed {

	public static void generate(IWorldEditor editor, RandomSource rand, Cardinal dir, Coord pos) {
		generate(editor, dir, pos, Color.get(rand));
	}
	
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, Color.RED);
	}
	
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord origin, Color color){
		
		Block b = Blocks.BED.pick(Color.get(color));
		
		Coord pos = origin.copy();
		MetaBlock head = MetaBlock.of(b);
		head.with(BedBlock.PART, BedPart.HEAD);
		head.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir));
		head.set(editor, pos);
		
		pos.add(dir);
		MetaBlock foot = MetaBlock.of(b);
		foot.with(BedBlock.PART, BedPart.FOOT);
		foot.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir));
		foot.set(editor, pos);

	}
}
