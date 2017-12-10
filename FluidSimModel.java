import java.util.*;

public class FluidSimModel {
	
	private ArrayList<Particle> particles;
	private Box box;
	
	private double dt = 0.01;
	
	private static final Vector g = new Vector(0.0, -9.8);
	
	/** CONSTRUCTORS */
	public FluidSimModel(double boxWidth, double boxHeight) {
		
		particles = new ArrayList<Particle>();
		box = new Box(boxWidth, boxHeight);
	
	}
	
	/** OPERATIONS */
	
	public void advance() {
		
		if(StdDraw.isKeyPressed(']')) {
		
			box.rotate(-0.01);
			
			rotateParticles(-0.01);
		}
		
		if(StdDraw.isKeyPressed('[')) {
			
			box.rotate(0.01);

			rotateParticles(0.01);
		}
		
		for(Particle p : particles) {
			p.setNetForce(new Vector(0.0, 0.0));
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
			//doesn't work if hits multiple walls at once
			//now overlaps returns the normal force direction
			//now all the parallels should be perpendicular to the returned value
			ArrayList<Vector> normals = box.overlaps(p);
			if(normals.size() == 0) {
				//not hitting anything
				//StdOut.println("not hitting anything");
				//p.applyForce(new Vector(0.0, -9.8));
			} 
			if (normals.size() == 2) {
				//TODO : FIX maybe
				//in a corner
				p.setVelocity(new Vector(0.0, 0.0));
				if (Math.abs( normals.get(0).getX()) >=  Math.abs( normals.get(0).getX())) {
					Vector parallel = normals.get(0).perp();
					double angleBetween = Math.acos( parallel.dot(g) / 9.8 );
					p.applyForce(normals.get(1).vectorMult(((9.8 * p.getMass() ) * Math.cos(angleBetween)) ));
					p.applyForce(normals.get(0).vectorMult(((9.8 * p.getMass() ) * Math.sin(angleBetween)) ));
				} else {
					Vector parallel = normals.get(1).perp();
					double angleBetween = Math.acos( parallel.dot(g) / 9.8 );
					p.applyForce(normals.get(0).vectorMult(((9.8 * p.getMass() ) * Math.cos(angleBetween)) ));
					p.applyForce(normals.get(1).vectorMult(((9.8 * p.getMass() ) * Math.sin(angleBetween)) ));
				}
				
				
			}
			if (normals.size() == 1){
				//hitting just one wall
				p.setVelocity(new Vector(0.0, 0.0));
				// vector parallel to the box's edge
				Vector parallel = normals.get(0).perp();
				/*
				Vector shouldBe = box.getBottomLeft().add(box.getBottomRight().vectorMult(-1));
				shouldBe.normalize();
				StdOut.println("my normal should be " + shouldBe.perp() + " and is " + normals.get(0));*/
				//StdOut.println("my normal is " + normals.get(0) + " so my parallel is " + parallel.toString());
				parallel.normalize();
				// angle between the box's edge and gravity
				double angleBetween = Math.acos( parallel.dot(g) / (9.8 * p.getMass() )); //THIS WORKS
				// apply normal force from the ground
				p.applyForce(normals.get(0).vectorMult((9.8 * p.getMass() * Math.sin(angleBetween)) ));
			}			
			p.move(dt);
			
		}
	}
	


	private void rotateParticles(double angle) {
		for(Particle p: particles) {
			
			p.rotate(angle);
		
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
