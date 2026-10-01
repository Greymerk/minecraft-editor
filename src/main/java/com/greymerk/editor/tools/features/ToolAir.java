package com.greymerk.editor.tools.features;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.editor.blocks.Air;
import com.greymerk.editor.editor.factories.BlockProvider;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class ToolAir implements ITool {

	@Override
	public void onClick(IWorldEditor editor, RandomSource rand, Player player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = Air.get();
		state.init(BlockProvider.METABLOCK, block);
		String msg = "Brush set to: Air";
		player.sendOverlayMessage(Component.literal(msg));
	}

}
