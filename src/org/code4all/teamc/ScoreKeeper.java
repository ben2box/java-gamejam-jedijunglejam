package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;

public class ScoreKeeper {

    private double score = 0;
    private final Text visual;

    public ScoreKeeper(Grid grid) {
        visual = new Text(80, 40, "Distance: " + getRoundedScore() + "m");
        visual.grow(30, 10);
        visual.setColor(Color.BLACK);
        visual.draw();
    }

    public void setScore(double score) {
        this.score = score;
        visual.setText("Distance: " + getRoundedScore() + "m");
    }

    public void increaseScore(double increase) {
        setScore(score + increase);
    }

    public void reset() {
        setScore(0);
    }

    private double getRoundedScore() {
        return (double) Math.round(score * 100) / 100;
    }
}
