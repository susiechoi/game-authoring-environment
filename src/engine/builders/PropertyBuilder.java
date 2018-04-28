package engine.builders;

import java.util.ResourceBundle;

import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
import voogasalad.util.reflection.Reflection;

public class PropertyBuilder {

//   protected ResourceBundle PROPERTIES = ResourceBundle.getBundle("authoring/resources/properties");
    private String PACKAGE = "engine.sprites.properties.";
    private String PROPERTIES = "authoring/resources/properties";
    //private ResourceBundle bundle;

    public Property getProperty(Property p) {
	Property ret;
	System.out.println(p);
	System.out.println(p.getName());
	String propertyName = p.getName();
	String className = PACKAGE + propertyName;
	String type = null;
	for(String key : bundle().keySet()) {
	    if(propertyName.equals(key)) {
		type = (String) bundle().getObject(key);
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
    
    private ResourceBundle bundle() {
	return ResourceBundle.getBundle("authoring/resources/properties");
    }


}
