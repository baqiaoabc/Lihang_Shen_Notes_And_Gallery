package inkball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test file for Line class
 */
public class LineTest {
    Line line;

    /**
     * initiate a line object before each test
     */
    @BeforeEach
    public void setUp() {
        line = new Line();
    }

    /**
     * test if only contain one point, line will be regarded as invalid
      */
    @Test
    public void testValidLine1() {
        assertFalse(line.validLine());
    }

    /**
     * test if contain more than one point, line will be regarded as valid
     */
    @Test
    public void testValidLine2() {
        line.addPoint(1.0f, 2.0f);
        assertTrue(line.validLine());
    }

    /**
     * test if a point locate at a vertical line
     */
    @Test
    public void testIsPointOnVerticalLineSegment() {
        // not locate on line
        assertFalse(line.isPointOnLineSegment(10f, 11f, 2.0f, 2.0f, 2.0f, 4.0f));

        // Point on nearly vertical line (x-coordinates are very close)
        assertTrue(line.isPointOnLineSegment(10.001f, 15.0f, 10.0f, 10.0f, 10.002f, 20.0f));

        // Point not on the near-vertical line (y out of bounds)
        assertFalse(line.isPointOnLineSegment(10.001f, 25.0f, 10.0f, 10.0f, 10.002f, 20.0f));
    }

    /**
     * test if a point locate at a horizontal line
     */
    @Test
    public void testIsPointOnHorizontalLineSegment() {
        // Point on horizontal line
        assertTrue(line.isPointOnLineSegment(150.0f, 300.0f, 100.0f, 300.0f, 200.0f, 300.0f));

        // Point not on the horizontal line (out of bounds on x-axis)
        assertFalse(line.isPointOnLineSegment(250.0f, 300.0f, 100.0f, 300.0f, 200.0f, 300.0f));

        // Point not on the horizontal line (different y-coordinate)
        assertFalse(line.isPointOnLineSegment(150.0f, 350.0f, 100.0f, 300.0f, 200.0f, 300.0f));
    }

    /**
     * test if a point locate at a diagonal line
     */
    @Test
    public void testIsPointOnDiagonalLineSegment() {
        // Point on diagonal line
        assertTrue(line.isPointOnLineSegment(5.0f, 5.0f, 0.0f, 0.0f, 10.0f, 10.0f));

        // Point not on diagonal line (out of bounds on x and y)
        assertFalse(line.isPointOnLineSegment(12.0f, 12.0f, 0.0f, 0.0f, 10.0f, 10.0f));

        // Point not on diagonal line (wrong slope)
        assertFalse(line.isPointOnLineSegment(5.0f, 6.0f, 0.0f, 0.0f, 10.0f, 10.0f));
    }

    /**
     * test if a point locate outside the line segment
     */
    @Test
    public void testIsPointOutsideLineSegment() {
        // Point outside line bounds (x too small)
        assertFalse(line.isPointOnLineSegment(50.0f, 50.0f, 100.0f, 100.0f, 200.0f, 200.0f));

        // Point outside line bounds (x too large)
        assertFalse(line.isPointOnLineSegment(250.0f, 250.0f, 100.0f, 100.0f, 200.0f, 200.0f));

        // Point outside line bounds (y too small)
        assertFalse(line.isPointOnLineSegment(100.0f, 50.0f, 100.0f, 100.0f, 200.0f, 200.0f));
    }


}

