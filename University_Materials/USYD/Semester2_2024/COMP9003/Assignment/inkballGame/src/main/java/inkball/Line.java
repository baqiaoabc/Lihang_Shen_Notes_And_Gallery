package inkball;

import java.util.ArrayList;

/**
 * The Line class represents a geometric line consisting of multiple points.
 * It allows adding points, validating if the line is valid, and checking if a point
 * lies on a line segment between two points.
 */
public class Line {
    /**
     * record all points for current Line, we need to use it to draw line on App.draw()
      */
    public ArrayList<float[]> pointsArray;

    /**
     * the number of points that line has
     */
    public int pointsNum;

    /**
     * Constructor of Line
     */
    public Line(){
        this.pointsArray = new ArrayList<float[]>();
        this.pointsNum=1;
    }

    /**
     * Adds a new point to the line based on the given x and y coordinates.
     *
     * @param mousex The x-coordinate of the mouse (i.e. new point).
     * @param mousey The y-coordinate of the mouse (i.e. new point).
     */
    public void addPoint(float mousex, float mousey){
        float[] coordinator = {mousex,mousey};
        this.pointsArray.add(coordinator);
        this.pointsNum++;
    }

    /**
     * if the line is valid, then it has at least two points.
     *
     * @return true if the line has two or more points, false otherwise.
     */
    public boolean validLine(){
        return this.pointsNum >=2;
    }

    /**
     * Checks if a given point (px, py) lies on the line segment between points (x1, y1) and (x2, y2).
     * The method handles vertical, horizontal, and diagonal lines with an epsilon (0.01)
     * tolerance for floating-point comparisons.
     *
     * @param px The x-coordinate of the point to check.
     * @param py The y-coordinate of the point to check.
     * @param x1 The x-coordinate of the first end point of the line segment.
     * @param y1 The y-coordinate of the first end point of the line segment.
     * @param x2 The x-coordinate of the second end point of the line segment.
     * @param y2 The y-coordinate of the second end point of the line segment.
     * @return true if the point (px, py) lies on the line segment, false otherwise.
     */
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
