package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToolFillHollow implements ITool {

	@Override
	public void onClick(WorldEditor editor, Random rand, EntityPlayer player, ToolState state, Coord pos) {
		boolean success = state.fillRectHollow(editor, rand, pos);
		
		if(success) return;
		String msg = "Must set start point first";
		player.addChatComponentMessage(new ChatComponentText(msg));
	}
}
