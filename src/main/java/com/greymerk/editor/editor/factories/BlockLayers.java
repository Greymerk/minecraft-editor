package com.greymerk.editor.editor.factories;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockLayers extends BlockBase{

	private List<IBlockFactory> blocks;
	
	public BlockLayers(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = this.blocks.get(pos.getY() % this.blocks.size());
		return block.set(editor, rand, pos, fillAir, replaceSolid);
	}

}
