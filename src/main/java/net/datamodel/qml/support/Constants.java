// CVS $Id$

// Constants.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.support;

// Hmm. This file amounts to a header file in C. 
//
// TODO: Put these various things in the objects where they are used. 
// as very few overlap and are shared between classes.

/**
 * Stores constants. Usually these are things which are shared across
 * several classes.
 * @version $Revision$
 */

public abstract class Constants {

  /** The name of the relevant version of the DTD file for this package.
  */
  public static final String QML_DTD_NAME = "QML_06.dtd";

  /** The name of the relevant version of the schema file for this package.
   */
  public static final String QML_SCHEMA_NAME = "QML_06.xsd";

  /** The namespace URI of this package.
   */
  public static final String QML_NAMESPACE_URI = "http://www.data-model.net/Quantity";

  /** The version of QML spec this package implements */
  public static final String QML_SPEC_VERSION = "0.06";

  /** The version of XML that will be output from a toXML* method call.
  */
  public static final String XML_SPEC_VERSION = "1.0";

  /** The XML schema-instance version used by this package. 
   */
  public static final String XML_SCHEMA_INSTANCE_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema-instance"; 

  /** The XML schema version used by this package. 
   */
  public static final String XML_SCHEMA_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema";

  /** The namespace URI of the mappings package.
   */
// FIX : this is a quick hack to avoid creation of the separate mappings package
// right now.
  public static final String MAPPING_NAMESPACE_URI = "http://www.data-model.net/Mappings";
 
  public static final String QML_PROPERTY_URN = "urn:qml-property";
  public static final String QML_REF_FRAME_URN = "urn:qml-ref-frame";
  

  /** QML notation information.
   */
  public static final String QML_NOTATION_NAME = "qml";
  public static final String QML_NOTATION_PUBLICID = "application/quantity";

  // size of intial hash tables that hold attributes
  public static final int INIT_ATTRIBUTE_HASH_SIZE = 6;

  public static final int EXPAND_VALUELIST_FACTOR = 2;

  // XML Field Types. Tells how the information within a class
  // will be serialized in XML. 
  // "ATTRIB_TYPE" serializes information as an attribute on the node. Only
  //               simple String/Integer/Double classes are supported (must 
  //               have toString() method). 
  // "CHILD_NODE_TYPE" serializes an object as a child xml node. 
  // "CHILD_NODE_LIST_TYPE" serializes one or more objects as child xml nodes. 
  // "CHILD_PCDATA_TYPE" serializes the object as PCDATA within the node
  //                     (class must have toString() method). 
  // "CHILD_CDATA_TYPE" serializes the object as CDATA within the node
  //                     (class must have toString() method). 
  // "CHILD_TAGGED_PCDATA_TYPE" special type..for allowing <value>item</value>, <value>item2</value>,.. 
  //                     serialization without having to resort to having objects in
  //                     memory, which would be quite wasteful. 
  // "CHILD_TAGGED_CDATA_TYPE" special type.. like the former but each child node has 
 //                      cdata sections, e.g. <value><![CDATA[item]]></value>

// FIX: replace with an enumeration ??
  public static final String FIELD_ATTRIB_TYPE = "0";
  public static final String FIELD_CHILD_NODE_LIST_TYPE  = "1";
  public static final String FIELD_CHILD_NODE_TYPE = "2";
  public static final String FIELD_PCDATA_TYPE = "3";
  public static final String FIELD_CDATA_TYPE = "4";
  public static final String FIELD_TAGGED_CDATA_TYPE = "5";
  public static final String FIELD_TAGGED_PCDATA_TYPE = "6";
  public static final String[] XML_FIELD_TYPE_LIST = {
                                           FIELD_ATTRIB_TYPE, FIELD_CHILD_NODE_TYPE, 
                                           FIELD_CHILD_NODE_LIST_TYPE, FIELD_PCDATA_TYPE,
                                           FIELD_CDATA_TYPE, FIELD_TAGGED_CDATA_TYPE, 
                                           FIELD_TAGGED_PCDATA_TYPE
                                                     };

  // the default tagged value for "tagged data"
  public static final String TAGGED_DATA_NODE_NAME = "value";

  // System related stuff.. lets cache it.
  public static final String NEW_LINE = System.getProperty("line.separator");
  public static final String FILE_SEP = System.getProperty("file.separator");
  

  // Ref Q serialization choices
  public static final int REF_QUANTITY_EXPAND = 0;
  public static final int REF_QUANTITY_COLLAPSE = 1; // the 'default' 

  // Values serialization choices
  public static final int VALUE_SERIALIZE_CONTAINER = 0; // container may determine
  public static final int VALUE_SERIALIZE_TAGGED = 1;
  public static final int VALUE_SERIALIZE_SPACE = 2;

  //Integer types
  public static final String INTEGER_TYPE_DECIMAL = "decimal";
  public static final String INTEGER_TYPE_HEX = "hexadecimal";
  public static final String INTEGER_TYPE_OCTAL = "octal";
  public static final String[] INTEGER_TYPE_LIST = { INTEGER_TYPE_DECIMAL, 
                                                     INTEGER_TYPE_HEX, 
                                                     INTEGER_TYPE_OCTAL };

  /* IO Section */

  // Binary Endianess.
  public static final String BIG_ENDIAN = "BigEndian";
  public static final String LITTLE_ENDIAN = "LittleEndian";
  public static final String[] ENDIANS_LIST = { BIG_ENDIAN, LITTLE_ENDIAN };

  // what bits are allowed in floating point numbers 
  public static final int[] FLOATING_POINT_BITS_LIST = { 32, 64 }; 

  // what bits are allowed in binary integer numbers
  public static final int[] INTEGER_BITS_LIST = {  4, 16, 32, 64 };

  // IO encodings
  public static final String IO_ENCODING_UTF_8 = "UTF-8";
  public static final String IO_ENCODING_UTF_16 = "UTF-16";
  public static final String IO_ENCODING_ISO_8859_1 = "ISO-8859-1";
  public static final String IO_ENCODING_ANSI = "ANSI";
  public static final String[] IO_ENCODINGS_LIST = { IO_ENCODING_UTF_8, IO_ENCODING_UTF_16,
                                                     IO_ENCODING_ISO_8859_1, IO_ENCODING_ANSI };

  // Uuencodings (Is there a better name??) 
  public static final String DATA_ENCODING_UUENCODED = "uuencoded";
  public static final String DATA_ENCODING_BASE64 = "base64";
  public static final String[] DATA_ENCODING_LIST = { DATA_ENCODING_UUENCODED, DATA_ENCODING_BASE64 };

  // Compression types. All are various common algorithms
  public static final String DATA_COMPRESSION_ZIP = "zip";
  public static final String DATA_COMPRESSION_GZIP = "gzip";
  public static final String DATA_COMPRESSION_BZIP2 = "bzip2";
  public static final String DATA_COMPRESSION_XMILL = "XMILL"; // IBM makes this 
  public static final String DATA_COMPRESSION_COMPRESS = "compress";

  public static final String[] DATA_COMPRESSION_LIST = { DATA_COMPRESSION_ZIP, DATA_COMPRESSION_GZIP, 
                                                         DATA_COMPRESSION_BZIP2, DATA_COMPRESSION_XMILL, 
                                                         DATA_COMPRESSION_COMPRESS };

  /** The character sequence to use if we serialize values in non-tagged format. 
   */
  public static String VALUE_SEPARATOR_STRING = " ";

  /** The name of the XML "size" attribute in Quantities. 
   */
  public static String QID_ATTRIBUTE_NAME = "qid";
  public static String QIDREF_ATTRIBUTE_NAME = "qidRef";
  public static String SIZE_ATTRIBUTE_NAME = "size";

  /** The key used for finding the data/value(s) field.
   */
  public static String DATA_FIELD_NAME = "data";

  /*
       Now, Some defines based on the DTD/schema.
       Change these as needed to reflect new namings of same nodes as they occur.
    */
  public static class NodeName
  {
       // *sigh* cant decide if making this hashtable is better or not.
       public static final String ALTERN_VALUES = "altValues";
       public static final String ATOMIC_QUANTITY = "atomicQuantity";
       public static final String AXISFRAME = "axisFrame";
       public static final String COMPONENT = "component";
       public static final String COMPOSITE_QUANTITY = "compositeQuantity";
       public static final String FLOAT_DATATYPE = "float";
       public static final String INTEGER_DATATYPE = "integer";
       public static final String LIST_QUANTITY = "listQuantity";
       public static final String MATRIX_QUANTITY = "matrixQuantity";
       public static final String REFERENCE_QUANTITY = "refQuantity";
       public static final String STRING_DATATYPE = "string";
       public static final String TRIVIAL_QUANTITY = "trivialQuantity";
       public static final String VALUE = "value";
       public static final String VALUES = "values";
       public static final String VECTOR_DATATYPE = "vector";
       public static final String UNITS = "units";
  }

  // type names from XML schema for various elements
  public static class NodeTypeName
  {
       public static final String ALTERN_VALUES = "AltValuesContainerType";
       public static final String ATOMIC_QUANTITY = "AtomicQuantityType";
       public static final String AXISFRAME = "AxisFrameType";
       public static final String COMPONENT = "ComponentType";
       public static final String COMPOSITE_QUANTITY = "CompositeQuantityType";
       public static final String FLOAT_DATATYPE = "floatType";
       public static final String INTEGER_DATATYPE = "integerType";
       public static final String LIST_QUANTITY = "ListQuantityType";
       public static final String MATRIX_QUANTITY = "MatrixQuantityType";
       public static final String MAP = "MappingType";
       public static final String QUANTITY = "QuantityType"; // base class for all Q's 
       public static final String QUANTITY_CONTAINER = "QuantityContainer"; 
       public static final String REFERENCE_QUANTITY = "refQuantityType";
       public static final String STRING_DATATYPE = "stringType";
       public static final String TRIVIAL_QUANTITY = "TrivialQuantityType";
       public static final String VALUE = "valueType";
       public static final String VALUES = "ValuesContainer";
       public static final String VECTOR_DATATYPE = "vectorType";
       public static final String UNITS = "stringUnitsType";

  }

}

