package com.greymerk.editor.editor.blocks.stair;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;
import com.greymerk.editor.editor.shapes.IShape;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;



public class MetaStair implements IStair{

	MetaBlock stair;
	
	public static MetaStair of(Block block) {
		return new MetaStair(block);
	}
	
	public static MetaStair of(Stair type) {
		return new MetaStair(type);
	}
	
	private MetaStair(Block block) {
		this.stair = MetaBlock.of(block);
	}
	
	private MetaStair(Stair type) {
		this.stair = MetaBlock.of(Stair.getBlock(type));
	}
	
	private MetaStair(MetaBlock stair) {
		this.stair = stair;
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		stair.with(StairBlock.FACING, Cardinal.facing(dir));
		stair.with(StairBlock.HALF, upsideDown ? Half.TOP : Half.BOTTOM);
		return this;
	}
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos) {
		setStairShape(editor, pos);
		return this.stair.set(editor, rand, pos);
	}
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		setStairShape(editor, pos);
		return this.stair.set(editor, rand, pos, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		shape.get().forEach(pos -> {
			this.set(editor, rand, pos, fillAir, replaceSolid);
		});
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape) {
		this.fill(editor, rand, shape, true, true);
	}
	
	private void setStairShape(IWorldEditor editor, Coord pos) {
		
		// in front
		MetaBlock mb = editor.getBlock(pos.copy().add(this.direction()));
		if(isStair(mb)){
			MetaStair other = new MetaStair(mb);
			if(other.isUpsideDown() == this.isUpsideDown()){
				if(other.direction() == Cardinal.left(this.direction())) {
					this.setShape(StairsShape.INNER_LEFT);
					return;
				}
				if(other.direction() == Cardinal.right(this.direction())) {
					this.setShape(StairsShape.INNER_RIGHT);
					return;
				}
			}
		}
		
		// behind
		mb = editor.getBlock(pos.copy().add(Cardinal.reverse(this.direction())));
		if(isStair(mb)) {
			MetaStair other = new MetaStair(mb);
			if(other.isUpsideDown() == this.isUpsideDown()){
				if(other.direction() == Cardinal.left(this.direction())) {
					this.setShape(StairsShape.OUTER_LEFT);
					return;
				}
				if(other.direction() == Cardinal.right(this.direction())) {
					this.setShape(StairsShape.OUTER_RIGHT);
					return;
				}
			}
		}
		
		this.setShape(StairsShape.STRAIGHT); // resetting by default
	}
	
    public Cardinal direction() {
    	return Cardinal.of(this.stair.get(StairBlock.FACING));
    }
    
    public boolean isUpsideDown() {
    	return this.stair.get(StairBlock.HALF) == Half.TOP;
    }
    
    private boolean isStair(MetaBlock mb) {
    	return mb.getBlock() instanceof StairBlock;
    }
    
    private MetaStair setShape(StairsShape shape) {
		stair.with(StairBlock.SHAPE, shape);
		return this;
	}
}
