package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import net.minecraft.entity.player.EntityPlayer;

public class ToolPlaceBlock implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		Coord cursor = pos;
		if(editor.isReplaceable(pos)){
			state.setBlock(editor, rand, pos);
			return;
		}
		cursor.add(Cardinal.reverse(dir));
		state.setBlock(editor, rand, cursor);
	}

}
