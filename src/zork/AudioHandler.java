package zork;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;

public class AudioHandler {
    public static void playClip(File clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        class AudioPlayer implements Runnable {
            private final File clipFile;
    
            public AudioPlayer(File clipFile) {
                this.clipFile = clipFile;
            }
    
            @Override
            public void run() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                        clip.addLineListener(new LineListener() {
                            @Override
                            public void update(LineEvent event) {
                                if (event.getType() == Type.STOP || event.getType() == Type.CLOSE) {
                                    clip.close();
                                }
                            }
                        });
                    } finally {
                        audioInputStream.close();
                    }
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        }
        AudioPlayer player = new AudioPlayer(clipFile);
        Thread audioThread = new Thread(player);
        audioThread.start();
    }
    public static void loopClip(File clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the audio indefinitely
            clip.start();
        } finally {
            audioInputStream.close();
        }
    }
    public static void closeClip(File clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
        try {
            Clip clip = AudioSystem.getClip();
            clip.close();
            audioInputStream.close();
        } finally {
            audioInputStream.close();
        }
    }
    
}



/*
  public static void main(String[] args) {
    File clipFile = new File("C:\\Users\\kdeslauriers\\Downloads\\africa-toto.wav");
    try {
      playClip(clipFile);
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
*/