
// CVS $Id$

// IntegerDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.core.XMLSerializableField;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Utility;
import net.datamodel.qml.support.Constants.NodeName;
import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;

import org.apache.log4j.Logger;

/**
 * A scalar integer datatype. May be hexidecimal, octal or decimal in nature.
 */
public class IntegerDataType extends NumberDataType {
	
	private static final Logger logger = Logger.getLogger(IntegerDataType.class);

    // Fields

    // The type of representation to use for these integers in 
    // the XML serialization. Chioces are "decimal", "hexadecimal" 
    // and "decimal".
    private final static String SIGNED_XML_FIELD_NAME = "signed";
    private final static boolean DEFAULT_SIGNED = false;

    // Whether or not the integers represented by this data type are signed.
    private final static String INTTYPE_XML_FIELD_NAME = "type";

    // Methods
    // Constructors
    // No-arg Constructor
    public IntegerDataType ( ) { 
        init();
    }
    // Accessor Methods
    /**
     * The type of representation to use for these integers in the XML serialization. Chioces are "decimal", "hexadecimal" and "decimal".
     */
    public String getType (  ) {
        return (String) ((XMLSerializableField) fieldHash.get(INTTYPE_XML_FIELD_NAME)).getValue();
    }

    /**
     * The type of representation to use for these integers in the XML serialization. Chioces are "decimal", "hexadecimal" and "decimal".
     */
    public void setType ( String value  ) {

       if( value != null)
       {
           if(!Utility.isValidIntegerType(value) ) {
              logger.warn("Warning: "+value+" is not a valid value for the type attribute, ignoring set request.");
              return;
           }
        }

        ((XMLSerializableField) fieldHash.get(INTTYPE_XML_FIELD_NAME)).setValue(value);
    }

    /**
     * Whether or not the integers represented by this data type are signed.
     */
    public Boolean getSigned (  ) {
        return (Boolean) ((XMLSerializableField) fieldHash.get(SIGNED_XML_FIELD_NAME)).getValue();
    }
    /**
     * Whether or not the integers represented by this data type are signed.
     */
    public void setSigned ( Boolean value  ) {
        ((XMLSerializableField) fieldHash.get(SIGNED_XML_FIELD_NAME)).setValue(value);
    }

    /**
     * The number of bytes this data type represents.
     */
    public int numOfBytes ( ) {
        // FIX : correct??
        return getWidth().intValue();
    }

    /** Determine if other units are equivalent to these.
      * @@Overrides
      */
    public boolean equals (Object obj) 
    {
        if (obj instanceof IntegerDataType) {
            if (
                 super.equals(obj)
                      &&
                 this.getSigned().equals( ((IntegerDataType)obj).getSigned())
                      &&
                 this.getType().equals( ((IntegerDataType)obj).getType())
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

      xmlNodeName = Constants.NodeName.INTEGER_DATATYPE;

      setWidth(new Integer(2));

      try {
          setNoDataValue(new Integer(-9));
       } catch (Exception e) { }

      // now initialize XML fields
      // order matters!
      fieldOrder.add(SIGNED_XML_FIELD_NAME);
      fieldOrder.add(INTTYPE_XML_FIELD_NAME);

      // FIX : default is INTYTPE:Decimal
      fieldHash.put(INTTYPE_XML_FIELD_NAME, new XMLSerializableField((String) null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(SIGNED_XML_FIELD_NAME, new XMLSerializableField(new Boolean(DEFAULT_SIGNED), Constants.FIELD_ATTRIB_TYPE));

    }

}

