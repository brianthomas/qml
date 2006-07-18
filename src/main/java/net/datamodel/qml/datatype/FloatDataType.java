
// CVS $Id$

// FloatDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
import net.datamodel.qml.support.Constants.NodeName;

/**
 * Class FloatDataType
 * An IEEE floating point datatype (scalar). May be serialized as either float or exponential (scientific notation) depending on whether or not an exponent value is specified.
 */
public class FloatDataType extends NumberDataType {
    // Fields

    /** The precision (length after the ".") of this floating point datatype, in characters.
     * (precision is stored in this fashion so as to separate it out from the actual encoding of the text).
     */
    private static final String PRECISION_XML_FIELD_NAME = new String("precision");

   /** The exponent width (length after the "e") of this floating point datatype, 
     * in characters (precision is stored in this fashion so as to separate it 
     * out from the actual encoding of the text). If exponent is greater than 1, 
     * then an "e" will be inserted in the serialization of the field, so that 
     * scientific notation is used to represent the number.
    */
    private static final String EXPONENT_XML_FIELD_NAME = new String("exponent");

    // Methods

    // Constructors

    /** No-argument Constructor
     */
    public FloatDataType ( ) { 
       init();
    }
    // Accessor Methods

    /** The precision (length after the ".") of this floating point datatype, in characters.
     * (precision is stored in this fashion so as to separate it out from the actual encoding of the text).
     */
    public Integer getPrecision (  ) {
        return (Integer) ((XMLSerializableField) fieldHash.get(PRECISION_XML_FIELD_NAME)).getValue();
    }

    /** The precision (length after the ".") of this floating point datatype, in characters.
     * (precision is stored in this fashion so as to separate it out from the actual encoding of the text).
     */
// FIX : throw error for incorrect value
    public void setPrecision ( Integer value ) {
        ((XMLSerializableField) fieldHash.get(PRECISION_XML_FIELD_NAME)).setValue(value);
    }

   /** The exponent width (length after the "e") of this floating point datatype,
     * in characters (precision is stored in this fashion so as to separate it
     * out from the actual encoding of the text). If exponent is greater than 1,
     * then an "e" will be inserted in the serialization of the field, so that
     * scientific notation is used to represent the number.
    */
    public Integer getExponent (  ) {
        return (Integer) ((XMLSerializableField) fieldHash.get(EXPONENT_XML_FIELD_NAME)).getValue();
    }

   /** The exponent width (length after the "e") of this floating point datatype,
     * in characters (precision is stored in this fashion so as to separate it
     * out from the actual encoding of the text). If exponent is greater than 1,
     * then an "e" will be inserted in the serialization of the field, so that
     * scientific notation is used to represent the number.
    */
    public void setExponent ( Integer value  ) {
        ((XMLSerializableField) fieldHash.get(EXPONENT_XML_FIELD_NAME)).setValue(value);
    }

    /**
     * The number of bytes this data type represents.
     */
    public int numOfBytes ( ) {
        return getWidth().intValue();
    }

    /** Determine if other units are equivalent to these 
      * @@Overrides
      */
    public boolean equals (Object obj) 
    {
       if (obj instanceof FloatDataType) {
           if (
                super.equals(obj)
                     &&
                this.getExponent().equals( ((FloatDataType)obj).getExponent())
                     &&
                this.getPrecision().equals( ((FloatDataType)obj).getPrecision())
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
      xmlNodeName = Constants.NodeName.FLOAT_DATATYPE;

      setWidth(new Integer(4));

      try {
          setNoDataValue(new Double(-9.9));
       } catch (Exception e) { }

      // now initialize XML fields
      // order matters!
      fieldOrder.add(PRECISION_XML_FIELD_NAME);
      fieldOrder.add(EXPONENT_XML_FIELD_NAME);

      fieldHash.put(EXPONENT_XML_FIELD_NAME, new XMLSerializableField((Integer) null, Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(PRECISION_XML_FIELD_NAME, new XMLSerializableField(new Integer(1), Constants.FIELD_ATTRIB_TYPE));

    }



}

