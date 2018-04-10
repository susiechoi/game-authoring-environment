package engine.factories;

import java.util.Collection;
import engine.sprites.Sprite;
import engine.sprites.towers.launcher.Launcher;

/**
 * Factory class for constructing a launcher.
 * 
 * @author Katherine Van Dyk
 *
 */
public class LauncherFactory extends Factory {
    
    /**
     * Constructor that takes in parameter data for a launcher
     * 
     * @param data: Map containing launcher type as key, and appropriate parameter data as value
     */
    public LauncherFactory(Collection<Sprite> data) {
	super(data);
    }
    
    /**
     * Constructs and returns a launcher object of the appropriate type
     * 
     * @return Launcher object of type string
     */
    public Launcher construct(String type) {
	return null;
    }

    

}
