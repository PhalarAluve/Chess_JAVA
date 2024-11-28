package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Click {
        public void playMusic(String musicLocation) {
            try {
                File musicPath = new File(musicLocation);
                if(musicPath.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }
                else {}
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
}
