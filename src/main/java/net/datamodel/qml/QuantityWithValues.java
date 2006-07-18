
// CVS $Id$

// QuantityWithValues.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
 * The interface for all Quantities which hold "values" as data. By values we mean Strings and numbers, and not other quanities. Note: It would nice if this interface didnt have to exist, e.g. we could have CompositeQuantity have "get/setValue" methods which allow adding other Q's. Will look into it...
 */
public interface QuantityWithValues extends Quantity, Component {

    // Operations

    /**
     * Get the size of the quantity.
     * @return  Integer value of number of values contained within the quantity.
     * @uml.property  name="size"
     */
    public Integer getSize ( );

    /** The ultimate number of values that may be held within the quantity. 
     * The capacity is always equal to or greater than the size of the quantity. 
     */
    public int getCapacity();

    /**
     * Get the container within the Q which is "holding" (or creating..as for a map)  the actual values.
     * @uml.property  name="valueContainer"
     * @uml.associationEnd  
     */
    public ValueContainer getValueContainer();

    /**
     * Get the value at the specified location. For atomic quantities only one location will exist.
     * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @return Object value at requested location.
     */
    public Object getValue (Locator loc) throws IllegalArgumentException, NullPointerException;

    /**
     * Set the value at the specified location.
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
     * @param obj String value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (String obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException;

    /**
     * Create a locator for this quantity.
     * @return Locator
     */
    public Locator createLocator ( );

    /**
     * Get a list of locators belonging to this quantity.
     * @return  List of Locator objects belonging to the quantity.
     * @uml.property  name="locatorList"
     */
    public List getLocatorList();
    
    /** Determine if this object is similar to the comparison object.
     *  Similarity means that all child-objects, fields of the two quantities 
     *  are the same with the exception of the values held by both Quantities,
     *  which may be the same or not (must have the same number of values however).
     */
//    public boolean isSimilar (QuantityWithValues q);
    
}
