
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
	
	public Vector vectorMult(double scale) {
		
		Vector temp = new Vector();
			
		temp.setX(scale * getX());
		temp.setY(scale * getY());
		
		return temp;
		
	}
	
	public Vector matrixMult(double[][] a) {
		double total;
		double [] temp = new double [3];
		for (int i = 0; i < 3; i++) {
				total = 0; 
				for (int k = 0; k < 3; k++) {
					switch (k) {
					case 0: 
						total += a[i][k] * X;
						break;
					case 1:
						total += a[i][k] * Y;
						break;
					case 2:
						total += a[i][k];
						break;
					default: 
						break;
					}
			temp [i] = total;		
				}
			}
		Vector res = new Vector (temp[0]/temp[2], temp[1]/temp[2]);
		return res;
		}
	
	public Vector dot(Vector a) {
		return vectorMult(X*a.getX() + Y*a.getY());
		
	}
	
	public void normalize() {
		
		Vector temp = new Vector();
		temp = vectorMult(1/getLength());
		
		setX(temp.getX());
		setY(temp.getY());
		
	}
	
	@Override
	public String toString() {
		return "[" + X + ", " + Y + "]";
	}

}
