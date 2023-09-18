package com.greymerk.editor.editor.shapes;

import java.util.List;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public interface IShape extends Iterable<Coord>{

	public void fill(IWorldEditor editor, Random rand, IBlockFactory block);
	
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);
	
	public List<Coord> get();
	
}
