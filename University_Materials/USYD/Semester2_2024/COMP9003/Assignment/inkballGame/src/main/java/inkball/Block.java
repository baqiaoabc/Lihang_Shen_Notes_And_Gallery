package inkball;

/**
 * represent a block, it includes wall, spawner, etc. For more detail, please see the below instruction
 */
public class Block {
    /**
     * x coordinate of block
     */
    public int x;

    /**
     * y coordinate of block
     */
    public int y;

    /**
     * indicate whether edge tile is used for ending enimation,
     * true means are currently used for settlement animation,
     * false otherwise
     */
    public boolean animation;


    /**
     * The block could be:
     * (1) W: wall from 0 - 4;
     * (2) S: spawner;
     * (3) H: hole;
     * (4) B: initial ball spawner;
     * (5) T: tile;
     */
    public String blockType;

    /**
     * H: hole only needs top left block to draw the image, so this variable will only be used for "H" block
     */
    public boolean ImageBlock;

    /**
     *  image size of current block, "H" block should be twice larger than other block.
     */
    public int imageBlockSize;

    /**
     * except for "T", "S" and "B", all blocks have color, namely,
     * "T", "S" and "B" will have "colorless" color and "-1" colorIndex
     */
    public String color;
    /**
     * the color index of corresponding color, for detail, refer to App.colorIndex
     */
    public String colorIndex;

    /**
     * indicate whether block is Bouncy, namely, only "W" block is bouncy
     */
    public boolean bouncy;

    /**
     * indicate how many times bouncy block is hit by the corresponding ball,
     * notice that Different colour bricks can only be damaged by the ball of
     * corresponding colour, unless it’s a grey brick.
     */
    public int bouncyNum;

    /**
     * upperLeft corner point of current block, used for collision detection
     */
    public float[] upperLeft= new float[2];
    /**
     * upperRight corner point of current block, used for collision detection
     */
    public float[] upperRight = new float[2];
    /**
     * bottomLeft corner point of current block, used for collision detection
     */
    public float[] bottomLeft = new float[2];
    /**
     * bottomRight corner point of current block, used for collision detection
     */
    public float[] bottomRight = new float[2];

    /**
     * Block constructor
     *
     * @param x The x coordinate of block
     * @param y The y coordinate of block
     * @param blockType The block type of block, refer to class variable explain
     * @param color The color of block
     * @param ImageBlock Indicate whether we need to draw image for current block,
     *                   since some block is part of the large block, such as a hole need 4 blocks
     *                   but only top left block need to draw image
     */
    public Block(int x, int y, String blockType, String color, boolean ImageBlock){
        // this is the base pint for image drawing
        this.x=x;
        this.y=y;
        this.blockType = blockType;
        this.bouncyNum=0;
        this.animation = false;

        // match color with the colorIndex
        this.color = color;
        this.colorIndex = "-1";
        for(int i = 0; i < App.colors.length; i++){
            if (color.equals(App.colors[i])){
                this.colorIndex = String.valueOf(i);
            }
        }

        // only wall can bounce ball
        this.bouncy = this.blockType.equals("W");

        // set current block as image block
        this.ImageBlock = ImageBlock;
        // set size of current block
        this.imageBlockSize =App.CELLSIZE;
        if(this.blockType.equals("H") && this.ImageBlock){
            this.imageBlockSize = 2*App.CELLSIZE;
        }
        // I will use 4 block to represent a hole, so need not to specially treat "H"
        this.upperLeft[0] = this.x;
        this.upperLeft[1] = this.y;

        this.upperRight[0] = this.x + App.CELLSIZE;
        this.upperRight[1] = this.y;

        this.bottomLeft[0] = this.x;
        this.bottomLeft[1] = this.y + App.CELLSIZE;

        this.bottomRight[0] = this.x + App.CELLSIZE;
        this.bottomRight[1] = this.y + App.CELLSIZE;
    }
}
