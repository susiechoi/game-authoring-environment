package engine.managers;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.Sprite;
import engine.sprites.towers.Tower;
import javafx.collections.ObservableList;

/**
 * Uses a composite design pattern to manage all active objects. Used by towers,
 * enemies and projectiles
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 * @author Miles Todzo
 * @param <E>: The object being managed
 */
public class Manager {
    
    private List<Sprite> active;
    private List<Sprite> available;
    
    public Manager() {
    		active = new ArrayList<>();
    		available = new ArrayList<>();
    }
    
    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public ObservableList<Sprite> getObservableListOfAvailable(){
    		return (ObservableList<Sprite>) available;
    }
    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public ObservableList<Sprite> getObservableListOfActive(){
    		return (ObservableList<Sprite>) active;
    }

    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedActors
     */
    public void checkForCollisions(List<Sprite> passedActors) {
    		for (Sprite activeActor: active) {
    			for (Sprite passedActor: passedActors) {
    				activeActor.checkForCollision(passedActor);
    			}
    		}
    }
}