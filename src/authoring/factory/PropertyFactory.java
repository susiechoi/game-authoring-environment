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

import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.level.Level;
import engine.sprites.enemies.Enemy;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.towers.Tower;
import frontend.PropertiesReader;
import voogasalad.util.reflection.*;

/**
 * 
 * @author katherinevandyk
 *
 */
public class PropertyFactory {
    
	public static final String DEFAULT_PROPERTIES_FILES_PATH = "default_objects/Properties/properties.properties";
	private Map<String, Property> currentProperties;
    public static final String PACKAGE = "engine.sprites.properties.";

    
    public PropertyFactory() {
	currentProperties = new HashMap<String, Property>();
    }
    
    public void setProperty(Level currentLevel, String objectType, String objectName, String propertyName, List<Double> attributes) throws ObjectNotFoundException, MissingPropertiesException {
//	System.out.println("SETTING PROPERTY");
//	System.out.println(currentLevel);
//	System.out.println(objectType);
//	System.out.println(objectName);
//	System.out.println(propertyName);
	for (Double d : attributes) System.out.println(d);
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
    
    private Property getProperty(String objectName, String propertyName, List<Double> attributes) throws MissingPropertiesException {
	Property ret;
	String className = PACKAGE + propertyName;
	String type = new PropertiesReader().findKey(DEFAULT_PROPERTIES_FILES_PATH, propertyName);
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
    
    private Property createUpgradeProperty(String className, String type, List<Double> attributes) {
	double cost = ((Double)attributes.get(0)).doubleValue();
	double value =  ((Double)attributes.get(1)).doubleValue();
	double property = ((Double)attributes.get(2)).doubleValue();
//	System.out.println("Class name: " + className + " property: " + property);
	return (UpgradeProperty) Reflection.createInstance(className, cost, value, property );
    }

    private Property createProperty(String className, String type, Object attribute) {
	return (Property) Reflection.createInstance(className, (double) attribute);
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
