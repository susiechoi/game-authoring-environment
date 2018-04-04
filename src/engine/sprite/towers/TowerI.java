package engine.sprite.towers;

import engine.sprites.enemies.EnemyI;

/**
 * 
 * @author Ben Hodgson 3/28/18
 * @author Katherine Van Dyk
 *
 * Interface for Tower objects in the game. 
 */

public interface TowerI {
    
    /**
     * Changes the tower's health
     * 
     * @param amount: the amount to change the tower's health
     */
    public void changeHealth(double health);

    /**
     * Causes damage to tower
     * 
     * @param myEnemy: Enemy that hit the tower
     */
    public void getHitBy(EnemyI myEnemy);

    /**
     * Sells the Tower
     * 
     * @return: The amount of money that the user gains by selling the tower
     */
    public double sell();

    /**
     * Upgrades all aspects of the Tower
     * 
     * @param: User's current balance
     * @return: The user's new balance
     */
    public double upgradeGeneral(double balance);

    /**
     * Upgrades the health of the tower
     * 
     * @param: User's current balance
     * @return: The user's new balance
     */
    public double upgradeHealth(double balance);

    /**
     * Upgrades rate of fire of the tower
     * 
     * @param: User's current balance
     * @return: The user's new balance
     */
    public double upgradeRateOfFire(double balance);

    /**
     * Upgrades the damage the tower does
     * 
     * @param: User's current balance
     * @return: The user's new balance
     */
    public double upgradeDamage(double balance);

}
