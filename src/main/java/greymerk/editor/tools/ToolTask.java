package greymerk.editor.tools;

import java.util.Iterator;
import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.shapes.IShape;

public class ToolTask{
	
	private IWorldEditor editor;
	private Random rand;
	private Iterator<Coord> shape;
	private IBlockFactory blocks;
	private boolean fillAir;
	private boolean replaceSolid;
	
	public ToolTask(IWorldEditor editor, Random rand, IShape shape, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		this.editor = editor;
		this.rand = rand;
		this.shape = shape.iterator();
		this.blocks = blocks;
		this.fillAir = fillAir;
		this.replaceSolid = replaceSolid;
		
	}
	
	public boolean hasNext(){
		return this.shape.hasNext();
	}
	
	public boolean process(){
		Coord pos = shape.next();
		blocks.set(editor, rand, pos, fillAir, replaceSolid);
		return shape.hasNext();
	}
}
