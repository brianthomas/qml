
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
 * The inteface for all value containers used by quantities. Note that "value" here means number or string data. Generic objects are not  permissible, and ObjectWithQuantities containers are used hold quantity objects.
 */
public interface ValueContainer 
extends XMLSerializableObjectWithValues
{
	
	/**
	 * Get the first value of the quantity. This is a utility method for getValue("Location origin").
	 * @return  Object at the first location.
	 * @uml.property  name="firstValue"
	 */
	public Object getFirstValue();

	/**
	 * Utility method to set the value at the first location.
	 * 
	 * @param obj  Object value to set. It may NOT be null (use a noDataValue instead).
	 * @throws NullPointerException  when null parameters are passed.
	 * @throws IllegalAccessException  when called for mapping-based containers.
	 * @throws SetDataException  when quantity object is passed.
	 * @uml.property  name="firstValue"
	 */
	public void setFirstValue (Object obj) throws IllegalAccessException, NullPointerException, SetDataException;

	/**
	 * Get a list of all values held by the container.
	 * 
	 * @return List of object values held by the container.
	 */
	public List<Object> getValues();

	/** Get the number of values (datum) held by the data container.
	 *  @return int value of the number of values held.
	 */
	public int getNumberOfValues ( );

	/** Set the number of values within the container. This will
	 * cause previously unallocated locations to be set to "noDataValue".
	 */
	public void setNumberOfValues ( int size);

	/** Determine the maximum utilized (internal) index of the container.
	 * 
	 * @return int
	 */
	public int getMaxUtilizedIndex();
	
}

