package sound;

import javafx.scene.control.Button;

/**
 * This class represents and place-able, click-able button used to play the background music
 * When pressed, it will play background music from the instance of SoundFactory it was created from
 * @author benauriemma
 *
 */
public class PlayBackgroundMusicButton extends Button {

    private SoundFactory mySoundFactory;

    /**
     * This constructor is called from SoundFactory.createPlayBackgroundMusicButton()
     * @param soundFactory is the soundFactory whose background music is played by this button
     */
    protected PlayBackgroundMusicButton(SoundFactory soundFactory) {
	super();
	mySoundFactory = soundFactory;
	this.setOnAction(e -> playBackgroundMusic());
    }
    
    /**
     * Makes a direct call to its SoundFactory's playBackgroundMusic() method
     */
    private void playBackgroundMusic() {
	mySoundFactory.playBackgroundMusic();
    }
    
}
