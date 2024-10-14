package inkball;

public class Block {
    /**
     * Since we have many tile instances, so we cannot store tile.png in this class
     */
    public int x;
    public int y;

    // indicate edge tile is used for ending enimation
    public boolean animation;

    // block could be:
    // (1) W: wall from 0 - 4
    // (2) S: spawner
    // (3) H: hole
    // (4) B: initial ball spawner
    // (5) T: tile
    public String blockType;
    // H: hole only needs top left block to draw the image
    public boolean ImageBlock;
    // size of current block
    public int imageBlockSize;

    // except for "T", "S" and "B", all blocks have color
    // therefore, "T", "S" and "B" will have "colorless" color and "-1" colorIndex
    public String color;
    public String colorIndex;

    // indicate whether block is Bouncy
    public boolean bouncy;
    // 4 corner point of current block, used for collision detection
    public float[] upperLeft= new float[2];
    public float[] upperRight = new float[2];
    public float[] bottomLeft = new float[2];
    public float[] bottomRight = new float[2];

    public Block(int x, int y, String blockType, String color, boolean ImageBlock){
        // this is the base pint for image drawing
        this.x=x;
        this.y=y;
        this.blockType = blockType;
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
