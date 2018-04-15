package engine.sprites.properties;

/**
 * A property describes any attribute held by an object.
 * 
 * @author Katherine Van Dyk
 *
 */
public abstract class Property {

    private double myProperty;
    private String mySimpleName;
    
    /**
     * Constructor for property object
     * 
     * @param property: initial value of property
     */
    public Property(double property) {
	myProperty = property;
	mySimpleName = this.getClass().getSimpleName();
    }
   
    /**
     * Returns current value of property
     */
    public double getProperty() {
	return myProperty;
    }
    
    /**
     * Returns current value of property
     */
    protected void setProperty(double newValue) {
	myProperty = newValue;
    }
    
    public String getName() {
    	return mySimpleName;
    }
}
