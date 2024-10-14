package inkball;

import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level {
    /*
    store map info for each level
     */

    // number of current level
    public int currentLevel;

    // collection of all levels
    public JSONArray levels;

    // setting of current levels
    // used for read txt file
    public String layoutFile;
    public ArrayList<ArrayList<String>> layoutContent;
    // remaining time for each level
    public int time;
    // remaining time for current level
    public float remainTime;
    // ball spawn interval for each level
    public int spawn_interval;
    // Indicate the number of spawner in current level
    public int spawner_number;
    // score modifier for each level
    public float increaseModifier;
    public float decreaseModifier;
    // balls queue for each level, spawner will use it to generate ball
    public ArrayList<Ball> ballsQueue;
    // balls currently on the Map
    public ArrayList<Ball> ballsOnTheMap;


    // denote the first frame of each level, will change to false after the first frame
    // currently, only generate ball at spawner uses it
    public boolean firstFrame;
    // total score for all level before current level
    public float resetScore;
    // All scores received up to the current time
    public float currentScore;

    // Line will use below attributes
    public ArrayList<Line> linesCollection; // need to reset to empty at each level
    public Line currentline;

    // level status:
    // "play" means player can do anything;
    // "pause" means player press "space"
    // "time's up" means player cannot do anything except press 'r' happen when time runs out
    // "wining" means player successfully capture all balls within time limit.
    public String levelStatus;
    // Indicate how many frame are used, will reset to 0 when balls run out, begin from 0 in each level
    public int frameElapsedForBallTimer;
    // Indicate how many frame are used for current level, begin from 0 in each level
    public int frameElapsedForTimer;
    // Frame used when perform wining animation
    public int frameForWiningAnimation;


    // Constructor
    public Level(JSONArray levels){

        this.currentLevel = 0;
        this.levels = levels;
        this.ballsQueue = new ArrayList<Ball>();
        this.ballsOnTheMap = new ArrayList<Ball>();
        this.layoutContent = new ArrayList<ArrayList<String>>();

        this.linesCollection = new ArrayList<Line>();

        for(int i = 0; i < 18;i++){
            ArrayList<String> temp = new ArrayList<>();
            this.layoutContent.add(temp);
        }
        for(int i = 0; i < 18;i++){
            for(int j = 0; j < 18;j++){
                this.layoutContent.get(i).add(" ");
            }
        }
        // initial the first level setting
        this.updateMapSetting(0);
    }

    // advance to the next level if next level exist, automatically call when satisfy wining phase
    public void enterNextlevel(){
        //TODO only use when player successfully pass current level && current level is not last level
        if (this.currentLevel < levels.size()-1){
            this.currentLevel++;
            System.out.println("next!!!!!!!!!!!!!!!!!!!!!");
            this.updateMapSetting(this.currentScore);
            System.out.println(this.ballsOnTheMap.size());
        }
    }

    public boolean hasNextlevel(){
        //return whether have next level
        return this.currentLevel < levels.size()-1;
    }

    // update Map when
    // (1) level changes or
    // (2) use "r" to replay current level
    // (3) when game end and want to use "r" to replay the game
    public void updateMapSetting(float Score){
        // We need to reset App.startTime whenever we use this function, but we can only do it inside App class

        this.frameForWiningAnimation=0;
        this.frameElapsedForBallTimer = 0;
        this.frameElapsedForTimer=0;
        this.levelStatus ="play";

        JSONObject level = levels.getJSONObject(this.currentLevel);
        this.firstFrame = true;
        this.resetScore = Score;
        this.currentScore = Score;
        this.currentline = null;
        this.linesCollection.clear();
        this.spawner_number = 0;

        this.time = level.getInt("time");
        this.remainTime = this.time;
        this.spawn_interval = level.getInt("spawn_interval");

        this.increaseModifier = level.getFloat("score_increase_from_hole_capture_modifier");
        this.decreaseModifier = level.getFloat("score_decrease_from_wrong_hole_modifier");

        this.ballsOnTheMap.clear();
        this.ballsQueue.clear();
        JSONArray balls = level.getJSONArray("balls");
        for (int i = 0; i < balls.size(); i++){
            Ball ball = new Ball(balls.getString(i),0,0);
            ballsQueue.add(ball);
        }

        // read current level layout from txt file
        this.layoutFile = level.getString("layout");
        this.readLayoutFile();
        // setup board for current level according to this.layoutContent
        this.setupBoard();
        System.out.println("level "+currentLevel+": "+ballsOnTheMap.size());
    }

    // Read txt file to get layout of current level in the form of array list of array list of string
    // the size is 18*18
    public void readLayoutFile(){
        // can only draw the map for current level in App.java
        // return the "layout" txt file content in the format of [[],[]]
        // Map number of rows: len of outside list can be less than 18, for example, bottom edge set to tile
        // Map row: len of inside list can be less than 18
        try {
            File file = new File(this.layoutFile);
            Scanner scanner = new Scanner(file);
            int numRows = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // if line does not have 18 characters, below code will fill them with space " "
                int lineLength = line.length();
                for(int i = 0; i < 18;i++){
                    if (i<lineLength){
                        this.layoutContent.get(numRows).set(i,Character.toString(line.charAt(i)));
                    }else {
                        this.layoutContent.get(numRows).set(i," ");
                    }
                }
                numRows++;
            }
            // if there are less than 18 rows, below code will set rest rows with empty space
            for (int i = numRows; i < 18; i++){
                for(int j = 0; j < 18;j++){
                    this.layoutContent.get(i).set(j," ");
                }
            }
            /*
                Since hole blocks need 64*64 pixel, therefore, I decided to use
                                H1
                                FF
                to represent a hole. Notice that, a hole never gonna appear in the edge
                so we begin from idx 1 to idx 16
             */
            for (int i = 1; i < 17; i++){
                for(int j = 1; j < 17;j++){
                    if("H".equals(this.layoutContent.get(i).get(j))){
                        this.layoutContent.get(i+1).set(j,"F");
                        this.layoutContent.get(i+1).set(j+1,"F");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can not find layout file " + this.currentLevel + " correctly!!!");
        }
    }

    // setup the App.board according to this.layoutContent
    public void setupBoard(){
        for (int rowN = 0; rowN < App.NUM_ROWS; rowN++) {
            for (int colN = 0; colN < App.NUM_COLUMNS; colN++) {
                String currentLetter = this.layoutContent.get(rowN).get(colN);
                // After below if statement, I guarantee that all block assign appropriate size
                if("H".equals(currentLetter)){
                    // represent the image block for a hole (hole has 4 blocks, only the top left one should draw the image)
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "H",
                            App.colors[Integer.parseInt(this.layoutContent.get(rowN).get(colN+1))],
                            true);
                }else if(colN>0 &&("F".equals(currentLetter) ||
                        "H".equals(this.layoutContent.get(rowN).get(colN-1))
                )){
                    // Generate rest block of Hole "H", color should "colorless"
                    // hole attraction only need to consider about the bottom right pixel of image block for the hole
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "H",
                            "colorless",
                            false);
                }else if(currentLetter.equals("X")){
                    // Should create wall 0 block "W which is a grey wall
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "W",
                            "grey",
                            true);
                }else if("0".equals(currentLetter) || "1".equals(currentLetter) || "2".equals(currentLetter)||
                        "3".equals(currentLetter)|| "4".equals(currentLetter)){
                    // Does need to worry about H1 cases, because that case already consider in above if statement
                    if(colN>0 && "B".equals(this.layoutContent.get(rowN).get(colN-1))) {
                        // create tile block "T" because last block is "B"
                        App.board[rowN][colN] = new Block(
                                colN * App.CELLSIZE,
                                rowN * App.CELLSIZE + App.TOPBAR,
                                "T",
                                "colorless",
                                true);
                    }else{
                        // create wall block "W" with corresponding color 1 to 4
                        App.board[rowN][colN] = new Block(
                                colN * App.CELLSIZE,
                                rowN * App.CELLSIZE + App.TOPBAR,
                                "W",
                                App.colors[Integer.parseInt(currentLetter)],
                                true);
                    }
                }else if(currentLetter.equals("S")){
                    // count the number of spawner for current level
                    this.spawner_number+=1;
                    // Should draw spawner "S"
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "S",
                            "colorless",
                            true);
                }else if(currentLetter.equals(("B"))){
                    // Create "B" block
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "B",
                            App.colors[Integer.parseInt(this.layoutContent.get(rowN).get(colN+1))],
                            true);

                    Ball newball = new Ball(App.colors[Integer.parseInt(App.board[rowN][colN].colorIndex)],
                            App.board[rowN][colN].x+App.CELLSIZE/2,App.board[rowN][colN].y+App.CELLSIZE/2);
                    this.addBallToTheMap(newball);
                }else if(currentLetter.equals(" ")){
                    // Create tile block "T"
                    App.board[rowN][colN] = new Block(
                            colN * App.CELLSIZE,
                            rowN * App.CELLSIZE + App.TOPBAR,
                            "T",
                            "colorless",
                            true);
                }
            }
        }
    }

    public void addBallToTheMap(Ball newBall){
        this.ballsOnTheMap.add(newBall);
    }

    // add currentline to linesCollection
    public void addLine(){
        this.linesCollection.add(currentline);
    }

    // delete currentline from linesCollection
    public void deleteLine(){
        this.linesCollection.remove(currentline);
    }

    // Generate a ball at random spawner
    public void serveBall(){
        int spawneridx= App.random.nextInt(spawner_number);
        int idx = 0;
        for (Block[] row : App.board) {
            for (Block b : row) {
                if (b.blockType.equals("S") && !this.ballsQueue.isEmpty()){
                    if (idx == spawneridx){
                        Ball shotBall = this.ballsQueue.remove(0);
                        shotBall.x = b.x + App.CELLSIZE/2;
                        shotBall.y = b.y + App.CELLSIZE/2;
                        this.ballsOnTheMap.add(shotBall);
                    }
                    idx++;
                }
            }
        }
    }
}
