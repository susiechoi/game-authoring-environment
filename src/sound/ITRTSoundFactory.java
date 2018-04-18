package sound;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ITRTSoundFactory implements SoundFactory {
    
    MediaPlayer myMediaPlayer;
    Double myVolume;

    public ITRTSoundFactory() {
	this.myMediaPlayer = null;
	this.myVolume = 1.0;
    }

    @Override
    public void playSoundEffect(String fileName) throws FileNotFoundException { //THIS SHOULD EVENTUALLY BE soundName NOT FILENAME
	File file = new File(fileName);
	Media sound = new Media(file.toURI().toString());
	MediaPlayer soundPlayer = new MediaPlayer(sound);
	soundPlayer.setVolume(myVolume);
	soundPlayer.play();
    }
    
    @Override
    public void setBackgroundMusic(String fileName) throws FileNotFoundException { //THIS SHOULD EVENTUALLY BE soundName NOT FILENAME
	File file = new File(fileName);
	Media sound = new Media(file.toURI().toString());
	this.myMediaPlayer = new MediaPlayer(sound);
    }
    
    @Override
    public void playBackgroundMusic() {
	this.myMediaPlayer.play();
    }

    @Override
    public void pauseBackgroundMusic() {
	this.myMediaPlayer.pause();
    }

    @Override
    public void setVolume(Integer percentVolume) {
	this.myVolume = percentVolume/100.0; //this hard-coded value exists because the parameter is on a percentage scale, 
					//but is used a value between 0 and 1. This value should never be changed
	this.myMediaPlayer.setVolume(myVolume);
    }

    @Override
    public void mute() {
	this.myVolume = 0.0;
	this.myMediaPlayer.setVolume(myVolume);
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
