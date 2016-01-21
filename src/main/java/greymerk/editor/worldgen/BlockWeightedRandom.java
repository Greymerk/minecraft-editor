package greymerk.editor.worldgen;

import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.editor.util.WeightedChoice;
import greymerk.editor.util.WeightedRandomizer;

public class BlockWeightedRandom extends BlockBase {

	private WeightedRandomizer<IBlockFactory> blocks;
	
	public BlockWeightedRandom(){
		blocks = new WeightedRandomizer<IBlockFactory>();
	}
	
	public BlockWeightedRandom(JsonElement data) {
		this();
		for(JsonElement entry : (JsonArray)data){
			JsonObject d = entry.getAsJsonObject();
			int weight = d.get("weight").getAsInt();
			IBlockFactory toAdd = BlockProvider.create(d);
			this.addBlock(toAdd, weight);
		}
	}

	public void addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand);
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}
}
