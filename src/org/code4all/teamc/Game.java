package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import javax.sound.sampled.Clip;

public class Game {

    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private Grid grid;
    private Player character;
    private ObstacleController obstacleController;
    private CollisionDetection collider;
    private ScoreKeeper scoreKeeper;

    private SoundController soundController;
    private Clip activeAudioClip;

    private Picture startGamePicture;
    private Picture gameOverPicture;

    public boolean gameStarted = false;
    private boolean isRunning = false;

    public void init() throws InterruptedException {
        grid = new Grid(32, 16);
        grid.init();

        soundController = new SoundController();

        mainInit();

        scoreKeeper = new ScoreKeeper(grid);

        // show start screen splash
        startGamePicture = drawPictureInCenter("/start_game.png");
        
        run();
    }

    public void mainInit() {
        character = new Player(grid, 4, 12);
        collider = new CollisionDetection(character);
        obstacleController = new ObstacleController(grid, collider);
        new Controls(character, this);
    }

    public void update() {
        // update player
        character.update();

        // spawn obstacles if needed
        obstacleController.spawnObstacles();


        // update obstacles position and detect collisions
        if (obstacleController.moveObstaclesAndCheckCollisions()) {
            // kill player upon collision detected 
            character.kill();
            return;
        }

        // update score
        scoreKeeper.increaseScore(0.1);
    }


    public void run() {
        // wait for game start event
        while (!gameStarted) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        activeAudioClip = soundController.playMainTheme();

        isRunning = true;

        character.startRunning();

        long now;
        long updateTime;
        long wait;

        int framesPostDeath = 0;

        while (isRunning) {
            now = System.nanoTime();

            // update game state
            update();

            // if player is dead, wait a few frames to show game over screen so animations can finish
            if (character.isDead()) {
                framesPostDeath++;
                if (framesPostDeath > 120) { // 120 frames = 2 seg
                    isRunning = false;
                    break;
                }
            }

            // calculate how long we need to sleep to hit the target FPS
            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Negative timeout, frame skipped!");
            }
        }

        gameOver();
    }

    public void restart() {
        if (gameOverPicture != null) {
            gameOverPicture.delete();
            gameOverPicture = null;
        }

        if (startGamePicture != null) {
            startGamePicture.delete();
            startGamePicture = null;
        }
        
        obstacleController.reset();
        character.delete();
        mainInit();

        scoreKeeper.reset();

        gameStarted = true;
    }

    public void gameOver() {
        soundController.stopMusic(activeAudioClip);

        gameStarted = false;
        isRunning = false;
        gameOverPicture = drawPictureInCenter("/game_over.png");
        new SoundController().playGameOver();
        run();
    }

    private Picture drawPictureInCenter(String image) {
        int screenCenterX = (grid.width + grid.PADDING) / 2;
        int screenCenterY = (grid.height + grid.PADDING) / 2;

        Picture pic = new Picture(screenCenterX, screenCenterY, image);

        int width = pic.getWidth();
        int height = pic.getHeight();

        // make sure picture is centered
        pic.translate(-(width/2), -(height/2));

        pic.draw();

        return pic;
    }

    public void exit() {
        isRunning = false;
        System.exit(0);
    }
}
