package com.greymerk.editor.tools;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;


public interface ITool {

	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos);
	
}
