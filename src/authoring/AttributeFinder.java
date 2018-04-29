/**
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring;

import java.lang.reflect.Field;

import authoring.frontend.exceptions.ObjectNotFoundException;

public class AttributeFinder {
	
	public static final String DEFAULT_CLASSPATH_SEPARATOR = ".";
	public static final int DEFAULT_CLASSPATH_INDEX_OFFSET = 1; 

	/**
	 * Retrieves value of requested field from specified object
	 * @param fieldName - name of the field whose info is desired
	 * @param objectWithFields - object of interest
	 * @return value held within the field
	 * @throws ObjectNotFoundException 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object retrieveFieldValue(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException{
		Object fieldValue = null; 
		fieldValue = loopThroughFields(fieldName, objectWithFields.getClass(), objectWithFields);
		if (fieldValue != null) {
			return fieldValue;
		}
		fieldValue = retrieveFieldValueSuper(fieldName, objectWithFields); 
		if (fieldValue != null) {
			return fieldValue; 
		}
		throw new ObjectNotFoundException(fieldName);
	} 
	
	private Object retrieveFieldValueSuper(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		Object fieldValue = null; 
		while (objectWithFields.getClass().getSuperclass() != null) {
			fieldValue = loopThroughFields(fieldName, objectWithFields.getClass().getSuperclass(), objectWithFields);
			if (fieldValue != null) {
				return fieldValue; 
			}
		}
		throw new ObjectNotFoundException(fieldName);
	}
	
	private Object loopThroughFields(String fieldName, Class<?> objectClass, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException {
		Object fieldValue = null; 
		Field foundField = getField(fieldName, objectClass); 
		foundField.setAccessible(true);
		fieldValue = foundField.get(objectWithFields);
		return fieldValue;
	}

	private Field getField(String fieldName, Class<?> objectClass) {
		for (Field aField : objectClass.getDeclaredFields()) {
			String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(DEFAULT_CLASSPATH_SEPARATOR)+DEFAULT_CLASSPATH_INDEX_OFFSET); 
			if (fieldSimpleString.equals(fieldName)) {
				return aField; 
			}
		}
		return null; 
	}
	
	public void setFieldValue(String fieldName, Object objectWithFields, Object fieldValue) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		Field foundField = getField(fieldName, objectWithFields.getClass());
		if (foundField == null) {
			while (objectWithFields.getClass().getSuperclass() != null) {
				foundField = getField(fieldName, objectWithFields.getClass().getSuperclass());
				if (foundField != null) {
					break; 
				}
			}
		}
		foundField.setAccessible(true);
		foundField.set(objectWithFields, fieldValue);
		return; 
	}
	
}

