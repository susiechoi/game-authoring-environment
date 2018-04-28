/**
 * @author Katherine Van Dyk
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.level.Level;
import engine.sprites.enemies.Enemy;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.towers.Tower;
import src.voogasalad.util.reflection.*;

/**
 * 
 * @author katherinevandyk
 *
 */
public class PropertyFactory {
    
    private Map<String, Property> currentProperties;
    protected ResourceBundle PROPERTIES = ResourceBundle.getBundle("authoring/resources/properties");
    private final String PACKAGE = "engine.sprites.properties.";
    
    public PropertyFactory() {
	currentProperties = new HashMap<String, Property>();
    }
    
    public void setProperty(Level currentLevel, String objectType, String objectName, String propertyName, List<Object> attributes) throws ObjectNotFoundException {
	if (objectType.equals("Enemy")) {
	    if (currentLevel.containsEnemy(objectName)) {
		Enemy enemy = currentLevel.getEnemy(objectName);
		enemy.addProperty(getProperty(objectName, propertyName, attributes));
	    }
	}
	else if (objectType.equals("Tower")) {
	    if (currentLevel.containsTower(objectName)) {
		Tower tower = currentLevel.getTower(objectName);
		tower.addProperty(getProperty(objectName, propertyName, attributes));
	    }
	}
	else if (objectType.equals("Projectile")) {
	    if (currentLevel.containsTower(objectName)) {
		Tower tower = currentLevel.getTower(objectName);
		tower.addProjectileProperty(getProperty(objectName, propertyName, attributes));
	    }
	}
	else if (objectType.equals("Launcher")) {
	    if (currentLevel.containsTower(objectName)) {
		Tower tower = currentLevel.getTower(objectName);
		tower.addLauncherProperty(getProperty(objectName, propertyName, attributes));
	    }
	}
    }
    
    private Property getProperty(String objectName, String propertyName, List<Object> attributes) {
	Property ret;
	String className = PACKAGE + propertyName;
	String type = null;
	for(String key : PROPERTIES.keySet()) {
	    if(propertyName.equals(key)) {
		type = (String) PROPERTIES.getObject(key);
	    }
	}
	if(type == null) {
	    return null;
	}
	else if(type.equals("UpgradeProperty")) {
	    ret = createUpgradeProperty(className, type, attributes);
	}
	else if(type.equals("Property")) {
	    ret = createProperty(className, type, attributes.get(0));
	}
	else {
	    return null;
	}
	currentProperties.put(objectName, ret);
	return ret;
    }
    
    private Property createUpgradeProperty(String className, String type, List<Object> attributes) {
	double cost = ((Double)attributes.get(0)).doubleValue();
	double value =  ((Double)attributes.get(1)).doubleValue();
	double property = ((Double)attributes.get(2)).doubleValue();
	Reflection reflection = new Reflection();
	return (UpgradeProperty) reflection.createInstance(className, cost, value, property );
    }

    private Property createProperty(String className, String type, Object attribute) {
	Reflection reflection = new Reflection();
	return (Property) reflection.createInstance(className, (double) attribute);
    }
    
    public List<Object> retrieveProperty(String objectName, String propertyName) {
	for(String object : currentProperties.keySet()) {
	    if(object.equals(objectName)) {
		return currentProperties.get(object).getAttributes();
	    }
	}
	return null;
    }
   
}

