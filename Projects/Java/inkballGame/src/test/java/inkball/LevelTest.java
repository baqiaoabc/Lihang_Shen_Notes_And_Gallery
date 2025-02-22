package inkball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static processing.core.PApplet.loadJSONObject;

/**
 * Note that this test file is only valid if the supplied
 * txt file and configuration file are not modified
 */
public class LevelTest {
    private static JSONObject json = loadJSONObject(new File("config.json"));
    private Level level;

    /**
     * test static variable is correct
     */
    @Test
    public void testCharacters(){
        assertEquals(Level.characters[0], "X");
        assertEquals(Level.characters[1], "S");
        assertEquals(Level.characters[2], "H");
        assertEquals(Level.characters[3], "B");
        assertEquals(Level.characters[4], " ");
        assertEquals(Level.characters[5], "0");
        assertEquals(Level.characters[6], "1");
        assertEquals(Level.characters[7], "2");
        assertEquals(Level.characters[8], "3");
        assertEquals(Level.characters[9], "4");
    }

    /**
     * test enterNextlevel method is correct
     */
    @Test
    public void testEnterNextLevel() {
        level = new Level(json.getJSONArray("levels"));
        level.enterNextlevel();
        assertEquals(1, level.currentLevel);
        level.enterNextlevel();
        level.enterNextlevel();
        level.enterNextlevel();
        assertEquals(2, level.currentLevel);
    }


    /**
     * test hasNextlevel method is correct
     */
    @Test
    public void testHasNextLevel() {
        level = new Level(json.getJSONArray("levels"));
        level.enterNextlevel();
        assertTrue(level.hasNextlevel());
        level.enterNextlevel();
        level.enterNextlevel();
        level.enterNextlevel();
        assertFalse(level.hasNextlevel());
    }

    /**
     * test updateMapSetting works with given argument
     */
    @Test
    public void testUpdateMapSetting() {
        level = new Level(json.getJSONArray("levels"));
        level.updateMapSetting(1.1f);
        assertEquals(level.resetScore,1.1f);
        assertEquals(level.currentScore,1.1f);
    }

    /**
     * test readLayoutFile() and setupBoard() works correctly
     */
    @Test
    public void testReadLayoutFile(){
        level = new Level(json.getJSONArray("levels"));

        // read non-exist file
        level.layoutFile = "11ssss.tasdxt";
        level.readLayoutFile();
        // Since this file does not exist which means the original layoutContent will not change,
        // that is still "level1.txt"
        assertEquals("X", level.layoutContent.get(0).get(0));

        // read file with incorrect characters, some line has less than 18 characters, have more than 18 rows
        level.layoutFile = "LevelTest.txt";
        level.readLayoutFile();
        assertEquals(" ", level.layoutContent.get(0).get(0));

        // read file with less than 18 rows
        level.layoutFile = "LevelTest2.txt";

        level.readLayoutFile();
        assertEquals(" ", level.layoutContent.get(15).get(12));

    }

    /**
     * test add line successfully
     */
    @Test
    public void testAddLine() {
        level = new Level(json.getJSONArray("levels"));
        Line l = new Line();
        level.currentline=l;
        level.addLine();
        assertEquals(level.linesCollection.get(0),l);
    }

    /**
     * test delete line successfully
     */
    @Test
    public void testDeleteLine() {
        level = new Level(json.getJSONArray("levels"));
        Line l = new Line();
        level.currentline=l;
        level.addLine();
        level.deleteLine();
        assertEquals(level.linesCollection.size(),0);
    }

    /**
     * test serve ball successfully
     */
    @Test
    public void testServeBall() {
        level = new Level(json.getJSONArray("levels"));
        assertEquals(level.ballsQueue.size(),6);

        // when spawner number is 1, but there is only one spawner block,
        // test serve ball function
        level.spawner_number = 1;
        level.serveBall();
        // remember there is already one ball on the map
        assertEquals(2, level.ballsOnTheMap.size());

        // when spawner is 0, test serve ball function
        level.spawner_number = 0;
        level.serveBall();
        assertEquals(2, level.ballsOnTheMap.size());
    }



}
