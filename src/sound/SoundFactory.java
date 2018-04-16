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
     * 
     * @param soundName is the name of the sound, which should map to a file path in a properties file at /sound/resources/mp3Files.properties
     * @throws FileNotFoundException if soundName is not found in mp3Files.properties
     */
    public void playSound(String soundName) throws FileNotFoundException;
    
    /**
     * This method can be used to set the volume of sounds being played currently and in the future
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
