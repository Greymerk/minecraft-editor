package com.greymerk.editor.tools;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.tools.features.ToolAddBlock;
import com.greymerk.editor.tools.features.ToolAir;
import com.greymerk.editor.tools.features.ToolBlockSet;
import com.greymerk.editor.tools.features.ToolFillHollow;
import com.greymerk.editor.tools.features.ToolFillSolid;
import com.greymerk.editor.tools.features.ToolFillWireframe;
import com.greymerk.editor.tools.features.ToolJumble;
import com.greymerk.editor.tools.features.ToolPlaceBlock;
import com.greymerk.editor.tools.features.ToolSingle;
import com.greymerk.editor.tools.features.ToolSphereSolid;
import com.greymerk.editor.tools.features.ToolSquarePyramid;
import com.greymerk.editor.tools.features.ToolStart;
import com.greymerk.editor.tools.features.ToolStripes;
import com.greymerk.editor.tools.features.ToolToggleFillAir;
import com.greymerk.editor.tools.features.ToolToggleReplaceSolid;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

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
		tools.put(Items.BRICK, new ToolFillWireframe());
	}
	
	public ITool get(Item item){
		if(!tools.containsKey(item)) return null;
		return tools.get(item);
	}

	public void action(IWorldEditor editor, Random rand, PlayerEntity player, Cardinal dir, Coord pos) {
		ItemStack held = player.getMainHandStack();
		if(held == null) return;
		ITool tool = get(held.getItem());
		if(tool == null) return;
		tool.onClick(editor,  rand, player, state, dir, pos);
	}
	
	public void process(int count){
		if(!this.state.hasNext()) return;
		
		for(int i = 0; i < count; ++i){
			this.state.process();	
		}
	}
	
	public void undo(){
		this.state.undo();
	}
	
	public void redo(){
		this.state.redo();
	}
}
