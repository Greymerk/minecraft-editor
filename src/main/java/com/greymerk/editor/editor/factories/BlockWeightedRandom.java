package com.greymerk.editor.editor.factories;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.util.WeightedChoice;
import com.greymerk.editor.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class BlockWeightedRandom extends BlockBase {

	private WeightedRandomizer<IBlockFactory> blocks;
	
	public BlockWeightedRandom(){
		blocks = new WeightedRandomizer<IBlockFactory>();
	}

	public void addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand);
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}
}
