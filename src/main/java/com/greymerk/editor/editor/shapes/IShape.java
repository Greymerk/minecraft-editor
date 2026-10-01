package com.greymerk.editor.editor.shapes;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;

import net.minecraft.util.RandomSource;

public interface IShape extends Iterable<Coord>{

	public void fill(IWorldEditor editor, RandomSource rand, @NotNull IBlockFactory block);
	
	public void fill(IWorldEditor editor, RandomSource rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);
	
	public List<Coord> get();
	
}
