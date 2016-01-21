package greymerk.editor.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.editor.worldgen.BlockJumble;
import greymerk.editor.worldgen.BlockProvider;
import greymerk.editor.worldgen.BlockStripes;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.MetaBlock;
import greymerk.editor.worldgen.shapes.RectHollow;
import greymerk.editor.worldgen.shapes.RectSolid;
import net.minecraft.init.Blocks;

public class ToolState {

	private BlockProvider type;
	Map<BlockProvider, IBlockFactory> brushes;
	private boolean fillAir;
	private boolean replaceSolid;
	private Coord start;
	
	public ToolState(){
		this.brushes = new HashMap<BlockProvider, IBlockFactory>();
		this.fillAir = true;
		this.replaceSolid = true;
		this.init(BlockProvider.METABLOCK, new MetaBlock(Blocks.air));
	}
	
	public void setBlock(IWorldEditor editor, Random rand, Coord pos){
		brushes.get(this.type).set(editor, rand, pos);
	}
	
	public void setStart(Coord pos){
		this.start = new Coord(pos);
	}
	
	public boolean fillRectSolid(IWorldEditor editor, Random rand, Coord end){
		if(start == null) return false;
		
		brushes.get(this.type).fill(editor, rand, new RectSolid(this.start, end), fillAir, replaceSolid);
		start = null;
		return true;
	}
	
	public boolean fillRectHollow(IWorldEditor editor, Random rand, Coord end){
		if(start == null) return false;
		brushes.get(this.type).fill(editor, rand, new RectHollow(this.start, end), fillAir, replaceSolid);
		start = null;
		return true;
	}
	
	public void init(BlockProvider type, MetaBlock block){
		this.type = type;		
		switch(type){
		case METABLOCK: 
			this.brushes.put(type, block);
			return;
		case STRIPES:
			BlockStripes stripes = new BlockStripes();
			stripes.addBlock(block);
			this.brushes.put(type, stripes);
			return;
		case JUMBLE:
			BlockJumble jumble = new BlockJumble();
			jumble.addBlock(block);
			this.brushes.put(type, jumble);
			return;
		default: return;
		}
	}
	
	public void addBlock(IWorldEditor editor, MetaBlock block){		
		IBlockFactory brush = brushes.get(this.type);
		
		switch(this.type){
		case JUMBLE:
			BlockJumble jumble = (BlockJumble)brush;
			jumble.addBlock(block);
			return;
		case STRIPES:
			BlockStripes stripes = (BlockStripes)brush;
			stripes.addBlock(block);
			return;
		default: return;
		}
	}
	
	public boolean toggleFillAir(){
		this.fillAir = !this.fillAir;
		return this.fillAir;
	}
	
	public boolean toggleReplaceSolid(){
		this.replaceSolid = !this.replaceSolid;
		return this.replaceSolid;
	}
}
