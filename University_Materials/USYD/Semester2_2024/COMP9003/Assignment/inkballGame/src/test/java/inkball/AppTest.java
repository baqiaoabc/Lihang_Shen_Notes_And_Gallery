package inkball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import static inkball.App.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    App app;

    @BeforeEach
    public void setUp(){
        app = new App();
//        app.loop();
        PApplet.runSketch(new String[]{"App"}, app);
        app.setup();
    }

    /**
     * test when player press "r" or " ", the game will reply properly.
     */
    @Test
    public void testKeyPressed(){
        app.setup();
        KeyEvent keyEvent = new KeyEvent(new Object(), 0, 1, 0, 'r', 'r');

        // Case1: !level.levelStatus.equals("wining") && !level.levelStatus.equals("time's up") is true
        KeyEvent keyEvent2 = new KeyEvent(new Object(), 0, 1, 0, ' ', ' ');
        app.level.levelStatus = "play";
        app.key = ' ';
        app.keyPressed(keyEvent2);
        assertEquals("pause", app.level.levelStatus);
        app.key = ' ';
        app.keyPressed(keyEvent2);
        assertEquals("play", app.level.levelStatus);

        app.key = 'r';
        app.keyPressed(keyEvent);
        // restart current level will not change level number
        assertEquals(0,app.level.currentLevel);

        KeyEvent keyEvent3 = new KeyEvent(new Object(), 0, 1, 0, 'z', 'z');
        app.key = 'z';
        app.keyPressed(keyEvent3);
        // wrong key will do nothing
        assertEquals(0,app.level.currentLevel);

        // Case2: !level.levelStatus.equals("wining") && !level.levelStatus.equals("time's up") is false
        app.level.levelStatus = "wining";
        app.key = 'r';
        app.keyPressed(keyEvent);
        // restart current level because still has next level
        assertEquals(0,app.level.currentLevel);

        app.key = ' ';
        app.keyPressed(keyEvent2);
        // wrong key will do nothing
        assertEquals(0,app.level.currentLevel);

        app.level.levelStatus = "time's up";
        app.key = 'r';
        app.keyPressed(keyEvent);
        // restart current level because of the time is up and press r
        assertEquals(0,app.level.currentLevel);

        app.key = 'z';
        app.keyPressed(keyEvent3);
        // wrong key will do nothing
        assertEquals(0,app.level.currentLevel);

        // only have 3 levels, so here, we enter the last level of the game
        app.level.enterNextlevel();
        app.level.enterNextlevel();

        app.level.levelStatus = "time's up";
        app.key = 'r';
        app.keyPressed(keyEvent);
        // since time is up, even in the last level, we still need tp replay current level
        assertEquals(2,app.level.currentLevel);

        app.key = 'r';
        app.level.levelStatus = "wining";
        app.keyPressed(keyEvent);
        // now should back to level 0
        assertEquals(0,app.level.currentLevel);

    }

    /**
     * test whether click LEFT mouse will create a line object
     */
    @Test
    public void testMousePressed(){
        MouseEvent mouseEvent = new MouseEvent(
                new Object(), 0, 0, 0, 100, 100, 1, 1
        );
        app.mousePressed(mouseEvent);
        assertEquals(1,app.level.linesCollection.size());
    }

    /**
     * test drag LEFT mouse will create line, drag right mouse will remove line
     */
    @Test
    public void testMouseDragged(){
        MouseEvent mouseEvent = new MouseEvent(
                new Object(), 0, 4, 0, 100, 100, 1, 1
        );

        app.level.levelStatus = "wining";
        // in the wining phase, player cannot use this function
        app.mouseDragged(mouseEvent);
        assertEquals(0,app.level.linesCollection.size());

        // this function only works when current level not in wining phase
        app.level.levelStatus = "play";
        Line l1 = new Line();
        // set current line as l1 which does not have point
        app.level.currentline = l1;

        // LEFT click
        app.mouseButton = 37;

        app.mouseDragged(mouseEvent);
        // after drag, l1 should have 2 points, the start point and the end point
        assertEquals(2,l1.pointsNum);

        // add another point to l1, I just pick it for removing line test
        l1.addPoint(100,200);

        // RIGHT Click
        MouseEvent mouseEvent2 = new MouseEvent(
                new Object(), 0, 4, 0, 100, 200, 2, 1
        );
        app.mouseButton = 39;
        // add line to the lines collection
        app.level.linesCollection.add(l1);
        app.mouseDragged(mouseEvent2);
        // now it should successfully remove from the lines collection
        assertEquals(1,app.level.linesCollection.size());

    }

    /**
     * it just test the rightDraggedHelper() works properly
     */
    @Test
    public void testRightDraggedHelper(){
        app.mouseButton = 39;
        Line l1 = new Line();
        l1.addPoint(100, 100);
        l1.addPoint(200, 100);
        l1.addPoint(300, 100);
        Line l2 = new Line();
        l2.addPoint(100, 400);
        l2.addPoint(200, 400);
        l2.addPoint(300, 400);
        app.level.linesCollection.add(l1);
        app.level.linesCollection.add(l2);

        app.mouseX = 150;
        app.mouseY = 100;

        app.rightDraggedHelper();
        assertEquals(1,app.level.linesCollection.size());
    }

    /**
     * when mouse released, if current line is invalid, it should be removed from lines collection
     */
    @Test
    public void testMouseReleased(){
        Line l = new Line();
        app.level.currentline = l;
        app.level.linesCollection.add(l);
        MouseEvent mouseEvent = new MouseEvent(
                new Object(), 0, 4, 0, 100, 100, 1, 1
        );
        app.mouseReleased(mouseEvent);

        Line l2 = new Line();
        l2.addPoint(12,12);
        l2.addPoint(50,90);
        app.level.currentline = l2;
        app.level.linesCollection.add(l2);
        MouseEvent mouseEvent2 = new MouseEvent(
                new Object(), 0, 4, 0, 100, 100, 1, 1
        );
        app.mouseReleased(mouseEvent2);
    }

    /**
     * see draw function works properly in different level phases
     */
    @Test
    public void testDraw(){
        app.setup();
        // level status "play"
        app.level.levelStatus = "play";
        app.draw();
        // only elapse 0 frame
        assertEquals(1,app.level.frameElapsedForTimer);

        // level status "time's up"
        app.level.levelStatus = "time's up";
        app.draw();
        // since time is up, the frame will not change
        assertEquals(1,app.level.frameElapsedForTimer);

        // level status "pause"
        app.level.levelStatus = "pause";
        app.draw();
        // since level is paused, the frame will not change
        assertEquals(1,app.level.frameElapsedForTimer);

        // level status "wining"
        app.level.levelStatus = "wining";
        app.draw();
        // the first time to run wining phase should get 1 frameForWiningAnimation
        assertEquals(1,app.level.frameForWiningAnimation);

        app.level.remainTime = 0;
        app.draw();
        // should enter next level
        assertEquals(1,app.level.currentLevel);

        // enter last level of the game
        app.level.enterNextlevel();
        app.level.enterNextlevel();

        app.level.levelStatus = "wining";
        app.draw();
        // does not change to level 1 because there are balls on the map
        assertEquals(2,app.level.currentLevel);

        // check wining condition, level.ballsOnTheMap.isEmpty() && level.ballsQueue.isEmpty()
        // && !level.levelStatus.equals("wining"); it has 6 combination
        // FFF, we did before

        // FFT
        app.level.ballsQueue.add(new Ball("grey",0,0));
        app.level.ballsOnTheMap.add(new Ball("grey",0,0));
        app.level.levelStatus = "play";
        app.draw();
        // do nothing
        assertFalse(board[0][0].animation);

        //TTT, do it by clear ballsQueue and ballsOnTheMap
        app.setup();
        app.level.levelStatus = "play";
        app.level.ballsQueue.clear();
        app.level.ballsOnTheMap.clear();
        app.draw();
        // do nothing
        assertTrue(board[0][0].animation);
    }

    /**
     * test draw winning animation works correctly
     */
    @Test
    public void testDrawWinningAnimation(){
        app.setup();
        // initialization
        board[0][0].animation = true;
        board[NUM_ROWS-1][NUM_COLUMNS-1].animation = true;

        // after run 17 times, the bottom right yellow wall should move to the bottom left corner
        for(int i = 0; i <17; i++){
            app.drawWinningAnimation();
        }
        assertTrue(board[0][17].animation);

        // after run additional 17 times, the bottom right yellow wall should move to the top left corner
        for(int i = 0; i <17; i++){
            app.drawWinningAnimation();
        }
        assertTrue(board[0][0].animation);
    }
}
