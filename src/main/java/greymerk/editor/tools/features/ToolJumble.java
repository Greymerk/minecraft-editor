package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.BlockProvider;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.MetaBlock;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToolJumble implements ITool {

	@Override
	public void onClick(WorldEditor editor, Random rand, EntityPlayer player, ToolState state, Coord pos) {
		MetaBlock block = editor.getBlock(pos);
		state.init(BlockProvider.JUMBLE, block);
		String msg = "New Jumble created";
		player.addChatComponentMessage(new ChatComponentText(msg));
	}

}
