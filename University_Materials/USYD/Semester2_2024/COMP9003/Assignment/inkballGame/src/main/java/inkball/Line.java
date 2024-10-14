package inkball;

import java.util.ArrayList;

public class Line {
    // record all points for current Line, we need to use it to draw line on App.draw()
    public ArrayList<float[]> pointsArray;
    public int pointsNum;

    public Line(){
        this.pointsArray = new ArrayList<float[]>();
        this.pointsNum=1;
    }

    public void addPoint(float mousex, float mousey){
        float[] coordinator = {mousex,mousey};
        this.pointsArray.add(coordinator);
        this.pointsNum++;
    }

    public boolean validLine(){
        return this.pointsNum >=2;
    }

    public boolean isPointOnLineSegment(float px, float py, float x1, float y1, float x2, float y2) {
        double epsilon = 0.01;

        if (Math.abs(x1 - x2) < epsilon) {
            return Math.abs(px - x1) < epsilon && py >= Math.min(y1, y2) && py <= Math.max(y1, y2);
        }

        if (Math.abs(y1 - y2) < epsilon) {
            return Math.abs(py - y1) < epsilon && px >= Math.min(x1, x2) && px <= Math.max(x1, x2);
        }

        float slope1 = (py - y1) / (px - x1);
        float slope2 = (y2 - y1) / (x2 - x1);

        return Math.abs(slope1 - slope2) < epsilon && px >= Math.min(x1, x2) && px <= Math.max(x1, x2);
    }
}
