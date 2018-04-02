package engine.towers;

import engine.enemies.EnemyI;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for Tower objects in the game. 
 */

public interface TowerI {
    
    /**
     * Sets the tower's health
     * 
     * @param health: the health to give the tower
     */
    public void setHealth(double health);
    
    /**
     * Changes the tower's health
     * 
     * @param amount: the amount to change the tower's health
     */
    public void changeHealth(double health);
    
    /**
     * Sets the tower's cost
     * 
     * @param cost: the cost for the tower     
     */
    public void setCost(double cost);

    /**
     * Causes damage to tower
     * 
     * @param myEnemy: Enemy that hit the tower
     */
    public void getHitBy(EnemyI myEnemy);

    /**
     * Moves the X and Y coordinates of the tower
     * 
     * @param newX: X coordinate
     * @param newY: Y coordinate
     */
    public void move(int newX, int newY);

    /**
     * Sells the Tower
     * 
     * @return: The amount of money that the user gains by selling the tower
     */
    public Integer sell();

    /**
     * Upgrades all aspects of the Tower
     * 
     * @return: The cost of the upgrade
     */
    public Integer upgradeGeneral();

    /**
     * Upgrades the health of the tower
     * 
     * @return: The cost of upgrade
     */
    public Integer upgradeHealth();

    /**
     * Upgrades rate of fire of the tower
     * 
     * @return: The cost of upgrade
     */
    public Integer upgradeRateOfFire();

    /**
     * Upgrades the damage the tower does
     * 
     * @return: The cost of the upgrade
     */
    public Integer upgradeDamage();

}
