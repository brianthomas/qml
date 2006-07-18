
// CVS $Id$

// ValueContainer.java Copyright (c) 2004 Brian Thomas. All rights reserved.

/* LICENSE

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

*/

/* AUTHOR

   Brian Thomas  (baba-luu@earthlink.net)
   

*/

// code generation timestamp: Tue Apr 20 2004-14:22:31 



package net.datamodel.qml;

import java.util.List;

/**
 * The inteface for all value containers used by quantities. Note that "value" here means number or string data. Generic objects are not  permissible, and Quantity containers are used hold quantity objects.
 */
public interface ValueContainer extends XMLSerializableObject {

    // Operations

    /**
     * Create a locator for this class.
     */
    public Locator createLocator ( );
    
   /**
     * Get the value at the specified location. For atomic quantities  only one location will exist.
     * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @return Object value at requested location.
     */
    public Object getValue (Locator loc) throws IllegalArgumentException, NullPointerException; 
        
    /**
     * Set the value (a Byte) at the specified location.
     * @param obj Byte value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Byte obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Set the value (a Double ) at the specified location.
     * @param obj Double value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Double obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Set the value (a Float) at the specified location.
     * @param obj Float value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Float obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Set the value (an Integer) at the specified location.
     * @param obj Integer value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Integer obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Set the value (a Short) at the specified location.
     * @param obj Short value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Short obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Set the value (a String) at the specified location.
     * @param obj String value to set. Value cannot be "null" (use an noDataValue instance instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (String obj, Locator loc) 
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Get the first value of the quantity. This is a utility method for getValue("Location origin").
     * @return  Object at the first location.
     * @uml.property  name="firstValue"
     */
    public Object getFirstValue();

   /**
 * Utility method to set the value at the first location.
 * @param obj  Object value to set. It may NOT be null (use a noDataValue instead).
 * @throws NullPointerException  when null parameters are passed.
 * @throws IllegalAccessException  when called for mapping-based containers.
 * @throws SetDataException  when quantity object is passed.
 * @uml.property  name="firstValue"
 */
    public void setFirstValue (Object obj) throws IllegalAccessException, NullPointerException, SetDataException;

    /*
     * Get a list of all values held by the container.
     * @return List of values held by this quantity
     */
    /**
     * @uml.property  name="valueList"
     */
    public List getValueList();
    
    /** Get the number of values (datum) held by the data container.
     *  @return int value of the number of values held.
     */
    public int getNumberOfValues ( );

    /** Set the number of values within the container. This will
     * cause previously unallocated locations to be set to "noDataValue".
     */
    public void setNumberOfValues ( int size);

    /** Get the capacity of the container to hold values.
     *  Values may or may not exist at each location. 
     *  If every location contains a value, then the container is "full" and 
     *  getCapacity() returns the same number as getNumberOfValues().
     *  @return int value of the number of values held.
     */
    public int getCapacity( );

    /** 
     * Set whether or not to output value(s) as CDATASection(s).
     */
    public void setCDATASerialization (boolean value);

    /** 
     * Determine whether or not value(s) are output as CDATASection(s).
     */
    public boolean getCDATASerialization ( );

    /** Whether to serialize values as tagged or space-delimited. 
     */
    public void setTaggedValuesSerialization (boolean value);

    /** Determine whether values are serialized as tagged or space-delimited. 
     */
    public boolean getTaggedValuesSerialization ( );
    
    /** Determine the maximum utilized (internal) index of the container.
     * 
     * @return int
     */
    public int getMaxUtilizedIndex();
    
}

