
public class Vector {
	
	private double X;
	private double Y;
	
	/** CONSTRUCTORS */
	public Vector() {
		X = 0.0;
		Y = 0.0;
	}
	
	public Vector(double x, double y) {
		X = x;
		Y = y;
	}
	
	public Vector(Vector a) {
		X = a.getX();
		Y = a.getY();
	}
	
	/** GETTERS & SETTERS */	
	public void setX(double x) {
		X = x;
	}
	
	public void setY(double y) {
		Y = y;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	public double getLength() {
		return Math.sqrt(X*X + Y*Y);
	}
	
	/** OPERATIONS */
	public Vector add(Vector a) {
		
		Vector temp = new Vector();
		
		temp.setX(a.getX() + getX());
		temp.setY(a.getY() + getY());
		
		return temp;
		
	}
	
	public Vector Mult(double scale) {
		
		Vector temp = new Vector();
			
		temp.setX(scale * getX());
		temp.setY(scale * getY());
		
		return temp;
		
	}
	
	public void normalize() {
		
		Vector temp = new Vector();
		temp = Mult(1/getLength());
		
		setX(temp.getX());
		setY(temp.getY());
		
	}

}
