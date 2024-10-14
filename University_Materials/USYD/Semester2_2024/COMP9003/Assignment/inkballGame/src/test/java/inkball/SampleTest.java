package inkball;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void simpleTest() {
        /*App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);*/ // delay is to give time to initialise stuff before drawing begins
    }
    @Test
    public void testPointOnLine() {
        Line l = new Line();
        assertTrue(l.isPointOnLineSegment(3, 5, 1, 5, 5, 5));

        assertTrue(l.isPointOnLineSegment(2, 3, 2, 1, 2, 5));

        assertTrue(l.isPointOnLineSegment(2, 2, 1, 1, 3, 3));

        assertFalse(l.isPointOnLineSegment(6, 5, 1, 5, 5, 5));

        assertFalse(l.isPointOnLineSegment(2, 6, 2, 1, 2, 5));

        assertFalse(l.isPointOnLineSegment(3, 3, 1, 1, 2, 2));
    }
}

// gradle run						Run the program
// gradle test						Run the testcases

// Please ensure you leave comments in your testcases explaining what the testcase is testing.
// Your mark will be based off the average of branches and instructions code coverage.
// To run the testcases and generate the jacoco code coverage report: 
// gradle test jacocoTestReport
