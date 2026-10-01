package com.greymerk.editor.editor.blocks;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IBlockFactory;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.editor.factories.BlockWeightedRandom;
import com.greymerk.editor.editor.shapes.IShape;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;

public class IronBar implements IBlockFactory{

	MetaBlock bar;
	
	public static IronBar get() {
		return new IronBar();
	}

	public static IBlockFactory getBroken() {
		BlockWeightedRandom blocks = new BlockWeightedRandom();
		blocks.addBlock(Air.get(), 1);
		blocks.addBlock(IronBar.get(), 2);
		return blocks;
	}
	
	public IronBar() {
		this.bar = MetaBlock.of(Blocks.IRON_BARS);
	}

	private void setShape(IWorldEditor editor, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			setConnection(editor, origin, dir, connects(editor, origin, dir));
		}
	}
	
	private boolean connects(IWorldEditor editor, Coord origin, Cardinal dir) {
		if(editor.isFaceFullSquare(origin.copy().add(dir), Cardinal.reverse(dir))) return true;
		if(editor.getBlock(origin.copy().add(dir)).getBlock() == Blocks.IRON_BARS) return true;		
		return false;
	}
	
	private void setConnection(IWorldEditor editor, Coord origin, Cardinal dir, boolean connects) {
		switch(dir) {
		case EAST: this.bar.with(IronBarsBlock.EAST, connects); return;
		case NORTH: this.bar.with(IronBarsBlock.NORTH, connects); return;
		case SOUTH: this.bar.with(IronBarsBlock.SOUTH, connects); return;
		case WEST: this.bar.with(IronBarsBlock.WEST, connects); return;
		default: return;
		}
	}

	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos){
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos);
	}
	
	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos, fillAir, replaceSolid);
	}
	
	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		this.bar.fill(editor, rand, shape, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape) {
		this.bar.fill(editor, rand, shape);
	}
}
