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
import greymerk.editor.tools.features.ToolSpawner;
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

public class ToolBox {

	ToolState state;
	Map<Item, ITool> tools;
	
	public ToolBox(){
		
		this.state = new ToolState();
		tools = new HashMap<Item, ITool>();
		tools.put(Items.quartz, new ToolBlockSet());
		tools.put(Items.arrow, new ToolStart());
		tools.put(Items.stick, new ToolFillSolid());
		tools.put(Items.blaze_rod, new ToolFillHollow());
		tools.put(Items.slime_ball, new ToolSphereSolid());
		tools.put(Items.flint, new ToolSingle());
		tools.put(Items.iron_ingot, new ToolJumble());
		tools.put(Items.gold_ingot, new ToolStripes());
		tools.put(Items.bone, new ToolAddBlock());
		tools.put(Items.feather, new ToolAir());
		tools.put(Items.ghast_tear, new ToolToggleFillAir());
		tools.put(Items.gold_nugget, new ToolToggleReplaceSolid());
		tools.put(Items.glowstone_dust, new ToolPlaceBlock());
		tools.put(Items.sugar, new ToolSquarePyramid());
		tools.put(Items.spawn_egg, new ToolSpawner());
	}
	
	public ITool get(Item item){
		if(!tools.containsKey(item)) return null;
		return tools.get(item);
	}

	public void action(WorldEditor editor, Random rand, EntityPlayer player, Cardinal dir, Coord pos) {
		ItemStack held = player.getHeldItem();
		if(held == null) return;
		ITool tool = get(held.getItem());
		if(tool == null) return;
		tool.onClick(editor,  rand, player, state, dir, pos);
	}
	
}
