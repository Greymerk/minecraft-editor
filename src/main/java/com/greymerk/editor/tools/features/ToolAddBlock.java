package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class ToolAddBlock implements ITool {

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = editor.getBlock(pos);
		state.addBlock(editor, block);
		String msg = "Block Added: " + block.getName();
		player.sendOverlayMessage(Component.literal(msg));		
	}

}
