package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;



public class Furnace {

	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, false, ItemStack.EMPTY);
	}
	
	public static void generate(IWorldEditor editor, boolean lit, Cardinal dir, Coord pos){
		generate(editor, dir, pos, lit, ItemStack.EMPTY);
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, boolean lit, ItemStack fuel){
		MetaBlock.of(Blocks.FURNACE)
			.with(FurnaceBlock.LIT, lit)
			.with(FurnaceBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.set(editor, pos);
		
		BlockEntity te = editor.getBlockEntity(pos);
		if(te == null) return;
		if(!(te instanceof FurnaceBlockEntity)) return;
		FurnaceBlockEntity teFurnace = (FurnaceBlockEntity)te;
		teFurnace.setItem(FUEL_SLOT, fuel);
	}
}
