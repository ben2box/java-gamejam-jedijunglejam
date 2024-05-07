package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Position extends Grid {

    Rectangle rectangle;

    public Position(int columns, int rows, int width, int height) {
        super(columns, rows);
        rectangle = new Rectangle(colToX(columns)-width, rowToY(rows), width, height);
//        rectangle.draw();
    }

    @Override
    public String toString() {
        return this.columns + " " + this.rows;
    }
}
