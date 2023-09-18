package com.greymerk.editor.tools.features;



import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.editor.factories.BlockProvider;
import com.greymerk.editor.tools.ITool;
import com.greymerk.editor.tools.ToolState;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public class ToolAir implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, PlayerEntity player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = new MetaBlock(Blocks.AIR);
		state.init(BlockProvider.METABLOCK, block);
		String msg = "Brush set to: Air";
		player.sendMessage(Text.of(msg));	
	}

}
