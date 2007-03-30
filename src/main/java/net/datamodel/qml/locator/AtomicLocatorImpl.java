
//CVS $Id$

//AtomicLocatorImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.locator;

import net.datamodel.qml.ValueContainer;

/**
 * AtomicLocatorImpl is implementation of a locator for Atomic Quantities.
 * As these quantities only ever have ONE location within them, its more or
 * less pointless to use them. This class is provided to allow consistency
 * with the interface of the higher-dimensional quantities.
 * 
 * @version $Revision$
 */
public class AtomicLocatorImpl 
extends AbstractLocator
{

//	private static final Logger logger = Logger.getLogger(AtomicLocatorImpl.class);

	/** Vanilla constructor. */
	public AtomicLocatorImpl ( ValueContainer parent ) {  super (parent); }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#next()
	 */
	// DO NOTHING.. only one location ever exists
	public final void next ( ) { }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#prev()
	 */
	// DO NOTHING.. only one location ever exists
	public final void prev ( ) { }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#hasNext()
	 */
	public final boolean hasNext ( ) {
		return false;  // always false.. only one location ever exists
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#hasPrevious()
	 */
	public final boolean hasPrevious ( ) {
		return false;  // always false.. only one location ever exists
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#setListIndex(int)
	 */
	public final void setListIndex (int index) throws IllegalArgumentException {
		if (index != 1)
			throw new IllegalArgumentException("setListIndex cant handle value of "+index);
	}

	@Override
	public final int getListIndex() { return 0; }

	@Override
	public final void reset() {
		// does nothing
	}

}

