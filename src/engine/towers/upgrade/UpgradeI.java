package engine.towers.upgrade;

public interface UpgradeI {
    
    /**
     * Upgrades Tower's health
     */
    public double upgradeGeneral();
    
    /**
     * Upgrades Tower's health
     */
    public double upgradeHealth();
    
    /**
     * Upgrades Tower's attack rate
     */
    public double upgradeAttackRate();
    
    /**
     * Upgrades Tower's damage
     */
    public double upgradeDamage(Double balance);
    
    public double upgradeCost();
    
    public double upgradeHealthCost();
    
    public boolean upgradeAttackRateCost();
    
    public boolean upgradeDamageCost();
    
}
