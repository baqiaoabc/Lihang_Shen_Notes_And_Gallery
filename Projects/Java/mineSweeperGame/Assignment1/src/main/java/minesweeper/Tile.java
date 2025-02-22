package minesweeper;

public class Tile {

    protected boolean hidden = false;
    protected boolean isMine;
    protected boolean isHidden;
    protected boolean checked; // represent empty tile is checked after revealed in 1st loop
    protected boolean isFlagged;
    protected boolean isEmpty;
    protected int nearbyMines;

    protected int stage; // explosion stage
    protected boolean beginDraw; // begin mine's animation

    protected int x;
    protected int y;

    protected static int size;

    public Tile(int x, int y, boolean isMine, boolean isEmpty, int nearbyMines) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
        // TODO: Remember change back to true
        this.isHidden = true;
        this.isFlagged = false;
        this.isEmpty = isEmpty;
        this.nearbyMines = nearbyMines;
        this.checked = false;
        this.stage= 0; // from 0 to 9
        this.beginDraw=false;
    }
    
}
