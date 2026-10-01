package com.greymerk.editor.editor;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.shapes.VoxelShape;



public class WorldEditor implements IWorldEditor{

	private Level world;
	private ResourceKey<Level> worldKey;
	
	public static WorldEditor of(Level world) {
		return new WorldEditor(world);
	}
	
	public static WorldEditor of(ServerLevel world) {
		return new WorldEditor(world);
	}

	private WorldEditor(Level world) {
		this.world = world;
		this.worldKey = world.dimension();
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
		if(this.hasBlockEntity(pos)) return false;
		
		if(!fillAir && this.isAir(pos)) return false;
		if(!replaceSolid && this.isSolid(pos))	return false;
		
		try{
			world.setBlock(pos.getBlockPos(), block.getState(), block.getFlag());
		} catch(NullPointerException npe){
			//ignore it.
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
		return MetaBlock.of(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return this.world.isEmptyBlock(pos.getBlockPos());
	}
	
	@Override
	public long getSeed() {
		MinecraftServer server = this.world.getServer();
		ServerLevel sw = server.overworld();
		return sw.getSeed();
	}
	
	@Override
	public RandomSource getRandom(Coord pos) {
		return new LegacyRandomSource(Objects.hash(getSeed(), pos.hashCode()));
	}

	public boolean isChunkLoaded(Coord pos) {
		ChunkPos cp = pos.getChunkPos();
		return world.hasChunk(cp.x(), cp.z());
	}
	
	public boolean surroundingChunksLoaded(Coord pos) {
		ChunkPos cpos = pos.getChunkPos();
		for(int x = cpos.x() - 1; x <= cpos.x() + 1; x++) {
			for(int z = cpos.z() - 1; z <= cpos.z() + 1; z++) {
				if(!world.hasChunk(x, z)) return false;
				ChunkAccess chunk = world.getChunk(x, z);
				ChunkStatus status = chunk.getPersistedStatus();
				if(status != ChunkStatus.FULL) return false;
			}
		}
		
		return true;
	}

	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), world.getMaxY(), pos.getZ());
		
		while(cursor.getY() > 60) {
			MetaBlock m = this.getBlock(cursor);
			if(m.getState().is(BlockTags.LOGS)) continue;
			if(m.getState().is(BlockTags.LEAVES)) continue;
			
			if(!this.isAir(cursor) && !m.isPlant()) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}
	
	@Override
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isRedstoneConductor(world, pos.getBlockPos());
	}
	
	public boolean isSupported(Coord pos) {
		if(pos.getY() <= world.getMinY()) return false;
		Coord under = pos.copy().add(Cardinal.DOWN);
		Block b = this.world.getBlockState(under.getBlockPos()).getBlock();
		if(b instanceof FallingBlock) {
			return isSupported(under);
		}
		
		if(!FallingBlock.isFree(world.getBlockState(under.getBlockPos()))) return true;
		return false;
	}
	

	
	public boolean isOverworld() {
		MinecraftServer mcServer = world.getServer();
		ServerLevel sw = mcServer.getLevel(worldKey);
		return sw.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD);
	}

	@Override
	public boolean hasBlockEntity(Coord pos) {
		return this.getBlockEntity(pos) != null;
	}
	
	@Override
	public BlockEntity getBlockEntity(Coord pos) {
		return world.getBlockEntity(pos.getBlockPos());
	}
	
	@Override
	public <T> Optional<T> setBlockEntity(Coord pos, MetaBlock block, Class<T> beClass){
		if(!this.set(pos, block)) return Optional.empty();
		Optional<BlockEntity> obe = Optional.of(this.getBlockEntity(pos));
		if(obe.isEmpty()) return Optional.empty();
		
		BlockEntity be = obe.get();
		if(beClass.isInstance(be)) {
			return Optional.of(beClass.cast(be));
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public boolean isFaceFullSquare(Coord pos, Cardinal dir) {
		BlockState b = this.world.getBlockState(pos.getBlockPos());
		Direction facing = Cardinal.facing(dir);
		VoxelShape shape = b.getShape(world, pos.getBlockPos());
		VoxelShape collision = b.getCollisionShape(world, pos.getBlockPos());
		boolean isShapeSquare = Block.isFaceFull(shape, facing);
		boolean isCollisionSquare = Block.isFaceFull(collision, facing);
		return isShapeSquare || isCollisionSquare;
	}

	@Override
	public int getMaxDepth() {
		return world.getMinY();
	}
	
	public RegistryAccess getRegistryManager() {
		return this.world.registryAccess();
	}
	
	public FeatureFlagSet getFeatureSet() {
		return this.world.enabledFeatures();
	}
	
	public Path getWorldDirectory() {
		return this.world.getServer().getWorldPath(LevelResource.ROOT);
	}
	
	public GameRules getGameRules() {
		return this.getServerWorld().getGameRules();
	}
	
	public ServerLevel getServerWorld() {
		MinecraftServer server = this.world.getServer();
		ServerLevel sw = server.overworld();
		return sw;
	}
	
	@Override
	public Optional<Coord> getStructureLocation(ResourceKey<StructureSet> key, ChunkPos cpos){
		MinecraftServer mcServer = world.getServer();
		ServerLevel sw = mcServer.getLevel(worldKey);
		ChunkGeneratorStructureState calculator = sw.getChunkSource().getGeneratorState();
		RegistryAccess reg = world.registryAccess();
		Registry<StructureSet> structures = reg.lookupOrThrow(Registries.STRUCTURE_SET);
		StructureSet structure = structures.getValue(key);
		StructurePlacement placement = structure.placement(); 
		
		if(!placement.isStructureChunk(calculator, cpos.x(), cpos.z())) return Optional.empty();
		return Optional.of(Coord.of(placement.getLocatePos(cpos)));
	}
}
