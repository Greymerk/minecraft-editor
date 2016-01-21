package greymerk.editor.worldgen.shapes;

import java.util.List;
import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;

public interface IShape extends Iterable<Coord>{

	public void fill(IWorldEditor editor, Random rand, IBlockFactory block);
	
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);
	
	public List<Coord> get();
	
}
