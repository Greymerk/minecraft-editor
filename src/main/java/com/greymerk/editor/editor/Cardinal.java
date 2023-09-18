package com.greymerk.editor.editor;

import java.util.Arrays;
import java.util.List;

import com.greymerk.editor.util.math.RandHelper;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public enum Cardinal {
	NORTH, EAST, WEST, SOUTH, UP, DOWN;
	
	public static final List<Cardinal> directions = Arrays.asList(new Cardinal[] {NORTH, EAST, SOUTH, WEST});
	
	public static Cardinal reverse(Cardinal dir){
		switch(dir){
		case NORTH: return SOUTH;
		case EAST: return WEST;
		case WEST: return EAST;
		case SOUTH: return NORTH;
		case DOWN: return UP;
		case UP: return DOWN;
		default: return null;
		}
	}

	public static Cardinal left(Cardinal dir){
		switch(dir){
		case NORTH: return WEST;
		case EAST: return NORTH;
		case SOUTH: return EAST;
		case WEST: return SOUTH;
		default: return dir;
		}
	}

	public static Cardinal right(Cardinal dir){
		switch(dir){
		case NORTH: return EAST;
		case EAST: return SOUTH;
		case SOUTH: return WEST;
		case WEST: return NORTH;
		default: return dir;
		}
	}
	
	public static List<Cardinal> orthogonal(Cardinal dir) {
		
		switch(dir){
		case NORTH: return Arrays.asList(new Cardinal[] {WEST, EAST});
		case SOUTH: return Arrays.asList(new Cardinal[] {EAST, WEST});
		case EAST: return Arrays.asList(new Cardinal[] {NORTH, SOUTH});
		case WEST: return Arrays.asList(new Cardinal[] {SOUTH, NORTH});
		default: return Arrays.asList(new Cardinal[]{dir, dir});
		}
	}

	public static Direction facing(Cardinal dir){
		
		switch(dir){
		case NORTH: return Direction.SOUTH;
		case EAST: return Direction.WEST;
		case WEST: return Direction.EAST;
		case SOUTH: return Direction.NORTH;
		case UP: return Direction.UP;
		case DOWN: return Direction.DOWN;
		default: return null;
		}
	}

	public static Direction.Axis axis(Cardinal dir){
		switch(dir) {
		case NORTH: return Direction.Axis.Z;
		case EAST: return Direction.Axis.X;
		case WEST: return Direction.Axis.X;
		case SOUTH: return Direction.Axis.Z;
		case UP: return Direction.Axis.Y;
		case DOWN: return Direction.Axis.Y;
		default: return Direction.Axis.Y;
		}
	}
	
	public static Cardinal fromDirection(Direction d) {
		switch(d) {
		case DOWN: return Cardinal.DOWN;
		case EAST: return Cardinal.EAST;
		case NORTH: return Cardinal.NORTH;
		case SOUTH: return Cardinal.SOUTH;
		case UP: return Cardinal.UP;
		case WEST: return Cardinal.WEST;
		default: return Cardinal.DOWN;
		}
	}
	
	public static List<Cardinal> randDirs(Random rand){
		List<Cardinal> dirs = Arrays.asList(new Cardinal[] {NORTH, EAST, SOUTH, WEST});
		RandHelper.shuffle(Arrays.asList(dirs), rand);
		return dirs;
	}
}
