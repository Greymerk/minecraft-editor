package com.greymerk.editor.editor.factories;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.util.math.random.Random;

public class BlockGrid extends BlockBase {

	private MetaBlock block;
	
	public BlockGrid(MetaBlock block){
		this.block = block;
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		int x = origin.getX();
		int z = origin.getZ();
		
		if(x % 2 != 0) return false;
		if(z % 2 != 0) return false;
		if(x % 6 == 0) return false;
		if(z % 6 == 0) return false;
		
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}

}
