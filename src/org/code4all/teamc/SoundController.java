package org.code4all.teamc;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundController {

    public Clip playMainTheme() {
        try {
            Clip audioClip = getAudioClip("theme_music.wav");
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            return audioClip;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public Clip playTrampled() {
        try {
            Clip audioClip = getAudioClip("trampled.wav");
            audioClip.loop(0);
            return audioClip;
        } catch (Exception ex) {
            System.out.println("NOT");
        }

        return null;
    }

    public Clip playJump() {
        try {
            Clip audioClip = getAudioClip("jump.wav");
            audioClip.loop(0);
            return audioClip;
        } catch (Exception ex) {
            System.out.println("NOT");
        }

        return null;
    }

    public Clip playGameOver() {
        try {
            Clip audioClip = getAudioClip("gameover_clip.wav");
            audioClip.loop(0);
            return audioClip;
        } catch (Exception ex) {
            System.out.println("NOT");
        }

        return null;
    }

    public void stopMusic(Clip clip) {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }


    private Clip getAudioClip(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = getClass().getResource("/sounds/" + name);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
        return clip;
    }
}
