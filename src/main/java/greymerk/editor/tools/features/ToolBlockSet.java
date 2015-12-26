package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;

public class ToolBlockSet implements ITool {

	@Override
	public void onClick(WorldEditor editor, Random rand, EntityPlayer player, ToolState state, Coord pos) {
		state.setBlock(editor, rand, pos);
	}
}
