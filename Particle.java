
public class Particle {
	
	private Vector position;
	private double mass;
	private Vector velocity;
	
	public Particle() {
		position = new Vector(0.0, 0.0);
		
		mass = 1.0;
		
		velocity = new Vector(0.0, 0.0);
	}
	
	public Particle(Vector position, double mass, Vector velocity) {
		this.position = new Vector(position);
		
		this.mass = mass;
		
		this.velocity = new Vector(velocity);
	}
	
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
	
	public Vector getMomentem() {
		
		Vector temp = new Vector();
		
		temp = temp.Mult(mass);
		
		return temp;
		
	}
	
	public void applyForce(Vector force, double time) {
		
		Vector acceleration = new Vector();
		acceleration = force.Mult(1/mass);
		
		velocity = velocity.add(acceleration.Mult(time));
		
	}

}
