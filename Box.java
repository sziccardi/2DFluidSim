   
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
	
	public int overlaps(Particle p) {

		// horizontal
		if (angle % (Math.PI) == 0) {
			//hit bottom
			if( Math.abs( bottomleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				return 1;
			}
			//hit top
			if( Math.abs( topleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				return 2;
			}
			//hit left
			if( Math.abs( bottomleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				return 3;
			}
			//hit right
			if( Math.abs( bottomright.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				return 4;
			}
		}
		// vertical
		else if (angle % (Math.PI / 2) == 0) {
			//hit bottom
			if( Math.abs( bottomleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				return 1;
			}
			//hit top
			if( Math.abs( topleft.getX() - p.getPosition().getX() ) < p.getRadius() ) {
				return 2;
			}
			//hit left
			if( Math.abs( bottomleft.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				return 3;
			}
			//hit right
			if( Math.abs( bottomright.getY() - p.getPosition().getY() ) < p.getRadius() ) {
				return 4;
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
			StdOut.println(temp.distanceTo(p) + "");
			if (temp.distanceTo(p) < p.getRadius()) {
				StdOut.println("hit bottom");
				return 1;
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
				return 2;
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
				return 3;
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
				return 4;
			}
		}

		return 0;
	}
	
		
	
	/** GETTERS AND SETTERS */
	
	/**
	 * returns the direction (unit vector) of the normal force from the floor
	 * needs to be scaled before use
	 * @return
	 */
	public Vector getFloorNormal() {
		Vector normal = new Vector(Math.sin(angle), Math.cos(angle));
		normal.normalize();
		return normal;
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the left wall
	 * needs to be scaled before use
	 * @return
	 */
	public Vector getLeftWallNormal() {
		Vector normal = new Vector(Math.cos(angle), Math.sin(angle));
		normal = normal.vectorMult(-1);
		normal.normalize();
		return normal;
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the right wall
	 * needs to be scaled before use
	 * @return
	 */
	public Vector getRightWallNormal() {
		Vector normal = new Vector(Math.cos(angle), Math.sin(angle));
		normal.normalize();
		return normal;
	}
	
	/**
	 * returns the direction (unit vector) of the normal force from the ceiling
	 * needs to be scaled before use
	 * @return
	 */
	public Vector getCeilingNormal() {
		Vector normal = new Vector(Math.sin(angle), Math.cos(angle));
		normal = normal.vectorMult(-1);
		normal.normalize();
		return normal;
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
