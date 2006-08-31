
// CVS $Id$

// AtomicLocatorImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.locator;

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;

import org.apache.log4j.Logger;


/**
 * AtomicLocatorImpl is implementation of a locator for Atomic Quantities.
 * As these quantities only ever have ONE location within them, its more or
 * less pointless to use them. This class is provided to allow consistency
 * with the interface of the higher-dimensional quantities.
 * 
 * @version $Revision$
 */
public class AtomicLocatorImpl implements Locator {

	private static final Logger logger = Logger.getLogger(AtomicLocatorImpl.class);
    // Fields

    // Get the value of parentQuantity  
    // @return parentQuantity 
    /**
     * @uml.property  name="parentQuantity"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    protected Quantity parentQuantity;

    // Methods
    //

    // Constructor

    /* not allowed */
    private AtomicLocatorImpl ( ) { } 

    /** Vanilla constructor */
    public AtomicLocatorImpl ( Quantity parent ) 
    throws NullPointerException
    { 
        if (parent == null)
           throw new NullPointerException();

        parentQuantity = parent;
        reset();
    }

    // Accessor Methods

    /**
     * @return AxisFrame null value is always returned.
     */
    public AxisFrame getCurrentAxisFrame ( ) {
        return (AxisFrame) null;
    }

    /**
     * Set the current AxisFrame. If the frame is "null" then list-ordered iteration
     * will be used by the locator. This method does nothing for non-matrix locators. 
     * @param frame to set. It may be "null".
     */
    public void setCurrentAxisFrame ( AxisFrame frame)
    {
       // do nothing
    }

    /**
     * Get the value of parentQuantity  
     * @return  parentQuantity
     * @uml.property  name="parentQuantity"
     */
    public Quantity getParentQuantity (  ) {
        return parentQuantity;
    }

    // Operations

    public void next ( ) {
	// DO NOTHING.. only one location ever exists
    }

    public void prev ( ) {
	// DO NOTHING.. only one location ever exists
    }

    public boolean hasNext ( ) {
        // always false.. only one location ever exists
        return false; 
    }

    public boolean hasPrevious ( ) {
        // always false.. only one location ever exists
        return false; 
    }

    /** Reset the locator to the origin.
     */
    public void reset () 
    {
    }

    /** Get the list index. All values are held (or are idealized as being
     *  held) in 1-dimensional lists internally by data containers. This
     *  returns the index of where the locator is currently pointed in the
     *  internal list.
     *  @return int value of index.
     */
    public int getListIndex () {
       // only one location, so its always "1"
       return 1;
    }

    /** Set the list index. All values are held (or are idealized as being
     *  held) in 1-dimensional lists internally by data containers. This
     *  returns the index of where the locator is currently pointed in the
     *  internal list.
     *
     * @throws IllegalArgumentException when a value which is negative or greater than the list size (quantity.getSize()-1) is passed.
     */
    public void setListIndex (int index) throws IllegalArgumentException {
       if (index != 1)
          throw new IllegalArgumentException("setListIndex cant handle value of "+index);
    }

}

