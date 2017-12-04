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
		//TODO : some way to add particles from user
		model.addParticle(new Vector(300,300), 1.0, new Vector(), 10.0);
		
		while (true) {
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
