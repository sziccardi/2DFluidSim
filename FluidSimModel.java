import java.util.*;

public class FluidSimModel {
	
	private ArrayList<Particle> particles;
	private Box box;
	
	private double dt = 0.01;
	
	/** CONSTRUCTORS */
	public FluidSimModel(double boxWidth, double boxHeight) {
		
		particles = new ArrayList<Particle>();
		box = new Box(boxWidth, boxHeight);
	
	}
	
	/** OPERATIONS */
	
	public void advance() {
		
		if(StdDraw.isKeyPressed(']')) {
			box.rotate(-0.01);
		}
		
		if(StdDraw.isKeyPressed('[')) {
			box.rotate(0.01);
		}
		
		for(Particle p : particles) {
			
			//check if it hits any other particles
			for(Particle q: particles) {
				if(p != q) {
					if( p.overlaps(q) ){
						Vector temp1 = new Vector();
						temp1 = p.getVelocity().vectorMult(p.getMass());
						Vector temp2 = new Vector();
						temp2 = q.getVelocity().vectorMult(q.getMass());
						Vector initialMomentum = new Vector();
						initialMomentum = temp1.add(temp2);
						Vector finalVelocity = new Vector();
						finalVelocity = initialMomentum.vectorMult(1/(p.getMass() + q.getMass()));
						p.setVelocity(finalVelocity);
						q.setVelocity(finalVelocity);
					}
				}
			}
			//check if it hits any walls
			switch(box.overlaps(p)) {
			case 1:
				//StdOut.println("I hit the floor!");
				p.setVelocity(new Vector(p.getVelocity().getX(), 0.0));
				p.applyForce(box.getFloorNormal().dot(p.getNetForce().vectorMult(-1)));
				break;
			case 2:
				p.setVelocity(new Vector(p.getVelocity().getX(), 0.0));
				p.applyForce(box.getCeilingNormal().dot(p.getNetForce().vectorMult(-1)));
				break;
			case 3: 
				p.setVelocity(new Vector(0.0, p.getVelocity().getY()));
				p.applyForce(box.getLeftWallNormal().dot(p.getNetForce().vectorMult(-1)));
				break;
			case 4:
				p.setVelocity(new Vector(0.0, p.getVelocity().getY()));
				p.applyForce(box.getRightWallNormal().dot(p.getNetForce().vectorMult(-1)));
				break;
			default:
				//StdOut.println("not touching a wall!");
				p.applyForce(new Vector(0.0, -9.8));
				break;
			}
			
			//GUESSING THE FRAME RATE
			p.move(dt);
			
		}
	}
	
	public void addParticle(Vector position, double mass, Vector velocity, double radius) {
		
		Particle toAdd = new Particle(position, mass, velocity, radius);
		particles.add(toAdd);
		
	}
	
	/** GETTERS AND SETTERS */
	public Particle getParticleAt(int index) {
		
		return particles.get(index);
		
	}

	public Box getBox() {
		return box;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

}
