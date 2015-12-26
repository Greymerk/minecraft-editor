package greymerk.editor.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.editor.worldgen.BlockJumble;
import greymerk.editor.worldgen.BlockProvider;
import greymerk.editor.worldgen.BlockStripes;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.MetaBlock;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class ToolState {

	private BlockProvider type;
	Map<BlockProvider, IBlockFactory> brushes;
	private boolean fillAir;
	private boolean replaceSolid;
	private Coord cursor;
	
	public ToolState(){
		type = BlockProvider.JUMBLE;
		this.brushes = new HashMap<BlockProvider, IBlockFactory>();
		this.brushes.put(BlockProvider.JUMBLE, new BlockJumble());
		this.fillAir = true;
		this.replaceSolid = true;
		BlockJumble jumble = (BlockJumble)this.brushes.get(BlockProvider.JUMBLE);
		jumble.addBlock(new MetaBlock(Blocks.quartz_block));
		jumble.addBlock(new MetaBlock(Blocks.obsidian));
	}
	
	public void setBlock(WorldEditor editor, Random rand, Coord pos){
		brushes.get(this.type).setBlock(editor, rand, pos);
	}
	
	public void setStart(Coord pos){
		this.cursor = new Coord(pos);
	}
	
	public void fillRectSolid(WorldEditor editor, Random rand, Coord end){
		brushes.get(this.type).fillRectSolid(editor, rand, this.cursor, end, fillAir, replaceSolid);
	}
	
	public void fillRectHollow(WorldEditor editor, Random rand, Coord end){
		brushes.get(this.type).fillRectHollow(editor, rand, this.cursor, end, fillAir, replaceSolid);
	}
	
	public void init(WorldEditor editor, BlockProvider type, MetaBlock block){
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
	
	public void addBlock(WorldEditor editor, MetaBlock block){		
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
