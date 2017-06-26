package greymerk.editor.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.shapes.IShape;

public class ToolTask{
	
	private List<TaskStep> steps;
	private Iterator<TaskStep> processor;
	private boolean rollback;
	
	public ToolTask(IWorldEditor editor, Random rand, IShape shape, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		this.rollback = false; // whether to apply or undo the steps
		
		this.steps = new ArrayList<TaskStep>();
		for(Coord pos : shape){
			this.steps.add(new TaskStep(editor, rand, pos, blocks, fillAir, replaceSolid));
		}
		
		this.processor = steps.iterator();
	}
	
	public boolean isDone(){
		return !this.processor.hasNext();
	}
	
	public void process(){
		if(!this.processor.hasNext()) return;
		
		TaskStep step = this.processor.next();
		
		if(this.rollback){
			step.undo();
		} else {
			step.apply();
		}
	}
	
	public void apply(){
		this.processor = this.steps.iterator();
		this.rollback = false;
	}
	
	public void rollback(){
		this.processor = this.steps.iterator();
		this.rollback = true;
	}
}
