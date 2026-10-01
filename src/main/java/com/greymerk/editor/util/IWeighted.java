package com.greymerk.editor.util;

import net.minecraft.util.RandomSource;

public interface IWeighted<T> {

	public int getWeight();
	
	public T get(RandomSource rand);
		
}
