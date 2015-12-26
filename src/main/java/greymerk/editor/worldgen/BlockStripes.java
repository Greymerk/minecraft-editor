package greymerk.editor.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class BlockStripes extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public BlockStripes(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public BlockStripes(JsonElement data) {
		this();
		for(JsonElement entry : (JsonArray)data){
			this.addBlock(BlockProvider.create(entry.getAsJsonObject()));
		}
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}

	@Override
	public boolean setBlock(WorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		int size = blocks.size();
		int choice = Math.abs((origin.getX() % size + origin.getY() % size + origin.getZ() % size)) % size;
		IBlockFactory block = blocks.get(choice);
		return block.setBlock(editor, rand, origin, fillAir, replaceSolid);
	}
}
