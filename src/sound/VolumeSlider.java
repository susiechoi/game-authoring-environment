package sound;

import javafx.scene.control.Slider;

public class VolumeSlider extends Slider {

    private static final Integer MIN_VOLUME = 0;
    private static final Integer MAX_VOLUME = 100;
    private static final Integer DEFAULT_VOLUME = 110;
    
    private SoundFactory mySoundFactory;

    public VolumeSlider(SoundFactory soundFactory) {
	super(MIN_VOLUME, MAX_VOLUME, DEFAULT_VOLUME);
	mySoundFactory = soundFactory;
	this.valueProperty().addListener(e -> setVolume());
    }
    
    private void setVolume() {
	mySoundFactory.setVolume((int) this.getValue());
    }
    
}
