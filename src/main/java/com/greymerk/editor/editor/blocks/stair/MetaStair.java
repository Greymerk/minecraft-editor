package com.greymerk.editor.editor.blocks.stair;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MetaStair extends MetaBlock implements IStair{

	public MetaStair(Block block) {
		super(block);
	}

	public MetaStair(MetaBlock block){
		super(block);
	}
	
	public MetaStair(StairType stair) {
		super(StairType.getBlock(stair));
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		BlockState stair = this.getBlock().getDefaultState();
		stair = stair.with(StairsBlock.FACING, Cardinal.facing(dir));
		stair = stair.with(StairsBlock.HALF, upsideDown ? BlockHalf.TOP : BlockHalf.BOTTOM);
		this.setState(stair);
		return this;
	}
	
	public MetaStair setShape(StairShape shape) {
		BlockState stair = this.getState();
		stair = stair.with(StairsBlock.SHAPE, shape);
		this.setState(stair);
		return this;
	}
	
	public boolean set(IWorldEditor editor, Coord pos) {
		StairShape shape = getStairShape(this.getState(), editor, pos.getBlockPos());
		this.setShape(shape);
		return editor.set(pos, this, true, true);
	}
	
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
		StairShape shape = getStairShape(this.getState(), editor, pos.getBlockPos());
		this.setShape(shape);
		return editor.set(pos, this, fillAir, replaceSolid);
	}
	
    private static StairShape getStairShape(BlockState state, IWorldEditor world, BlockPos pos) {
        Direction direction3;
        Direction direction2;
        Direction direction = state.get(StairsBlock.FACING);
        BlockState blockState = world.getBlock(new Coord(pos.offset(direction))).getState();
        if (StairsBlock.isStairs(blockState) 
        		&& state.get(StairsBlock.HALF) == blockState.get(StairsBlock.HALF) 
        		&& (direction2 = blockState.get(StairsBlock.FACING)).getAxis() != state.get(StairsBlock.FACING).getAxis() 
        		&& isDifferentOrientation(state, world, pos, direction2.getOpposite())) {
            if (direction2 == direction.rotateYCounterclockwise()) {
                return StairShape.OUTER_LEFT;
            }
            return StairShape.OUTER_RIGHT;
        }
        BlockState blockState2 = world.getBlock(new Coord(pos.offset(direction.getOpposite()))).getState();
        if (StairsBlock.isStairs(blockState2) && state.get(StairsBlock.HALF) == blockState2.get(StairsBlock.HALF) 
        		&& (direction3 = blockState2.get(StairsBlock.FACING)).getAxis() != state.get(StairsBlock.FACING).getAxis() 
        		&& isDifferentOrientation(state, world, pos, direction3)) {
            if (direction3 == direction.rotateYCounterclockwise()) {
                return StairShape.INNER_LEFT;
            }
            return StairShape.INNER_RIGHT;
        }
        return StairShape.STRAIGHT;
    }

    private static boolean isDifferentOrientation(BlockState state, IWorldEditor world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlock(new Coord(pos.offset(dir))).getState();
        return !StairsBlock.isStairs(blockState) 
        		|| blockState.get(StairsBlock.FACING) != state.get(StairsBlock.FACING) 
        		|| blockState.get(StairsBlock.HALF) != state.get(StairsBlock.HALF);
    }
	
}
