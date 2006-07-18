
// CVS $Id$

// Specification.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;

import org.apache.log4j.Logger;

/** a singleton class that manages some specification
 * parameters related to .
 */

public class Specification {
	
	private static final Logger logger = Logger.getLogger(IllegalCharDataHandlerFunc.class);
	
  //
  //Fields
  //
  private static Specification instance;

  private static Boolean mutex = new Boolean(true);

  /** Tells how the output values should be serialized.
   *  Choices are TAGGED, or SPACE or CONTAINER. The last choice
   *  allows the individual values containers to determine how to 
   *  best serialize their values. These settings dont effect how
   *  values are *read in* by the QMLReader.
   */
  private static int serializeValuesStyle = Constants.VALUE_SERIALIZE_CONTAINER;

  /** Tells how the output referenced Q's should be serialized.
   *  Choices are EXPAND, or COLLAPSE. The first choice results
   *  in the referenced quantity simply being expanded to the full
   *  set of child nodes with a new Id for the expanded Q. The 
   *  latter choice places a simple "refQuantity" node in place 
   *  of the refrenced Q (with appropriate refId).
   */
  private static int serializeRefQuantityStyle = Constants.REF_QUANTITY_COLLAPSE;

  /**
 * Stores whether nicely formatted  should be output from any toXML* method. Nice formatting includes nested indentation and return characters to improve human readability of output  (but blows up the size of the  file!).
 * @uml.property  name="prettyOutput"
 */
  private boolean prettyOutput = false;

  /** The indentation string that will be used for every nesting level within an
      output . For example, if the sPrettyOutputIndentation string consists
      of 3 spaces, then a doubly nested node will be indented 6 spaces, its parent
      node will be indented 3 spaces and the root node will not be indented at all.
      You can be creative with the indentation: any sequence of characters is valid
      (no need to just use spaces!).
  */
  private static String prettyOutputIndentation = "  ";

  /** whether or not to print attributes of size "1".
   */
  private static boolean printSizeOneAttribs = false;

  /** The default allocation size for each dimension within all  arrays.
      The practical meaning of this field is that it indicates the initial
      size of each  Axis/FieldAxis (the number of axis values/fields along
      the axis) and the number of data cells within a dimension of the 
      DataCube object. If more axis values/fields/datacells are placed on a
      given Axis/FieldAxis or data in a unallocated spot within the dataCube
      then the package allocates the needed memory and enlarges the
      DataCube/Axis/FieldAxis objects as it is needed.

      This automated allocation is slow however, so it is desirable, IF you
      know how big your arrays will be, to pre-set this value to encompass your
      data set. Doing so will to improve efficency in some cases. Note that if
      you are having keeping all of your data in memory (a multi-dimensional
      dataset) it may be desirable to DECREASE the value.
  */

  /**
 * This is the XML parser to use by the reader. The default is to use the Xerces (version 2) parser.
 * @uml.property  name="xMLParserClass"
 */
  private String XMLParserClass = "org.apache.xerces.parsers.SAXParser";

 // private String QMLDocumentClass = "net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl";

  /**
 * The default starting quantity (List/Matrix) compacity for values.
 * @uml.property  name="defaultValueContainerCapacity"
 */
  private int defaultValueContainerCapacity = 10;

  /**
   * This private constructor is defined so the compiler won't generate a
   * default public contructor
   */
  private Specification() { }

  /**
   * Return a reference to the only instance of this class
   */

  public static Specification getInstance() {
    if (instance == null) {
      synchronized (mutex) {
        if (instance == null)
          instance = new Specification();
      } //synchronized
    }
    return instance;
  }

  //
  //Get/Set methods
  //

  /** Set the name of the XML parser we want to use. The supplied string must
      be the classname of the desired parser (ex. "com.sun.xml.parser.Parser")
  */
  public void setXMLParser (String parserName) {
    synchronized (mutex) {
      XMLParserClass = parserName;
    }
  }

  /** Get the class name of the XML parser that will be used by the  reader.
   */
  public String getXMLParser () {
    return XMLParserClass;
  }

  /** Get the output  format style.
      @return the value of sPrettyOutput field  (which is true if nicely formatted
               XML is to be outputted from any call to a toXML* method, false if not).
  */
  public boolean isPrettyOutput() {
    return prettyOutput;
  }

  /**
 * Set this to true for nicely formatted XML output from any call to a toXML* method. Setting this value will change the runtime behavior of all  Objects within an application.
 * @return  the value of prettyOutput field.
 * @uml.property  name="prettyOutput"
 */
  public void setPrettyOutput (boolean turnOnPrettyOutput) {
    synchronized (mutex) {
      prettyOutput = turnOnPrettyOutput;
    }
  }

  /** Determine whether or not quantities with size of "1" have that attribute printed out.
      @return the value of printSizeOneAttribs field.
   */
  public boolean printSizeAttribOfOne() {
     return printSizeOneAttribs; 
  } 

  /** Set whether or not quantities with size of "1" have that attribute printed out.
      @return the value of printSizeOneAttribs field.
   */
  public void setPrintSizeAttribOfOne(boolean value) {
     synchronized (mutex) {
        printSizeOneAttribs = value;
     }
  }

  /** Gets the indentation string that will be used for every nesting level
      within an output . For example, if the string consists of 3 spaces,
      then a doubly nested node will be indented 6 spaces, its parent node will
      be indented 3 spaces and the root node will not be indented at all.
      @return String object containing  output indentation.
  */
  public String getPrettyOutputIndentation() {
    return prettyOutputIndentation;
  }


  /** 
   *  get indentation length
   */
  public static int getPrettyOutputIndentationLength() {
    return prettyOutputIndentation.length();
  }


  /** Set the indentation string for PrettyOutput. You aren't limited to just spaces
     here, ANY sequence of characters may be used to indent your  documents.
  */
  public void setPrettyOutputIndentation(String indentString) {
     synchronized (mutex) {
      prettyOutputIndentation = indentString;
     }
  }

  /**
 * Get the default pre-allocation size of internal data container array.
 * @return  non-negative integer with the size.
 * @uml.property  name="defaultValueContainerCapacity"
 */
  public int getDefaultValueContainerCapacity(){
    return defaultValueContainerCapacity;
  }

  /**
 * Set the default pre-allocation size of internal data container array.
 * @uml.property  name="defaultValueContainerCapacity"
 */
  public void setDefaultValueContainerCapacity(int size) {
    synchronized (mutex) {
      if(size > 0)
         defaultValueContainerCapacity = size;
      else 
         logger.warn("Specification.setDefaultdataArraySize(): warning cannot set below 1. Ignoring request.");
    }
  }

  /** Determine status of how the output values should be serialized */
  public int getSerializeValuesStyle() {
     return serializeValuesStyle;
  }

  /** Set if the output values should be serialized in tagged format */
  public void setSerializeValuesStyle(int value) {
     serializeValuesStyle = value;
  }

 /** Determine status of how the output reference Q's should be serialized */
  public int getSerializeRefQuantityStyle() {
     return serializeRefQuantityStyle;
  }

 /** Set if the output Q's should be serialized as refQ's IF 
     repeated ids exist. 
  */
  public void setSerializeRefQuantityStyle(int value) 
  {
     serializeRefQuantityStyle = value;
  }

}

