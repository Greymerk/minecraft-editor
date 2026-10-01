package com.greymerk.editor.editor;

import java.nio.file.Path;
import java.util.Optional;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.structure.StructureSet;



public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public MetaBlock getBlock(Coord pos);
	
	public boolean hasBlockEntity(Coord pos);
	
	public BlockEntity getBlockEntity(Coord pos);
	
	public <T> Optional<T> setBlockEntity(Coord pos, MetaBlock block, Class<T> beClass);
	
	public long getSeed();
	
	public RandomSource getRandom(Coord pos);
			
	public Coord findSurface(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean surroundingChunksLoaded(Coord pos);
	
	public boolean isOverworld();

	public boolean isSolid(Coord pos);
		
	public boolean isSupported(Coord pos);
	
	public boolean isFaceFullSquare(Coord pos, Cardinal dir);
	
	public int getMaxDepth();
	
	public RegistryAccess getRegistryManager();
	
	public FeatureFlagSet getFeatureSet();
	
	public Path getWorldDirectory();
	
	public GameRules getGameRules();
	
	public ServerLevel getServerWorld();
	
	//public RoguelikeState getState();

	boolean isAir(Coord pos);
	
	public Optional<Coord> getStructureLocation(ResourceKey<StructureSet> key, ChunkPos cpos);
}
