package greymerk.editor.tools;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
	
	private Deque<ToolTask> tasks;
	private Deque<ToolTask> redo;
	
	public ToolState(){
		this.brushes = new HashMap<BlockProvider, IBlockFactory>();
		this.fillAir = true;
		this.replaceSolid = true;
		this.init(BlockProvider.METABLOCK, new MetaBlock(Blocks.AIR));
		tasks = new LinkedList<ToolTask>();
		redo = new LinkedList<ToolTask>();
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
		redo = new LinkedList<ToolTask>();
		if(tasks.size() > 20){
			tasks.removeFirst();
		}
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
		
		for(ToolTask task : this.tasks){
			if(!task.isDone()) return true;
		}
		
		for(ToolTask task : this.redo){
			if(!task.isDone()) return true;
		}
		
		return false;
	}
	
	public void process(){
		
		Iterator<ToolTask> taskiter = tasks.iterator();
		Iterator<ToolTask> redoiter = redo.iterator();
		
		while(taskiter.hasNext()){
			taskiter.next().process();
		}
		
		while(redoiter.hasNext()){
			redoiter.next().process();
		}
	}
	
	public void undo(){
		if(this.tasks.isEmpty()) return;
		
		ToolTask task = this.tasks.removeLast();
		task.rollback();
		this.redo.push(task);
	}
	
	public void redo(){
		if(this.redo.isEmpty()) return;
		
		ToolTask task = this.redo.pop();
		task.apply();
		this.tasks.add(task);
	}
}
