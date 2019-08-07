
import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSound {

    String file;
    Clip clip;

    public GameSound(String s) {
        file = s;
    }

    public void playSound() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(file)));
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void playTrack(){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(file)));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void stopTrack(){
        clip.stop();
    }

}
