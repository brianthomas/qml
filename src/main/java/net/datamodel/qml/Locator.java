
//CVS $Id$

//Locator.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

//code generation timestamp: Tue Apr 20 2004-14:22:31 

package net.datamodel.qml;

/**
 * Interface Locator. An iterator for values held in 
 * @link{net.datamodel.qml.ObjectsWithValues}.
 */
public interface Locator {

	/** Find the parent object to which the locator belongs.
	 * 
	 * @return  ObjectWithQuantities
	 */
	public ObjectWithValues getParent ( );

	/** Change the location pointer to the next location. 
	 */
	public void next ( );

	/** Change the location pointer to the prior location. 
	 */
	public void prev ( );

	/** Determine if there are any more locations remaining.
	 */
	public boolean hasNext ( );

	/** Determine if there are any prior locations to the current one.
	 */
	public boolean hasPrevious ( );

	/** Get the list index. All values are held (or are idealized as being
	 *  held) in 1-dimensional lists internally by data containers. This
	 *  returns the index of where the locator is currently pointed in the
	 *  internal list.
	 *  @return int value of index.
	 */
	public int getListIndex();

	/** Set the list index. All values are held (or are idealized as being
	 *  held) in 1-dimensional lists internally by data containers. This
	 *  returns the index of where the locator is currently pointed in the
	 *  internal list.
	 *
	 * @throws IllegalArgumentException when a value which is negative or greater than the list size (quantity.getSize()-1) is passed.
	 */
	public void setListIndex(int index) throws IllegalArgumentException;

	/** Reset the locator back to the first value.
	 *
	 */ 
	public void reset();
}

