package Game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Sounds {
	String filename;
	private Clip clip;
	
	Sounds(String filename){
		this.filename = filename;
		try {
			PlayMusic();
		} catch (Exception e) {}
	}

	private void PlayMusic() throws Exception {
		clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
        clip.setFramePosition(0);
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
            	if (event.getType().equals(LineEvent.Type.STOP))
                {
                    Line soundClip = event.getLine();
                    soundClip.close();
                }
            }

        });
        clip.open(ais);
        clip.start();
        
		
	}
	

}
