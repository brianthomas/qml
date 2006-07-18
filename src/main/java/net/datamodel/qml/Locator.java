
// CVS $Id$

// Locator.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
 * Interface Locator. Problem: need to do something sensible for locator when we have Atomic/ListQuantities.  In other words.. NO AxisFrame exists then.
 */

public interface Locator {

    // Operations

    // preferred constructor.
//    public Locator ( QuantityWithValues parent);

    /**
     * @return  AxisFrame
     * @uml.property  name="currentAxisFrame"
     * @uml.associationEnd  
     */
    public AxisFrame getCurrentAxisFrame ( );
        
    /**
     * Set the current AxisFrame. If the frame is "null" then list-ordered iteration will be used by the locator. This method does nothing for non-matrix locators. 
     * @param frame  to set. It may be "null".
     * @uml.property  name="currentAxisFrame"
     */
    public void setCurrentAxisFrame ( AxisFrame frame);
        
    /**
     * @return  Quantity
     * @uml.property  name="parentQuantity"
     * @uml.associationEnd  
     */
    public QuantityWithValues getParentQuantity ( );
        
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

    /** reset the locator back to the origin */
    public void reset();
}

