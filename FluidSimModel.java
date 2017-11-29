import java.util.*;

public class FluidSimModel {
	
	ArrayList<Particle> particles;
	Box box;
	
	public FluidSimModel(double boxWidth, double boxHeight) {
		
		particles = new ArrayList<Particle>();
		box = new Box(boxWidth, boxHeight);
	
	}
	
	public void addParticle(Vector position, double mass, Vector velocity) {
		
		Particle toAdd = new Particle(position, mass, velocity);
		particles.add(toAdd);
		
	}
	
	public Particle getParticleAt(int index) {
		
		return particles.get(index);
		
	}

}
