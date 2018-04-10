package engine.builders;

import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.RangeProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class that uses builder design pattern to construct a launcher
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class LauncherBuilder {
    
    
    public Launcher construct(double fireRate, double fireRateCost, double fireRateValue, double fireRange, double fireRangeCost, double fireRangeValue, Projectile projectile) {
	FireRateProperty rate = new FireRateProperty(fireRateCost, fireRateValue, fireRate);
	RangeProperty range = new RangeProperty(fireRangeCost, fireRangeValue, fireRange);
	return new Launcher(rate, projectile, range);
    }

}
