package com.greymerk.editor.editor.blocks.stair;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;

public interface IStair {

	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);

}
