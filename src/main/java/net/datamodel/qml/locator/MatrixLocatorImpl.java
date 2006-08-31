// CVS $Id$

// MatrixLocatorImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.core.MatrixQuantityImpl;

/**
 * Implementation of a locator for List quantities.
 */
public class MatrixLocatorImpl extends ListLocatorImpl 
implements Locator 
{

    // the current axis frame we are using (e.g. always "null")
    /**
     * @uml.property  name="currentAxisFrame"
     * @uml.associationEnd  
     */
    private AxisFrame currentAxisFrame;

    // for the current axisFrame, a list of axes, and how we wish to iterator over them 
    /**
     * @uml.property  name="axisOrderList"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="net.datamodel.qml.Quantity" qualifier="new:java.lang.Integer java.lang.Integer"
     */
    protected List axisOrderList;

    // cache value of whether next cell location exists
    /**
     * @uml.property  name="nextCellAvailable"
     */
    private boolean nextCellAvailable;

    // cache value of whether previous cell location exists
    /**
     * @uml.property  name="prevCellAvailable"
     */
    private boolean prevCellAvailable;

    // number of axes in the present axis frame
    /**
     * @uml.property  name="dimension"
     */
    private int dimension;

    //For the currentAxisFrame, a hashtable to store the (axis, index) pairs
    /**
     * @uml.property  name="locations"
     * @uml.associationEnd  qualifier="axis:net.datamodel.qml.Quantity java.lang.Integer"
     */
    protected Hashtable locations;

    /**
     * @uml.property  name="needToUpdateIndexMultiplier"
     */
    public boolean needToUpdateIndexMultiplier = true;
    /**
     * @uml.property  name="indexMult" multiplicity="(0 -1)" dimension="1"
     */
    private int[] indexMult;

    // Fields

    // where in the data container list we are
    /**
     * @uml.property  name="listIndex"
     */
    protected int listIndex;

    // Methods
    //

    // Constructor
    /** Vanilla constructor */
    public MatrixLocatorImpl ( Quantity parent ) 
    {
        super(parent);
    }

    /** Construct locator with particular axis frame.
     */
    public MatrixLocatorImpl ( Quantity parent, AxisFrame useFrame ) 
    throws NullPointerException
    {
        super(parent);
        setCurrentAxisFrame(currentAxisFrame);
    }

    // Accessor Methods

    /**
     * @return  AxisFrame null value is always returned.
     * @uml.property  name="currentAxisFrame"
     */
    public AxisFrame getCurrentAxisFrame ( ) {
        return currentAxisFrame;
    }

    /**
     * Set the current AxisFrame. If the frame is "null" then list-ordered iteration will be used by the locator.
     * @param frame  to set. It may be "null".
     * @uml.property  name="currentAxisFrame"
     */
    public void setCurrentAxisFrame ( AxisFrame frame)
    {

        if(frame != null && !((MatrixQuantityImpl) parentQuantity).getAxisFrameList().contains(frame))
           throw new IllegalArgumentException("Can't setCurrentAxisFrame in locator : parent quantity contains no such object:"+frame);

        // reset our location to the origin
        reset();

        // no need to do anything else if its set to "null"
        if (frame == null)
           return;

        // note it is the current frame
        currentAxisFrame = frame;

        // now do stuff to init our location, for the present frame, to the origin

        axisOrderList = currentAxisFrame.getAxisList();

        // set all indices to '0'..the origin
        Iterator iter = axisOrderList.iterator();
        while (iter.hasNext())
        {
            locations.put((Quantity)iter.next(), new Integer(0));
        }

        dimension = axisOrderList.size();

        updateIndexMultiplierArray();

// FIX : need call back to make sure this is in sync with "current" AxisFrame.
// ISSUE: if user changes/removes/adds) an axis, we should re=init the locator

    }

    /** Set the location by identified axis list index.
     */
    public void setLocationIndex ( Quantity axis, int listIndex )
    throws IllegalArgumentException
    {

       if(listIndex < 0)
          throw new IllegalArgumentException("setLocationIndex index value cannot be less than 0.");

       if(axis == null)
          throw new NullPointerException("setLocationIndex required non-null object for the axis.");

       // could throw location out of bounds exception (!?)
       if(listIndex >= axis.getSize().intValue())
          throw new NullPointerException("setLocationIndex index size is greater or equal to the size of the axis.");

       locations.put(axis, new Integer(listIndex)); // set the index 

       updateListIndex(); // update list index pointer for current location

    }

    /** Set the order in which the locator is expected to iterate over the 
     * current axis frame.
     */
    public void setIterationOrder ( List axisIterationOrderList )
    throws IllegalArgumentException
    {

       if(axisIterationOrderList == null || axisIterationOrderList.size() != dimension)
           throw new IllegalArgumentException("setIterationOrder : passed list is null or wrong size.");

	// FIX: need to check that all azes belong to the current frame

	// set it
        axisOrderList = axisIterationOrderList;

    }

    /** Get the iteration order for the current frame. List may be empty
     *  if no current frame is specified.
     */
    public List getIterationOrder ( ) {
       return axisOrderList;
    }

    /** Determine the location in terms of the listIndex for a particular
     * quantity which is serving as an axis.
     */ 
    public Integer getLocationIndex ( Quantity axis ) 
    throws IllegalArgumentException
    {
        if (currentAxisFrame == null)
           throw new IllegalArgumentException("Can't setAxisIndex in locator with null current AxisFrame.");
        if (!currentAxisFrame.getAxisList().contains(axis))
           throw new IllegalArgumentException("Can't setAxisIndex. Passed Axis is not present in current locator AxisFrame.");

        // everything is kosher..return the current index
        return (Integer) locations.get(axis);

    }

    /** Utility method to allow determination of the value of the location 
     * in terms of a particular quantity which is serving as an axis.
     */
    public Object getLocationValue ( Quantity axis )
    throws IllegalArgumentException
    {

        if (currentAxisFrame == null)
           throw new IllegalArgumentException("Can't setAxisIndex in locator with null current AxisFrame.");
        if (!currentAxisFrame.getAxisList().contains(axis))
           throw new IllegalArgumentException("Can't setAxisIndex. Passed Axis is not present in current locator AxisFrame.");

        // everything is kosher..return the current index
        Integer index = (Integer) locations.get(axis);
        Locator loc = axis.createLocator(); 
        loc.setListIndex(index.intValue());
        return axis.getValue(loc);

    }

    // Operations

    /** Change the location pointer to the next location.
     */
    public void next ( ) {

        if (currentAxisFrame == null)
            super.next(); // use list ordered iteration
        else {

           boolean outOfDataCells = true;
       
           nextCellAvailable = true;
       
           for (int i = 0; i < dimension; i++) {

             Quantity axis = (Quantity) axisOrderList.get(i);
             int index = ((Integer) locations.get(axis)).intValue();

             // are we still within the axis?
             if (index < axis.getSize().intValue() -1) {
               outOfDataCells = false;
               // advance current index by one
               index++;
               locations.put(axis, new Integer(index));
               break;  //get out of the for loop
             }
       
             locations.put(axis, new Integer(0)); // reset index back to the origin of this axis
           }
       
// FIX : determine, and cache, the list index position
// hint: get from getLongArrayIndex method in XDF Array class

           // we cycled back to the origin. Set the global
           // to let us know
           if (outOfDataCells)
               nextCellAvailable = false;
       
           //return !outOfDataCells;

           // calculate and cache our location in terms of the list index
           updateListIndex();
        }
    }

    /** Change the location pointer to the prior location.
     */
    public void prev ( ) {
        if (currentAxisFrame == null)
            super.prev(); // use list ordered iteration
        else {
            boolean outOfDataCells = true;
        
            prevCellAvailable = true;
        
            for (int i = 0; i < dimension ; i++) {
              Quantity axis = (Quantity) axisOrderList.get(i);
              int index = ((Integer) locations.get(axis)).intValue();
              index--;
              if (index < 0) {
                locations.put(axis, new Integer(axis.getSize().intValue()-1));
              }
              else {
                locations.put(axis, new Integer(index));
                outOfDataCells = false;
                break;  //get out of the for loop
              }
            }
        
            if (outOfDataCells)
                prevCellAvailable = false;
        
            // return !outOfDataCells;

            // calculate and cache our location in terms of the list index
            updateListIndex();
        }
    }

    /** Determine if there are any more locations remaining.
     */
    public boolean hasNext ( ) {
        if (currentAxisFrame == null)
            return super.hasNext(); // use list ordered iteration
        else {
            return nextCellAvailable;
        }
    }

    /** Determine if there are any prior locations to the current one.
     */
    public boolean hasPrevious ( ) { 
        if (currentAxisFrame == null)
            return super.hasPrevious(); // use list ordered iteration
        else {
            return prevCellAvailable;
        }
    }

    /** Reset the locator back to the origin.
     */
    public void reset () 
    {
        super.reset();

        currentAxisFrame = (AxisFrame) null;

        locations = new Hashtable();
        axisOrderList = new Vector();
        dimension = 0;

        nextCellAvailable = (parentQuantity.getSize().intValue() > 1 ) ? true : false;
        prevCellAvailable = false;

        needToUpdateIndexMultiplier = true;

    }

    // update the list index for the current axisFrame
    // ONLY works if there is a currentAxisFrame 
    protected void updateListIndex () 
    {

      if(currentAxisFrame != null)
      {

          List axisList = currentAxisFrame.getAxisList();
          int index = 0;

          if (dimension > 0) {

             Quantity axis = (Quantity) axisList.get(0);
             index = getLocationIndex(axis).intValue();

             if (dimension > 1) {

                // safety to insure we are up-to-date
                if (indexMult == null || needToUpdateIndexMultiplier ) 
                    updateIndexMultiplierArray();

                // each of the higher axes contribute 2**(i-1) * index
                // to the overall index value.
                for (int i = 1; i < dimension; i++) {
                   axis = (Quantity) axisList.get(i);
                   index += getLocationIndex(axis).intValue() * indexMult[i];
                }
             }
          }
          setListIndex(index);
       }
    }

    protected void updateIndexMultiplierArray() {

         indexMult = new int[dimension];
         if (dimension > 1) {

            List axisList = currentAxisFrame.getAxisList();

            // axis 0 as prev axis for axis #1
            int mult = ((Quantity) axisList.get(0)).getSize().intValue();
            indexMult[1] = mult;

            // algorithm for higher dimension axes
            for (int i = 2; i < dimension; i++) {
                mult *= ((Quantity) axisList.get(i-1)).getSize().intValue();
                indexMult[i] = mult;
            }

         } 

         needToUpdateIndexMultiplier = false; 

   }

}

