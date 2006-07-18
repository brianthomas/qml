
// CVS $Id$

// BaseDataType.java Copyright (c) 2006 Brian Thomas. All rights reserved.

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

package net.datamodel.qml;

import net.datamodel.qml.core.XMLSerializableField;
import net.datamodel.qml.core.XMLSerializableObjectImpl;
import net.datamodel.qml.support.Constants;

/**
 * An abstract class for describing the format of the data value in all value-based
 * quantities and component classes (composite quantities do not have data types). 
 */
abstract public class BaseDataType extends XMLSerializableObjectImpl 
implements DataType
{
    // Fields
    // The object which represents the "no data available" value.
    private static final String NODATAVALUE_XML_FIELD_NAME = new String("noDataValue");

    // Methods
    // Constructors
    // Empty Constructor
    public BaseDataType ( ) { 
       init ();
    }
    // Accessor Methods
    /**
     * The object which represents the "no data available" value.
     */
    public Object getNoDataValue (  ) {
        return ((XMLSerializableField) fieldHash.get(NODATAVALUE_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "no data available" value.
     * @throws IllegalAccessException if called for some datatypes (e.g. VectorDataType).
     */
    public void setNoDataValue ( Object value  ) 
    throws IllegalAccessException
    {
        ((XMLSerializableField) fieldHash.get(NODATAVALUE_XML_FIELD_NAME)).setValue(value);
    }
    // Operations
    /**
     * The number of bytes this data type represents.
     */
    abstract public int numOfBytes ( );

    /** Determine if other datatypes are equivalent to this one. 
     *  @@Overrides
     */
    public boolean equals (Object obj) {
       if (obj instanceof BaseDataType) {
           if (
                this.getNoDataValue().equals( ((BaseDataType)obj).getNoDataValue())
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

       resetFields();

      // now initialize XML fields
      // order matters!
      fieldOrder.add(NODATAVALUE_XML_FIELD_NAME);

      fieldHash.put(NODATAVALUE_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));

    }

}
