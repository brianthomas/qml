
// CVS $Id$

// ScalarDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.BaseDataType;
import net.datamodel.qml.core.XMLSerializableField;
import net.datamodel.qml.support.Constants;

/**
 * Class ScalarDataType
 * A scalar (single-valued) data type. 
 */
abstract public class ScalarDataType extends BaseDataType {

    // Fields

    // The width (total length) of this scalar field, 
    // in characters (Width is stored in this fashion so as 
    // to separate it out from the actual encoding of the text).
    private static final String WIDTH_XML_FIELD_NAME = new String("width");

    // Methods

    // Constructors

    // No-arguement Constructor
    public ScalarDataType ( ) { 
       init();
    }

    // Accessor Methods

    /**
     * Determine the width (total length) of this scalar field, in characters.
     * (Width is stored in this fashion so as to separate it out from 
     * the actual encoding of the text).
     */
    public Integer getWidth (  ) {
        return (Integer) ((XMLSerializableField) fieldHash.get(WIDTH_XML_FIELD_NAME)).getValue();
    }

    /**
     * Set the width (total length) of this scalar field, in characters.
     * (Width is stored in this fashion so as to separate it out from 
     * the actual encoding of the text).
     */
    public void setWidth ( Integer value ) {
        ((XMLSerializableField) fieldHash.get(WIDTH_XML_FIELD_NAME)).setValue(value);
    }

    /** Determine if other units are equivalent to this one.*/
    public boolean equals (Object obj) {
       if (obj instanceof ScalarDataType) {
           if (
                super.equals(obj)
                     &&
                this.getWidth().equals( ((ScalarDataType)obj).getWidth())
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
      xmlNodeName = (String) null;

      // now initialize XML fields
      // order matters!
      fieldOrder.add(WIDTH_XML_FIELD_NAME);

      fieldHash.put(WIDTH_XML_FIELD_NAME, new XMLSerializableField((Integer) null, Constants.FIELD_ATTRIB_TYPE));

    }
}

