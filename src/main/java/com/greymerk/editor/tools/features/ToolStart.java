package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class ToolStart implements ITool {

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		state.setStart(pos);
		String msg = "Start set to: " + pos.toString();
		player.sendOverlayMessage(Component.literal(msg));
	}
}
