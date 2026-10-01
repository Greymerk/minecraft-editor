package com.greymerk.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.IWorldEditor;
import com.greymerk.editor.editor.WorldEditor;
import com.greymerk.editor.tools.ToolBox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class GreyEdit implements ModInitializer{


	public static Map<UUID, ToolBox> boxes;
	
	static{
		boxes = new HashMap<UUID, ToolBox>();
	}
	
	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new OnUse());
		ServerTickEvents.END_LEVEL_TICK.register(new OnWorldTick());
	}
	
	private class OnUse implements UseBlockCallback{

		@Override
		public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
			if(world.isClientSide()) return InteractionResult.PASS;
			if(hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
			if(!player.isCreative()) return InteractionResult.PASS;
			
			UUID playerID = player.getUUID();
			if(!boxes.containsKey(playerID)) {
				boxes.put(playerID, new ToolBox());
			}
			
			IWorldEditor editor = WorldEditor.of(world);
			ToolBox tools = boxes.get(playerID);
			
			if(!tools.holdingTool(player)) return InteractionResult.PASS;
			
			Coord pos = Coord.of(hitResult.getBlockPos());
			Cardinal dir = Cardinal.of(hitResult.getDirection());
			tools.action(editor, editor.getRandom(pos), player, Cardinal.reverse(dir), pos);
			return InteractionResult.SUCCESS_SERVER;
		}
	}
	
	private class OnWorldTick implements ServerTickEvents.EndLevelTick{
		@Override
		public void onEndTick(ServerLevel world) {
			boxes.values().forEach(tb -> tb.process());
		}
	}
}
