package engine.sprites.properties;

/**
 * Damage Property represents how much damage an object can exert on another
 * 
 * @author Katherine Van Dyk
 *
 */
public class DamageProperty extends UpgradeProperty {
	
	private double myDamage; 
	
	public DamageProperty(double cost, double value, double damage) {
	    	super(cost, value, damage);
	    	myDamage = damage; 
	}

	@Override
	public Property makeCopy() {
	    // TODO Auto-generated method stub
	    return null;
	}
}
