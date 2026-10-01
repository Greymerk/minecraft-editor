package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnderChestBlock;

public class EnderChest {
	public static void set(IWorldEditor editor, Cardinal dir, Coord pos){
		MetaBlock.of(Blocks.ENDER_CHEST)
			.with(EnderChestBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.set(editor, pos);
	}
}
