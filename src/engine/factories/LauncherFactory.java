package engine.factories;

import java.util.Collection;
import java.util.Map;

import engine.sprites.towers.launcher.Launcher;

/**
 * Factory class for constructing a launcher.
 * 
 * @author Katherine Van Dyk
 *
 */
public class LauncherFactory extends Factory {
    
    private ProjectileFactory projectileFactory;
    
    /**
     * Constructor that takes in parameter data for a launcher
     * 
     * @param data: Map containing launcher type as key, and appropriate parameter data as value
     */
    public LauncherFactory(Map<String, Collection<Object>> data) {
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
