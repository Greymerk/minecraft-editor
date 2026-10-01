package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.shapes.Ellipsoid;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;



public class ToolSphereSolid implements ITool{

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		Coord start = state.getStart();
		if(start == null){
			String msg = "Must set start point first";
			player.sendOverlayMessage(Component.literal(msg));
			return;
		};
		state.fill(editor, rand, new Ellipsoid(start, pos));
	}
}
