
public class Particle {
	
	private double radius;
	
	private Vector position;
	private double mass;
	private Vector velocity;
	
	private Vector netForce;
	
	/** Important Equations to note : 
	 * Force = mass * acceleration
	 * change in velocity = acceleration * time elapsed
	 * momentum = mass * velocity
	 */
	
	/** CONSTRUCTORS */
	public Particle() {
		position = new Vector(0.0, 0.0);
		mass = 1.0;
		velocity = new Vector(0.0, 0.0);
		radius = 1.0;
		//TODO : look at this
		netForce = new Vector(0.0, 0.0);
	}
	
	public Particle(Vector position, double mass, Vector velocity, double radius) {
		this.position = new Vector(position);
		this.mass = mass;
		this.velocity = new Vector(velocity);
		this.radius = radius;
		netForce = new Vector(0.0, 0.0);
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

	public void applyForce(Vector force) {
		
		netForce = netForce.add(force);
		
	}
	
	public double distanceTo(Particle other) {
		return Math.sqrt(Math.pow(position.getX()-other.getPosition().getX(),2)+Math.pow(position.getY()-other.getPosition().getY(),2));
	}
	
	public boolean overlaps(Particle other) {
		
		if(distanceTo(other) < radius+other.getRadius()) {
			return true;
		} else {
			return false; 
		}
	}

	public void move(double dt) {
		
		netForce = netForce.add(new Vector(0.0, -9.8));
		
		Vector acceleration = new Vector();
		//StdOut.println("my net force is " + netForce.toString()); //Gives us the correct net force?
		acceleration = netForce.vectorMult(1/mass);
		//StdOut.println("my acceleration is " + acceleration.toString());
		
		velocity = velocity.add(acceleration.vectorMult(dt));
		
		//StdOut.println("my velocity is " + velocity.toString());
		
		position.setX(position.getX() + velocity.getX()*dt);
		position.setY(position.getY() + velocity.getY()*dt);
		
	}
	

	public Vector getNetForce() {
		return netForce;
	}

	public void setNetForce(Vector vector) {
		netForce = vector;
		
	}

	public void rotate(double angle) {
		double[][] rotationMatrix = { { Math.cos( angle), -Math.sin( angle), 0 },
				{ Math.sin( angle), Math.cos( angle), 0 }, { 0, 0, 1 } };
	
		Vector originalPoint = position.add(new Vector(-300, -300));
		Vector transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		position = transformedPoint;
		
	}

}
