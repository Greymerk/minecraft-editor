package greymerk.editor.tools;

import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;

public interface ITool {

	public void onClick(WorldEditor editor, Random rand, EntityPlayer player, ToolState state, Coord pos);
	
}
