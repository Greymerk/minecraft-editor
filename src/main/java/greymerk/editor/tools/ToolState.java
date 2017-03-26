package greymerk.editor.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import greymerk.editor.worldgen.BlockJumble;
import greymerk.editor.worldgen.BlockProvider;
import greymerk.editor.worldgen.BlockStripes;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.MetaBlock;
import greymerk.editor.worldgen.shapes.IShape;
import net.minecraft.init.Blocks;

public class ToolState {

	private BlockProvider type;
	Map<BlockProvider, IBlockFactory> brushes;
	private boolean fillAir;
	private boolean replaceSolid;
	private Coord start;
	
	private List<ToolTask> tasks;
	
	public ToolState(){
		this.brushes = new HashMap<BlockProvider, IBlockFactory>();
		this.fillAir = true;
		this.replaceSolid = true;
		this.init(BlockProvider.METABLOCK, new MetaBlock(Blocks.AIR));
		tasks = new ArrayList<ToolTask>();
	}
	
	public void setBlock(IWorldEditor editor, Random rand, Coord pos){
		brushes.get(this.type).set(editor, rand, pos);
	}
	
	public void setStart(Coord pos){
		this.start = new Coord(pos);
	}
	
	public Coord getStart(){
		return this.start;
	}
	
	public void fill(IWorldEditor editor, Random rand, IShape shape){
		//shape.fill(editor, rand, brushes.get(this.type), fillAir, replaceSolid);
		ToolTask task = new ToolTask(editor, rand, shape, brushes.get(this.type), fillAir, replaceSolid);
		
		this.tasks.add(task);
		start = null;
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

	public boolean hasNext(){
		return !this.tasks.isEmpty();
	}
	
	public void process(){
		
		Iterator<ToolTask> iter = tasks.iterator();
		
		while(iter.hasNext()){
			ToolTask task = iter.next();
			
			task.process();
			
			if(!task.hasNext()){
				iter.remove();
			}
		}
	}
}
