
// CVS $Id$

// Utility.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.dom;

import java.net.URI;

import net.datamodel.qml.Constant;

import org.xml.sax.Attributes;


/**
 * Shared (utility) routines.
 * @version $Revision$
 */

  public class Utility {

    /** Determine if passed value is valid integer type. 
     */
    public static boolean isValidIntegerType(String strIntegerType) {
      String[] integerTypeList = Constant.INTEGER_TYPE_LIST;
      int stop = integerTypeList.length;
      for (int i = 0; i < stop; i++) {
        if (strIntegerType.equals(integerTypeList[i]))
          return true;
      }

      return false;
    }

    /** Determine if the passed string is a valid attribute type.
     */
    public static boolean isValidXMLSerializableFieldType(String strXMLSerializableFieldType) {
      String[] attributeTypeList = Constant.XML_FIELD_TYPE_LIST;
      int stop = attributeTypeList.length;
      for (int i = 0; i< stop; i++) {
        if (strXMLSerializableFieldType.equals(attributeTypeList[i]))
          return true;
      }
      return false;
    }
    
    /*
    public static URI getURIFromAttribs(Attributes attrs) {
    	// FIXME: this will crash!
    	String strValue = attrs.getValue(net.datamodel.soml.Constant.SOML_RDFTYPE_FIELD_NAME); 
    	URI uri = null;
    	try {
    		uri = new URI (strValue);
    	} catch (Exception e) {
    		// do nothing, pass thru 
    	}
    	return uri; 
    }
    */

    /** Determine if string is a valid xml declaration standalone 
     *  attribute value.
     */
    public static boolean isValidStandalone (String str) {

        if ( str != null &&
                (str.equals("yes") || str.equals("no")) )
           return true;

        return false;
    }

/*
// FIX : remove extra utility stuff we don't need.

    public static boolean isValidEndian(String strEndian) {

      // tagged dataformat (which must be all ASCII) will need to have
      // the null value set here.
      // if (strEndian == null) return true;

      String[] endianList = Constant.ENDIANS_LIST;
      int stop = endianList.length;
      for (int i = 0; i < stop; i++) {
        if (strEndian.equals(endianList[i]))
          return true;
      }

      return false;
    }

     public static boolean isValidIOEncoding(String strEncoding) {
      String[] encodingList = Constant.IO_ENCODINGS_LIST;
      int stop = encodingList.length;
      for (int i = 0; i< stop; i++) {
        if (strEncoding.equals(encodingList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidDatatype(String strDatatype) {

      if (strDatatype == null) return true;

      String[] dataTypeList = Constant.DATATYPE_LIST;
      int stop = dataTypeList.length;
      for (int i = 0; i < stop; i++) {
        if (strDatatype.equals(dataTypeList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidDataEncoding(String strDataEncoding) {
      String[] dataEncodingList = Constant.DATA_ENCODING_LIST;
      int stop = dataEncodingList.length;
      for (int i = 0; i < stop; i++) {
        if (strDataEncoding.equals(dataEncodingList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidRelationRole(String strRole) {
       String[] roleList = Constant.RELATION_ROLE_LIST;
       int stop = roleList.length;
       for (int i = 0; i < stop; i++) {
          if (strRole.equals(roleList[i])) return true;
       }
       return false;
    }

    public static boolean isValidDataCompression(String strDataCompression) {
      String[] dataCompressionList = Constant.DATA_COMPRESSION_LIST;
      int stop = dataCompressionList.length;
      for (int i = 0; i < stop; i++) {
        if (strDataCompression.equals(dataCompressionList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidFloatBits (int bits) {
      int[] bitList = Constant.FLOATING_POINT_BITS_LIST;
      int stop = bitList.length;
      for (int i = 0; i < stop; i++) {
         if (bits == bitList[i]) return true;
      }
      return false;
    }

    public static boolean isValidIntegerBits (int bits) {
      int[] bitList = Constant.INTEGER_BITS_LIST;
      int stop = bitList.length;
      for (int i = 0; i < stop; i++) {
         if (bits == bitList[i]) return true;
      }
      return false;
    }

    public static boolean isValidBinaryIntegerSigned (String strSigned) {

        if ( strSigned != null && 
                (strSigned.equals("yes") || strSigned.equals("no")) ) 
           return true;

        return false;
    }

    public static boolean isValidDataRepeatable (String strRepeatable) {
        if ( strRepeatable != null &&
                (strRepeatable.equals("yes") || strRepeatable.equals("no")) )
           return true;
        return false;
    }

    public static boolean isValidValueSpecial(String strValueSpecial) {
      if (strValueSpecial == null) return true;
      String[] valueSpecialList = Constant.VALUE_SPECIAL_LIST;
      int stop = valueSpecialList.length;
      for (int i = 0; i < stop; i++) {
        if (strValueSpecial.equals(valueSpecialList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidValueInequality(String strValueInequality) {
      if (strValueInequality == null) return true;
      String[] valueInequalityList = Constant.VALUE_INEQUALITY_LIST;
      int stop = valueInequalityList.length;
      for (int i = 0; i < stop; i++) {
        if (valueInequalityList.equals(valueInequalityList[i]))
          return true;
      }
      return false;
    }

    public static boolean isValidNumberObject (Object numberObj) 
    {
       if (numberObj != null && numberObj instanceof Number) return true;
       return false;
    }
*/

  }  //end of Utility class

