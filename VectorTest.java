import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VectorTest {

	Vector vector;
	@Before
	public void setUp() throws Exception {
		vector = new Vector ();
	}

	@Test
	public void matrixMultTest() {
		vector.setX(3);
		vector.setY(1);
		double [][] matrix = {{1, -1, 0}, {1, 1, 0}, {0, 0, 1}};
		vector = vector.matrixMult(matrix);
		assertEquals ("[2.0, 4.0]", vector.toString());
	}
}
