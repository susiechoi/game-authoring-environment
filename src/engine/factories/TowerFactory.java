package engine.factories;

import java.util.Collection;
import java.util.Map;

import engine.sprites.towers.Tower;

/**
 * Tower Factory that creates Tower object from parsed parameter values
 *
 * @author katherinevandyk
 *
 */
public class TowerFactory extends Factory {
    
    private LauncherFactory launcherFactory;
    
    /**
     * Constructor that takes in Tower parameter data for all types of towers.
     * 
     * @param data: Map of Tower type to its specific collection of parameter data
     */
    public TowerFactory(Map<String, Collection<Object>> data) {
	super(data);
    }
    
    /**
     * Constructs and returns a Tower object with type @param type
     * 
     * @param type: Type of tower to be returned
     * @return Tower object containing specific launcher and projectile
     */
    public Tower construct(String type) {
	return null;
    }
    
    
}
