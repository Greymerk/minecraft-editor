package com.greymerk.editor.editor.factories;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockJumble extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public BlockJumble(){
		blocks = new ArrayList<IBlockFactory>();
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}

}
