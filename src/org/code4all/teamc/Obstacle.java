package org.code4all.teamc;

public class Obstacle extends DrawableItem {
    private Grid grid;

    private int speed;

    public boolean isOffscreen = false;

    public Obstacle(Grid grid, int cols, int rows, String animalref, int speed, int width, int height) {
        super(grid, cols, rows, width, height, animalref);

        this.speed = speed;
        this.grid = grid;
    }

    public void move(){
//        if (getX()+getWidth() > grid.PADDING) {
            translate((-1 * speed), 0);
//        }

        if (getX()+getWidth() <= 0) {
            isOffscreen = true;
            delete();
        }
    }
}
