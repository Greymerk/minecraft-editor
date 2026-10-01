package com.greymerk.editor.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.greymerk.editor.editor.Cardinal;
import com.greymerk.editor.editor.Coord;
import com.greymerk.editor.editor.boundingbox.BoundingBox;

import net.minecraft.world.level.ChunkPos;


public class ChunkSet implements Iterable<ChunkPos>{

	private Set<ChunkPos> chunks;
	
	public ChunkSet(Coord origin, int range) {
		this.chunks = new HashSet<ChunkPos>();
		
		ChunkPos center = origin.getChunkPos();
		BoundingBox.of(Coord.of(center.x(), 0, center.z())).grow(Cardinal.directions, range >> 4).forEach(pos -> {
			this.chunks.add(new ChunkPos(pos.getX(), pos.getZ()));
		});
	}
	
	@Override
	public Iterator<ChunkPos> iterator(){
		return chunks.iterator();
	}
	
}
