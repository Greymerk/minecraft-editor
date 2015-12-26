package greymerk.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.editor.tools.ToolBox;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.WorldEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EditorEventHandler {

	Map<EntityPlayer, ToolBox> boxes;
	
	public EditorEventHandler(){
		boxes = new HashMap<EntityPlayer, ToolBox>();
	}
	
	@SubscribeEvent
	public void interact(PlayerInteractEvent event){
		
		if(event.world.isRemote) return;
		if(event.action == Action.RIGHT_CLICK_AIR) return;
		if(event.action == Action.LEFT_CLICK_BLOCK) return;
		
		WorldEditor editor = new WorldEditor(event.world);
		Random rand = new Random();
		
		EntityPlayer player = event.entityPlayer;
		Coord pos = new Coord(event.pos.getX(), event.pos.getY(), event.pos.getZ());
		
		ToolBox box = fetchBox(player);
		box.action(editor, rand, player, pos);
	}
	
	private ToolBox fetchBox(EntityPlayer player){
		
		if(!boxes.containsKey(player)){
			boxes.put(player, new ToolBox());
		}
			
		return boxes.get(player);
	}
}
