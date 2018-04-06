package engine.sprites.properties;

public class DamageProperty extends Property {

	private double myDamage;

	public DamageProperty(double cost, double value, double damage) {
	    	super(cost, value);
		myDamage = damage;
	}

	@Override
	public double upgrade(double balance) {
		if(balance - upgradeCost < 0) {
			return balance;
		}
		else {
			myDamage += upgradeValue;
			return balance - upgradeCost;
		}
	}

	@Override
	public double getProperty() {
		return myDamage;
	}	


}
