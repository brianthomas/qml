
// CVS $Id$

// ReferenceFrameImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.core;

import java.util.List;

import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.ListQuantity;
import net.datamodel.soml.impl.SemanticObjectImpl;

/**
 * This special quantity is a description of a frame of reference for 
 * accessing another quantity. Each axis is assumed to describe 
 * an orthogonal dimension within the Axis Frame, and thus the number 
 * of axes (size of the axis Frame) is the same as the number 
 * of dimensions within the frame.
 * @version $Revision$
 */
public class ReferenceFrameImpl 
extends AbstractObjectWithProperties
implements ReferenceFrame {

    /** No-argument constructor.
     */
    public ReferenceFrameImpl () {
    	super();
    	setXMLNodeName("axisFrame");
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#addAxis(net.datamodel.qml.ListQuantity)
     */
    public boolean addAxis ( ListQuantity axis) {
    	// TODO
// NEED TO signal all locators which belong to quantities which have this axis frame
// to update themselves (perhaps reinitialize and log a warning for the user).
    	//return addMember(axis);
    	return false;
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#removeAxis(net.datamodel.qml.ListQuantity)
     */
    public boolean removeAxis ( ListQuantity axis )
    {
    	
        if (axis == null)
            throw new NullPointerException();
        
        /*
    	return super.addMember(axis, relationship);
    	*/
    	return false;
    }
    
    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#getAxes()
     */
    public List<ListQuantity> getAxes ( ) {
    	// TODO
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#getNumberOfAxisLocations()
     */
    public int getNumberOfAxisLocations() {
    	// TODO
    	/*
        Iterator iter = getAxes().iterator();
        int number = 1;
        while (iter.hasNext()) {
           Quantity q = (Quantity) iter.next();
           number = number*q.getSize().intValue();
        }
        return number;
        */
        return -1;
    }

}

