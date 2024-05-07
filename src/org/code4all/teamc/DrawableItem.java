package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class DrawableItem {
    protected Position pos;
    protected Picture picture;

    public DrawableItem(Grid grid, int cols, int rows, int width, int height, String image) {
        this.pos = new Position(cols, rows, width, height);
        this.picture = new Picture(grid.colToX(cols)-width, grid.rowToY(rows), image);
        this.picture.draw();
    }

    public int getX() {
        return pos.rectangle.getX();
    }

    public int getY() {
        return pos.rectangle.getY();
    }

    public int getWidth() {
        return pos.rectangle.getWidth();
    }

    public int getHeight() {
        return pos.rectangle.getHeight();
    }

    public void translate(int x, int y) {
        picture.translate(x, y);
        pos.rectangle.translate(x, y);
    }

    public void load(String image) {
        picture.load(image);
    }

    public void delete() {
        pos.rectangle.delete();
        picture.delete();
    }
}
