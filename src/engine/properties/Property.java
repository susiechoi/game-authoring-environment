package engine.properties;

public abstract class Property implements PropertyI {
    
    protected double upgradeCost;
    protected double upgradeValue;
    
    @Override
    public abstract double upgrade(double balance);
    
    protected boolean canUpgrade(double balance) {
	return (balance - upgradeCost) < 0;
    }

}
