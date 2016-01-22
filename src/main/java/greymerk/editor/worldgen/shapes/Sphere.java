package greymerk.editor.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.editor.worldgen.Coord;
import greymerk.editor.worldgen.IBlockFactory;
import greymerk.editor.worldgen.IWorldEditor;

public class Sphere implements IShape {

	private Coord s;
	private Coord e;
	private List<Coord> sphere;
	
	public Sphere(Coord start, Coord end){
		this.s = new Coord(start);
		this.e = new Coord(end);
		
		Coord.correct(this.s, this.e);
		Coord diff = this.e.sub(this.s);
		
		int r = diff.getX();
		r = r < diff.getY() ? diff.getY() : r;
		r = r < diff.getZ() ? diff.getZ() : r;
		
		diff = new Coord(r, r, r);
		
		this.s = new Coord(start);
		this.s.sub(diff);
		this.e = new Coord(start);
		this.e.add(diff);

		RectSolid cube = new RectSolid(this.s, this.e);
		
		double radius = (double)r + 0.5;
		
		this.sphere = new ArrayList<Coord>();
		for(Coord pos : cube){
			double d = pos.distance(start);
			if(d < radius) this.sphere.add(pos);
		}
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return sphere.iterator();
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.fill(editor, rand, block, true, true);

	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		for(Coord pos : sphere){
			block.set(editor, rand, pos, fillAir, replaceSolid);
		}
	}

	@Override
	public List<Coord> get() {
		List<Coord> copy = new ArrayList<Coord>();
		copy.addAll(sphere);
		return copy;
	}

}
