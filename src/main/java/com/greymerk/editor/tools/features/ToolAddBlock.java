package com.greymerk.editor.tools.features;


import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public class ToolAddBlock implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, PlayerEntity player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = editor.getBlock(pos);
		state.addBlock(editor, block);
		String msg = "Block Added: " + block.getName();
		player.sendMessage(Text.of(msg), true);		
	}

}
