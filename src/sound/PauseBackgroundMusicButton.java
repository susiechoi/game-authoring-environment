package sound;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * This class represents and place-able, click-able button used to pause the background music
 * When pressed, it will call the pauseBackgroundMusic() method in the instance of SoundFactory it was created from
 * @author benauriemma
 *
 */
public class PauseBackgroundMusicButton extends Button {

    private SoundFactory mySoundFactory;

    /**
     * This method is called from SoundFactory.createPauseBackgroundMusicButton()
     * @param soundFactory is the soundFactory whose background music is paused by this button
     */
    protected PauseBackgroundMusicButton(SoundFactory soundFactory) {
	super();
	mySoundFactory = soundFactory;
	this.setOnAction(e -> pauseBackgroundMusic());
    }
    
    /**
     * Makes a direct call to its SoundFactory's pauseBackgroundMusic() method
     */
    private void pauseBackgroundMusic() {
	mySoundFactory.pauseBackgroundMusic();
    }
    
}
