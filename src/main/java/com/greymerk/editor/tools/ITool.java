package com.greymerk.editor.tools;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;

public interface ITool {

	public void onClick(IWorldEditor editor, Random rand, PlayerEntity player, ToolState state, Cardinal dir, Coord pos);
	
}
