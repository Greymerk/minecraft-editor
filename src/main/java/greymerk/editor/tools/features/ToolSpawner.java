package greymerk.editor.tools.features;

import java.util.Random;

import greymerk.editor.tools.ITool;
import greymerk.editor.tools.ToolState;
import greymerk.editor.worldgen.Cardinal;
import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IWorldEditor;
import greymerk.editor.worldgen.MetaBlock;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class ToolSpawner implements ITool {

	@Override
	public void onClick(IWorldEditor editor, Random rand, EntityPlayer player, ToolState state, Cardinal dir, Coord pos) {
		ItemStack egg = player.getHeldItem();
		int data = egg.getItemDamage();
		
		MetaBlock block = editor.getBlock(pos);
		if(block.getBlock() != Blocks.mob_spawner) return;
		
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) editor.getTileEntity(pos);
		if(spawner == null) return;
		
		String name = EntityList.getStringFromID(egg.getMetadata());
		
		MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
		logic.setEntityName(name);
		
	}
}
