package engine.builders;

import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;

/**
 * @author Katherine Van Dyk 4/7/18
 * @author Ben Hodgson 4/8/18
 * 
 * Uses builder design pattern to construct a Tower object
 */
public class TowerBuilder {

    /**
     * Creates a Tower Object based on tower properties
     * 
     * @param image: Image Tower will use
     * @param size: size of Image
     * @param health: Health of Tower
     * @param healthValue: Upgrade increment of Health
     * @param healthCost: Cost to upgrade Health
     * @return Tower object
     */
    public Tower construct(String name, Image image, double size, double health, double healthValue, double healthCost, Launcher launcher, double towerValue, double towerUpgradeCost, double towerUpgradeValue) {
	ValueProperty valueProperty = new ValueProperty(towerValue);
    	HealthProperty healthProperty = new HealthProperty(healthCost, healthValue, health);
	return new Tower(name, image, size, launcher, healthProperty, valueProperty);
    }
}
