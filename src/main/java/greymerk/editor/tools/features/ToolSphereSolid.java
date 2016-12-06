package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.shapes.Ellipsoid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ToolSphereSolid implements ITool{

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		Coord start = state.getStart();
		if(start == null){
			String msg = "Must set start point first";
			player.addChatMessage(new TextComponentString(msg));
		};
		state.fill(editor, rand, new Ellipsoid(start, pos));
	}
}
