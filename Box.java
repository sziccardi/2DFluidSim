
public class Box {
	
	private double width;
	private double height;
	private double angle;
	
	public Box(double width, double height) {
		this.width = width;
		this.height = height;
		angle = 0.0;
	}
	
	public void rotate(double amount) {
		
		angle+=amount;
		
	}
	
	public Vector getNormal() {
		//TODO
		
		return null;
	}
	

}
