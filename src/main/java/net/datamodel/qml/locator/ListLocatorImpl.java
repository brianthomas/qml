//CVS $Id$

//ListLocatorImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.ObjectWithValues;
import net.datamodel.qml.core.ListValueContainerImpl;

import org.apache.log4j.Logger;

/**
 * Implementation of a locator for List quantities.
 */
public class ListLocatorImpl extends AbstractLocator
{

	private static final Logger logger = Logger.getLogger(ListLocatorImpl.class);
	
	private int listIndex = 0;

	/** */
	public ListLocatorImpl (ListValueContainerImpl parent) {
		super (parent);
	}
	
	// public ListLocatorImpl (ObjectWithValues parent) { super(parent); }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#next()
	 */
	public final void next() {
		if(hasNext())
			listIndex++;
		else
			listIndex = 0; // wraps around
		logger.debug("Listlocator.next() index is now:"+listIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#prev()
	 */
	public final void prev ( ) {

		if(hasPrevious())
			listIndex--;
		else
			listIndex = getMaxLocation(); // wraps around

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#hasNext()
	 */
	public final boolean hasNext ( ) {
		int maxLocation = getMaxLocation();
		logger.debug("ListLocator.hasNext() called. maxLocation is:"+maxLocation);

		// max location indice is parentQ.size -1
		if ( listIndex < maxLocation)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#hasPrevious()
	 */
	public final boolean hasPrevious ( ) { 
		if ( listIndex > 0) // 0 is the lowest indice
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.AbstractLocator#setListIndex(int)
	 */
	@Override
	public final void setListIndex(int index) throws IllegalArgumentException {
		if(index < 0)
			throw new IllegalArgumentException ("setListIndex can't set index to negative value");

		listIndex = index;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.AbstractLocator#getListIndex()
	 */
	@Override
	public final int getListIndex() { return listIndex; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.locator.AbstractLocator#reset()
	 */
	@Override
	public final void reset() { listIndex = 0; }


}

