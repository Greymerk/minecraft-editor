package com.greymerk.editor.tools.features;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.editor.factories.BlockProvider;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class ToolJumble implements ITool {

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = editor.getBlock(pos);
		state.init(BlockProvider.JUMBLE, block);
		String msg = "New Jumble started with " + block.getName();
		player.sendOverlayMessage(Component.literal(msg));
	}

}
