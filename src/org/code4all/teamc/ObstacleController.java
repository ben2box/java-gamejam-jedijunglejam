package org.code4all.teamc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ObstacleController {

    private List<Obstacle> obstacles = new ArrayList<>();

    private final ObstacleFactory factory;
    private final CollisionDetection collider;

    public ObstacleController(Grid grid, CollisionDetection collider) {
        factory = new ObstacleFactory(grid);
        this.collider = collider;
    }

    public boolean moveObstaclesAndCheckCollisions() {
        boolean collided = false;

        List<Obstacle> toDelete = new LinkedList<>();

        for (Obstacle obs : obstacles){
            // move obstacle
            obs.move();

            // mark for deletion
            if (obs.isOffscreen) {
                toDelete.add(obs);
                continue; // no need to check for collision if offscreen
            }

            // check for collision
            if (!collided) {
                collided = collider.collisionCheck(obs);
            }
        }
    
        // delete obstacles off-screen
        for (Obstacle obs : toDelete) {
            obstacles.remove(obs);
//            obs.delete();
        }

        return collided;
    }

    public void spawnObstacles() {
        // TODO implement better logic for deciding spawn
        if (obstacles.size() >= 4) return;

        Obstacle newObstacle = factory.createObstacle();
        obstacles.add(newObstacle);
    }

    public void reset() {
        for (Obstacle obs : obstacles){
            obs.delete();
        }
        obstacles = new ArrayList<>();
    }
}