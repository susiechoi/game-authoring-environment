/**
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesMap {

    protected final ResourceBundle PROPERTIES = ResourceBundle.getBundle("authoring/resources/properties");
    private Map<Object, Map<String, Object>> properties;

    public PropertiesMap() {
	properties = new HashMap<Object, Map<String, Object>>();
    }

    /**
     * Retrieves value of requested field from specified object
     * @param fieldName - name of the field whose info is desired
     * @param objectWithFields - object of interest
     * @return value held within the field
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public Object retrieveFieldValue(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, NullPointerException {
	if(properties.get(objectWithFields).get(fieldName) == null) {
	    return properties.get(objectWithFields).get(fieldName);
	}
	else{ 
	    throw new NullPointerException();
	}
    }

    public void setFieldValue(String fieldName, Object objectWithFields, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
//	System.out.println("Field name: " + fieldName);
//	System.out.println("Field value: " + fieldValue);
//	System.out.println("Object with fields: " + objectWithFields);
	addProperty(fieldName, objectWithFields, fieldValue);
    }

    private void addProperty(String field, Object objectWithFields, Object fieldValue) {
	if(properties.get(objectWithFields) == null) {
	    properties.put(objectWithFields, new HashMap<String, Object>());
	}
	properties.get(objectWithFields).put(field, fieldValue);
    }

}

