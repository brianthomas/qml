
// CVS $Id$

// ListValueContainerImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.core;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.Locator;
import net.datamodel.qml.QuantityWithValues;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;

import org.apache.log4j.Logger;

/**
 * Container class for values held explicitly.
 * Right now, we are allowing any old Java object as values. It may be that we
 * will want to be more restrictive (e.g. only String, int/Integer, float/Float, etc)
 * objects to be allowed to be held by this class. In this fashion, we can 
 * automatically not have to worry about the user passing us wierd objects to
 * be held by the quantity..and allows us in the future we to be checking that the 
 * passed information conforms to the declared dataType of the parent quantity.
 */
public class ListValueContainerImpl extends XMLSerializableObjectImpl 
implements Cloneable,ValueContainer
{
	
	private static final Logger logger = Logger.getLogger(ListValueContainerImpl.class);
	
    // Fields..

    /* XML attribute names */
    protected static final String VALUES_XML_FIELD_NAME = new String("values");

    // other fields..
    // parent quantity to which this container belongs
    /**
     * @uml.property  name="parentQuantity"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    protected QuantityWithValues parentQuantity;

    /**
     * @uml.property  name="valueList" multiplicity="(0 -1)" dimension="1"
     */
    private Object[] valueList;
    /**
     * @uml.property  name="isNoDataValue" multiplicity="(0 -1)" dimension="1"
     */
    private byte [] isNoDataValue;
    
    /**
     * The present capacity of this container.
     * @uml.property  name="capacity"
     */
    private int capacity;
	/**
     * The maximum index which is a utilized location.
     * @uml.property  name="maxUtilizedIndex"
     */
    protected int maxUtilizedIndex;

    /**
     * @uml.property  name="cdataSerialization"
     */
    private boolean cdataSerialization;
    /**
     * @uml.property  name="taggedValuesSerialization"
     */
    private boolean taggedValuesSerialization;

    // Constructors

    // Constructor
    /** Vanilla constructor. Will create a list with default capacity
     * (Specification.getDefaultValueContainerCapacity() 
     */
    public ListValueContainerImpl ( QuantityWithValues parent ) 
    {
    	logger.debug("New ListValueContainerImpl");
       parentQuantity = parent;
       int capacity = Specification.getInstance().getDefaultValueContainerCapacity();
       init(capacity);
    }

    /** Constuct the container with a number of pre-allocated capacity of the list.
     */
    public ListValueContainerImpl ( QuantityWithValues parent, int capacity) 
    { 
    	logger.debug("New ListValueContainerImpl with special capacity of"+capacity);
       parentQuantity = parent;
       if(capacity < 1)
          capacity = Specification.getInstance().getDefaultValueContainerCapacity();
       init(capacity);
    }

    // Public Operations
    //
    
    /**
     * Get the maximum value of the list index which has been used.
     * @return  int
     * @uml.property  name="maxUtilizedIndex"
     */
    public int getMaxUtilizedIndex () {
    	return maxUtilizedIndex;
    }
    
    /**
     * Set the value at the specified location.
     * @param obj Byte value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator Locator object to indicate where to set the value.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Byte obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Set the value at the specified location.
     * @param obj Double value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Double obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Set the value at the specified location.
     * @param obj Float value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Float obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Set the value at the specified location.
     * @param obj Integer value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Integer obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Set the value at the specified location.
     * @param obj Short value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Short obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Set the value at the specified location.
     * @param obj (String) value to set. Value cannot be "null" (use a noDataValue instead).
     * @param locator (Locator) object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (String obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
         setValue((Object) obj, locator);
    }

    /**
     * Get the value at the specified location. For atomic quantities  only one location will exist.
     * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @return Object value at requested location.
     */
    public Object getValue ( Locator locator ) 
       throws IllegalArgumentException, NullPointerException
    {

       if(locator == null)
       {
          throw new NullPointerException("getValue passed null locator.");
       }

       // quick check to see if the locator is kosher, however
       if (locator.getParentQuantity() != this.parentQuantity)
       {
           throw new  IllegalArgumentException("Can't getValue with locator "+locator+" does not belong to quantity "+this.parentQuantity);
       }
       
       logger.debug("ValueContainer getValue has value:"+valueList[locator.getListIndex()]+" at list index"+locator.getListIndex()); 

       return valueList[locator.getListIndex()];

    }

    /*
     * Get a list of all values held by the container.
     * @return List of values held by this quantity.
     */
    public List getValueList ()
    {
    	logger.debug("ListValueContainer getValueList() returns"+valueList.toString());
       return java.util.Arrays.asList(valueList);
    }

    /**
     * Set the capacity of this container. Calling this method will re-initialize the values held by the container list. 
     * @param new_capacity  the new capacity of the container.
     * @throws IllegalArgumentException  if a value of 0 or less is passed.
     * @uml.property  name="capacity"
     */
    public void setCapacity (int new_capacity) 
    throws IllegalArgumentException
    {

       if(capacity <= 0) 
          throw new IllegalArgumentException("setCapacity() value <= 0 is illegal.");

      byte[] newBytes = new byte[new_capacity];
      Object[] newObjs = new Object[new_capacity];

      int size = (capacity < new_capacity) ? capacity : new_capacity;
      for (int i = 0; i < size; i++) {
         newBytes[i] = isNoDataValue[i];
         newObjs[i] = valueList[i];
      }

      isNoDataValue = newBytes;
      valueList = newObjs;

      capacity = new_capacity;

      // since we could have trimmed off some values, we
      // need to insure this is recorded properly
      if(maxUtilizedIndex > new_capacity)
          maxUtilizedIndex = new_capacity;

    }

    /**
     * Get the number of locations available. This isn't the same as the number of values which are actually held. Null values within the container reserve locations, but don't count as values.
     * @return  int value of number of locations.
     * @uml.property  name="capacity"
     */
    public int getCapacity ( )
    {
       return capacity;
    }

    /** Get the number of values (datum) held.
     *  @return int value of number of datum.
     */
    public int getNumberOfValues ( ) {
       return maxUtilizedIndex + 1;
    }

    /** Set the number of values held.
     * @throws IllegalArgumentException if a value of less than 0 or greater than the number of locations is passed.
     */
    public void setNumberOfValues (int value ) 
    throws IllegalArgumentException
    {

       if(value < 1)
          throw new IllegalArgumentException("setNumberOfValues() < 1 is illegal.");
       if(value > capacity)
          throw new IllegalArgumentException("setNumberOfValues() value > number of locations in list.");

       maxUtilizedIndex = value -1;

    }

   /** Utility method to set the value at the first location.
     * @param obj (Object) value to set. A null value means "noData".
     * @throws IllegalAccessException when called for mapping-based containers.
     */
    public void setFirstValue ( Object obj)
       throws IllegalAccessException
    {

       if (obj == null) {
          isNoDataValue[0] = 1;
       } else { 
          isNoDataValue[0] = 0;
       }

       if( maxUtilizedIndex < 0 )
          maxUtilizedIndex = 0;

       valueList[0] = obj;

    }

    /**
     * Get the first value of the quantity. This is a utility method
     * for getValue("Location origin").
     * @return Object at the first location.
     */
    public Object getFirstValue ( )
    {
       return valueList[0];
    }

    /**
     * Create a locator for this container.   
     * @return Locator 
     */
    public Locator createLocator ( ) {
        return parentQuantity.createLocator();
    }

    /**Make a deep copy of this Data object
     */
    public Object clone() throws CloneNotSupportedException {
       ListValueContainerImpl cloneObj = (ListValueContainerImpl) super.clone();
       synchronized (this) {
          synchronized (cloneObj) {
// FIX? what else?
             cloneObj.parentQuantity = parentQuantity;
          }
       }
       return cloneObj;
    }

   /**
     * Set whether or not to output value(s) as CDATASection(s).
     */
    public void setCDATASerialization (boolean value) {
       cdataSerialization = value;
    }

    /**
     * Determine whether or not value(s) are output as CDATASection(s).
     */
    public boolean getCDATASerialization ( ) {
       return cdataSerialization;
    }

    /**
     * Whether to serialize values as tagged or space-delimited.
     * @uml.property  name="taggedValuesSerialization"
     */
    public void setTaggedValuesSerialization (boolean value) {
       taggedValuesSerialization = value;
    }

    /**
     * Determine whether values are serialized as tagged or space-delimited.
     * @uml.property  name="taggedValuesSerialization"
     */
    public boolean getTaggedValuesSerialization ( ) {
       return taggedValuesSerialization;
    }

    // Protected Ops

    /**
     * @return boolean value of whether or not one or more an xml nodes were written.
     */
    protected boolean basicXMLWriter (
                                      Hashtable idTable,
                                      Hashtable prefixTable,
                                      Writer outputWriter,
                                      String indent,
                                      String newNodeNameString, 
                                      boolean doFirstIndent
                                    )
    throws IOException
    {

       // serialize it
       // If there are fewer than 2 values, serialize with NO node name
       // so that only the child "value" will appear instead of <values><value>..</value></values>
       if(getNumberOfValues() < 2 && newNodeNameString == null)
          return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, "", doFirstIndent);
       else
          return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, doFirstIndent );

    }

   /** returns true if there were child nodes to handle */
    protected boolean handleChildNodes(Hashtable idTable, Hashtable prefixTable, Writer outputWriter, String nodeNameString,
                                       String indent, Vector childObjs, XMLSerializableField PCDATA)
    throws IOException
    {

       Specification spec = Specification.getInstance();
       boolean writeTaggedValues = true;
       int serialize_style = spec.getSerializeValuesStyle();
       if(serialize_style == Constants.VALUE_SERIALIZE_SPACE) 
          writeTaggedValues = false;
       else if(serialize_style == Constants.VALUE_SERIALIZE_TAGGED 
    		   || serialize_style == Constants.VALUE_SERIALIZE_CONTAINER )
          writeTaggedValues = taggedValuesSerialization;

       // close the opening tag
       if (nodeNameString != null && !nodeNameString.equals(""))
       {
           outputWriter.write(">");

/*
           if (Specification.getInstance().isPrettyOutput() && writeTaggedValues)
           {
               String newindent = indent + spec.getPrettyOutputIndentation();
               outputWriter.write(Constants.NEW_LINE+newindent);
           }
*/
       }

       Object noDataValue = parentQuantity.getDataType().getNoDataValue();
       String noDataValueStr = entifyString(noDataValue.toString());

       // we write tagged values if requested OR we only have ONE value
       // (the latter is because we set the master nodeName to "", so its
       // not being written out :P
       if(writeTaggedValues || maxUtilizedIndex == 0) 
       {


          String prefix = getXMLNodePrefix(prefixTable);

          String nodeName = Constants.TAGGED_DATA_NODE_NAME;
          if(prefix != null && !prefix.equals("") && nodeName != null)
              nodeName = prefix + ":" + nodeName;

          for (int i = 0; i <= maxUtilizedIndex; i++) 
          {

             outputWriter.write("<"+nodeName+">");
   
             if(cdataSerialization)
                outputWriter.write("<![CDATA[");
   
             Object value = valueList[i];
             if(isNoDataValue[i] == 1 || value == null)
                outputWriter.write(noDataValueStr);
             else
                outputWriter.write(entifyString(value.toString()));
   
             if(cdataSerialization)
                outputWriter.write("]]>");
               
             outputWriter.write("</"+nodeName+">");
   
          } 

       } else { 

          String separator = Constants.VALUE_SEPARATOR_STRING;

          if(cdataSerialization)
             outputWriter.write("<![CDATA[");

          for (int i = 0; i <= maxUtilizedIndex; i++) 
          {

             Object value = valueList[i];
             if(isNoDataValue[i] == 1 || value == null)
                outputWriter.write(noDataValueStr);
             else
                outputWriter.write(entifyString(value.toString()));

             if(i != maxUtilizedIndex)
                outputWriter.write(separator);
          } 

          if(cdataSerialization)
             outputWriter.write("]]>");

       }

       return true;
    }

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init (int numOfLocations)
    {

       resetFields();

       xmlNodeName = "values";
       cdataSerialization = false;
       taggedValuesSerialization = true;

       // Code badness..This is onlY here to kludge basicXMLWriter into seeing we have pcdata
       fieldOrder.add("PCDATA");
       fieldHash.put("PCDATA", new XMLSerializableField("pcdataExists", Constants.FIELD_PCDATA_TYPE));

       resetValues(numOfLocations);

    }

    /** reset the values within the container. */
    protected void resetValues (int numOfLocations) 
    {

       isNoDataValue = new byte[numOfLocations];
       valueList = new Object[numOfLocations];

       capacity = numOfLocations;
       maxUtilizedIndex = -1;

    }

    /**
     * Set the value at the specified location.
     * @param Object value to set. Value cannot be "null" (use a noDataValue instead).
     * @param Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    protected void setValue (Object obj, Locator locator)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

       if (obj == null || locator == null)
       {
           throw new NullPointerException("Error: setValue - was passed a null object/locator parameter.");
       }

       if (locator.getParentQuantity() != this.parentQuantity)
       {
           throw new  IllegalArgumentException("Can't setValue with locator "+locator+" does not belong to quantity "+this.parentQuantity);
       }

       // ugh. Java wont let us set an unpopulated location. Check
       // to see size (number of populated locations) in order to
       // know which method to call
       int index = locator.getListIndex();

       if (obj == null) {
          isNoDataValue[index] = 1;
       } else {
          isNoDataValue[index] = 0;
       }

       if( index > maxUtilizedIndex)
          maxUtilizedIndex = index;

       if ( index > getCapacity())
          setCapacity(index * 2);

       valueList[index] = obj;

    }

// FIX
public void dump () {
    for (int i = 0; i <= maxUtilizedIndex; i++) {
       System.err.println(" data : "+valueList[i]);
    }
}

}

