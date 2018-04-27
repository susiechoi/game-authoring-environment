package sound;

import javafx.scene.control.Button;

/**
 * This class represents and place-able, click-able button
 * When pressed, it will mute the instance of SoundFactory it was created from
 * @author benauriemma
 *
 */
public class MuteButton extends Button {
    SoundFactory mySoundFactory;
    
    /**
     * This constructor is called from SoundFactory.createMuteButton()
     * @param soundFactory is the soundFactory which this button will mute
     */
    protected MuteButton(SoundFactory soundFactory) {
	super();
	mySoundFactory = soundFactory;
	this.setOnAction(e -> mute());
    }
    
    /**
     * Makes a direct call to its SoundFactory's mute() method
     */
    private void mute() {
	mySoundFactory.mute();
    }
    
}
