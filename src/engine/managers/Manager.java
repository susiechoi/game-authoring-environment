package engine.managers;

import java.util.List;

import engine.sprites.Sprite;
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
public class Manager<Sprite> {
    
    private List<Sprite> active;
    private List<Sprite> available;
    
    public Manager() {
    	
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
    
    public void checkForCollisions(List<Sprite> sprites) {}
}
