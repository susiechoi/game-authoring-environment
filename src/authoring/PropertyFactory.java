/**
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring;

import java.util.List;
import java.util.ResourceBundle;

import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;

/**
 * 
 * @author katherinevandyk
 *
 */
public class PropertyFactory {

    protected final ResourceBundle PROPERTIES = ResourceBundle.getBundle("authoring/resources/properties");

    public Property getProperty(String propertyName, List<Object> attributes) {
	//TODO : if upgrade property
	String className = propertyName + "Property";
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
	    return createUpgradeProperty(className, type, attributes);
	}
	else if(type.equals("Property")) {
	    return createProperty(className, type, attributes.get(0));
	}
	else return null;
    }
    
    private Property createUpgradeProperty(String className, String type, List<Object> attributes) {
	double cost = (double) attributes.get(0);
	double value =  (double) attributes.get(1);
	double property = (double) attributes.get(2);
	Reflection reflection = new Reflection();
	return (UpgradeProperty) reflection.createInstance(className, cost, value, property );
    }

    private Property createProperty(String className, String type, Object attribute) {
	Reflection reflection = new Reflection();
	return (UpgradeProperty) reflection.createInstance(className, (double) attribute);
    }

}

