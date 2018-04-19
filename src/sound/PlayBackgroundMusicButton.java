package sound;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * This class represents and place-able, click-able button used to play the background music
 * When pressed, it will call the playBackgroundMusic() method in the instance of SoundFactory it was created from
 * @author benauriemma
 *
 */
public class PlayBackgroundMusicButton extends Button {

    private SoundFactory mySoundFactory;

    /**
     * This method is called from SoundFactory.createPlayBackgroundMusicButton()
     * @param soundFactory is the soundFactory whose background music is played by this button
     */
    protected PlayBackgroundMusicButton(SoundFactory soundFactory) {
	super();
	mySoundFactory = soundFactory;
	this.setOnAction(e -> playBackgroundMusic());
    }
    
    /*
     * I don't think this should ever be used
    public PlayBackgroundMusicButton(SoundFactory soundFactory, String musicName) throws FileNotFoundException {
	this(soundFactory);
	mySoundFactory.setBackgroundMusic(musicName);
    }
    */
    
    /**
     * Makes a direct call to its SoundFactory's playBackgroundMusic() method
     */
    private void playBackgroundMusic() {
	mySoundFactory.playBackgroundMusic();
    }
    
}
