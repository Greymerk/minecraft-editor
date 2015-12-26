package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToolToggleReplaceSolid implements ITool {

	@Override
	public void onClick(WorldEditor editor, Random rand, EntityPlayer player, ToolState state, Coord pos) {
		boolean replaceSolid = state.toggleReplaceSolid();
		String msg = "Replace Solid: " + (replaceSolid ? "Yes" : "No");
		player.addChatComponentMessage(new ChatComponentText(msg));
	}
}
