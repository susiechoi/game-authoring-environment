package engine.managers;

import java.util.ArrayList;
import java.util.List;


/**
 * Uses a composite design pattern to manage all active objects. Used by towers,
 * enemies and projectiles
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 * @author Miles Todzo
 */
public class Manager<E> {

    private List<E> active;
    private List<E> available;

    public Manager() {
	active = new ArrayList<>();
	available = new ArrayList<>();
    }

    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public List<E> getListOfAvailable(){
	return  available;
    }
    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public List<E> getListOfActive(){
	return active;
    }

    public void addToActiveList(E sprite) {
	active.add(sprite);
    }

    public void addToAvailableList(E sprite) {
	available.add(sprite);
    }
    
    public void removeFromActiveList(E sprite) {
    	active.remove(sprite);
    }

    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedSprites
     */
    //    public void checkForCollisions(List<Sprite> passedSprites) {
    //    	for (Sprite activeSprite: this.getObservableListOfActive()) {
    //    		for (Sprite passedActor: passedSprites) {
    //    			ShootingSprites shootingSprite = (ShootingSprites) passedActor;
    //    			activeSprite.checkForCollision(shootingSprite, shootingSprite.getProjectiles());
    //    		}
    //    	}
    //    }
}