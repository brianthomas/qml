/**
 * 
 */
package net.datamodel.qml;

import java.util.List;

/** Interface for any object which will hold 'values' that are parsable
 * in or out of the object by a @link{net.datamodel.qml.Locator}. 
 * 
 * @author thomas
 *
 */
public interface ObjectWithValues {
	
	/**
	 * Create a locator for this object which will be used to
	 * parse values in and out of the object.
	 * 
	 * @return Locator
	 */
	public Locator createLocator();
	
	/** Get a list of locators belonging to the object.
	 * 
	 * @return  List of Locator objects belonging to the object.
	 */
	public List<Locator> getLocators();
	
	/** The ultimate number of values that <i>may</i> be held within the 
	 *  object. The capacity is always equal to or greater than 
	 *  the size of the object.
	 *  
	 * @return int capacity 
	 */
	public int getCapacity();
	
	/** Get the number of values which are held within the object.
	 * 
	 * @return  Integer value of number of values contained within the quantity.
	 */
	public int getNumberOfValues();
	
	/** Set the capacity of this object to hold values. This value
     * is the allocated size of the object to hold values, not 
     * necessarily the attual number of values held within the 
     * container. 
     * 
     * @param new_capacity 
     * @throws IllegalAccessException if called on mapping-based values container.
     */
    // TODO: I'm conflicted about whether or not this is a good idea...
    public void setCapacity ( int new_capacity )
    throws IllegalAccessException;
    
    
	/** Simply get the first value in the object.
	 * 
	 * @return
	 */
	public Object getValue();
	
	/**
	 * Get the value at the specified location. 
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @return Object value at requested location.
	 */
	public Object getValue (Locator loc) throws IllegalArgumentException, NullPointerException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj Byte value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (Byte obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj Double value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (Double obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj Float value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (Float obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj Integer value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (Integer obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj Short value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (Short obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

	/**
	 * Set the value at the specified location.
	 * For some objects (such as @link{net.datamodel.qml.core.AbstractQuantity}) only one location will exist.
	 * 
	 * @param obj String value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param loc Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based quantities.
	 * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	public void setValue (String obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;
	
}
