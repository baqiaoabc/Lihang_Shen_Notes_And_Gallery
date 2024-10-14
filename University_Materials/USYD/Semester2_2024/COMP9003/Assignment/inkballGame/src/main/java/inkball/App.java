package inkball;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 64;
    public static int WIDTH = 576; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE;
    public static final int BOARD_HEIGHT = 20;
    public static final int INITIAL_PARACHUTES = 1;

    public static final int FPS = 30;
    public String configPath;
    public static Random random = new Random();


	//TODO: Feel free to add any additional methods or attributes you want. Please put classes in different files.
    // Indicates how many rows of tiles do we have
    public static final int NUM_ROWS = (HEIGHT - TOPBAR) / CELLSIZE; //18
    // Indicates how many cols of tiles do we have
    public static final int NUM_COLUMNS = BOARD_WIDTH; // 18

    // represent current level info
    public static Level level;
    // each color has a correspond number in the image files title
    // 0-4 represent "grey", "orange", "blue", "green", "yellow" respectively
    public static final String[] colors = {"grey", "orange", "blue", "green", "yellow"};
    public static final String[] colorsSIndices = {"0", "1", "2", "3", "4"};
    public static final int[] colorsIndices = {0, 1, 2, 3, 4};

    // Basic Score rule collections
    public Map<String, Integer> score_increase;
    public Map<String, Integer> score_decrease;

    // create the board exclude top bar
    public static Block[][] board = new Block[NUM_ROWS][NUM_COLUMNS]; // 18*18 matrix

    // Image collections
    public Map<String, PImage> balls_image;
    public PImage tile_image;
    public PImage entrypoint;
    public Map<String, PImage> walls_image;
    public Map<String, PImage> holes_image;

    public App() {
        this.configPath = "config.json";
        // initialize score rule hash map
        this.score_increase = new HashMap<>();
        this.score_decrease = new HashMap<>();
        // initialize image collection hash map
        this.balls_image = new HashMap<>();
        this.walls_image = new HashMap<>();
        this.holes_image = new HashMap<>();
//        this.frameCounter = 0;
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player and map elements.
     */
	@Override
    public void setup() {
        // Set refresh rate
        frameRate(FPS);
        // Load tile image, Since we have many tile instances, so we cannot store tile.png in this class
        tile_image = loadImage("inkball/tile.png");
        entrypoint = loadImage("inkball/entrypoint.png");
        // Load balls_image, wall_images, and holes_image
        for (int i : colorsIndices){
            balls_image.put(colorsSIndices[i], loadImage("inkball/ball"+i+".png"));
            walls_image.put(colorsSIndices[i], loadImage("inkball/wall"+i+".png"));
            holes_image.put(colorsSIndices[i], loadImage("inkball/hole"+i+".png"));
        }

        // load config.json file
        JSONObject json = loadJSONObject(this.configPath);


        // basic score info
        JSONObject score_increase_from_hole_capture = json.getJSONObject("score_increase_from_hole_capture");
        JSONObject score_decrease_from_wrong_hole = json.getJSONObject("score_decrease_from_wrong_hole");

        for (String color:colors){
            Integer temp = score_increase_from_hole_capture.getInt(color);
            score_increase.put(color,score_increase_from_hole_capture.getInt(color));
            score_decrease.put(color,score_decrease_from_wrong_hole.getInt(color));
        }
        // Check that the scores for each color are set correctly
        System.out.println(score_increase);
        System.out.println(score_decrease);

        // create a Level instance, should initiate after the initialization of board
        level = new Level(json.getJSONArray("levels"));
        System.out.println("outside " + level.layoutContent.get(0));
        System.out.println(level.layoutContent.get(16));
        System.out.println(board[0][0].y);
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        if(!level.levelStatus.equals("wining") && !level.levelStatus.equals("time's up")){
            if(key == 'r'){
                // reset timer when replay current level or replay the game
                level.updateMapSetting(level.resetScore);
            }else if(key==' '){
                if(level.levelStatus.equals("pause")){
                    level.levelStatus = "play";
                }else{
                    level.levelStatus = "pause";
                }
            }
        }else {
            // restart game when game ended and press 'r' && level status is not wining
            if(key == 'r') {
                if (!level.hasNextlevel()&&level.levelStatus.equals("wining")) {
                    level.currentLevel = 0;
                    level.updateMapSetting(0);
                }else{
                    level.updateMapSetting(level.resetScore);
                }
            }
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
        // create a new player-drawn line object; need a global indicator point to
        //  that object, otherwise, we cannot modify it in mouseDragged; need a Line arrayList
        //  to include all line we draw for current level in Level instance (should also include
        //  the initial statement of current level for reset).
        level.currentline = new Line();
        level.addLine();
    }
	
	@Override
    public void mouseDragged(MouseEvent e) {
        if(!level.levelStatus.equals("wining")) {
            // left-click
            // add line segments to player-drawn line object if left mouse button is held
            // add current mouse location to Line object created in last mousePressed
            if (mouseButton == LEFT) {
                level.currentline.addPoint(mouseX, mouseY);
            }

            // right-click
            // remove player-drawn line object if right mouse button is held
            // and mouse position collides with the line
            rightDraggedHelper();
        }

    }

    public void rightDraggedHelper(){
        if(mouseButton==RIGHT){
            Line delete = null;
            for (Line l : level.linesCollection){
                // Can be optimized, but I am lazy guy
                for(int i = 0; i <l.pointsArray.size()-2;i++){
                    float x1 = l.pointsArray.get(i)[0];
                    float y1 = l.pointsArray.get(i)[1];
                    float x2 = l.pointsArray.get(i + 1)[0];
                    float y2 = l.pointsArray.get(i + 1)[1];
                    if(l.isPointOnLineSegment(mouseX, mouseY, x1, y1, x2, y2)){
                        delete = l;
                        break;
                    }
                }
            }
            level.linesCollection.remove(delete);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // left-click
        //  only add level.currentline to level.linesCollection if it has at least 2 points
        if (!level.currentline.validLine()){
            level.deleteLine();
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {

        //----------------------------------
        //display Board for current level:
        //----------------------------------
        switch (level.levelStatus) {
            case "play":
                // count the frame elapsed for current level only when play phase
                level.frameElapsedForTimer++;
                // level status is "play"
                this.drawMap();
                this.drawBallsOnTheMap();
                this.drawTopBar();
                // draw lines
                this.drawLines();
                break;
            case "time's up":
                // level status is "time's up"
                fill(0);
                textSize(20);
                textAlign(CENTER, CENTER);
                text("=== TIME'S UP ===", 330, 40);
                break;
            case "wining":

                // TODO check with Ankit
                // draw wining animation
                this.drawMap();
                this.drawLines();

                // TODO check with Ankit
                // draw score animation
                this.drawTopBar();
                // Only last level will perform === ENDED === after finished
                if(!level.hasNextlevel()){
                    fill(0);
                    textSize(20);
                    textAlign(CENTER, CENTER);
                    text("=== ENDED ===", 330, 40);
                }
                if(level.remainTime<0){
                    // enter next level if exist
                    level.enterNextlevel();
                }
                level.frameForWiningAnimation++;
                break;
            case "pause":
                this.drawMap();
                this.drawBallsOnTheMap();
                this.drawTopBar();
                // draw text
                fill(0);
                textSize(20);
                textAlign(CENTER, CENTER);
                text("*** PAUSED ***", 330, 40);
                this.drawLines();
                break;
        }

        // check Wining
        if(level.ballsOnTheMap.isEmpty() && level.ballsQueue.isEmpty() && !level.levelStatus.equals("wining")){
            level.levelStatus = "wining";
            // initialize yellow block
            board[0][0].animation = true;
            board[NUM_ROWS-1][NUM_COLUMNS-1].animation = true;
        }
    }

    public void drawWinningAnimation(){
        // Should display over edge tile
        ArrayList<Block> two = new ArrayList<>();
        Block b = board[0][0];

        for (int rowN = 0; rowN < NUM_ROWS; rowN++) {
            for (int colN = 0; colN < NUM_COLUMNS; colN++) {
                if(rowN ==0 || rowN == NUM_ROWS-1 || colN ==0 || colN == NUM_COLUMNS-1){
                    if(board[rowN][colN].animation){
                        // (0,0) row==0; (0,27) col==27;
                        // (27,27) row==27; (0,27) col==0
                        if(rowN==0 && colN!=NUM_COLUMNS-1){
                            // col ++
                            b = board[rowN][colN+1];
                        }else if(colN == NUM_COLUMNS-1 && rowN != NUM_ROWS-1){
                            // row++
                            b =board[rowN+1][colN];
                        }else if(rowN == NUM_ROWS-1 && colN != 0){
                            // col--
                            b = board[rowN][colN-1];
                        }else if(colN == 0 && rowN!=0){
                            // row--
                            b = board[rowN-1][colN];
                        }
                        if(!two.contains(board[rowN][colN])){
                            b.animation = true;
                            board[rowN][colN].animation = false;
                            two.add(b);
                        }
                        if(two.size()==2){
                            break;
                        }
                    }
                }
            }
        }
    }

    public void drawTopBar(){
        fill(200);
        rect(0, 0, WIDTH, TOPBAR);
        this.drawTimer();
        this.drawScore();
        this.drawBallTimer();
        this.drawNextFiveBalls();
    }

    public void drawMap(){
        // TODO extension part

        // Notice that board is a 18*18 matrix
        for (Block[] row : board) {
            for (Block block : row) {
                if(block.ImageBlock){
                    rect(block.x,block.y,block.imageBlockSize,block.imageBlockSize);
                    if(block.animation){
                        image(walls_image.get("4"),
                                block.x,
                                block.y,
                                block.imageBlockSize,
                                block.imageBlockSize);
                    }else{
                        switch (block.blockType) {
                            case "W":
                                // draw corresponding wall
                                image(walls_image.get(block.colorIndex),
                                        block.x,
                                        block.y,
                                        block.imageBlockSize,
                                        block.imageBlockSize);
                                break;
                            case "S":
                                image(entrypoint, block.x, block.y, block.imageBlockSize, block.imageBlockSize);
                                break;
                            case "H":
                                // draw corresponding hole
                                image(holes_image.get(block.colorIndex),
                                        block.x,
                                        block.y,
                                        block.imageBlockSize,
                                        block.imageBlockSize);
                                break;
                            case "B":
                                // Generate a ball instance, and add it to level.ballsOnTheMap
                                if(level.firstFrame){
                                    // this is the first frame of current level, hence should generate a ball

                                    // base image should be a tile, otherwise, base will be white
                                    image(tile_image, block.x, block.y, block.imageBlockSize, block.imageBlockSize);
                                    level.firstFrame = false;
                                }else{
                                    // in the rest frame, this block has no differences with other block
                                    image(tile_image, block.x, block.y, block.imageBlockSize, block.imageBlockSize);
                                }
                                break;
                            case "T":
                                image(tile_image, block.x, block.y, block.imageBlockSize, block.imageBlockSize);
                                break;
                        }
                    }

                }
            }
        }
    }

    public void drawBallsOnTheMap(){
        // Draw all balls currently on the map
        imageMode(CENTER);
        ArrayList<Ball> removeList = new ArrayList<>();
        for (Ball b : level.ballsOnTheMap){
            image(balls_image.get(b.colorIndex), b.x, b.y, 2*b.radius, 2*b.radius);

            // After move check whether collide with bouncy object, if yes, then change velocity
            b.checkCollision();

            // After move check whether any hole capture the ball
            Ball remove = b.checkEnterHole(level,this);
            if(remove != null){
                removeList.add(remove);
            }
            // move the ball with the velocity for the next frame
            if(level.levelStatus.equals("play")){
                b.ballMove();
            }
        }
        for(Ball b:removeList){
            level.ballsOnTheMap.remove(b);
        }



        imageMode(CORNER);
    }

    public void drawTimer(){
        if(level.levelStatus.equals("play")){
            level.remainTime = level.time-(level.frameElapsedForTimer/FPS);
        }
        // TODO check with Ankit
        // when current level end, the remaining time will add to score
        else if (level.levelStatus.equals("wining")){
            if(level.frameForWiningAnimation%(int) (FPS*0.067) == 0 && level.remainTime>0){
                level.currentScore ++;
                level.remainTime-=0.067;
                this.drawWinningAnimation();
            }
        }

        fill(0);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("Time: " + (int) Math.ceil(level.remainTime), 500, 40);

        // when time out, current level enter "time's up" status
        if(level.remainTime == 0){
            level.levelStatus = "time's up";
        }
    }

    public void drawScore(){
        fill(0);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("Score: " + (int) level.currentScore, 500, 15);
    }

    // Timer for ball serve, also will serve ball every interval time
    public void drawBallTimer(){
        fill(0);
        textSize(20);
        textAlign(CENTER, CENTER);
        // Timer should stop when there is no ball in current queue,
        //  and start from 10 when a new ball add; use frame to count the time
        if(level.levelStatus.equals("play")){
            if(!level.ballsQueue.isEmpty()){
                level.frameElapsedForBallTimer++;
                // we need to serve the ball before draw next five balls
                // frame begin from 1
                if (level.frameElapsedForBallTimer%(FPS*level.spawn_interval) == 0){
                    level.serveBall();
                }
            }else{
                // reset to 0 frame
                level.frameElapsedForBallTimer = 0;
            }
        }
        double remainTime = level.spawn_interval-(1.0*level.frameElapsedForBallTimer / FPS)%level.spawn_interval;

        text(String.format("%.1f",remainTime), 200, 30);
    }

    public void drawNextFiveBalls(){
        // the animation of move ball in top bar
        // through update level.ballsQueue to update the image
        // need 32*5=160 frame to move one ball from right to left
        // need 32 frame to move one ball from one tile to another
        rect(10, 15, CELLSIZE*5, CELLSIZE);
        clip(10, 15, CELLSIZE*5, CELLSIZE);
        fill(0);
        int notreadyidx = 0;
        for (int i =0; i < 5; i++){
            if(i < level.ballsQueue.size()){
                Ball b = level.ballsQueue.get(i);
                if(level.levelStatus.equals("play")){
                    if(!b.ready) {
                        b.x = 160 + 10 + notreadyidx *CELLSIZE;
                        b.y = 15;
                        b.ready = true;
                        notreadyidx++;
                    }else{
                        if(b.x != 10 + CELLSIZE * i) {
                            b.x--;
                            b.y = 15;
                        }
                    }
                }
                image(balls_image.get(b.colorIndex), b.x, b.y, CELLSIZE, CELLSIZE);
            }
        }
        noClip();
    }

    public void drawLines(){
        stroke(0);
        // line width
        strokeWeight(10);
        for(Line l : level.linesCollection){
            for(int i = 0; i < l.pointsArray.size()-1; i++){
                if (i+1<l.pointsNum){
                    line(l.pointsArray.get(i)[0],l.pointsArray.get(i)[1],
                            l.pointsArray.get(i+1)[0],l.pointsArray.get(i+1)[1]);
                }
            }
        }
        noStroke();
    }

    public static void main(String[] args) {
        PApplet.main("inkball.App");
    }

}
