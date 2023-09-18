package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.blocks.BlockType;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;

public class ToolBlockSet implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, PlayerEntity player, ToolState state, Cardinal dir, Coord pos) {
		if(editor.isReplaceable(pos)){
			BlockType.get(BlockType.AIR).set(editor, pos);
			return;
		}
		state.setBlock(editor, rand, pos);
	}
}
