package engine.factories;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract factory class that is implemented by other factories to make
 * towers, enemies, and tower objects (launcher/projectiles)
 * 
 * @author Katherine Van Dyk
 * @date 4/1/18
 *
 */
public abstract class Factory {
    
    Map<String, Collection<Object>> types;
    
    /**
     * Imports parsed XML data, which will eventually be used to construct 
     * game objects.
     * 
     * @param data: Map of object type (XML value) to object parameters
     */
    public Factory(Map<String, Collection<Object>> data) {
	types = data;
    }
    
}
