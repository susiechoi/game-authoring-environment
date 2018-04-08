package engine.sprites.properties;

public class ValueProperty extends Property {

    private double myValue;

    /**
     * Returns a value of the sprite (how much it sells for)
     * 
     * @param cost
     * @param upgradeValue
     * @param propertyValue
     */
    public ValueProperty(double propertyValue) {
	myValue = propertyValue;
    }

    /**
     * Updates an object's value in response to an upgrade 
     * (assumption: value is incremented by property's cost)
     * 
     * @param features
     */
    public void updateValue(UpgradeProperty property) {
	myValue += property.getCost();
    }

    /**
     * Returns an object's value
     */
    @Override
    public double getProperty() {
	return myValue;
    }
}
