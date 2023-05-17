package main;
import javax.sound.sampled.*;

import gamestates.GameStates;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private Clip currentMusic = null;
    private Map<GameStates, String> musicFiles = new HashMap<>();

    public MusicManager() {
        musicFiles.put(GameStates.MENU, "/tfc2.wav");
        musicFiles.put(GameStates.PLAYING, "/ddt.wav");
    }
    public void setVolume(float volume) {
        if (currentMusic != null) {
            FloatControl volumeControl = (FloatControl) currentMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            volumeControl.setValue((max - min) * volume + min);
        }
    }
    public void playMusic(GameStates state) {
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }

        String filename = musicFiles.get(state);
        if (filename != null) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(filename));
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                currentMusic = (Clip) AudioSystem.getLine(info);
                currentMusic.open(audioStream);
                currentMusic.start();
                currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}