package com.greymerk.editor.editor.blocks.stair;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.shapes.IShape;
import com.greymerk.editor.util.WeightedChoice;
import com.greymerk.editor.util.WeightedRandomizer;

import net.minecraft.util.RandomSource;



public class RandomStair implements IStair, IBlockFactory {

	private Cardinal dir;
	private boolean upsideDown;
	private WeightedRandomizer<IStair> stairs;
	
	public RandomStair() {
		this.stairs = new WeightedRandomizer<IStair>();
		this.dir = Cardinal.NORTH;
		this.upsideDown = false;
	}

	public RandomStair add(IStair toAdd, int weight) {
		this.stairs.add(new WeightedChoice<IStair>(toAdd, weight));
		return this;
	}
	
	@Override
	public IStair setOrientation(Cardinal dir, Boolean upsideDown) {
		this.dir = Cardinal.directions.contains(dir) ? dir : Cardinal.NORTH;
		this.upsideDown = upsideDown;
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos) {
		if(this.stairs.isEmpty()) return false;
		return this.stairs.get(rand).setOrientation(this.dir, this.upsideDown).set(editor, rand, pos);
	}

	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		if(this.stairs.isEmpty()) return false;
		return this.stairs.get(rand).setOrientation(this.dir, this.upsideDown).set(editor, rand, pos, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		shape.forEach(pos -> {
			this.set(editor, rand, pos, fillAir, replaceSolid);
		});
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape) {
		this.fill(editor, rand, shape, true, true);
	}
	
}
