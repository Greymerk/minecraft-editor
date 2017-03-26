package greymerk.editor.worldgen;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.editor.worldgen.shapes.RectSolid;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldEditor implements IWorldEditor{
	
	World world;
	private Map<Block, Integer> stats;
	
	public WorldEditor(World world){
		this.world = world;
		stats = new HashMap<Block, Integer>();
	}
	
	private boolean setBlock(Coord pos, MetaBlock block, int flags, boolean fillAir, boolean replaceSolid){
		
		MetaBlock currentBlock = getBlock(pos);
		
		if(currentBlock.getBlock() == Blocks.CHEST) return false;
		if(currentBlock.getBlock() == Blocks.TRAPPED_CHEST) return false;
		if(currentBlock.getBlock() == Blocks.MOB_SPAWNER) return false;
		
		boolean isAir = world.isAirBlock(pos.getBlockPos());
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
		
		try{
			world.setBlockState(pos.getBlockPos(), block.getState(), flags);
		} catch(NullPointerException npe){
			//ignore it.
		}
		
		Block type = block.getBlock();
		SoundType sound = type.getSoundType(block, world, pos.getBlockPos(), null);
		
		world.playSound(
				(EntityPlayer)null,
				(double)((float)pos.getX() + 0.5F), 
				(double)((float)pos.getY() + 0.5F), 
				(double)((float)pos.getZ() + 0.5F), 
				sound.getPlaceSound(),
				SoundCategory.BLOCKS,
				(sound.getVolume() + 1.0F) / 2.0F, 
				sound.getPitch() * 0.8F);
		
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
		
	}
	
	@Override
	public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid){
		return this.setBlock(pos, block, block.getFlag(), fillAir, replaceSolid);
	}
	
	@Override
	public boolean isAirBlock(Coord pos){
		return world.isAirBlock(pos.getBlockPos());
	}
	
	@Override
	public long getSeed(){
		return this.world.getSeed();
	}
	
	@Override
	public Biome getBiome(Coord pos){
		return world.getBiome(pos.getBlockPos());
	}
	
	@Override
	public int getDimension(){
		return world.provider.getDimensionType().getId();
	}
	
	@Override
	public Random getSeededRandom(int a, int b, int c){
		return world.setRandomSeed(a, b, c);
	}
		
	@Override
	public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		RectSolid.fill(this, rand, start, end, air);
		fill.set(this, rand, origin);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(this, cursor);
		cursor.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(this, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(this, cursor);
	}
	
	@Override
	public void fillDown(Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!getBlock(cursor).isOpaqueCube() && cursor.getY() > 1){
			blocks.set(this, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	
	@Override
	public MetaBlock getBlock(Coord pos){
		return new MetaBlock(world.getBlockState(pos.getBlockPos()));
	}
	
	@Override
	public TileEntity getTileEntity(Coord pos){
		return world.getTileEntity(pos.getBlockPos());
	}
	
	@Override
	public boolean validGroundBlock(Coord pos){
		
		if(isAirBlock(pos)) return false;
		
		MetaBlock block = this.getBlock(pos);
		
		if(block.getMaterial() == Material.WOOD) return false;
		if(block.getMaterial() == Material.WATER) return false;
		if(block.getMaterial() == Material.CACTUS) return false;
		if(block.getMaterial() == Material.SNOW) return false;
		if(block.getMaterial() == Material.GRASS) return false;
		if(block.getMaterial() == Material.GOURD) return false;
		if(block.getMaterial() == Material.LEAVES) return false;
		if(block.getMaterial() == Material.PLANTS) return false;
		
		return true;
	}
	
	@Override
	public String toString(){
		String toReturn = "";

		for(Map.Entry<Block, Integer> pair : stats.entrySet()){
			toReturn += pair.getKey().getLocalizedName() + ": " + pair.getValue() + "\n";
		}
		
		return toReturn;
	}
	
	@Override
	public int getStat(Block type){
		if(!this.stats.containsKey(type)) return 0;
		return this.stats.get(type);
	}
	

	@Override
	public boolean canPlace(MetaBlock block, Coord pos, Cardinal dir){
		if(!this.isAirBlock(pos)) return false;
		return block.getBlock().canPlaceBlockOnSide(world, pos.getBlockPos(), Cardinal.facing(dir));
	}

	@Override
	public boolean isReplaceable(Coord pos) {
		Block target = this.getBlock(pos).getBlock();
		return target.isReplaceable(world, pos.getBlockPos());
	}
}

