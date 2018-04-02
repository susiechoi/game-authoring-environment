package engine.managers;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Uses a composite design pattern to manage all active objects. Used by towers,
 * enemies and projectiles
 * 
 * @author Katherine Van Dyk
 *
 * @param <E>: The object being managed
 */
public abstract class Manager<E> {
    
    List<E> objects;
    
    /**
     * Returns observable list of all active objects
     * 
     * @return
     */
    public ObservableList<E> getList(){
	return null;
    }

}
