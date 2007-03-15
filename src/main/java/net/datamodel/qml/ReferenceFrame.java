
// CVS $Id$

// ReferenceFrame.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml;

import java.util.List;

import net.datamodel.soml.SemanticObject;

/**
 * Interface for axis frames. These objects contain the dimensional information that defines a unique frame of reference for one or more other quantities.
 * @version  $Revision$
 */
public interface ReferenceFrame 
extends SemanticObject
{

    // Operations

    /** A method to add another dimension (axis) to this frame of refernence.
     * 
     * @param axis 
     * @return boolean value of whether an axis was successfully added. 
     */
    public boolean addAxis (ListQuantity axis);

    /** A method to remove a dimension (axis) from this frame.
     * 
     * @param axis 
     * @return boolean value of whether an axis was successfully removed. 
     */
    public boolean removeAxis (ListQuantity axis);

    /** Retrieve all dimensions (axes) described by this frame of reference.
     * 
     * @return  List of axes in a given instance of ReferenceFrame.
     */
    public List<ListQuantity> getAxes();

    /** Utility method to find the total number of locations implied by 
      * the values held by child axes.
      */
    public int getNumberOfAxisLocations();

}
