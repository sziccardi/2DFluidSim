import java.util.ArrayList;

public class Box {
	
	private double width;
	private double height;
	private double angle;
	private Vector topright;
	private Vector topleft;
	private Vector bottomright;
	private Vector bottomleft;
	
	/** CONSTRUCTORS */
	
	public Box(double width, double height) {
		this.width = width;
		this.height = height;
		angle = 0.0;
		setCorners();
	}
	
	/** OPERATIONS */ 
	
	public void rotate(double amount) {
		//rotation around the center of the box
		angle+=amount; 
		setCorners();
	}
	
	public boolean validParticle(Particle p) {
		
		Vector edgePoint = (bottomleft.add(bottomright).vectorMult(0.5));
		Particle temp = new Particle();
		temp.setPosition(edgePoint);
		Particle temp1 = new Particle();
		temp1.setPosition(new Vector(300, 300));
		double radius = temp.distanceTo(temp1);
		
		if(temp1.distanceTo(p) < radius) {
			return true;
		}
		
		return false;
	}
	
	private void setCorners() {
		double[][] rotationMatrix = { { Math.cos( getAngle()), -Math.sin( getAngle()), 0 },
				{ Math.sin( getAngle()), Math.cos( getAngle()), 0 }, { 0, 0, 1 } };
		// top right point
		Vector originalPoint = new Vector(.5 *  getWidth(), .5 *  getHeight());
		Vector transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		setTopRight(transformedPoint);
		// bottom right point
		originalPoint = new Vector(.5 *  getWidth(), -.5 *  getHeight());
		transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		 setBottomRight(transformedPoint);
		// bottom left point
		originalPoint = new Vector(-.5 *  getWidth(), -.5 *  getHeight());
		transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		 setBottomLeft(transformedPoint);
		// top left point
		originalPoint = new Vector(-.5 *  getWidth(), .5 *  getHeight());
		transformedPoint = originalPoint.matrixMult(rotationMatrix);
		transformedPoint.setX(300 + transformedPoint.getX());
		transformedPoint.setY(300 + transformedPoint.getY());
		 setTopLeft(transformedPoint);
	}
	
	/**
	 * returns the normal vector of all the walls acting on particle p
	 * @param p
	 * @return
	 */
	
	public ArrayList<Vector> overlaps(Particle p) {
		
		ArrayList<Vector> normalForces = new ArrayList<Vector>(0);
		
		// horizontal
		if (angle % (Math.PI) == 0) {
			//hit bottom
			if( Math.abs( bottomleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				normalForces.add(getFloorNormal());
			}
			//hit top
			if( Math.abs( topleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				normalForces.add(getCeilingNormal());
			}
			//hit left
			if( Math.abs( bottomleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				normalForces.add(getLeftWallNormal());
			}
			//hit right
			if( Math.abs( bottomright.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				normalForces.add(getRightWallNormal());
			}
		}
		// vertical
		else if (angle % (Math.PI / 2) == 0) {
			//hit bottom
			if( Math.abs( bottomleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				normalForces.add(getFloorNormal());
			}
			//hit top
			if( Math.abs( topleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				normalForces.add(getCeilingNormal());
			}
			//hit left
			if( Math.abs( bottomleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				normalForces.add(getLeftWallNormal());
			}
			//hit right
			if( Math.abs( bottomright.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				normalForces.add(getRightWallNormal());
			}
		}
		// for any other angle
		else {
			
			double slope;
			// hit bottom of box
			slope = Math.tan(angle);
			Particle temp = new Particle();
			Vector temp1 = new Vector();
			temp1.setX((slope * bottomleft.getX() - bottomleft.getY() + 1 / slope * p.getPosition().getX()
					+ p.getPosition().getY()) / (slope + 1 / slope));
			temp1.setY(slope * (temp1.getX() - bottomleft.getX()) + bottomleft.getY());
			temp.setPosition(temp1);
			if (temp.distanceTo(p) < p.getRadius()) {
				normalForces.add(getFloorNormal());
			}
			// hit top of box
			slope = Math.tan(angle);
			temp = new Particle();
			temp1 = new Vector();
			temp1.setX((slope * topleft.getX() - topleft.getY() + 1 / slope * p.getPosition().getX()
					+ p.getPosition().getY()) / (slope + 1 / slope));
			temp1.setY(slope * (temp1.getX() - topleft.getX()) + topleft.getY());
			temp.setPosition(temp1);
			if (temp.distanceTo(p) < p.getRadius()) {
				normalForces.add(getCeilingNormal());
			}
			// hit left of box
			slope = -1 / Math.tan(angle);
			temp = new Particle();
			temp1 = new Vector();
			temp1.setX((slope * topleft.getX() - topleft.getY() + 1 / slope * p.getPosition().getX()
					+ p.getPosition().getY()) / (slope + 1 / slope));
			temp1.setY(slope * (temp1.getX() - topleft.getX()) + topleft.getY());
			temp.setPosition(temp1);
			if (temp.distanceTo(p) < p.getRadius()) {
				normalForces.add(getLeftWallNormal());
			}
			// hit right of box
			slope = -1 / Math.tan(angle);
			temp = new Particle();
			temp1 = new Vector();
			temp1.setX((slope * topright.getX() - topright.getY() + 1 / slope * p.getPosition().getX()
					+ p.getPosition().getY()) / (slope + 1 / slope));
			temp1.setY(slope * (temp1.getX() - topright.getX()) + topright.getY());
			temp.setPosition(temp1);
			if (temp.distanceTo(p) < p.getRadius()) {
				normalForces.add(getRightWallNormal());
			}
		}
		
		
		//StdOut.println("I have " + normalForces.size() + " normals");
		return normalForces;
	}
	
		
	
	/** GETTERS AND SETTERS */
	
	/**
	 * returns the direction (unit vector) of the normal force from the floor
	 * @return
	 */
	public Vector getFloorNormal() {
		Vector normal = new Vector(bottomright.getY() - bottomleft.getY(), bottomleft.getX() - bottomright.getX());
		normal.normalize();
		return normal;
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the left wall
	 * @return
	 */
	public Vector getLeftWallNormal() {
		Vector normal = new Vector(topleft.getY() - bottomleft.getY(), bottomleft.getX() - topleft.getX());
		normal.normalize();
		return normal.vectorMult(-1);
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the right wall
	 * @return
	 */
	public Vector getRightWallNormal() {
		Vector normal = new Vector(topright.getY() - bottomright.getY(), bottomright.getX() - topright.getX());
		normal.normalize();
		return normal;
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the ceiling
	 * @return
	 */
	public Vector getCeilingNormal() {
		Vector normal = new Vector(topright.getY() - topleft.getY(), topleft.getX() - topright.getX());
		normal.normalize();
		return normal.vectorMult(-1);
	}

	public double getAngle() {
		return angle;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setTopRight(Vector a) {
		topright = a;
		
	}

	public void setBottomRight(Vector a) {
		bottomright = a;
	}

	public void setBottomLeft(Vector a) {
		bottomleft = a;
	}

	public void setTopLeft(Vector a) {
		topleft = a;
	}

	public Vector getTopRight() {
		return topright;
	}

	public Vector getBottomRight() {
		return bottomright;
	}

	public Vector getBottomLeft() {
		return bottomleft;
	}

	public Vector getTopLeft() {
		return topleft;
	}
	

}
