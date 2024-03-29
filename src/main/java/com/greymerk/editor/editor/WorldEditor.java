package com.greymerk.editor.editor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.ChunkStatus;

public class WorldEditor implements IWorldEditor{

	WorldAccess world;
	private Map<Block, Integer> stats;
	
	public WorldEditor(StructureWorldAccess world){
		this.world = world;
		stats = new HashMap<Block, Integer>();
	}

	public WorldEditor(World world) {
		this.world = world;
		stats = new HashMap<Block, Integer>();
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
		MetaBlock currentBlock = getBlock(pos);
		
		if(currentBlock.getBlock() == Blocks.CHEST) return false;
		if(currentBlock.getBlock() == Blocks.TRAPPED_CHEST) return false;
		if(currentBlock.getBlock() == Blocks.SPAWNER) return false;
		
		if(!fillAir && this.isAir(pos)) return false;
		if(!replaceSolid && this.isSolid(pos))	return false;
		BlockSoundGroup soundGroup = block.getState().getSoundGroup();
		SoundEvent sound = soundGroup.getPlaceSound();
		try{
			world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
			world.playSound(null, pos.getBlockPos(), 
					sound, SoundCategory.BLOCKS, 
					(soundGroup.getVolume() + 1.0f) / 2.0f, 
					soundGroup.getPitch() * 0.8f);
		} catch(NullPointerException npe){
			//ignore it.
		}
		
		Block type = block.getBlock();
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
	}
	
	@Override
	public boolean set(Coord pos, MetaBlock metaBlock) {
		return this.set(pos, metaBlock, true, true);
	}
	
	@Override
	public MetaBlock getBlock(Coord pos) {
		BlockState state = world.getBlockState(pos.getBlockPos());
		return new MetaBlock(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return world.isAir(pos.getBlockPos());
	}

	@Override
	public long getSeed() {
		MinecraftServer server = this.world.getServer();
		ServerWorld sw = server.getOverworld();
		return sw.getSeed();
	}
	
	@Override
	public Random getRandom(Coord pos) {
		return new CheckedRandom(Objects.hash(getSeed(), pos.hashCode()));
	}

	public boolean isChunkLoaded(Coord pos) {
		return world.getChunk(pos.getBlockPos()).getStatus() == ChunkStatus.FULL;
	}

	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), 256, pos.getZ());
		
		while(cursor.getY() > 60) {
			MetaBlock m = this.getBlock(cursor);
			if(m.getState().isIn(BlockTags.LOGS)) continue;
			if(m.getState().isIn(BlockTags.LEAVES)) continue;
			
			if(!isAir(cursor) && !isPlant(cursor)) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}
	
	@Override
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isSolidBlock(world, pos.getBlockPos());
	}
	
	public boolean isPlant(Coord pos) {
		BlockState bs = getBlock(pos).getState();
		if(bs.isIn(BlockTags.LOGS)) return true;
		if(bs.isIn(BlockTags.SWORD_EFFICIENT)) return true;
		return false;
	}
	
	public boolean isGround(Coord pos) {
		if(isPlant(pos)) return false;
		if(this.isAir(pos)) return false;
		
		List<TagKey<Block>> tags = new ArrayList<TagKey<Block>>();
		tags.add(BlockTags.BASE_STONE_OVERWORLD);
		tags.add(BlockTags.DIRT);
		tags.add(BlockTags.SAND);
		tags.add(BlockTags.SNOW);
		tags.add(BlockTags.STONE_ORE_REPLACEABLES);
		
		MetaBlock m = getBlock(pos);
		
		for(TagKey<Block> tag : tags) {
			if(m.getState().isIn(tag)) return true;
		}
		return false;
	}
	
	public boolean isOverworld() {
		return this.world.getDimension().hasSkyLight();
	}

	@Override
	public BlockEntity getBlockEntity(Coord pos) {
		return world.getBlockEntity(pos.getBlockPos());
	}
	
	@Override
	public boolean isFaceFullSquare(Coord pos, Cardinal dir) {
		BlockState b = this.world.getBlockState(pos.getBlockPos());
		Direction facing = Cardinal.facing(dir);
		VoxelShape shape = b.getSidesShape(world, pos.getBlockPos());
		VoxelShape collision = b.getCollisionShape(world, pos.getBlockPos());
		boolean isShapeSquare = Block.isFaceFullSquare(shape, facing);
		boolean isCollisionSquare = Block.isFaceFullSquare(collision, facing);
		return isShapeSquare || isCollisionSquare;
	}

	@Override
	public int getMaxDepth() {
		return world.getBottomY();
	}
	
	public DynamicRegistryManager getRegistryManager() {
		DynamicRegistryManager reg = this.world.getRegistryManager();
		return reg;
	}
	
	public Path getWorldDirectory() {
		return this.world.getServer().getSavePath(WorldSavePath.ROOT);
	}

	@Override
	public boolean isReplaceable(Coord pos) {
		BlockState bs = this.world.getBlockState(pos.getBlockPos());
		return bs.isReplaceable();
	}
}
