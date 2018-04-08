package engine.builders;

import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;

/**
 * @author Katherine Van Dyk 4/7/18
 * @author Ben Hodgson 3/29/18
 * 
 * Uses builder design pattern to construct a Tower object
 */
public class TowerBuilder {

    /**
     * Creates a Tower Object based on tower properties
     * 
     * @param image: Image Tower will use
     * @param size: size of Image
     * @param value: Cost to buy Tower
     * @param health: Health of Tower
     * @param healthValue: Upgrade increment of Health
     * @param healthCost: Cost to upgrade Health
     * @return Tower object
     */
    public Tower construct(Image image, double size, double value, double health, double healthValue, double healthCost, Launcher launcher) {
	ValueProperty valueProperty = new ValueProperty(value);
	HealthProperty healthProperty = new HealthProperty(healthCost, healthValue, health);
	return new Tower(image, size, launcher, healthProperty, valueProperty);
    }
}
