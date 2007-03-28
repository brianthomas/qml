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
import java.util.Map;
import java.util.Vector;

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixLocator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.ObjectWithValues;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.core.MatrixValueContainerImpl;

/**
 * Implementation of a locator for List quantities.
 */
public class MatrixLocatorImpl extends AbstractLocator
implements MatrixLocator 
{

	// the current axis frame we are using 
	private ReferenceFrame currentAxisFrame = null;

	// for the current axisFrame, a list of axes, and how we wish to iterator over them 
	private List<ListQuantity> axisOrderList = new Vector<ListQuantity>();

	// cache value of whether next cell location exists
	private boolean nextCellAvailable = false;

	// cache value of whether previous cell location exists
	private boolean prevCellAvailable = false;

	// number of axes in the present axis frame
	private int dimension = 0;

	//For the currentAxisFrame, a hashtable to store the (axis, index) pairs
	protected Map<ListQuantity,Integer> locations = new Hashtable<ListQuantity, Integer>();

	public boolean needToUpdateIndexMultiplier = true;

	private int[] indexMult = {};
	
	private ListLocatorImpl listLocator = null;

	// Fields

	// Constructor
	/** Vanilla constructor */
	public MatrixLocatorImpl ( MatrixValueContainerImpl parent ) {
		this(parent, null);
	}

	/** Construct locator with particular axis frame. The specified frame
	 * must belong to the specified MatrixQuantity.
	 */
	public MatrixLocatorImpl ( MatrixValueContainerImpl parent, ReferenceFrame useFrame ) 
	throws NullPointerException
	{
		super(parent);
		setCurrentReferenceFrame(currentAxisFrame);
		listLocator = new ListLocatorImpl(parent); // TODO: do we want hardwired? 
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.ListLocatorImpl#getCurrentAxisFrame()
	 */
	public final ReferenceFrame getCurrentReferenceFrame ( ) { return currentAxisFrame; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.ListLocatorImpl#setCurrentAxisFrame(net.datamodel.qml.ReferenceFrame)
	 */
	public final void setCurrentReferenceFrame ( ReferenceFrame frame)
	{

		if(frame != null && !((MatrixQuantity) getParent()).getReferenceFrames().contains(frame))
			throw new IllegalArgumentException("Can't setCurrentAxisFrame in locator : parent quantity contains no such object:"+frame);

		// reset our location to the origin
		reset();

		// no need to do anything else if its set to "null"
		if (frame == null)
			return;

		// note it is the current frame
		currentAxisFrame = frame;

		// now do stuff to init our location, for the present frame, to the origin

		axisOrderList = currentAxisFrame.getAxes();

		// set all indices to '0'..the origin
		Iterator iter = axisOrderList.iterator();
		while (iter.hasNext())
		{
			locations.put((ListQuantity)iter.next(), new Integer(0));
		}

		dimension = axisOrderList.size();

		updateIndexMultiplierArray();

//		FIX : need call back to make sure this is in sync with "current" ReferenceFrame.
//		ISSUE: if user changes/removes/adds) an axis, we should re=init the locator

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.MatrixLocator#setLocationIndex(net.datamodel.qml.ListQuantity, int)
	 */
	public final void setLocationIndex ( ListQuantity axis, int listIndex )
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
	public final void setIterationOrder ( List<ListQuantity> axisIterationOrderList )
	throws IllegalArgumentException
	{

		if(axisIterationOrderList == null || axisIterationOrderList.size() != dimension)
			throw new IllegalArgumentException("setIterationOrder : passed list is null or wrong size.");

		// FIX: need to check that all azes belong to the current frame

		// set it
		axisOrderList = axisIterationOrderList;

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.MatrixLocator#getIterationOrder()
	 */
	public final List<ListQuantity> getIterationOrder ( ) { return axisOrderList; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.MatrixLocator#getLocationIndex(net.datamodel.qml.ListQuantity)
	 */
	public Integer getLocationIndex ( ListQuantity axis ) 
	throws IllegalArgumentException
	{
		if (currentAxisFrame == null)
			throw new IllegalArgumentException("Can't setAxisIndex in locator with null current ReferenceFrame.");
		if (!currentAxisFrame.getAxes().contains(axis))
			throw new IllegalArgumentException("Can't setAxisIndex. Passed Axis is not present in current locator ReferenceFrame.");

		// everything is kosher..return the current index
		return (Integer) locations.get(axis);

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.MatrixLocator#getLocationValue(net.datamodel.qml.ListQuantity)
	 */
	public final Object getLocationValue ( ListQuantity axis )
	throws IllegalArgumentException
	{

		if (currentAxisFrame == null)
			throw new IllegalArgumentException("Can't setAxisIndex in locator with null current ReferenceFrame.");
		if (!currentAxisFrame.getAxes().contains(axis))
			throw new IllegalArgumentException("Can't setAxisIndex. Passed Axis is not present in current locator ReferenceFrame.");

		// everything is kosher..return the current index
		Integer index = (Integer) locations.get(axis);
		Locator loc = axis.createLocator(); 
		loc.setListIndex(index.intValue());
		return axis.getValue(loc);

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.ListLocatorImpl#next()
	 */
	public final void next() {

		if (currentAxisFrame == null)
		{
			listLocator.next(); // use list ordered iteration
		} else {

			boolean outOfDataCells = true;

			nextCellAvailable = true;

			for (int i = 0; i < dimension; i++) {

				ListQuantity axis = axisOrderList.get(i);
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

//			FIX : determine, and cache, the list index position
//			hint: get from getLongArrayIndex method in XDF Array class

			// we cycled back to the origin. Set the global
			// to let us know
			if (outOfDataCells)
				nextCellAvailable = false;

			//return !outOfDataCells;

			// calculate and cache our location in terms of the list index
			updateListIndex();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.ListLocatorImpl#prev()
	 */
	public final void prev() {
		if (currentAxisFrame == null)
			listLocator.prev(); // use list ordered iteration
		else {
			boolean outOfDataCells = true;

			prevCellAvailable = true;

			for (int i = 0; i < dimension ; i++) {
				ListQuantity axis = axisOrderList.get(i);
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
	public final boolean hasNext ( ) {
		if (currentAxisFrame == null)
			return listLocator.hasNext(); // use list ordered iteration
		else {
			return nextCellAvailable;
		}
	}

	/** Determine if there are any prior locations to the current one.
	 */
	public final boolean hasPrevious ( ) { 
		if (currentAxisFrame == null)
			return listLocator.hasPrevious(); // use list ordered iteration
		else {
			return prevCellAvailable;
		}
	}

	/** Reset the locator back to the origin.
	 */
	public void reset () 
	{
		super.reset();

		currentAxisFrame = (ReferenceFrame) null;

		locations = new Hashtable<ListQuantity, Integer>();
		axisOrderList = new Vector<ListQuantity>();
		dimension = 0;

		nextCellAvailable = (getParent().getNumberOfValues() > 1 ) ? true : false;
		prevCellAvailable = false;

		needToUpdateIndexMultiplier = true;

	}

	// update the list index for the current axisFrame
	// ONLY works if there is a currentAxisFrame 
	private void updateListIndex () 
	{

		if(currentAxisFrame != null)
		{

			List<ListQuantity> axisList = currentAxisFrame.getAxes();
			int index = 0;

			if (dimension > 0) {

				ListQuantity axis = axisList.get(0);
				index = getLocationIndex(axis).intValue();

				if (dimension > 1) {

					// safety to insure we are up-to-date
					if (indexMult == null || needToUpdateIndexMultiplier ) 
						updateIndexMultiplierArray();

					// each of the higher axes contribute 2**(i-1) * index
					// to the overall index value.
					for (int i = 1; i < dimension; i++) {
						axis = axisList.get(i);
						index += getLocationIndex(axis).intValue() * indexMult[i];
					}
				}
			}
			setListIndex(index);
		}
	}

	private void updateIndexMultiplierArray() {

		indexMult = new int[dimension];
		if (dimension > 1) {

			List<ListQuantity> axisList = currentAxisFrame.getAxes();

			// axis 0 as prev axis for axis #1
			int mult = axisList.get(0).getNumberOfValues();
			indexMult[1] = mult;

			// algorithm for higher dimension axes
			for (int i = 2; i < dimension; i++) {
				mult *= axisList.get(i-1).getNumberOfValues();
				indexMult[i] = mult;
			}

		} 

		needToUpdateIndexMultiplier = false; 

	}

	@Override
	public void setListIndex(int index) 
	throws IllegalArgumentException {
		listLocator.setListIndex(index);
		// TODO: We have to update the axis index values, which will require some
		// kind of calculation. Also: Watch out for call to setListIndex in updateListIndex!!
		// if we call updateListIndex here we get infinite recursion.
		// updateListIndex();
	}

}

