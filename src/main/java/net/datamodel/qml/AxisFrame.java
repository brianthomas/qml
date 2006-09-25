
// CVS $Id$

// AxisFrame.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.util.Iterator;
import java.util.List;

/**
 * Interface for axis frames. These objects contain the dimensional information that defines a unique frame of reference for one or more other quantities.
 * @version  $Revision$
 */
public interface AxisFrame extends SemanticObject {

    // Operations

    /** A utility method for addMember(axis).
     * @param axis 
     * @return boolean value of whether an axis was successfully added. 
     */
    public boolean addAxis ( Quantity axis);

    /** A utility method for removeMember(axis).
     * @param axis 
     * @return boolean value of whether an axis was successfully removed. 
     */
    public boolean removeAxis ( Quantity axis);

    /**
     * Utility method. Synonym for getMemberList().
     * @return  List of axes in a given instance of AxisFrame.
     * @uml.property  name="axisList"
     */
    public List getAxisList ( );

    /** Utility method to find the number of locations implied by 
      * the values held by child axes.
      */
    public int getNumberOfAxisLocations();

}
