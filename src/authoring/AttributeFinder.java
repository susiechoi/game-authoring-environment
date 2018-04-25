/**
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring;

import java.lang.reflect.Field;

import authoring.frontend.exceptions.ObjectNotFoundException;

public class AttributeFinder {

	/**
	 * Retrieves value of requested field from specified object
	 * @param fieldName - name of the field whose info is desired
	 * @param objectWithFields - object of interest
	 * @return value held within the field
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object retrieveFieldValue(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException{
		Object fieldValue = null; 
		for (Field aField : objectWithFields.getClass().getDeclaredFields()) {
			String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
			if (fieldSimpleString.equals(fieldName)) {
				aField.setAccessible(true);
				fieldValue = aField.get(objectWithFields);
				return fieldValue; 
			}
		}
		throw new ObjectNotFoundException(fieldName);
	}

	public void setFieldValue(String fieldName, Object objectWithFields, Object fieldValue) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		for (Field aField : objectWithFields.getClass().getDeclaredFields()) {
			String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
			if (fieldSimpleString.equals(fieldName)) {
				aField.setAccessible(true);
				aField.set(objectWithFields, fieldValue);
				return; 
			}
		}
		throw new ObjectNotFoundException(fieldName);
	}

}

