package com.greymerk.editor.editor.blocks.slab;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;

public interface ISlab {

	public ISlab upsideDown(boolean upsideDown);
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);
	
}
