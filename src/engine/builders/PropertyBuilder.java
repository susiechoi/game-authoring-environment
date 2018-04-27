package engine.builders;

import java.util.ResourceBundle;

import authoring.Reflection;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;

public class PropertyBuilder {

    protected final ResourceBundle PROPERTIES = ResourceBundle.getBundle("authoring/resources/properties");
    private final String PACKAGE = "engine.sprites.properties.";

    public Property getProperty(Property p) {
	Property ret;
	String propertyName = p.getName();
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
	    ret = createUpgradeProperty(className, type, p);
	}
	else if(type.equals("Property")) {
	    ret = createProperty(className, type, p);
	}
	else {
	    return null;
	}
	return ret;
    }
    
    private Property createUpgradeProperty(String className, String type, Property p) {
	Reflection reflection = new Reflection();
	return (UpgradeProperty) reflection.createInstance(className, p );
    }

    private Property createProperty(String className, String type, Property p) {
	Reflection reflection = new Reflection();
	return (Property) reflection.createInstance(className, p);
    }


}
