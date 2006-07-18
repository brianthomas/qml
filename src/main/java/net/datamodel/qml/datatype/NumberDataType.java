
// CVS $Id$

// NumberDataFormat.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

/**
 * Class NumberDataFormat
 * A scalar data type which describes some kind of number.
 */
abstract public class NumberDataType extends ScalarDataType 
{

    // Fields
    private final static String INFINITE_XML_FIELD_NAME = "infinite";
    private final static String NEG_INFINITE_XML_FIELD_NAME = "infiniteNegative";
    private final static String NOT_A_NUMBER_XML_FIELD_NAME = "notANumber";
    private final static String DISABLED_XML_FIELD_NAME = "disabledValue";
    private final static String OVERFLOW_XML_FIELD_NAME = "overflowValue";
    private final static String UNDERFLOW_XML_FIELD_NAME = "overflowValue";

    // Methods
    // Constructors
    /** No-argument Constructor
     */
    public NumberDataType ( ) { }

    // Accessor Methods

    /**
     * The object which represents the "infinite" value.
     */
    public Object getInfinite ( ) 
    {
        return ((XMLSerializableField) fieldHash.get(INFINITE_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "infinite" value.
     */
    public void setInfinite ( Object value  ) 
    {
        ((XMLSerializableField) fieldHash.get(INFINITE_XML_FIELD_NAME)).setValue(value);
    }
    /**
     * The object which represents the "negative infinite" value.
     */
    public Object getInfiniteNegative (  ) {
        return ((XMLSerializableField) fieldHash.get(NEG_INFINITE_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "negative infinite" value.
     */
    public void setInfiniteNegative ( Object value  ) {
        ((XMLSerializableField) fieldHash.get(NEG_INFINITE_XML_FIELD_NAME)).setValue(value);
    }
    /**
     * The object which represents the "not a number" value.
     */
    public Object getNotANumber (  ) {
        return ((XMLSerializableField) fieldHash.get(NOT_A_NUMBER_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "not a number" value.
     */
    public void setNotANumber ( Object value  ) {
        ((XMLSerializableField) fieldHash.get(NOT_A_NUMBER_XML_FIELD_NAME)).setValue(value);
    }
    /**
     * The object which represents the "no data possible (disabled location)" value.
     */
    public Object getDisabledValue (  ) {
        return ((XMLSerializableField) fieldHash.get(DISABLED_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "no data possible (disabled location)" value.
     */
    public void setDisabledValue ( Object value  ) {
        ((XMLSerializableField) fieldHash.get(DISABLED_XML_FIELD_NAME)).setValue(value);
    }
    /**
     * The object which represents the "overflow" value.
     */
    public Object getOverflowValue (  ) {
        return ((XMLSerializableField) fieldHash.get(OVERFLOW_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "overflow" value.
     */
    public void setOverflowValue ( Object value  ) {
        ((XMLSerializableField) fieldHash.get(OVERFLOW_XML_FIELD_NAME)).setValue(value);
    }
    /**
     * The object which represents the "underflow" value.
     */
    public Object getUnderflowValue (  ) {
        return ((XMLSerializableField) fieldHash.get(UNDERFLOW_XML_FIELD_NAME)).getValue();
    }
    /**
     * The object which represents the "underflow" value.
     */
    public void setUnderflowValue ( Object value  ) {
        ((XMLSerializableField) fieldHash.get(UNDERFLOW_XML_FIELD_NAME)).setValue(value);
    }

    /** Determine if other units are equivalent to these.
      * @@Overrides
      */
    public boolean equals (Object obj) {
       if (obj instanceof NumberDataType) {
           if (
                super.equals(obj)
                     &&
                this.getInfinite().equals( ((NumberDataType)obj).getInfinite())
                     &&
                this.getInfiniteNegative().equals( ((NumberDataType)obj).getInfiniteNegative())
                     &&
                this.getNotANumber().equals( ((NumberDataType)obj).getNotANumber())
                     &&
                this.getDisabledValue().equals( ((NumberDataType)obj).getDisabledValue())
                     &&
                this.getOverflowValue().equals( ((NumberDataType)obj).getOverflowValue())
                     &&
                this.getUnderflowValue().equals(((NumberDataType)obj).getUnderflowValue())
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

      // now initialize XML fields
      // order matters!
      fieldOrder.add(INFINITE_XML_FIELD_NAME);
      fieldOrder.add(NEG_INFINITE_XML_FIELD_NAME);
      fieldOrder.add(NOT_A_NUMBER_XML_FIELD_NAME);
      fieldOrder.add(DISABLED_XML_FIELD_NAME);
      fieldOrder.add(OVERFLOW_XML_FIELD_NAME);
      fieldOrder.add(UNDERFLOW_XML_FIELD_NAME);

      // FIX : default is INTYTPE:Decimal
      fieldHash.put(INFINITE_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(NEG_INFINITE_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(NOT_A_NUMBER_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(DISABLED_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(OVERFLOW_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(UNDERFLOW_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));

    }


}

