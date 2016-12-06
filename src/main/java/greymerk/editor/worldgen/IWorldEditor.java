package greymerk.editor.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.Biome;

public interface IWorldEditor {

	boolean setBlock(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	MetaBlock getBlock(Coord pos);

	boolean isAirBlock(Coord pos);
	
	TileEntity getTileEntity(Coord pos);

	Biome getBiome(Coord pos);
	
	int getDimension();
	
	long getSeed();
	
	Random getSeededRandom(int m, int n, int i);
	
	void fillDown(Random rand, Coord pos, IBlockFactory pillar);
	
	boolean canPlace(MetaBlock block, Coord pos, Cardinal dir);
	
	boolean validGroundBlock(Coord pos);

	void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

	int getStat(Block block);

	boolean isReplaceable(Coord pos);

}
