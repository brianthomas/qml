
// CVS $Id$

// ListQuantity.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.io.IOException;
import java.io.Writer;

import java.util.List;

/** The interface for all Quantities.
 */
// hrmm. there doesnt seem to be a need for an "AtomicQuantity"
// interface, so we just extend "QuantityWithValues" instead.
public interface ListQuantity extends QuantityWithValues 
{

    // Operations

    /** Set the capacity of this quantity to hold values.
     * @param new_capacity
     * @throws IllegalAccessException if called on mapping-based quantity.
     */
    public void setCapacity ( int new_capacity ) throws IllegalAccessException; 

    /**
     * Utility method. Append a Byte value onto the current list.
     * @param obj Byte value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Byte obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException;

    /**
     * Utility method. Append a Double value onto the current list.
     * @param obj Double value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Double obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException; 

    /**
     * Utility method. Append a Float value onto the current list.
     * @param obj Float value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Float obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException;

    /**
     * Utility method. Append an Integer value onto the current list.
     * @param obj Integer value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Integer obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException; 

    /**
     * Utility method. Append a Short value onto the current list.
     * @param obj Short value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Short obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException;

    /**
     * Utility method. Append a String value onto the current list.
     * @param obj String value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( String obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException;

}
