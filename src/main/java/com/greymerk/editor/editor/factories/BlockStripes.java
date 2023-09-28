package com.greymerk.editor.editor.factories;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockStripes extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public BlockStripes(){
		blocks = new ArrayList<IBlockFactory>();
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		int size = blocks.size();
		int choice = Math.abs(
				Math.floorMod(
				(Math.floorMod(origin.getX(), size)
				+ Math.floorMod(origin.getY(), size)
				+ Math.floorMod(origin.getZ(), size))
				, size));
		IBlockFactory block = blocks.get(choice);
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}
}
