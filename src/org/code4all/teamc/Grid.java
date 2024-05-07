package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Grid {

    int PADDING = 10;


    int width;
    int height;
    int columns;
    int rows;

    Rectangle grid;
    Picture background;

    Picture ground;
    Picture clouds;

    static int cellSize = 50;

    public Grid(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        width = colToX(columns);
        height = rowToY(rows);

    }

    public void init(){

        grid = new Rectangle(PADDING, PADDING, width-PADDING, height-PADDING);
        grid.draw();
        grid.setColor(Color.BLACK);
       // grid.fill();

        background = new Picture(PADDING, PADDING, "/bg_1600.png");
        background.draw();

        ground = new Picture(colToX(0), rowToY(13), "/floor_1600x100.png");
        ground.draw();

        clouds = new Picture(colToX(0), rowToY(0), "/clouds_1600x100.png");
        clouds.draw();

    }

    public int rowToY(int rows) {
        return PADDING + (rows * cellSize);
    }

    public int colToX(int cols) {
        return PADDING + (cols * cellSize);
    }

    public int xToCol(int width) {
        return PADDING + (width / cellSize);
    }

    public int yToRows(int height) {
        return  PADDING + (height / cellSize);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}