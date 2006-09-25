
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

import java.net.URN;

/**
 * @author  thomas
 */
public interface Component {
    
    /** Determine equivalence between components (or quantities). Equivalence is 
      * the same as 'equals' but without checking that the id fields between both
      * objects are the same.
      */
    public boolean equivalent ( Object object );

    /**
     * Get the id of an instance of this class. It should be unique across all components and quantities within a given document/object tree.
     * @uml.property  name="id"
     */
    public String getId ( );

    /**
     * The id of an instance of this class. It should be unique across all components and quantities within a given document/object tree.
     * @uml.property  name="id"
     */
    public void setId ( String value ); 

    /*
     * Determine whether or not this quantity or component is immutable.
     * (e.g. may change meta-data/data within the instance) 
     */
    //public Boolean getImmutable ( );
        
    /**
     * Set the value of dataType
     * @uml.property  name="dataType"
     */
    public void setDataType ( DataType value  );

    /**
     * Get the value of dataType
     * @return  the value of dataType
     * @uml.property  name="dataType"
     * @uml.associationEnd  
     */
    public DataType getDataType (  );
 
    /**
     * Set the value of units
     * @param units to be set
     * @uml.property  name="units"
     */
    public void setUnits ( Units value ); 

    /**
     * Get the value of units
     * @return  the value of units
     * @uml.property  name="units"
     * @uml.associationEnd  
     */
    public Units getUnits ( );
    
    /** Get the URN which represents this object. URN is used to provide the
     * id of the semantic meaning.
     * @return URN of the object which represents its semantic meaning.
     */
	public URN getURN();

}

