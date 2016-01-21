package greymerk.editor.worldgen;

import java.util.Random;

import greymerk.editor.worldgen.shapes.IShape;

public interface IBlockFactory {
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos);
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape);
	
}
