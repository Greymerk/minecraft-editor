package com.greymerk.editor.editor.blocks.slab;

import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.MetaBlock;

public interface ISlab{

	public ISlab upsideDown(boolean upsideDown);
	
	public MetaBlock get();
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);
	
}
