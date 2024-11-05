package inkball;

import static java.lang.Math.sqrt;

/**
 * a ball object, have every function a ball should have.
 */
public class Ball {

    /**
     * color of ball
     */
    public String color;

    /**
     * corresponding color idx of ball, for example, grey's color idx is 0.
     */
    public String colorIndex;

    /**
     * x coordinate of ball center
     */
    public float x;

    /**
     * y coordinate of ball center
     */
    public float y;

    /**
     * radius of ball
     */
    public float radius;

    /**
     * speed of ball
     */
    public float x_velocity, y_velocity;

    /**
     * Indicate a ball need to move from right of loading zone to the left, each ball can be load from
     * right to left once.
     */
    public boolean ready;

    /**
     * constructor of ball
     *
     * @param color the color of the ball
     * @param x the x coordinate of ball center
     * @param y the y coordinate of ball center
     */
    public Ball(String color, float x, float y){
        // set color and correspond index of color for the ball
        this.updateColor(color);
        this.x = x;
        this.y = y;
        this.radius=12;
        this.ready = false;

        // v is -2 or 2 pixel/second? no, should be -60 or 60 pixel/second
        // below is the pixel/frame
        float[] initial_speed = {-60.0f/App.FPS,60.0f/App.FPS};
        this.updateVelocity(initial_speed[App.random.nextInt(initial_speed.length)],
                initial_speed[App.random.nextInt(initial_speed.length)]);
//        this.x_velocity = -2;
//        this.y_velocity = 2;
    }

    /**
     * update velocity of ball
     *
     * @param xv new x velocity of ball
     * @param yv new y velocity of ball
     */
    public void updateVelocity(float xv, float yv){
        this.x_velocity = xv;
        this.y_velocity = yv;
    }

    /**
     * update color and color index of ball
     *
     * @param color the new color of ball
     */
    public void updateColor(String color){
        this.color = color;
        this.colorIndex = "notExist";
        for(int i = 0; i < App.colors.length; i++){
            if (color.equals(App.colors[i])){
                this.colorIndex = String.valueOf(i);
            }
        }
    }

    /**
     * check whether ball inside hole attraction cycle;
     * if yes, then apply attraction force to the ball;
     * if ball enter the hole, the scorer should get correspond score
     * @param level current level class
     * @param a App class
     * @return return ball if ball enter the hole, null otherwise.
     */
    public Ball checkEnterHole(Level level,App a){
        // Check hole
        boolean cond = false;
        for (Block[] row : App.board) {
            for (Block b : row) {
                // 75% of ball within the hole will be captured by hole
                float epsilon = (float) 4;
                // Get THE BOTTOM RIGHT corner of image Hole block is the center of circle
                if(b.ImageBlock && b.blockType.equals("H")){
                    // Firstly, check is there a ball enter any hole (i.e. ball center equal hole's center)
                    if(Math.abs(this.x - b.bottomRight[0]) <= epsilon && Math.abs(this.y - b.bottomRight[1]) <= epsilon){
                        // after ball enter hole, the score should change
                        if(b.color.equals(this.color) || b.color.equals("grey") || this.color.equals("grey")){
                            level.currentScore += level.increaseModifier*a.score_increase.get(this.color);
                        }else{
                            level.currentScore -= level.decreaseModifier*a.score_decrease.get(this.color);
                            Ball ball = new Ball(this.color,0,0);
                            level.ballsQueue.add(ball);
                        }
                        return this;
                    }
                    // Check whether ball inside the circle of hole
                    double distance = Math.pow(this.x-b.bottomRight[0],2) + Math.pow(this.y-b.bottomRight[1],2);
                    if(distance <= Math.pow(32,2)){
                        // Calculate the attraction force, this is the additional force
                        float x_attraction = (b.bottomRight[0] - this.x);
                        float y_attraction = (b.bottomRight[1] - this.y);
                        float mag = (float) Math.sqrt(x_attraction * x_attraction + y_attraction * y_attraction);
                        float normalizedX = x_attraction / mag;
                        float normalizedY = y_attraction / mag;
//                        x_velocity += 0.4 * normalizedX;
//                        y_velocity += 0.4 * normalizedY;
                        // I also slow down the original speed of ball by 0.95
                        // otherwise it is very difficult to enter a whole
                        x_velocity = (float) (x_velocity * 0.95 + 0.4 * normalizedX);
                        y_velocity = (float) (y_velocity * 0.95 + 0.4 * normalizedY);

                        // Also need to update ball size
                        double portion = Math.sqrt(distance)/32;
                        this.radius= (float) 12.0 * (float) Math.exp(-0.5*(1-portion));
                        cond = true;
                    }
                }
            }
        }
        if(!cond)
            this.radius = 12;
        return null;
    }

    /**
     * check whether ball collide with bouncy object, namely, use boundaryCollisionCheck(),
     * wallCollisionCheck(), and lineCollisionCheck() to implement.
     *
     * @param app class instance we used for the game
      */
    public void checkCollision(App app){

        this.boundaryCollisionCheck();

        this.wallCollisionCheck();

        this.lineCollisionCheck(app);
    }

    /**
     * check whether ball collide with boundary, it it does, change its speed and direction correctly
     */
    public void boundaryCollisionCheck(){
        // p1
        float p1x =0;
        float p1y =64;
        // p2
        float p2x =App.WIDTH;
        float p2y =64;
        // if collision happens, update the ball velocity
        if (this.x+this.x_velocity< 12 || this.x+this.x_velocity > App.WIDTH-radius){

            // left edge and right edge have same normal vector
            // p1
            p1x =0;
            p1y =64;
            // p2
            p2x =0;
            p2y =App.HEIGHT;
            float [] normalVector = this.normalVectorHelper(p1x, p1y, p2x, p2y);
            float dotProduct = this.x_velocity*normalVector[0] + this.y_velocity*normalVector[1];
            this.x_velocity=this.x_velocity - 2*dotProduct*normalVector[0];
            this.y_velocity=this.y_velocity - 2*dotProduct*normalVector[1];
        }else if (this.y+this.y_velocity < 64+radius || this.y+this.y_velocity > App.HEIGHT - radius){
            // upper edge and lower edge have same normal vector
            float [] normalVector = this.normalVectorHelper(p1x, p1y, p2x, p2y);
            float dotProduct = this.x_velocity*normalVector[0] + this.y_velocity*normalVector[1];
            this.x_velocity=this.x_velocity - 2*dotProduct*normalVector[0];
            this.y_velocity=this.y_velocity - 2*dotProduct*normalVector[1];
        }
    }

    /**
     * if collide with a wall, the wall will break when ball's color equals wall's color or ball color is grey or ball color is grey
     */
    public void wallCollisionCheck(){
        for (Block[] row : App.board) {
            boolean bounceHappened = false;
            for (Block b : row) {
                if (b.bouncy){
                    boolean cond1 = false;
                    boolean cond2 = false;
                    float [] normalVector = new float[2];
                    // touch two adjacent line at the same time consider as contact a corner
                    if (this.checkCollisionHelper(b.upperLeft[0], b.upperLeft[1],
                            b.upperRight[0], b.upperRight[1],0)){
                         // upper edge need to check left and right edge
                         if(this.checkCollisionHelper(b.upperRight[0], b.upperRight[1],
                                 b.bottomRight[0], b.bottomRight[1],0) ||
                                 this.checkCollisionHelper(b.bottomLeft[0], b.bottomLeft[1],
                                 b.upperLeft[0], b.upperLeft[1],0)){
                            cond2 = true;
                        }

                        normalVector = this.normalVectorHelper(b.upperLeft[0], b.upperLeft[1],
                                b.upperRight[0], b.upperRight[1]);
                        cond1 = true;
                    } else if(this.checkCollisionHelper(b.upperRight[0], b.upperRight[1],
                            b.bottomRight[0], b.bottomRight[1],0)){
                        // right edge need to check upper and bottom edge
                        if(this.checkCollisionHelper(b.upperLeft[0], b.upperLeft[1],
                                b.upperRight[0], b.upperRight[1],0) ||
                                this.checkCollisionHelper(b.bottomRight[0], b.bottomRight[1],
                                b.bottomLeft[0], b.bottomLeft[1],0)){
                            cond2 = true;
                        }
                        normalVector = this.normalVectorHelper(b.upperRight[0], b.upperRight[1],
                                b.bottomRight[0], b.bottomRight[1]);
                        cond1 = true;
                    }else if(this.checkCollisionHelper(b.bottomRight[0], b.bottomRight[1],
                            b.bottomLeft[0], b.bottomLeft[1],0)){
                        // bottom edge need to check left and right edge
                        if(this.checkCollisionHelper(b.upperRight[0], b.upperRight[1],
                                b.bottomRight[0], b.bottomRight[1],0) ||
                                this.checkCollisionHelper(b.bottomLeft[0], b.bottomLeft[1],
                                        b.upperLeft[0], b.upperLeft[1],0)){
                            cond2 = true;
                        }
                        normalVector = this.normalVectorHelper(b.bottomRight[0], b.bottomRight[1],
                                b.bottomLeft[0], b.bottomLeft[1]);
                        cond1 = true;
                    }else if(this.checkCollisionHelper(b.bottomLeft[0], b.bottomLeft[1],
                            b.upperLeft[0], b.upperLeft[1],0)){
                        // left edge need to check upper and bottom edge
                        if(this.checkCollisionHelper(b.upperLeft[0], b.upperLeft[1],
                                b.upperRight[0], b.upperRight[1],0) ||
                                this.checkCollisionHelper(b.bottomRight[0], b.bottomRight[1],
                                b.bottomLeft[0], b.bottomLeft[1],0)){
                            cond2 = true;
                        }
                        normalVector = this.normalVectorHelper(b.upperLeft[0], b.upperLeft[1],
                                b.bottomLeft[0], b.bottomLeft[1]);
                        cond1 = true;
                    }
                    if(cond1){
                        bounceHappened = true;
                        if (! cond2) {
                            float dotProduct = this.x_velocity * normalVector[0] + this.y_velocity * normalVector[1];
                            this.updateVelocity(this.x_velocity - 2 * dotProduct * normalVector[0],
                                    this.y_velocity - 2 * dotProduct * normalVector[1]);
                        }else{
                            this.x_velocity = -this.x_velocity;
                            this.y_velocity = -this.y_velocity;
                        }

                        // extension part
                        if(b.color.equals(this.color) || b.color.equals("grey")){
                            if(b.bouncyNum==2){
                                b.blockType="T";
                                b.bouncy=false;
                            }
                            b.bouncyNum++;
                        }

                        // update ball's color
                        if (! b.color.equals("grey")){
                            this.updateColor(b.color);
                        }

                        break;
                    }
                }
            }
            if(bounceHappened){
                break;
            }
        }
    }

    /**
     * if collide with a line, the line will be removed, and ball will bounce.
     *
     * @param app instance we used for the game
     */
    public void lineCollisionCheck(App app){
        // Line collision, also remove line from linesCollection after collision
        Line delete = null;
        for(Line l : App.level.linesCollection){
            for(int i = 0; i <l.pointsArray.size()-2;i++){
                if (this.checkLineCollisionHelper(l.pointsArray.get(i)[0], l.pointsArray.get(i)[1],
                        l.pointsArray.get(i+1)[0], l.pointsArray.get(i+1)[1])){
                    float[] normalVector = this.normalVectorHelper(l.pointsArray.get(i)[0], l.pointsArray.get(i)[1],
                            l.pointsArray.get(i+1)[0], l.pointsArray.get(i+1)[1]);
                    float dotProduct = this.x_velocity * normalVector[0] + this.y_velocity * normalVector[1];
                    this.updateVelocity(this.x_velocity - 2 * dotProduct * normalVector[0],
                            this.y_velocity - 2 * dotProduct * normalVector[1]);
                    delete = l;
                    break;
                }
            }
        }
        app.level.linesCollection.remove(delete);
    }

    /**
     * check if ball collide with a line, the difference between this one and checkLineCollisionHelper() is that we need
     * not to consider about the thickness of line
     * @param p1x x of one endpoint
     * @param p1y y of one endpoint
     * @param p2x x of another endpoint
     * @param p2y y of another endpoint
     * @param episilon the scaler we add to judge the touch condition.
     * @return return true if collide, false otherwise
     */
    public boolean checkCollisionHelper(float p1x, float p1y, float p2x, float p2y, int episilon){
        double distanceP1ToBall = sqrt(Math.pow(p1x - (double)(this.x+this.x_velocity), 2) + Math.pow(p1y - (double)(this.y+this.y_velocity), 2));
        double distanceP2ToBall = sqrt(Math.pow(p2x - (double)(this.x+this.x_velocity), 2) + Math.pow(p2y - (double)(this.y+this.y_velocity), 2));
        double edgeDistance = sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
        return (distanceP1ToBall + distanceP2ToBall < edgeDistance + radius+episilon);
    }

    /**
     * check if ball collide with a line, the difference between this one and checkCollisionHelper() is that we need to
     * consider about the thickness of line
     * @param p1x x of one endpoint
     * @param p1y y of one endpoint
     * @param p2x x of another endpoint
     * @param p2y y of another endpoint
     * @return return true if collide, false otherwise
     */
    public boolean checkLineCollisionHelper(float p1x, float p1y, float p2x, float p2y){
        return this.checkCollisionHelper(p1x, p1y, p2x, p2y,20);
    }

    /**
     * calculate the normal vector of given line which composed of point 1 and 2
     * @param p1x point 1 x
     * @param p1y point 1 y
     * @param p2x point 2 x
     * @param p2y point 2 y
     * @return The normal vector of the line closer to the ball
     */
    public float[] normalVectorHelper(float p1x, float p1y, float p2x, float p2y){
        float dx = p2x - p1x;
        float dy = p2y - p1y;

        float [] N1 = {-dy, dx};
        float [] N2 = {dy, -dx};

        double magN1 = sqrt(N1[0] * N1[0] + N1[1] * N1[1]);
        float[] unitN1 = {(float)(N1[0] / magN1), (float)(N1[1] / magN1)};

        double magN2 = sqrt(N2[0] * N2[0] + N2[1] * N2[1]);
        float[] unitN2 = {(float)(N2[0] / magN2), (float)(N2[1] / magN2)};

        // calculate mid point of p1 p2
        float midX = (p1x + p2x) / 2;
        float midY = (p1y + p2y) / 2;

        float P1_prime_x = midX + unitN1[0];
        float P1_prime_y = midY + unitN1[1];
        float P2_prime_x = midX + unitN2[0];
        float P2_prime_y = midY + unitN2[1];


        double distance1 = sqrt(Math.pow(this.x - P1_prime_x, 2) + Math.pow(this.y - P1_prime_y, 2));
        double distance2 = sqrt(Math.pow(this.x - P2_prime_x, 2) + Math.pow(this.y - P2_prime_y, 2));

        if (distance1 < distance2) {
            return unitN1;
        } else {
            return unitN2;
        }
    }

    /**
     * update the ball's coordinate with its velocity
     */
    public void ballMove(){
        this.x = this.x + this.x_velocity;
        this.y = this.y + this.y_velocity;
    }

}
