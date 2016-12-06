package greymerk.editor.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.editor.tools.features.ToolAddBlock;
import greymerk.editor.tools.features.ToolAir;
import greymerk.editor.tools.features.ToolBlockSet;
import greymerk.editor.tools.features.ToolFillHollow;
import greymerk.editor.tools.features.ToolFillSolid;
import greymerk.editor.tools.features.ToolJumble;
import greymerk.editor.tools.features.ToolPlaceBlock;
import greymerk.editor.tools.features.ToolSingle;
import greymerk.editor.tools.features.ToolSphereSolid;
import greymerk.editor.tools.features.ToolSquarePyramid;
import greymerk.editor.tools.features.ToolStart;
import greymerk.editor.tools.features.ToolStripes;
import greymerk.editor.tools.features.ToolToggleFillAir;
import greymerk.editor.tools.features.ToolToggleReplaceSolid;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ToolBox {

	ToolState state;
	Map<Item, ITool> tools;
	
	public ToolBox(){
		
		this.state = new ToolState();
		tools = new HashMap<Item, ITool>();
		tools.put(Items.QUARTZ, new ToolBlockSet());
		tools.put(Items.ARROW, new ToolStart());
		tools.put(Items.STICK, new ToolFillSolid());
		tools.put(Items.BLAZE_ROD, new ToolFillHollow());
		tools.put(Items.SLIME_BALL, new ToolSphereSolid());
		tools.put(Items.FLINT, new ToolSingle());
		tools.put(Items.IRON_INGOT, new ToolJumble());
		tools.put(Items.GOLD_INGOT, new ToolStripes());
		tools.put(Items.BONE, new ToolAddBlock());
		tools.put(Items.FEATHER, new ToolAir());
		tools.put(Items.GHAST_TEAR, new ToolToggleFillAir());
		tools.put(Items.GOLD_NUGGET, new ToolToggleReplaceSolid());
		tools.put(Items.GLOWSTONE_DUST, new ToolPlaceBlock());
		tools.put(Items.SUGAR, new ToolSquarePyramid());
	}
	
	public ITool get(Item item){
		if(!tools.containsKey(item)) return null;
		return tools.get(item);
	}

	public void action(WorldEditor editor, Random rand, EntityPlayer player, Cardinal dir, Coord pos) {
		ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
		if(held == null) return;
		ITool tool = get(held.getItem());
		if(tool == null) return;
		tool.onClick(editor,  rand, player, state, dir, pos);
	}
	
}
