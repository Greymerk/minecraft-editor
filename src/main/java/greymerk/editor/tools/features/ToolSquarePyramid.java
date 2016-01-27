package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.shapes.RectPyramid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToolSquarePyramid implements ITool{

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		Coord start = state.getStart();
		if(start == null){
			String msg = "Must set start point first";
			player.addChatComponentMessage(new ChatComponentText(msg));
		};
		state.fill(editor, rand, new RectPyramid(start, pos));
	}
}
