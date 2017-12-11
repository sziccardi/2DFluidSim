import java.util.ArrayList;

public class FluidSim {

	private double particleRadius = 10;
	private FluidSimModel model;

	public void drawBox(Box box) {
	
		StdDraw.line(box.getTopRight().getX(), box.getTopRight().getY(), box.getBottomRight().getX(),
				box.getBottomRight().getY());
		StdDraw.line(box.getBottomLeft().getX(), box.getBottomLeft().getY(), box.getBottomRight().getX(),
				box.getBottomRight().getY());
		StdDraw.line(box.getBottomLeft().getX(), box.getBottomLeft().getY(), box.getTopLeft().getX(),
				box.getTopLeft().getY());
		StdDraw.line(box.getTopLeft().getX(), box.getTopLeft().getY(), box.getTopRight().getX(),
				box.getTopRight().getY());

	}

	public void drawParticles(ArrayList<Particle> particles) {
		for (Particle p : particles) {
			StdDraw.filledCircle(p.getPosition().getX(), p.getPosition().getY(), particleRadius);
		}
	}

	public void run() {
		StdDraw.enableDoubleBuffering();
		model = new FluidSimModel(400, 200);
		while (true) {

			if(StdDraw.mousePressed()) {
				while(StdDraw.mousePressed()) {
					model.advance();
					StdDraw.clear(StdDraw.BLACK);
					StdDraw.setScale(0, 600);
					StdDraw.setPenColor(StdDraw.WHITE);
					drawBox(model.getBox());
					drawParticles(model.getParticles());
					StdDraw.show();
				}
				//TODO: Check to make sure in the box
				model.addParticle(new Vector(StdDraw.mouseX(),StdDraw.mouseY()), 10, new Vector(), 10.0);
			}
			
			model.advance();
			StdDraw.clear(StdDraw.BLACK);
			StdDraw.setScale(0, 600);
			StdDraw.setPenColor(StdDraw.WHITE);
			drawBox(model.getBox());
			drawParticles(model.getParticles());
			StdDraw.show();
		}
	}
	
	public static void main(String[] args) {
		new FluidSim().run();
	}
}
