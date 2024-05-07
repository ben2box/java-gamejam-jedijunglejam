package org.code4all.teamc;

public class Player extends DrawableItem {

    // CONSTS 

    private static final int MOVE_PER_FRAME_JUMP = 15;
    private static final int MOVE_PER_FRAME_LAND = 5;
    private static final int START_POS = 12 * Grid.cellSize;
    private static final int JUMP_HEIGHT = 4 * Grid.cellSize;
    private static final int CROUCH_HEIGHT = Grid.cellSize;
    private static final int GRAV_DISTANCE = Grid.cellSize * 10;


    /**
     * ========== PROPERTIES ============
     */

    private boolean isGravityInverted = false;

    private PlayerStatus status = PlayerStatus.IDLE;

    private int targetPosition = START_POS;
    private int currentPosition = START_POS;

    private long lastAnimationTime = System.nanoTime();


    /**
     * ========== CONSTRUCTOR ============
     */

    public Player(Grid grid, int cols, int rows) {
        super(grid, cols, rows, Grid.cellSize, Grid.cellSize*2, "/Nozk_idle_x2.png");
        reset();
    }



    /**
     * ========== METHODS ============
     */

    public void update() {
        if (status == PlayerStatus.JUMPING) {
            int gravityInversionMultiplier = isGravityInverted ? 1 : -1;
            int translatedPosition = gravityInversionMultiplier * MOVE_PER_FRAME_JUMP;
            currentPosition = currentPosition + translatedPosition;
            translate(0, translatedPosition);

            if (!isGravityInverted && currentPosition <= targetPosition) {
                status = PlayerStatus.LANDING;
                targetPosition = START_POS;
            } else if (isGravityInverted && currentPosition >= targetPosition) {
                status = PlayerStatus.LANDING;
                targetPosition = START_POS - GRAV_DISTANCE;
            }
        } else if (status == PlayerStatus.LANDING) {
            int gravityInversionMultiplier = isGravityInverted ? -1 : 1;
            int translatedPosition = gravityInversionMultiplier * MOVE_PER_FRAME_LAND;
            currentPosition = currentPosition + translatedPosition;
            translate(0, translatedPosition);

            if ((!isGravityInverted && currentPosition >= targetPosition) || (isGravityInverted && currentPosition <= targetPosition)) {
                status = PlayerStatus.RUNNING;
            }
        } else if (status == PlayerStatus.DEAD && currentPosition != targetPosition) {
            // make sure corpse lands if player dies while jumping (same logic as landing)
            int gravityInversionMultiplier = isGravityInverted ? -1 : 1;
            int translatedPosition = gravityInversionMultiplier * MOVE_PER_FRAME_LAND;
            currentPosition = currentPosition + translatedPosition;
            translate(0, translatedPosition);
        }

        updateSprite();
    }


    public void jump() {
        // can only jump if running
        if (status != PlayerStatus.RUNNING) return;

        status = PlayerStatus.JUMPING;

        if (!isGravityInverted) {
            targetPosition = START_POS - JUMP_HEIGHT;
        } else {
            targetPosition = (START_POS - GRAV_DISTANCE) + JUMP_HEIGHT;
        }

        new SoundController().playJump();
    }

    public void crouch() {
        if (status != PlayerStatus.RUNNING) return;
        // if (!isGravityInverted) {
        //     status = PlayerStatus.CROUCHING;
        //     character.translate(0, CROUCH_HEIGHT);
        //     pos.rectangle.translate(0, CROUCH_HEIGHT*2);
        // } else {
        //     status = PlayerStatus.CROUCHING;
        //     character.translate(0, -CROUCH_HEIGHT);
        //     pos.rectangle.translate(0, -CROUCH_HEIGHT);
        // }

        status = PlayerStatus.CROUCHING;
        int gravityInversionMultiplier = isGravityInverted ? -1 : 1;
        int translatedPosition = gravityInversionMultiplier * CROUCH_HEIGHT;
        translate(0, translatedPosition);
    }

    public void crouchRelease() {
        if (status != PlayerStatus.CROUCHING) return;

        // if (!isGravityInverted) {
        //     status = PlayerStatus.RUNNING;
        //     character.translate(0, -CROUCH_HEIGHT);
        //     pos.rectangle.translate(0, -CROUCH_HEIGHT*2);
        // }

        // status = PlayerStatus.RUNNING;
        // character.translate(0, CROUCH_HEIGHT);
        // pos.rectangle.translate(0, CROUCH_HEIGHT);

        status = PlayerStatus.RUNNING;
        int gravityInversionMultiplier = isGravityInverted ? 1 : -1;
        int translatedPosition = gravityInversionMultiplier * CROUCH_HEIGHT;
        translate(0, translatedPosition);
    }

    public void invertGravity() {
        // only invert if player is running
        if (status != PlayerStatus.RUNNING) return;

        if (isGravityInverted) {
            isGravityInverted = false;
            currentPosition = START_POS;
            translate(0, GRAV_DISTANCE);
        } else {
            isGravityInverted = true;
            currentPosition = START_POS - GRAV_DISTANCE;
            translate(0, -GRAV_DISTANCE);
        }
    }

    private int runCounter = 0;

    public void updateSprite() {
        String isGravityInvertedSuffix = (isGravityInverted ? "_inv" : "");
        
        if (status == PlayerStatus.RUNNING) {
            long currentTime = System.nanoTime();

            // update animation every 100ms
            long msSinceLastAnimation = (currentTime - lastAnimationTime) / 1000000;
            if (msSinceLastAnimation < 100) return;

            lastAnimationTime = currentTime;

            // switch (runCounter) {
            //     case 0:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_1_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_1_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            //     case 1:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_2_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_2_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            //     case 2:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_3_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_3_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            //     case 3:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_4_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_4_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            //     case 4:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_5_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_5_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            //     case 5:
            //         if (!isGravityInverted) {
            //             character.load("Resources/run/Nozk_run_6_x2.png");
            //             runCounter++;
            //         }
            //         if (isGravityInverted){
            //             character.load("Resources/run/Nozk_run_6_x2_inv.png");
            //             runCounter++;
            //         }
            //         break;
            // }

            load("/run/Nozk_run_" + (++runCounter) + "_x2" + isGravityInvertedSuffix + ".png");
            runCounter = runCounter % 6;
            return;
        } else if (status == PlayerStatus.JUMPING) {
            load("/jump/Nozk_jump_2_x2" + isGravityInvertedSuffix + ".png");
        } else if (status == PlayerStatus.LANDING) {
            load("/jump/Nozk_jump_3_x2" + isGravityInvertedSuffix + ".png");
        } else if (status == PlayerStatus.CROUCHING) {
            load("/slide/Nozk_slide_high_x2" + isGravityInvertedSuffix + ".png");
        } else if (status == PlayerStatus.DEAD) {
            load("/dead/Nozk_dead_wider" + isGravityInvertedSuffix + ".png");
        }

        // reset
        runCounter = 0;
    }





    /**
     ========== GETTERS/SETTERS ============
     */


    public boolean isGravityInverted() {
        return isGravityInverted;
    }

    public boolean isCrouched() {
        return status == PlayerStatus.CROUCHING;
    }

    public void startRunning() {
        status = PlayerStatus.RUNNING;
    }

    public void kill() {
        if (status == PlayerStatus.DEAD) return;

        status = PlayerStatus.DEAD;
        
        if (!isGravityInverted) {
            targetPosition = START_POS;
        } else {
            targetPosition = START_POS - GRAV_DISTANCE;
        }

        new SoundController().playTrampled();
    }

    public boolean isDead() {
        return status == PlayerStatus.DEAD;
    }

    public void reset() {
        status = PlayerStatus.IDLE;
        load("/Nozk_idle_x2.png");
        if (currentPosition > START_POS) {
            translate(0, currentPosition - START_POS);
        } else {
            translate(0, START_POS - currentPosition);
        }
        currentPosition = START_POS;
        targetPosition = START_POS;
        isGravityInverted = false;
    }
}