package engine.sprites.properties;

/**
 * This class will handle the movement of a projectile that is heat seeking 
 * (Follows the enemy it is shot at)
 * @author ryanpond
 *
 */
public class HeatSeekingProperty extends MovingProperty<Object>{

    public HeatSeekingProperty(double range) {
	super(range);
    }
    
    public HeatSeekingProperty(Property p) {
	super(p.getProperty());
    }

    @Override
    public Object execute(Object... args) {
	return null;
    }
    
    
    
    

}
