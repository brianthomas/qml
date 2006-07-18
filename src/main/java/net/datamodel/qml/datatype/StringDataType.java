
// CVS $Id$

// StringDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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


package net.datamodel.qml.datatype;

import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Constants.NodeName;

/**
 * A (scalar) string datatype.
 */
public class StringDataType extends ScalarDataType {

    // Constructors
    // Empty Constructor
    public StringDataType ( ) { 
       init();
    }

    /**
     * The number of bytes this data type represents.
     */
    public int numOfBytes ( ) {
        // FIX... need to check for encoding..
        return getWidth().intValue();
    }

    /** Determine if other units are equivalent to these.
      * @@Overrides
      */
    public boolean equals (Object obj)
    {
        if (obj instanceof StringDataType) {
            if (
                 super.equals(obj)
               )
            return true;
        }
        return false;
    }

    // Protected Methods
    //

    /** Special protected method used by constructor methods to
        conviently build the XML attribute list for a given class.
     */
    protected void init()
    {

       super.init();
       xmlNodeName = Constants.NodeName.STRING_DATATYPE;

       try {
          setNoDataValue("");
       } catch (Exception e) { }

    }

}

