package com.greymerk.editor.editor.blocks.stair;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.shapes.IShape;

import net.minecraft.util.RandomSource;

public interface IStair {

	public IStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos);
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, boolean fillAir, boolean replaceSolid);

	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, boolean fillAir, boolean replaceSolid);
	
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape);
}
