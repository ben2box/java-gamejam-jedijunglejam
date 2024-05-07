package org.code4all.teamc;

public class ObstacleFactory {
    private final Grid grid;

    private int previousAnimal = 0;

    public ObstacleFactory(Grid grid){
        this.grid = grid;
    }

    public Obstacle createObstacle() {
        int rando = previousAnimal;
        while (rando == previousAnimal) {
            rando = (int) Math.floor(Math.random()*6);
        }
        previousAnimal = rando;
        switch (rando) {
            default:
            case 0:
                return new Obstacle(grid, 30, 12,"/animals/hippo.png", 10 , Grid.cellSize*2, Grid.cellSize*2); //Hippo 2x2
            case 1:
                return new Obstacle(grid, 30, 11,"/animals/elephant.png", 5, Grid.cellSize*2, Grid.cellSize*3); //Elephant 3x3
            case 2:
                return new Obstacle(grid, 30, (int) (Math.ceil(Math.random()*10)+2),"/animals/bird_1x1.png",8, Grid.cellSize,Grid.cellSize); // LowBird 1x1
            case 3:
                return new Obstacle(grid, 30, 2,"/animals/hippo_inv.png",10,Grid.cellSize*2,Grid.cellSize*2); // Bird 2x2
            case 4:
                return new Obstacle(grid, 30, 4,"/animals/flying_pig.png",4,Grid.cellSize*2,Grid.cellSize*2); // FlyingPig 2x2
            case 5:
                return new Obstacle(grid, 30, 12,"/animals/bear.png",7,Grid.cellSize*2,Grid.cellSize*2); //Bear 3*2
//            case 6:
//                return new Obstacle(grid, 30, 2,"/animals/bear.png",7,Grid.cellSize*3,Grid.cellSize*2); //Bear 3*2
        }
    }
}
