package org.code4all.teamc;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Controls implements KeyboardHandler {

    private final Player character;
    private final Game game;

    public Controls(Player character, Game game){
        this.character = character;
        this.game = game;
        init();
    }

    private void init(){

        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent pressedUp = new KeyboardEvent();
        pressedUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pressedUp.setKey(KeyboardEvent.KEY_UP);
        keyboard.addEventListener(pressedUp);

        KeyboardEvent releaseUp = new KeyboardEvent();
        releaseUp.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseUp.setKey(KeyboardEvent.KEY_UP);
        keyboard.addEventListener(releaseUp);

        KeyboardEvent pressedDown = new KeyboardEvent();
        pressedDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pressedDown.setKey(KeyboardEvent.KEY_DOWN);
        keyboard.addEventListener(pressedDown);

        KeyboardEvent releaseDown = new KeyboardEvent();
        releaseDown.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseDown.setKey(KeyboardEvent.KEY_DOWN);
        keyboard.addEventListener(releaseDown);

        KeyboardEvent pressedSpace = new KeyboardEvent();
        pressedSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pressedSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboard.addEventListener(pressedSpace);

        KeyboardEvent releaseSpace = new KeyboardEvent();
        releaseSpace.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboard.addEventListener(releaseSpace);

        KeyboardEvent pressedG = new KeyboardEvent();
        pressedG.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        pressedG.setKey(KeyboardEvent.KEY_G);
        keyboard.addEventListener(pressedG);

        KeyboardEvent releaseG = new KeyboardEvent();
        releaseG.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseG.setKey(KeyboardEvent.KEY_G);
        keyboard.addEventListener(releaseG);

        KeyboardEvent releaseEsc = new KeyboardEvent();
        releaseEsc.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseEsc.setKey(KeyboardEvent.KEY_Q);
        keyboard.addEventListener(releaseEsc);

        KeyboardEvent releaseDeath = new KeyboardEvent();
        releaseDeath.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        releaseDeath.setKey(KeyboardEvent.KEY_K);
        keyboard.addEventListener(releaseDeath);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()){
            case KeyboardEvent.KEY_DOWN:
                if (!character.isGravityInverted() && !character.isCrouched()) {
                    character.crouch();

                    break;
                }
                break;

            case KeyboardEvent.KEY_UP:
                if (character.isGravityInverted() && !character.isCrouched()) {
                    character.crouch();

                    break;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()){
            case KeyboardEvent.KEY_DOWN:
                if (!character.isGravityInverted()) {
                    character.crouchRelease();
                    break;
                }
                break;

            case KeyboardEvent.KEY_UP:
                if (character.isGravityInverted()) {
                    character.crouchRelease();
                    break;
                }
                break;

            case KeyboardEvent.KEY_G:
                character.invertGravity();
                break;

            case KeyboardEvent.KEY_SPACE:
                if (game.gameStarted) {
                    character.jump();
                } else {
                    game.restart();
                }
                break;

            case KeyboardEvent.KEY_Q:
                game.exit();
                break;

            case KeyboardEvent.KEY_K:
                character.kill();
                break;
        }

    }
}