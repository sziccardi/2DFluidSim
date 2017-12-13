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

		if (StdDraw.isKeyPressed(']')) {

			box.rotate(-0.01);

			rotateParticles(-0.01);
		}

		if (StdDraw.isKeyPressed('[')) {

			box.rotate(0.01);

			rotateParticles(0.01);
		}
		if (particles.size() != 0) {
			for (int i = 0; i < particles.size(); i++) {
				Particle p = particles.get(i);
				p.setNetForce(new Vector(0.0, 0.0));
				// check if it hits any other particles
				// StdOut.println("my size is " + particles.size());
				if (particles.size() != 1) {
					for (int j = i + 1; j < particles.size(); j++) {
						Particle q = particles.get(j);
						if (p.overlaps(q)) {
							p.bounceOff(q, dt);
						}
					}
				}

				// check if it hits any walls
				ArrayList<Vector> normals = box.overlaps(p);

				if (normals.size() == 0) {
					p.setAcceleration(g);
				}
				// in a corner
				if (normals.size() == 2) {
					
					p.setVelocity(new Vector(0.0, 0.0));
					if (Math.abs(normals.get(0).getX()) >= Math.abs(normals.get(0).getX())) {
						Vector parallel = normals.get(0).perp();
						double angleBetween = Math
								.acos(parallel.dot(p.getNetForce().add(g)) / (p.getNetForce().add(g).getLength()));
						p.setAcceleration(normals.get(1)
								.vectorMult((-p.getAcceleration().add(g).getLength() * Math.cos(angleBetween))));
						p.setAcceleration(normals.get(0)
								.vectorMult((-p.getAcceleration().add(g).getLength() * Math.sin(angleBetween))));
					} else {
						Vector parallel = normals.get(1).perp();
						double angleBetween = Math
								.acos(parallel.dot(p.getNetForce().add(g)) / (p.getNetForce().add(g).getLength()));
						p.setAcceleration(normals.get(0)
								.vectorMult((-p.getAcceleration().add(g).getLength() * Math.cos(angleBetween))));
						p.setAcceleration(normals.get(1)
								.vectorMult((-p.getAcceleration().add(g).getLength() * Math.sin(angleBetween))));
					}

				}
				// hitting just one wall
				if (normals.size() == 1) {

					// set velocity thats in the direction of the wall to zero
					Vector wallVector = normals.get(0).perp();
					wallVector.normalize();
					Vector parallelVelocity = wallVector.vectorMult(p.getVelocity().dot(wallVector));
					Vector perpVelocityAway = new Vector();
					//if velocity is away from the wall
					if (normals.get(0).dot(p.getVelocity()) < 0) {
						perpVelocityAway = normals.get(0).vectorMult(normals.get(0).dot(p.getVelocity()));
					}
					p.setVelocity(parallelVelocity.add(perpVelocityAway));

					// angle between the box's edge and gravity
					double angleBetween = Math
							.acos(wallVector.dot(p.getNetForce().add(g)) / p.getNetForce().add(g).getLength());
					// apply normal force from the ground
					p.setAcceleration(
							normals.get(0).vectorMult((-p.getAcceleration().add(g).getLength() * Math.sin(angleBetween))));
				}
				p.move(dt);
			}

		}
	}

	private void rotateParticles(double angle) {
		for (Particle p : particles) {

			p.rotate(angle);

		}

	}

	public void addParticle(Vector position, double mass, Vector velocity, double radius) {

		Particle toAdd = new Particle(position, mass, velocity, radius);
		particles.add(toAdd);

	}
	
	public void addParticle(Particle p) {
		
		particles.add(p);
		
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
