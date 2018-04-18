package sound;

import java.io.FileNotFoundException;

/**
 * This serves as the API for our Sound Factory utility
 * Currently, it proves basic functionality such as playing mp3 files, setting volume, and generating basic buttons
 * @author benauriemma
 *
 */
public interface SoundFactory {
    
    /**
     * This method can be called to play a specified sound once
     * The sound will be played to completion and cannot be paused
     * The volume of the sound effect will be the volume that the SoundFactory was set to at the time of this method call
     * 
     * @param soundName is the name of the sound, which should map to a file path in a properties file at /sound/resources/mp3Files.properties
     * @throws FileNotFoundException if soundName is not found in mp3Files.properties
     */
    public void playSoundEffect(String soundEffectName) throws FileNotFoundException;
    
    /**
     * This method can be called to set a song as background music
     * The background song of SoundFactory is affected by the following methods:
     * playBackgroundMusic()
     * pauseBackgroundMusic()
     * muteBackgroundMusic()
     * setVolume()
     * 
     * @param musicName is the name of the song, which should map to a file path in a properties file at /sound/resources/mp3Files.properties
     * @throws FileNotFoundException if musicName is not found in mp3Files.properties
     */
    public void setBackgroundMusic(String musicName) throws FileNotFoundException;
    
    /**
     * This method can be called to play the song set as background music
     */
    public void playBackgroundMusic();
    
    /**
     * This method can be called to pause the song set as background music
     */
    public void pauseBackgroundMusic();
    
    /**
     * This method can be used to set the volume of background music being played currently and all sounds played in the future
     * Note, this only affects sounds played through this instance of SoundFactory.  As stated in documentation, it is recommended to create only one instance of SoundFactory
     * @param volume is an integer between 0 and 100. 0 is muted, and 100 is full volume
     */
    public void setVolume(Integer percentVolume);
    
    /**
     * This method sets volume to 0
     * Note, this only affects sounds played through this instance of SoundFactory.  As stated in documentation, it is recommended to create only one instance of SoundFactory
     */
    public void mute();
    
    /**
     * This method is used to generate a button which plays a specific sound when clicked
     * @param soundName is the sound to be played when this button is pressed. This can be changed later with a method call on PlaySoundButton
     * @return the PlaySoundButton which can be added to a screen and clicked
     */
    public PlaySoundButton createPlaySoundButton(String soundName) throws FileNotFoundException;
    
    /**
     * This method is used to create a volume slider which can be used to set the volume of all sounds played from this SoundFactory
     * @return the volume slider which can be added to the screen and interacted with
     */
    public VolumeSlider createVolumeSlider();
    
    /**
     * This method is used to create a button which, when clicked, mutes all sounds played from this instance of SoundFactory
     * @return the mute button which can be added to a screen and clicked
     */
    public MuteButton createMuteButton();

}
