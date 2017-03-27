package greymerk.editor.tools;

import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.MetaBlock;

public class TaskStep {
	
	private IWorldEditor editor;
	private Random rand;
	private Coord pos;
	private IBlockFactory blocks;
	private MetaBlock replacement;
	private MetaBlock original;
	private boolean fillAir;
	private boolean replaceSolid;
	
	public TaskStep(IWorldEditor editor, Random rand, Coord pos, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		this.editor = editor;
		this.rand = rand;
		this.pos = pos;
		this.blocks = blocks;
		this.original = editor.getBlock(pos);
		this.fillAir = fillAir;
		this.replaceSolid = replaceSolid;
	}
	
	public void apply(){
		
		if(this.replacement == null){
			blocks.set(editor, rand, pos, fillAir, replaceSolid);
			this.replacement = editor.getBlock(pos);
			return;
		}
		
		replacement.set(editor, rand, pos, fillAir, replaceSolid);
	}
	
	public void undo(){
		original.set(editor, rand, pos, true, true);
	}
}
