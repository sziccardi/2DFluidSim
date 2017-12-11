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
						if (p.distanceTo(q) < 30) {
							p.bounceOff(q, dt);
						}
					}
				}

				// check if it hits any walls
				ArrayList<Vector> normals = box.overlaps(p);

				if (normals.size() == 2) {
					// in a corner
					p.setVelocity(new Vector(0.0, 0.0));
					if (Math.abs(normals.get(0).getX()) >= Math.abs(normals.get(0).getX())) {
						Vector parallel = normals.get(0).perp();
						double angleBetween = Math.acos(parallel.dot(p.getNetForce().add(g) ) / (p.getNetForce().add(g).getLength()));
						p.applyForce(normals.get(1).vectorMult((p.getNetForce().add(g).getLength() * Math.cos(angleBetween))));
						p.applyForce(normals.get(0).vectorMult((p.getNetForce().add(g).getLength()  * Math.sin(angleBetween))));
					} else {
						Vector parallel = normals.get(1).perp();
						double angleBetween = Math.acos(parallel.dot(p.getNetForce().add(g)) / (p.getNetForce().add(g).getLength()));
						p.applyForce(normals.get(0).vectorMult((p.getNetForce().add(g).getLength()  * Math.cos(angleBetween))));
						p.applyForce(normals.get(1).vectorMult((p.getNetForce().add(g).getLength()  * Math.sin(angleBetween))));
					}

				}
				if (normals.size() == 1) {
					// hitting just one wall
					p.setVelocity(new Vector(0.0, 0.0));
					// vector parallel to the box's edge
					Vector parallel = normals.get(0).perp();
					parallel.normalize();
					// angle between the box's edge and gravity
					double angleBetween = Math.acos(parallel.dot(p.getNetForce().add(g)) / p.getNetForce().add(g).getLength());
					// apply normal force from the ground //PLAY WITH SCALING
					p.applyForce(normals.get(0).vectorMult((p.getNetForce().add(g).getLength() * Math.sin(angleBetween))));
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
