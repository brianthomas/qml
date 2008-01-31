
// CVS $Id$

// Quantity.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

/**
 * The interface for all Quantities which hold "values" as data. 
 * By values we mean Strings and numbers, and not other quanities.
 */
public interface Quantity 
extends Component, XMLSerializableObjectWithValues, Cloneable 
{
	
	/** The namespace URI of this package.
	   */
	public static final String namespaceURI = "http://archive.astro.umd.edu/ont/Quantity.owl#";
	
	public static final String ClassURI = namespaceURI+ "Quantity";
	
	/** Determine if this object is similar to the comparison object.
	 *  Similarity means that all child-objects, fields of the two quantities 
	 *  are the same with the exception of the values held by both Quantities,
	 *  which may be the same or not (must have the same number of values however).
	 */
//	public boolean isSimilar (Quantity q);
	
	/** Get the number of values which are held within the object.
	 * 
	 * @return Integer value of number of values contained within the quantity.
	 */
	public Integer getSize();
	
	
	/** Quantities can be cloned. 
	 * 
	 * @return
	 */
	// This is needed to make referenced ids within a document work right.
	public Object clone() throws CloneNotSupportedException;
	
	/** Return the container in this object which holds the values.
	 * 
	 * @return
	 */
	public ValueContainer getValueContainer();
	
}
