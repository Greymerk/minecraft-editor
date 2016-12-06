package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ToolToggleFillAir implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		boolean fillAir = state.toggleFillAir();
		String msg = "Fill Air: " + (fillAir ? "Yes" : "No");
		player.addChatMessage(new TextComponentString(msg));
	}
}
