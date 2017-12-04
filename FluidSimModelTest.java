import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FluidSimModelTest {

	private FluidSimModel model;
	
	@Before
	public void setUp() throws Exception {
		model = new FluidSimModel(300, 100);
	}
	
	@Test
	public void simpleAdvances() {
		//initializes a particle in the center of the frame
		//with no velocity, mass of 1, and size of 10
		model.addParticle(new Vector(300, 300), 1.0, new Vector(), 10.0);
		model.advance();
		//StdOut.println("" + model.getParticleAt(0).getPosition().toString());
	}

	@Test
	public void hitsFloor() {
		//initializes a particle near bottom
		//with no velocity, mass of 1, and size of 10
		model.addParticle(new Vector(300 - 0.5*model.getBox().getHeight() + 6, 300), 1.0, new Vector(), 10.0);
		
		model.advance();
		model.advance();
		model.advance();
		model.advance();
		model.advance();
		model.advance();
		
		StdOut.println("" + model.getParticleAt(0).getPosition().toString());
	}
}
