package minesweeper;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;
import java.util.List;

public class App extends PApplet {

    public static final int CELLSIZE = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 64;

    //
    public static int WIDTH = 864; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;

    // Ignore these two
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE; //27
    public static final int BOARD_HEIGHT = HEIGHT/CELLSIZE; //20

    // Indicates how many rows of tiles do we have
    public static final int NUM_ROWS = (HEIGHT - TOPBAR) / CELLSIZE;
    // Indicates how many cols of tiles do we have
    public static final int NUM_COLUMNS = BOARD_WIDTH;


    public static final int FPS = 30;
    public String configPath;
    public static Random random = new Random();

    // Update whenever "setup" is called
    public int tilesNum = 0;
    public int minesNum = 0;
    public int detectedMinesNum = 0;
    public boolean onemoretime = false;
    // change to true when a mine is clicked
    public boolean lose = false;
    // change to true when all tiles are clicked
    public boolean win = false;

    public boolean firstMine;
    public int explodeCenterx;
    public int explodeCentery;
    int startTime;

    public static int num;

    public int frame;

    public boolean[][] visited = new boolean[NUM_ROWS][NUM_COLUMNS];

    // represent near mines number colors
	public static int[][] mineCountColour = new int[][] {
            {0,0,0}, // 0 is not shown
            {0,0,255},
            {0,133,0},
            {255,0,0},
            {0,0,132},
            {132,0,0},
            {0,132,132},
            {132,0,132},
            {32,32,32}
    };

    //TODO: Image variable
    PImage hidden_tile;
    PImage highlight_tile;
    PImage base_tile;
    PImage flag;
    PImage explode0, explode1, explode2, explode3, explode4, explode5, explode6, explode7, explode8, explode9;
    PImage [] explode = {explode0, explode1, explode2, explode3, explode4, explode5, explode6, explode7, explode8, explode9};

    // board structure
    public static Tile[][] board = new Tile[NUM_ROWS][NUM_COLUMNS];
	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * return whether current tile is locate at left edge
     * true means tile is locate at left edge
     */
    public boolean locateLeftEdge(int t){
        if (t%BOARD_WIDTH == 0){
            return true;
        }
        return false;
    }

    /**
     * return whether current tile is locate at right edge
     */
    public boolean locateRightEdge(int t){
        if(t != 0 && (t+1)%BOARD_WIDTH==0) {
            return true;
        }
        return false;
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);//TODO change back to FPS
		//See PApplet javadoc:
		//loadJSONObject(configPath)
        lose=false;
        win=false;
        onemoretime=false;
        frame=0;
        firstMine = true;


        // TODO: reset timer
        startTime = millis();

        // TODO: Load Image from resources file
        flag = loadImage("minesweeper/flag.png");
        base_tile = loadImage("minesweeper/tile.png");
        hidden_tile = loadImage("minesweeper/tile1.png");
        highlight_tile = loadImage("minesweeper/tile2.png");
        explode[0] = loadImage("minesweeper/mine0.png");
        explode[1] = loadImage("minesweeper/mine1.png");
        explode[2] = loadImage("minesweeper/mine2.png");
        explode[3] = loadImage("minesweeper/mine3.png");
        explode[4] = loadImage("minesweeper/mine4.png");
        explode[5] = loadImage("minesweeper/mine5.png");
        explode[6] = loadImage("minesweeper/mine6.png");
        explode[7] = loadImage("minesweeper/mine7.png");
        explode[8] = loadImage("minesweeper/mine8.png");
        explode[9] = loadImage("minesweeper/mine9.png");


        // read commandline arguments and set mines number
        int mines_number = num;
        minesNum = mines_number;
        detectedMinesNum = 0;

        System.out.println("set mines number: "+ mines_number);

        tilesNum = NUM_ROWS*NUM_COLUMNS-mines_number;

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS*NUM_COLUMNS; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        ArrayList<Integer> random_numbers = new ArrayList<>();
        for (int i = 0; i < mines_number; i++) {
            random_numbers.add(numbers.get(i));
        }

//        //TODO: DELETE LATER
//        random_numbers.add(0);
//        random_numbers.add(4);
//        random_numbers.add(3);
//        random_numbers.add(100);
//        System.out.println("real mines number: "+ random_numbers.size());


        // begin to initialize the board
        int currenTiles = 0;
        for (int rowNum = 0; rowNum < NUM_ROWS; rowNum++) {
            for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
                boolean isEmpty = true;
                int nearbyMines = 0;
                // TODO: We also need to initialize whether tile is empty or has mines in adjacent tiles
                // check left and right tile
                if(random_numbers.contains(currenTiles+1) && !locateLeftEdge(currenTiles+1)){
                    nearbyMines++;
                }
                if(random_numbers.contains(currenTiles-1) && !locateRightEdge(currenTiles-1)){
                    nearbyMines++;
                }
                // check 3 tiles below current tile
                if(random_numbers.contains(currenTiles+BOARD_WIDTH+1) && !locateLeftEdge(currenTiles+BOARD_WIDTH+1)){
                    nearbyMines++;
                }
                if(random_numbers.contains(currenTiles+BOARD_WIDTH)){
                    nearbyMines++;
                }
                if(random_numbers.contains(currenTiles+BOARD_WIDTH-1) && !locateRightEdge(currenTiles+BOARD_WIDTH-1)){
                    nearbyMines++;
                }
                // check 3 tiles above current tile
                if(random_numbers.contains(currenTiles-BOARD_WIDTH+1) && !locateLeftEdge(currenTiles-BOARD_WIDTH+1)){
                    nearbyMines++;
                }
                if(random_numbers.contains(currenTiles-BOARD_WIDTH)){
                    nearbyMines++;
                }
                if(random_numbers.contains(currenTiles-BOARD_WIDTH-1) && !locateRightEdge(currenTiles-BOARD_WIDTH-1)){
                    nearbyMines++;
                }

                if (nearbyMines > 0){
                    isEmpty = false;
                }


                // random set tiles as mines
                if (random_numbers.contains(currenTiles)){
                    board[rowNum][colNum] = new Tile(
                            colNum * CELLSIZE,
                            rowNum * CELLSIZE + TOPBAR,
                            true,true,0);

                } else{
                    board[rowNum][colNum] = new Tile(
                            colNum * CELLSIZE,
                            rowNum * CELLSIZE + TOPBAR,
                            false,isEmpty,nearbyMines);
                }
                currenTiles++;
            }
        }
        //TODO: Set up a Timer

    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        // TODO: restart both during the game and game over
        if(key == 'r'){
            setup();
        }
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // only react when game is not win or lose
        if(!win && !lose) {
            for (Tile[] row : board) {
                for (Tile tile : row) {
                    if (tile.x <= mouseX && mouseX <= tile.x + CELLSIZE && tile.y <= mouseY && mouseY <= tile.y + CELLSIZE) {
                        // TODO: LEFT CLICK
                        if (mouseButton == LEFT) {
                            // TODO: do nothing when an open tile is revealed (need not write code for this case)
                            if (tile.isHidden && !tile.isFlagged && tile.isMine) {
                                // TODO: Click a mine will lead to game over
                                tile.isHidden = false;
                                detectedMinesNum++;
                                lose = true;
                            } else if (tile.isHidden && !tile.isFlagged) {
                                // TODO: reveal a hidden tile which is not flagged
                                tile.isHidden = false;
                                tilesNum -= 1;
                                // Todo: show number of adjacent mines (this.nearbyMines) with different color (give)
                            }
                        }
                        // TODO: RIGHT CLICK
                        if (mouseButton == RIGHT) {
                            // TODO: do nothing when an open tile is flagged (need not write code for this case)
                            if (tile.isHidden && !tile.isFlagged) {
                                // TODO: flag tile when it is a hidden un-flagged tile
                                tile.isFlagged = true;
                            } else if (tile.isHidden) {
                                // TODO: un-flag tile when it is a hidden flagged tile
                                tile.isFlagged = false;
                            }
                        }
                    }


                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public int update(int rowN, int colN){
        int res = 0;
        // check right tile of current tile
        if(colN+1<board[rowN].length){
            if (board[rowN][colN+1].isHidden ) {
                board[rowN][colN + 1].isHidden = false;
                res++;
            }
        }

        // check left tile of current tile
        if(colN-1>=0){
            if(board[rowN][colN-1].isHidden ) {
                board[rowN][colN - 1].isHidden = false;
                res++;
            }
        }

        // check upper right tile of current tile
        if(rowN + 1 < board.length){
            if(colN+1<board[rowN+1].length){
                if(board[rowN+1][colN+1].isHidden ){
                    board[rowN+1][colN+1].isHidden = false;
                    res++;
                }
            }
        }

        // check upper tile of current tile
        if(rowN + 1 < board.length){
            if(board[rowN+1][colN].isHidden ) {
                board[rowN + 1][colN].isHidden = false;
                res++;
            }
        }

        // check upper left tile of current tile
        if(rowN + 1 < board.length) {
            if (colN - 1 >= 0) {
                if(board[rowN+1][colN - 1].isHidden ) {
                    board[rowN + 1][colN - 1].isHidden = false;
                    res++;
                }
            }
        }

        // check lower right tile of current tile
        if(rowN - 1 >= 0){
            if(colN+1<board[rowN-1].length){
                if(board[rowN-1][colN+1].isHidden) {
                    board[rowN - 1][colN + 1].isHidden = false;
                    res++;
                }
            }
        }

        // check lower tile of current tile
        if(rowN - 1 >= 0){
            if(board[rowN-1][colN].isHidden ) {
                board[rowN - 1][colN].isHidden = false;

                res++;
            }
        }

        // check lower left tile of current tile
        if(rowN - 1 >= 0) {
            if (colN - 1 >= 0) {
                if (board[rowN - 1][colN - 1].isHidden && !board[rowN - 1][colN - 1].isFlagged){
                    board[rowN - 1][colN - 1].isHidden = false;
                    res++;
                }
            }
        }

        return res;
    }

    public void findNextMines(int rowN,int colN, int level){
        if (detectedMinesNum == num){
            return;
        }
        if(helper(rowN,colN,level)){
            System.out.println("level "+level);
            findNextMines(rowN,colN,level+1); // level begin from 1
        }
    }

    public boolean helper(int rowN,int colN, int level){
        int res = 0;

        for(int r = -level; r<= level; r++){
                for(int c = -level; c<=level; c++){
                    if(0 <= colN+c && colN+c<board[0].length && 0 <= rowN+r && rowN+r<board.length){
                        if (board[rowN+r][colN+c].isHidden && board[rowN+r][colN+c].isMine) {
                            board[rowN+r][colN + c].isHidden = false;
                            res++;
                            detectedMinesNum++;
                        }
                    }
                }
        }
        return res== 0;
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        if(tilesNum == 0 && num!=486){
            win = true;
        }
        if(win&&onemoretime){
            //TODO: Show "you win"
            //TODO: Show "you lose"
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("You win", width / 2, height / 2);
        }else if(minesNum==0 && onemoretime){
            //TODO: Show "you lose"
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("You lose", width / 2, height / 2);
        }else{
            // TODO: Show timer in upper right corner
            if(!lose) {
                fill(200);
                rect(0, 0, width, TOPBAR * CELLSIZE);


                int elapsedTime = (millis() - startTime) / 1000;
                fill(0);
                textSize(32);
                textAlign(CENTER, CENTER);
                text("Time: " + elapsedTime, 750, 30);
            }

            int rowN = 0;
            int colN = 0;
            for (Tile[] row : board) {
                colN = 0;
                for (Tile tile : row) {
                    // generate rectangular for each tile
                    rect(tile.x, tile.y, CELLSIZE, CELLSIZE);

                    boolean isNotEmpty = false;
                    if(tile.isHidden) {
                        // TODO: draw hidden tile (not include mouse hover case)
                        if (!tile.isFlagged) {
                            // hidden tile without flag
                            // TODO: should show tile1.png
                            image(hidden_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                        } else {
                            // hidden tile with flag
                            // TODO: should show flag.png over tile1.png
                            image(hidden_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                            image(flag,tile.x, tile.y, CELLSIZE, CELLSIZE);
                        }

                        //TODO: HIGHLIGHT hidden tile which mouse hoover over it
                        if (tile.x <= mouseX && mouseX <= tile.x + CELLSIZE && tile.y <= mouseY && mouseY <= tile.y + CELLSIZE){
                            if (tile.isFlagged){
                                image(highlight_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                                image(flag,tile.x, tile.y, CELLSIZE, CELLSIZE);
                            }else{
                                image(highlight_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                            }
                        }
                    }else {
                        // TODO: draw revealed tile
                        if (!tile.isMine) {
                            if(tile.isEmpty) {
                                // Empty tile
                                // TODO: reveal nearby tiles of empty tile
                                if(!tile.checked) {
                                    tilesNum -= update(rowN, colN);
                                    tile.checked = true;
                                }
                                image(base_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                            } else{
                                // Number tile
                                // TODO: show number of nearby mines correctly
                                isNotEmpty = true;
                                image(base_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                            }
                        }
                    }

                    // TODO: show number inside box
                    if (isNotEmpty) {
                        int[] color = mineCountColour[tile.nearbyMines];
//                        System.out.println(Arrays.toString(color));
                        fill(color[0],color[1],color[2]);
                        textSize(20);
                        textAlign(CENTER, CENTER);
                        text(tile.nearbyMines, tile.x + CELLSIZE / 2, tile.y + CELLSIZE / 2);
                    }

                    // TODO: display animation when lose the game
                    if(lose && tile.isMine && !tile.isHidden){
                        if(firstMine){
                            explodeCenterx = rowN;
                            explodeCentery = colN;
                            firstMine=false;
                        }
                        if (frame % 3 == 0) {
                            // third frame
                            boolean temp = false;
                            for (int i = 0; i< 10; i++){
                                if(i == tile.stage && frame%3 == 0){
//                                    System.out.println(tile.stage);
                                    image(explode[i],tile.x, tile.y, CELLSIZE, CELLSIZE);
                                    temp=true;
                                    tile.beginDraw = true;
                                }
                            }
                            if (temp){
                                tile.stage++;
                            }
                            if (tile.stage == 10){
                                image(explode[9],tile.x, tile.y, CELLSIZE, CELLSIZE);
                            }
                            System.out.println("frame: "+frame+"; "+"("+rowN+","+colN+")"+"; "+"stage: "+tile.stage);
                        }else{
                            // first and second frame
                            if(tile.beginDraw) {
                                image(explode[tile.stage - 1], tile.x, tile.y, CELLSIZE, CELLSIZE);
                            }else{
                                if (!tile.isFlagged) {
                                    // hidden tile without flag
                                    // TODO: should show tile1.png
                                    image(hidden_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                                } else {
                                    // hidden tile with flag
                                    // TODO: should show flag.png over tile1.png
                                    image(hidden_tile,tile.x, tile.y, CELLSIZE, CELLSIZE);
                                    image(flag,tile.x, tile.y, CELLSIZE, CELLSIZE);
                                }
                            }
                            // second frame
                            if(frame%3 == 2 && rowN == explodeCenterx && colN == explodeCentery){
                                System.out.println("--------------------------------");
                                System.out.println("frame: "+frame);
                                findNextMines(explodeCenterx, explodeCentery,1);
                                System.out.println("--------------------------------");
                            }
                        }

                        // will display "you lose" when last mine finish its last picture
                        if (tile.stage == 10) {
                            if (!tile.checked) {
                                minesNum--;
                                tile.checked = true;
                            }
                        }
                    }

                    colN++;
                }
                rowN ++;
            }
//            System.out.println(tilesNum);
            if(win || lose){
                onemoretime = true;
            }
        }
        if(lose){
            frame++;
        }
    }


    public static void main(String[] args) {
        try{
            int command_line = Integer.parseInt(args[0]);
            if (command_line < 0 || command_line > NUM_ROWS*NUM_COLUMNS){
                throw new IllegalArgumentException("invalid number of mines");
            }
            num = command_line;
        }catch (Exception e){
            // TODO: change back to 100
            num = 100;
        }
        PApplet.main("minesweeper.App");
    }

}
