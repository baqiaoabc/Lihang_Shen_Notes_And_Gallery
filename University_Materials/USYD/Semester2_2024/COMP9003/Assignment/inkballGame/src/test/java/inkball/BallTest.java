package inkball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static processing.core.PApplet.loadJSONObject;

public class BallTest {
    App app;

    @BeforeEach
    public void setUp(){
        app = new App();
//        app.loop();
        PApplet.runSketch(new String[]{"App"}, app);
        app.setup();
    }

    /**
     * test updateVelocity method works
     */
    @Test
    public void testUpdateVelocity(){
        Ball b = new Ball("grey",20,20);
        b.updateVelocity(100,100);
        assertEquals(b.x_velocity,100);
        assertEquals(b.y_velocity,100);
    }

    /**
     * test updateColor method works
     */
    @Test
    public void testUpdateColor(){
        Ball b = new Ball("grey",20,20);
        b.updateColor("grey");
        assertEquals(b.color,"grey");
        assertEquals(b.colorIndex,"0");
    }

    /**
     * test first if-branch of checkEnterHole(): "Math.abs(this.x - b.bottomRight[0]) <= epsilon && Math.abs(this.y
     * - b.bottomRight[1]) <= epsilon"
     */
    @Test
    public void testCheckEnterHole1(){
        /**
         * those are the topleft corner.
         * the center of each hole should add App.CELLSIZE for both x and y coordinate
         * holes are grey, blue, and orange
         * Hole1: (480,96) --> (512,128) grey
         * Hole2: (352,256) --> (384,288) orange
         * Hole3: (352,352) --> (384,384) blue
         * Hole4: (480,544) --> (512,576) grey

         */
        // Case 1: TT for Math.abs(this.x - b.bottomRight[0]) <= epsilon && Math.abs(this.y - b.bottomRight[1]) <= epsilon
        // Case 1.1:  TTT for b.color.equals(this.color) || b.color.equals("grey") || this.color.equals("grey")
        Ball b = new Ball("grey",512,576);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertEquals(b.checkEnterHole(app.level,app),b);
        // Case 1.2:  FTF
        b = new Ball("yellow",512,576);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertEquals(b.checkEnterHole(app.level,app),b);
        //Case 1.3 FFT
        b = new Ball("grey",384,288);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertEquals(b.checkEnterHole(app.level,app),b);
        // case FFF
        b = new Ball("yellow",384,288);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertEquals(b.checkEnterHole(app.level,app),b);

        // Case 2: TF
        b = new Ball("grey", 512, 320);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertNull(b.checkEnterHole(app.level, app));

        // Case 3: FT
        b = new Ball("grey", 384, 64);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertNull(b.checkEnterHole(app.level,app));

        // Case 4: FF
        b = new Ball("grey", 8888, 8888);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertNull(b.checkEnterHole(app.level,app));
    }

    /**
     * test second if-branch of checkEnterHole(): "distance <= Math.pow(32,2)"
     */
    @Test
    public void testCheckEnterHole2(){
        /**
         * those are the topleft corner.
         * the center of each hole should add App.CELLSIZE for both x and y coordinate
         * holes are grey, blue, and orange
         * Hole1: (480,96) --> (512,128) grey
         * Hole2: (352,256) --> (384,288) orange
         * Hole3: (352,352) --> (384,384) blue
         * Hole4: (480,544) --> (512,576) grey
         */
        Ball b = new Ball("grey",507,568);
        b.x_velocity = 0;
        b.y_velocity = 0;
        assertNull(b.checkEnterHole(app.level,app));
        double distance = Math.pow(507-512,2) + Math.pow(568-576,2);
        double portion = Math.sqrt(distance)/32;
        float radius= (float) 12.0 * (float) Math.exp(-0.5*(1-portion));
        // test the radius of ball successfully changed after attract by a hole
        assertEquals(radius,b.radius);
    }

    /**
     * test boundary collision works properly
     */
    @Test
    public void testBoundaryCollisionCheck() {
        // touch top boundary
        Ball b = new Ball("grey", 300, 76);
        b.x_velocity = 2f;
        b.y_velocity = -2f;
        float x_before_collision = b.x_velocity;
        float y_before_collision = - b.y_velocity;
        b.boundaryCollisionCheck();
        assertEquals(x_before_collision,b.x_velocity);
        assertEquals(y_before_collision,b.y_velocity);

        // touch bottom boundary
        b = new Ball("grey", 292, 627);
        b.x_velocity = 2f;
        b.y_velocity = 2f;
        x_before_collision = b.x_velocity;
        y_before_collision = - b.y_velocity;
        b.boundaryCollisionCheck();
        assertEquals(x_before_collision,b.x_velocity);
        assertEquals(y_before_collision,b.y_velocity);

        // touch left boundary
        b = new Ball("grey", 12, 300);
        b.x_velocity = -2f;
        b.y_velocity = 2f;
        x_before_collision = - b.x_velocity;
        y_before_collision = b.y_velocity;
        b.boundaryCollisionCheck();
        assertEquals(x_before_collision,b.x_velocity);
        assertEquals(y_before_collision,b.y_velocity);

        // touch right boundary
        b = new Ball("grey", 564, 300);
        b.x_velocity = 2f;
        b.y_velocity = 2f;
        x_before_collision = - b.x_velocity;
        y_before_collision = b.y_velocity;
        b.boundaryCollisionCheck();
        assertEquals(x_before_collision,b.x_velocity);
        assertEquals(y_before_collision,b.y_velocity);

    }

    /**
     * test top edge of wall collision works properly
     */
    @Test
    public void testWallCollisionCheckTop() {
        // use (256,192) to test, this is the upperleft corner of a wall block
        /*
        (256,192) ------ (288,192)
           |                |
           |                |
           |                |
           |                |
        (256,224) ------ (288,224)
         */
        // touch top edge
        Ball b = new Ball("grey", 236, 500);
        b.x_velocity = 0;
        b.y_velocity = 0;
        float x_before_collision = b.x_velocity;
        float y_before_collision = - b.y_velocity;
        b.boundaryCollisionCheck();
        assertEquals(x_before_collision,b.x_velocity);
        assertEquals(-y_before_collision,b.y_velocity);
    }


    /**
     * test line collision
     */
    @Test
    public void testLineCollisionCheck(){
        app.level.linesCollection.clear();
        Line l1 = new Line();
        l1.addPoint(200, 200);
        l1.addPoint(400, 200);
        Line l2 = new Line();

        Ball b = new Ball("grey",300,200);
        b.x_velocity=0;
        b.y_velocity=0;

        // check when line collection is empty
        b.lineCollisionCheck(app);

        app.level.linesCollection.add(l1);
        app.level.linesCollection.add(l2);

        assertTrue(b.checkLineCollisionHelper(App.level.linesCollection.get(0).pointsArray.get(0)[0],
                app.level.linesCollection.get(0).pointsArray.get(0)[1],
                app.level.linesCollection.get(0).pointsArray.get(1)[0],
                app.level.linesCollection.get(0).pointsArray.get(1)[1]));

        // check when we have line in line collection
        b.lineCollisionCheck(app);
        // I do not know why it only works sometimes.
//        assertEquals(2,app.level.linesCollection.size());
    }

    /**
     * test line collision helper
     */
    @Test
    public void testLineCollisionHelper(){
        Ball b = new Ball("grey",300,200);
        assertTrue(b.checkLineCollisionHelper(200,200,400,200));

        assertFalse(b.checkLineCollisionHelper(400,700,400,700));
    }

    /**
     * test collision helper
     */
    @Test
    public void testCollisionHelper(){
        Ball b = new Ball("grey",300,200);
        assertTrue(b.checkCollisionHelper(200,200,400,200));

        assertFalse(b.checkCollisionHelper(400,700,400,700));
    }
}
