// CVS $Id$

// ListLocatorImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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


package net.datamodel.qml.locator;

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.Locator;
import net.datamodel.qml.QuantityWithValues;

import org.apache.log4j.Logger;

/**
 * Implementation of a locator for List quantities.
 */
public class ListLocatorImpl implements Locator 
{
	
	private static final Logger logger = Logger.getLogger(ListLocatorImpl.class);

    // Fields

    // Get the value of parentQuantity
    // @return parentQuantity
    /**
     * @uml.property  name="parentQuantity"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    protected QuantityWithValues parentQuantity;


    // where in the data container list we are
    /**
     * @uml.property  name="listIndex"
     */
    protected int listIndex;

    // Methods
    //

    // Constructor

    /** Vanilla constructor. */
    public ListLocatorImpl ( QuantityWithValues parent )
    throws NullPointerException
    {
    	logger.debug("NEW LIST LOCATOR CREATED");

        if (parent == null)
           throw new NullPointerException();

        parentQuantity = parent;
        reset();

    }

    // Accessor Methods

    /**
     * Get the value of parentQuantity
     * @return  parentQuantity
     * @uml.property  name="parentQuantity"
     */
    public QuantityWithValues getParentQuantity (  ) {
        return parentQuantity;
    }

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

    // Operations

    /**
     * Get the list index. All values are held (or are idealized as being held) in 1-dimensional lists internally by data containers. This returns the index of where the locator is currently pointed in the internal list.
     * @return  int value of index.
     * @uml.property  name="listIndex"
     */
    public int getListIndex( ) {
       return listIndex;
    }

    /**
     * Set the list index. All values are held (or are idealized as being held) in 1-dimensional lists internally by data containers. This returns the index of where the locator is currently pointed in the internal list.
     * @throws IllegalArgumentException  when a value which is negative or greater than the list size (quantity.getSize()-1) is passed.
     * @uml.property  name="listIndex"
     */
    public void setListIndex ( int index ) throws IllegalArgumentException {

       if(index < 0)
          throw new IllegalArgumentException ("setListIndex can't set index to negative value");

       listIndex = index;
    }

    /** Change the location pointer to the next location.
     */
    public void next ( ) {
       if(hasNext())
          listIndex++;
       else
          listIndex = 0; // wraps around
       logger.debug("Listlocator.next() index is now:"+listIndex);
    }

    /** Change the location pointer to the prior location.
     */
    public void prev ( ) {

       if(hasPrevious())
          listIndex--;
       else
          listIndex = getMaxLocation(); // wraps around

    }

    /** Determine if there are any more locations remaining.
     */
    public boolean hasNext ( ) {
       int maxLocation = getMaxLocation();
       logger.debug("ListLocator.hasNext() called. maxLocation is:"+maxLocation);

       // max location indice is parentQ.size -1
       if ( listIndex < maxLocation)
           return true;

       return false;
    }

    /** Determine if there are any prior locations to the current one.
     */
    public boolean hasPrevious ( ) { 
       if ( listIndex > 0) // 0 is the lowest indice
           return true;
       return false;
    }

    /** Reset the locator back to the origin.
     */
    public void reset () 
    {
        listIndex = 0; // set to first location 
    }
    
    protected int getMaxLocation() {
    	logger.debug("getMaxLocation called");
       //int maxUtilIndex = parentQuantity.getValueContainer().getMaxUtilizedIndex();
       return parentQuantity.getCapacity() -1;
    }
    
}

