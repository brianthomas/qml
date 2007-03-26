
// CVS $Id$

// Component.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.net.URI;

import net.datamodel.soml.SemanticObject;


/**
 * @author  thomas
 */
public interface Component 
extends SemanticObject, ObjectWithProperties
{
    
    /** Determine equivalence between components (or quantities). Equivalence is 
      * the same as 'equals' but without checking that the id fields between both
      * objects are the same.
      */
	// TODO: we need to really figure out what this means..
    public boolean equivalent (Object object);
    
    /**
     * Get the value of dataType
     * @return  the value of dataType
     */
    public DataType getDataType (  );
 
    /** Set the value of the datatype.
     * 
     * @param type
     */
    public void setDataType(DataType type);
    
    /**
     * Get the value of units
     * @return  the value of units
     */
    public Units getUnits ( );
    
    /** Set the value of the units for this object.
     * 
     * @param u
     */
    public void setUnits(Units u);
}

