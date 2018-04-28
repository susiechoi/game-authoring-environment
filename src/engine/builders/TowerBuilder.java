package engine.builders;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;

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
    public Tower construct(String name, String imagepath, double size, double health, double healthValue, double healthCost, Launcher launcher, double towerValue, double towerUpgradeCost, double towerUpgradeValue) {
    	System.out.println("IN BUILDER");
    	System.out.println(name);
    	System.out.println(imagepath);
	List<Property> properties = new ArrayList<Property>();
    	properties.add(new ValueProperty(towerValue));
    	properties.add(new HealthProperty(healthCost, healthValue, health));
	return new Tower(name, imagepath, size, launcher, properties);
    }
}
