package engine.factories;

import java.util.Collection;
import java.util.Map;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Projectile factory returns creates a projectile of a specific type (each tower can hold
 * multiple types of projectiles)
 * 
 * @author Katherine Van Dyk
 *
 */
public class ProjectileFactory extends Factory {

    /**
     * ProjectileFactory constructor that takes in a map of a projectile type and a collection
     * of projectile parameters
     * 
     * @param data
     */
    public ProjectileFactory(Map<String, Collection<Object>> data) {
	super(data);
    }
    
    /**
     * 
     * Returns a projectile object of type @param type
     * 
     * @param type: String denoting projectile type
     * @return Projectile object
     */
    public Projectile construct(String type) {
	return null;
    }

}
