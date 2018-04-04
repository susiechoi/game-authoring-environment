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
 * @param <E>: The object being managed
 */
public abstract class Manager<E> {
    
    private List<E> objects;
    
    /**
     * Constructor, takes in the original list of E objects
     * @param list: list of objects that are initially active to be managed
     */
    public Manager(List<E> list) {
    		objects = list;
    }
    
    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public ObservableList<E> getList(){
    		return (ObservableList<E>) objects;
    }
    
    public abstract void checkForCollisions(List<Sprite> sprites);
}
