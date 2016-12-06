package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.BlockProvider;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.MetaBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ToolSingle implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		MetaBlock block = editor.getBlock(pos);
		state.init(BlockProvider.METABLOCK, block);
		String msg = "Brush set to: " + block.getBlock().getUnlocalizedName();
		player.addChatMessage(new TextComponentString(msg));	
	}

}
