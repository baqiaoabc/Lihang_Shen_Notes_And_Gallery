package inkball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test file for Block class
 */
public class BlockTest {
    private Block block;

    /**
     * test constructor of block works properly
     */
    @Test
    public void testBlockInitialization() {
        block = new Block(10, 20, "W", "grey", true);

        // Test basic block attributes
        assertEquals(10, block.x);
        assertEquals(20, block.y);
        assertEquals("W", block.blockType);
        assertEquals("grey", block.color);
        assertEquals("0", block.colorIndex); // grey should match the first color in App.colors
        assertTrue(block.bouncy);  // Since it's a wall (W), it should be bouncy
        assertTrue(block.ImageBlock);  // This block should need an image
        assertEquals(App.CELLSIZE, block.imageBlockSize);  // Normal size for wall
    }

    /**
     * test not bouncy block is correctly initialized
     */
    @Test
    public void testNonBouncyBlock() {
        block = new Block(15, 25, "S", "blue", false);

        assertFalse(block.bouncy);  // Spawner block (S) should not be bouncy
    }

    /**
     * test successfully initialize the image block of a hole block
     */
    @Test
    public void testHoleBlock() {
        block = new Block(30, 40, "H", "grey", true);

        assertEquals("H", block.blockType);
        assertTrue(block.ImageBlock);  // Hole block should have an image block
        assertEquals(2 * App.CELLSIZE, block.imageBlockSize);  // The size should be doubled for a hole block
        assertEquals("grey", block.color);
        assertEquals("0", block.colorIndex);  // grey should match the third color in App.colors
    }

    /**
     * test successfully initialize the ImageBlock of block
     */
    @Test
    public void testBlockWithoutImage() {
        block = new Block(50, 60, "H", "grey", false);
        assertFalse(block.ImageBlock);  // This block should not draw an image
    }

    /**
     * test successfully initialize the colorless block
     */
    @Test
    public void testBlockColorless() {
        block = new Block(10, 20, "T", "colorless", false);
        assertEquals("colorless", block.color);
        assertEquals("-1", block.colorIndex);  // Default colorless index
    }

    /**
     * test the corner of block is correctly initialized
     */
    @Test
    public void testBlockCorners() {
        block = new Block(100, 200, "W", "red", true);

        // Check the corners' coordinates
        assertArrayEquals(new float[]{100, 200}, block.upperLeft);
        assertArrayEquals(new float[]{132, 200}, block.upperRight);
        assertArrayEquals(new float[]{100, 232}, block.bottomLeft);
        assertArrayEquals(new float[]{132, 232}, block.bottomRight);
    }

}
