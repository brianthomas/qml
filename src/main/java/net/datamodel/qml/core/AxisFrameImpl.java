
// CVS $Id$

// AxisFrameImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SemanticObject;

/**
 * This special quantity is a description of a frame of reference for 
 * accessing another quantity. Each axis is assumed to describe 
 * an orthogonal dimension within the Axis Frame, and thus the number 
 * of axes (size of the axis Frame) is the same as the number 
 * of dimensions within the frame.
 * @version $Revision$
 */
public class AxisFrameImpl extends SemanticObjectImpl implements AxisFrame {

    // Fields

    // Methods

    // Constructors

    /** Construct an axis frame.
     */
    public AxisFrameImpl ( ) 
    {
       init();
    }

    // Accessor Methods

    // Operations

    /** A utility method for addMember(axis).
     * @param axis 
     * @return boolean value of whether an axis was successfully added. 
     */
    public boolean addAxis ( Quantity axis) {
       	// FIX 
// NEED TO signal all locators which belong to quantities which have this axis frame
// to update themselves (perhaps reinitialize and log a warning for the user).
    	return addMember(axis);
    }

   /**
     * Add an object of type SemanticObject to the List of member Quantities.
     * The only restrictions on membership are that a quantity may not "own"
     * itself, and only MatrixQuantities and CompositeQuantiites may have AxisFrameImpls.
     * Furthermore, incorrectly dimensioned AxisFrameImpls are not allowed.
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an AxisFrameImpl
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This AxisFrameImpl may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @throws IllegalArgumentException if adding self, an AxisFrameImpl to the wrong SemanticObject Type, or the AxisFrameImpl dimensional
ity is incorrect.
     * @throws NullPointerException if attempting to adding an null (!!)
     * @return boolean value of whether addition was successfull or not.
     */
    @Override
    public boolean addMember ( SemanticObject axis, URI relationship) 
    throws IllegalArgumentException, NullPointerException
    {
        if (axis == null)
            throw new NullPointerException();
        
        // cant add quantity IF its not list-based
        if (!(axis instanceof ListQuantity))
            throw new IllegalArgumentException("Can't add axis quantity member as its not list-based.");

    	return super.addMember(axis, relationship);
    }

    /** A utility method for removeMember(axis).
     * @param axis 
     * @return boolean value of whether an axis was successfully removed. 
     */
// FIX - change to "void"? 
    public boolean removeAxis ( Quantity axis) {
    	return removeMember((SemanticObject) axis);
    }

    /** Utility method. Synonym for getMemberList().
     * @return List of axes in a given instance of AxisFrameImpl. 
     */
    public List getAxisList ( ) {
        return getMemberList();
    }

    /** Utility method to find the number of locations implied by 
      * the values held by child axes.
      */
    public int getNumberOfAxisLocations() {
        Iterator iter = getAxisList().iterator();
        int number = 1;
        while (iter.hasNext()) {
           Quantity q = (Quantity) iter.next();
           number = number*q.getSize().intValue();
        }
        return number;
    }

    // Protected Operations

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init()
    {

       super.init();
       xmlNodeName = "axisFrame";

    }

}

