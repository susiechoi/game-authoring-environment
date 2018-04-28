package api;

import java.util.Collection;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface for Data encapsulation
 */
public interface Data {
    
    /**
     * Sets the data necessary to define an object
     * 
     * @param params: the data parameters 
     */
    public void data(Collection<?> params);
    
    /**
     * Creates an object from data 
     * 
     * @param name: the object name
     */
    public void create(String name);

}
