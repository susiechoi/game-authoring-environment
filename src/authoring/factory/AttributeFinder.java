/**
 * @author susiechoi
 * Object that returns the value of a field with a specified name from an object
 * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
 */

package authoring.factory;

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


	///**
	// * @author susiechoi
	// * Object that returns the value of a field with a specified name from an object
	// * Useful in populating sliders, dropdowns, and textboxes with user input that is currently held in objects
	// */
	//
	//package authoring.factory;
	//
	//import java.lang.reflect.Field;
	//
	//import authoring.frontend.exceptions.ObjectNotFoundException;
	//
	//public class AttributeFinder {
	//
	//	public static final String DEFAULT_CLASSPATH_SEPARATOR = ".";
	//	public static final int DEFAULT_CLASSPATH_INDEX_OFFSET = 1; 
	//
	//	/**
	//	 * Retrieves value of requested field from specified object
	//	 * @param fieldName - name of the field whose info is desired
	//	 * @param objectWithFields - object of interest
	//	 * @return value held within the field
	//	 * @throws ObjectNotFoundException 
	//	 * @throws IllegalArgumentException
	//	 * @throws IllegalAccessException
	//	 */
	//	public Object retrieveFieldValue(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException{
	//		System.out.println("CALLED");
	//		Object fieldValue = null; 
	//		fieldValue = loopThroughFields(fieldName, objectWithFields.getClass(), objectWithFields);
	//		if (fieldValue != null) {
	//			return fieldValue;
	//		}
	//		fieldValue = retrieveFieldValueSuper(fieldName, objectWithFields); 
	//		if (fieldValue != null) {
	//			return fieldValue; 
	//		}
	//		throw new ObjectNotFoundException(fieldName);
	//	} 
	//
	private Object retrieveFieldValueSuper(String fieldName, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		Object fieldValue = null; 
		while (objectWithFields.getClass().getSuperclass() != null) {
			for (Field aField : objectWithFields.getClass().getSuperclass().getDeclaredFields()) {
				String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
				if (fieldSimpleString.equals(fieldName)) {
					aField.setAccessible(true);
					fieldValue = aField.get(objectWithFields);
					return fieldValue; 
				}
			}
		}
		throw new ObjectNotFoundException(fieldName);
	}
	//
	//	private Object loopThroughFields(String fieldName, Class<?> objectClass, Object objectWithFields) throws IllegalArgumentException, IllegalAccessException {
	//		System.out.println("CALLED2");
	//
	//		Object fieldValue = null; 
	//		Field foundField = getField(fieldName, objectClass); 
	//		if (foundField != null) {
	//			foundField.setAccessible(true);
	//			fieldValue = foundField.get(objectWithFields);
	//		}
	//		return fieldValue;
	//	}
	//
	//	private Field getField(String fieldName, Class<?> objectClass) {
	//		System.out.println("CALLED3");
	//
	//		for (Field aField : objectClass.getDeclaredFields()) {
	//			String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(DEFAULT_CLASSPATH_SEPARATOR)+DEFAULT_CLASSPATH_INDEX_OFFSET); 
	//			if (fieldSimpleString.equals(fieldName)) {
	//				return aField; 
	//			}
	//		}
	//		return null; 
	//	}
	//
		public void setFieldValueSuper(String fieldName, Object objectWithFields, Object fieldValue) throws IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {	
			while (objectWithFields.getClass().getSuperclass() != null) {
				for (Field aField : objectWithFields.getClass().getSuperclass().getDeclaredFields()) {
					String fieldSimpleString = aField.toString().substring(aField.toString().lastIndexOf(".")+1); 
					if (fieldSimpleString.equals(fieldName)) {
						aField.setAccessible(true);
						aField.set(objectWithFields, fieldValue);
					}
				}
			}
			throw new ObjectNotFoundException(fieldName);
		}
	//
	//}
	//

}