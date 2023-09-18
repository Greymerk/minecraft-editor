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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class GreyEdit implements ModInitializer{


	public static Map<UUID, ToolBox> boxes;
	
	static{
		boxes = new HashMap<UUID, ToolBox>();
	}
	
	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new OnUse());
		ServerTickEvents.END_WORLD_TICK.register(new OnWorldTick());
	}
	
	private class OnUse implements UseBlockCallback{

		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
			if(world.isClient) return ActionResult.PASS;
			if(hand == Hand.OFF_HAND) return ActionResult.PASS;
			if(!player.isCreative()) return ActionResult.PASS;
			
			UUID playerID = player.getUuid();
			if(!boxes.containsKey(playerID)) {
				boxes.put(playerID, new ToolBox());
			}
			
			IWorldEditor editor = new WorldEditor(world);
			ToolBox tools = boxes.get(playerID);
			
			Coord pos = new Coord(hitResult.getBlockPos());
			Cardinal dir = Cardinal.fromDirection(hitResult.getSide());
			tools.action(editor, editor.getRandom(pos), player, dir, pos);
			return ActionResult.PASS;
		}
	}
	
	private class OnWorldTick implements ServerTickEvents.EndWorldTick{
		@Override
		public void onEndTick(ServerWorld world) {
			boxes.values().forEach(tb -> tb.process(1));
		}
	}
}
