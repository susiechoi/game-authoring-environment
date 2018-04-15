package engine.factories;

import java.util.Collection;

import engine.sprites.Sprite;

/**
 * Abstract factory class that is implemented by other factories to make
 * towers, enemies, and tower objects (launcher/projectiles)
 * 
 * @author Katherine Van Dyk
 * @date 4/1/18
 *
 */
public abstract class Factory {
    
    Collection<Sprite> availableSprites;
    
    /**
     * Imports list of all available Sprites
     * 
     * @param data: Map of object type (XML value) to object parameters
     */
    public Factory(Collection<Sprite> data) {
	availableSprites = data;
    }
    
}
