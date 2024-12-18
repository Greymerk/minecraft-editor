package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.shapes.RectPyramid;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public class ToolSquarePyramid implements ITool{

	@Override
	public void onClick(IWorldEditor editor, Random rand, PlayerEntity player, ToolState state, Cardinal dir, Coord pos) {
		Coord start = state.getStart();
		if(start == null){
			String msg = "Must set start point first";
			player.sendMessage(Text.of(msg), true);
			return;
		};
		state.fill(editor, rand, new RectPyramid(start, pos));
	}
}
