package com.greymerk.editor.editor.boundingbox;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.shapes.IShape;
import com.greymerk.editor.editor.shapes.Shape;

import net.minecraft.nbt.NbtCompound;

public interface IBounded {
	
	public BoundingBox getBoundingBox();
	
	public boolean collide(IBounded other);

	public boolean contains(Coord pos);
	
	public IShape getShape(Shape type);
	
	public Coord getStart();
	
	public Coord getEnd();

	public NbtCompound getNbt();
	
}
