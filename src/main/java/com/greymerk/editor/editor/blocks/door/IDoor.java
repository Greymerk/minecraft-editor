package com.greymerk.editor.editor.blocks.door;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;

public interface IDoor {
	
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir);
	
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open);
}
