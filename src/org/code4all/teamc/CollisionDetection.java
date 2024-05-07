package org.code4all.teamc;

public class CollisionDetection {

    private final Player player;

    public CollisionDetection(Player player) {
        this.player = player;
    }

    public boolean collisionCheck(Obstacle obs) {

        if (obs.getX() < (player.getX() + player.getWidth()) && (obs.getX() + obs.getWidth()) > player.getX()){
            return obs.getY() < player.getY() + player.getHeight() && obs.getY() + obs.getHeight() > player.getY();
        }
        
        return false;
    }
}
