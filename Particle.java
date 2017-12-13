import java.util.ArrayList;

public class Particle {

	private double radius;

	private Vector position;
	private double mass;
	private Vector velocity;

	private Vector netForce;

	private Vector acceleration;

	/**
	 * Important Equations to note : Force = mass * acceleration change in velocity
	 * = acceleration * time elapsed momentum = mass * velocity
	 */

	/** CONSTRUCTORS */
	public Particle() {
		position = new Vector(0.0, 0.0);
		mass = 1.0;
		velocity = new Vector(0.0, 0.0);
		radius = 1.0;
		// TODO : look at this
		acceleration = new Vector(0.0, 0.0);
	}

	public Particle(Vector position, double mass, Vector velocity, double radius) {
		this.position = new Vector(position);
		this.mass = mass;
		this.velocity = new Vector(velocity);
		this.radius = radius;
		acceleration = new Vector(0.0, 0.0);
	}

	/** GETTERS & SETTERS */
	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public Vector getPosition() {
		return position;
	}

	public double getMass() {
		return mass;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public double getRadius() {
		return radius;
	}

	public Vector getMomentem() {
		Vector temp = new Vector();
		temp = velocity.vectorMult(mass);
		return temp;
	}

	/** OPERATIONS */

//	public void applyForce(Vector force) {
//
//		netForce = netForce.add(force);
//
//	}

	public double distanceTo(Particle other) {
		return Math.sqrt(Math.pow(position.getX() - other.getPosition().getX(), 2)
				+ Math.pow(position.getY() - other.getPosition().getY(), 2));
	}

	public boolean overlaps(Particle other) {

		if (distanceTo(other) < radius + other.getRadius()) {
			// StdOut.println("I hit a particle!");
			return true;
		} else {
			return false;
		}
	}

	public void move(double dt) {

//		netForce = netForce.add(new Vector(0.0, -9.8));
//
		
		// StdOut.println("my net force is " + netForce.toString()); //Gives us the
		// correct net force?
		;
		// StdOut.println("my acceleration is " + acceleration.toString());

		velocity = velocity.add(acceleration.vectorMult(dt));

		// StdOut.println("my velocity is " + velocity.toString());

		position.setX(position.getX() + velocity.getX() * dt);
		position.setY(position.getY() + velocity.getY() * dt);

	}

	public void setAcceleration (Vector vector) {
		acceleration = vector;
	}
	
	public Vector getNetForce() {
		return netForce;
	}

	public void setNetForce(Vector vector) {
		netForce = vector;

	}

	public void rotate(double angle) {
		double[][] rotationMatrix = { { Math.cos(angle), -Math.sin(angle), 0 }, { Math.sin(angle), Math.cos(angle), 0 },
				{ 0, 0, 1 } };

		Vector originalPoint = position.add(new Vector(-300, -300));
		Vector transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		position = transformedPoint;

	}

	/**
	 * repulsive force method : adapted from 2010 Game Developers Conference
	 * presentation by Jaymin Kessler, Q-Games, Technology Team,
	 * jaymin+gdc@q-games.com normal force method : adapted from Particle.java from
	 * Sedwick and Wayne Algorithms 4th edition website
	 * 
	 * @param q
	 */
	// NEED TO FIX
	public void bounceOff(Particle q, double dt) {
		// REPULSIVE FORCE METHOD
		// //direction of one particles center to another
		// Vector deltaX = new Vector (q.getPosition().getX()-position.getX(),
		// q.getPosition().getY()-position.getY() );
		// deltaX.normalize();
		// //apply a repulsive force between particles that is scaled by their distance
		// if((distanceTo(q)-radius) < 0) {StdOut.println("something baaad"); }
		// applyForce(deltaX.vectorMult(-50/(distanceTo(q)-radius) ) );

		// NORMAL FORCE METHOD
		// Vector deltaPosition = position.add(q.getPosition().vectorMult(-1));
		// Vector deltaForce = netForce.add(q.getNetForce());
		// double amount = deltaForce.dot(deltaPosition);
		// deltaPosition.normalize();
		// Vector forceInDirection = deltaPosition.vectorMult(amount);
		// //magnitude of normal force
		// velocity = velocity.add(new Vector(forceInDirection.getX() * dt / mass,
		// forceInDirection.getY() * dt / mass));
		//

		// WALL METHOD
		// Vector wall = position.add(q.getPosition().vectorMult(-1)).perp();
		// wall.normalize();
		// double theta = Math.acos(wall.getLength() / velocity.getLength());
		// double alpha = Math.acos(wall.getLength() / q.getVelocity().getLength());
		// double wallAngle = Math.atan(wall.getY() / wall.getX());
		// Vector tempVelocity = velocity;
		// velocity = new Vector(Math.cos(wallAngle - theta), Math.sin(wallAngle -
		// theta)).vectorMult(q.getVelocity().getLength());
		// q.setVelocity (new Vector(Math.cos(wallAngle + alpha), Math.sin(wallAngle +
		// alpha)).vectorMult(tempVelocity.getLength()) );
		// StdOut.println("set my velocity to " + velocity.toString() + " and " +
		// q.getVelocity().toString());

		// AVERAGE PARALLEL VELOCITY
		Vector deltaPosition = position.add(q.getPosition().vectorMult(-1));
		deltaPosition.normalize();
		Vector parallelVelocityP = deltaPosition.vectorMult(velocity.dot(deltaPosition));
		Vector perpVelocityP = parallelVelocityP.perp();
		Vector parallelVelocityQ = deltaPosition.vectorMult(q.getVelocity().dot(deltaPosition));
		Vector perpVelocityQ = parallelVelocityQ.perp();

		// Vector averageParallel = deltaPosition.vectorMult((
		// parallelVelocityP.getLength() + parallelVelocityQ.getLength() ) /2 );
		//
		// velocity = perpVelocityP.add(averageParallel);
		// q.setVelocity(perpVelocityQ.add(averageParallel.vectorMult(-1)));

		Vector v1 = deltaPosition.vectorMult(q.getVelocity().getLength());
		Vector v2 = deltaPosition.vectorMult(velocity.getLength());

		velocity = perpVelocityP.add(v1).vectorMult(0.5);
		q.setVelocity(perpVelocityQ.add(v2.vectorMult(-1)).vectorMult(0.5));
		
		//velocity.add(perpVelocityP.add(v1).vectorMult(-0.5)).vectorMult(mass/dt);
//		applyForce(velocity.add(perpVelocityP.add(v1).vectorMult(-0.5)).vectorMult(mass/dt));
//		q.applyForce(q.getVelocity().add(perpVelocityQ.add(v2.vectorMult(-1)).vectorMult(-0.5)).vectorMult(q.getMass()/dt));
//		
	}

	public void setRadius(double d) {
		radius = d;

	}

	public Vector getAcceleration() {
		return acceleration;
	}

}
