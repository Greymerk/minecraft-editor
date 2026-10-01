package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class ToolPlaceBlock implements ITool {

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		if(editor.getBlock(pos).isReplaceable()){
			state.setBlock(editor, rand, pos);
			return;
		}
		state.setBlock(editor, rand, pos.add(dir));
	}

}
