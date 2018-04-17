package sound;

import java.io.FileNotFoundException;

public class ITRTSoundFactory implements SoundFactory {

    @Override
    public void playSound(String soundName) throws FileNotFoundException {
	// TODO Auto-generated method stub

    }

    @Override
    public void setVolume(Integer percentVolume) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mute() {
	// TODO Auto-generated method stub

    }

    @Override
    public PlaySoundButton createPlaySoundButton(String soundName) throws FileNotFoundException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VolumeSlider createVolumeSlider() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MuteButton createMuteButton() {
	// TODO Auto-generated method stub
	return null;
    }

}
