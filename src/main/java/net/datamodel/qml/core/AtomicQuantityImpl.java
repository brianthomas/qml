
// CVS $Id$

// AtomicQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.Component;
import net.datamodel.qml.Locator;
import net.datamodel.qml.ObjectWithQuantities;
import net.datamodel.qml.QuantityWithValues;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.locator.ListLocatorImpl;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.Constants.NodeName;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

/**
 * A quantity which holds one value. The value may be any object.
 * @version $Revision$
 */
public class AtomicQuantityImpl extends ComponentImpl 
implements QuantityWithValues 
{
	private static final Logger logger = Logger.getLogger(AtomicQuantityImpl.class);
	
    // Fields
    // 
    /**
     * @uml.property  name="locatorList"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="net.datamodel.qml.Locator"
     */
    protected List locatorList;

    /* XML attribute names */

//    private static final String ACCURACY_XML_FIELD_NAME = new String("accuracy");
    private static final String MEMBER_XML_FIELD_NAME = new String("member");
    private static final String SIZE_XML_FIELD_NAME = Constants.SIZE_ATTRIBUTE_NAME;
    private static final String DATA_XML_FIELD_NAME = Constants.DATA_FIELD_NAME;

    /**
     * @uml.property  name="hasMapping"
     */
    protected boolean hasMapping;

    // Methods
    //

    // Constructors

    /** No argument constructor. Values will be explicitly held.
     */
    public AtomicQuantityImpl ( ) 
    { 
       init(1);
    }

    /** Construct this quantity with mapping rather than explicitly holding 
      * values. Values will be generated on demand from the (value) mapping.
     */
    public AtomicQuantityImpl ( ValueMapping mapping ) 
    { 
       init(mapping);
    }

    // Accessor Methods
    
    /** The number of allocated capacity to hold values within the quantity. For an
     *  atomic quantity, this is always "1".
     */
    public int getCapacity() {
       return getValueContainer().getCapacity(); // only ever one location
    }

    /**
     * The number of values within this quantity. 
     * For atomic quantities, this can only be "1".
     *
     * @return int value of number of values within this quantity.
     */
    public Integer getSize ( ) {
        return (Integer) ((XMLSerializableField) fieldHash.get(SIZE_XML_FIELD_NAME)).getValue();
    }

    /**
     * Set the number of values within this quantity. 
     */
    protected void setSize ( Integer value ) {
        ((XMLSerializableField) fieldHash.get(SIZE_XML_FIELD_NAME)).setValue(value);
    }

    /** Set the number of values witin this quantity. This method will
     *  allow the user to set the number of values within the quantity. 
     *  If the size is smaller than the existing one, then some of
     *  the already allocated values will be removed from the quantity. 
     *  If it is larger than the current size, new locations will be 
     *  filled with "noDataValues".
     * @throws IllegalArgumentException if size is less than 1.
     */
    public void setSize (int size) 
    throws IllegalArgumentException
    {
       if(size > 0)
       {
          getValueContainer().setNumberOfValues(size);
          setSize(new Integer(size));
       }
    }

    /*
     * Add an object of type ObjectWithQuantities to the List of accuracies.
     * 
     * @return boolean value of whether addition was successful or not.
     */
/*
    public boolean addAccuracy ( ObjectWithQuantities value  ) {
        return getAccuracyList().add(value);
    }
*/

    /*
     * Remove an object of type ObjectWithQuantities from the List of accuracies.
     * 
     * @return boolean value of whether removal was successful or not.
     */
/*
    public boolean removeAccuracy ( ObjectWithQuantities value  ) {
        return getAccuracyList().remove(value);
    }
*/

    /*
     * Get the list of accuracy objects (Quantities).
     * 
     * @return List of accuracies.
     */
/*
    public List getAccuracyList (  ) {
        return (List) ((XMLSerializableField) fieldHash.get(ACCURACY_XML_FIELD_NAME)).getValue();
    }
*/

    /**
     * Get a list of locators belonging to this quantity.
     * @return  List of Locator objects belonging to the quantity.
     * @uml.property  name="locatorList"
     */
    public List getLocatorList() {
       return locatorList;
    }

    /**
     * Get the container which holds the value(s) of this quantity.
     * 
     * @return the container which holds the value(s) of this quantity
     */
    public ValueContainer getValueContainer() {
        return (ValueContainer) ((XMLSerializableField) fieldHash.get(DATA_XML_FIELD_NAME)).getValue();
    }

    /**
     * Set the value container. All prior value(s) held by the quantity will be lost.
     */
    protected void setValueContainer ( ValueContainer value ) {
        if (value != null)
        {
            ((XMLSerializableField) fieldHash.get(DATA_XML_FIELD_NAME)).setValue(value);

            // update our size
            setSize(new Integer(getValueContainer().getNumberOfValues()));
        }
    }

    // Operations
    //

    /** Set whether or not we wish to serialize our value as cdata section.
     * @param val boolean value of cdata serialization
     */
    public void setValueCDATASerialization (boolean val) 
    {
        getValueContainer().setCDATASerialization(val);
    }

    /** Determine whether or not we wish to serialize our value as cdata section.
     * @return boolean value of cdata serialization
     */
    public boolean getValueCDATASerialization ()
    {
        return getValueContainer().getCDATASerialization();
    }

    /**
     * @return Object within this quantity.
     */
    public Object getValue ( ) 
    {
        return getValueContainer().getFirstValue();
    }

    /**
     * Get the value at the specified location. For atomic quantities  only one location will exist.
     * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @return Object value at requested location.
     */
    public Object getValue ( Locator loc ) 
        throws IllegalArgumentException, NullPointerException
    {
        return getValue();
    }

    /**
     * Set the value at the specified location.
     * @param obj Byte value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( Byte obj, Locator loc) 
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the value at the specified location.
     * @param obj Double value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( Double obj, Locator loc)
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the value at the specified location.
     * @param obj Float value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( Float obj, Locator loc)
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the value at the specified location.
     * @param obj Integer value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( Integer obj, Locator loc)
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the value at the specified location.
     * @param obj Short value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( Short obj, Locator loc)
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the value at the specified location.
     * @param obj String value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue ( String obj, Locator loc)
        throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {

        if (hasMapping)
           throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

        getValueContainer().setValue(obj,loc);
        updateSize();
    }

    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj Byte to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( Byte obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }
    
    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj Double to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( Double obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }   
    
    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj Float to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( Float obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }
    
    
    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj Integer to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( Integer obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }
    
    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj Short to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( Short obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }
    
    /**
     * Set the first (and prehaps only) value of this quantity.
     * @param obj String to set the value to. It may be "null".
     * @throws SetDataException when setting a value that is a quantity.
     */
    public void setValue ( String obj )
    throws SetDataException
    {
       try {
          getValueContainer().setFirstValue(obj);
       } catch (IllegalAccessException e) {
           logger.error("Can't set data in quantity because "+e.getMessage());
       }
       updateSize();
    }


   /**
     * Get the list of member Quantities.
     *
     * @return List of member quantities.
     */
    public List getMemberList ( ) {
        return (List) ((QuantityContainerImpl) ((XMLSerializableField) fieldHash.get(MEMBER_XML_FIELD_NAME)).getValue()).getQuantityList();
    }

    // 
    // Other methods
    //

    /**
     * Add an object of type ObjectWithQuantities to the List of member Quantities.
     * The only restrictions on membership are that a quantity may not "own"
     * itself, and only MatrixQuantities and CompositeQuantiites may have AxisFrames.
     * Furthermore, incorrectly dimensioned AxisFrames are not allowed.
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an AxisFrame
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This AxisFrame may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @throws IllegalArgumentException if adding self, an AxisFrame to the wrong ObjectWithQuantities Type, or the AxisFrame dimensionality is incorrect.
     * @throws NullPointerException if attempting to adding an null (!!)
     * @return boolean value of whether addition was successfull or not.
     */
    public boolean addMember ( ObjectWithQuantities member)
    throws IllegalArgumentException, NullPointerException
    {

        if (member == null)
           throw new NullPointerException();

        if(member == this)
        {
           throw new IllegalArgumentException(" Q.addMember() attempted to add self to member list");
        }
 
        // perform dimensional checks on axisFrame
        if (member instanceof AxisFrame)
           throw new IllegalArgumentException(" Q.addMember() attempted to add AxisFrame.");

        return getMemberList().add(member);
    }

    /**
     * Remove an object of type ObjectWithQuantities from the List memberVector
     *
     * @return boolean value of whether removal was successful or not.
     */
    public boolean removeMember ( ObjectWithQuantities value  ) {
       boolean status = getMemberList().remove(value);
       if (status)
          setSize(new Integer(getMemberList().size()));
       return status;
    }

    /**
     * Create a locator for this quantity. This method provided for
     * compliance with ObjectWithQuantities interface..atomic quantities don't 
     * have more than one location, so it is of little value to use them,
     * if you know you are dealing with an atomic quantity.
     */
    public Locator createLocator ( ) 
    {
        Locator loc = new ListLocatorImpl (this);
        locatorList.add(loc);
        return loc;
    }

    /** Set the XMLfields of this object using the passed (XML/SAX) Attributes.
        A field will be appended to the existing list of XML fields if
        that field doesnt already exist, otherwise, the value of the existing
        field is overridden by this method. This method appears in AtomicQ because
        we need to prevent setFields from setting the size of the quanitity (which
        is protected and not public field).
     */
    public void setFields (Attributes attrs) {

       synchronized (fieldHash) {
         // set object fields from an Attributes Object
         if (attrs != null) {
            // whip thru the list, setting each value
            int size = attrs.getLength();
            for (int i = 0; i < size; i++) {
               String name = attrs.getQName(i);
               String value = attrs.getValue(i); // yes, Attributes can only return strings

               // set it as appropriate to the type
               if (name != null && name != SIZE_XML_FIELD_NAME && value != null) {
                  if (this.fieldHash.containsKey(name)) {
                     setField(name,value);
                  } else {
                     addXMLSerializableField(name,value);
                  }
               }

            }
         }
       } //synchronize
    }

    /** Deep copy of this QML object.
        @return an exact copy of this QML object
     */
    public Object clone () throws CloneNotSupportedException
    {
        AtomicQuantityImpl cloneObj = (AtomicQuantityImpl) super.clone();

        cloneObj.hasMapping = this.hasMapping;
        cloneObj.locatorList = new Vector(); // all locators are lost
   //     cloneObj.data = data.clone();

        return cloneObj;
    }

    /** Determine equivalence between objects (quantities). Equivalence is the same
      * as 'equals' but without checking that the id fields between both
      * objects are the same.
      * @@Overrides 
      */
    public boolean equivalent ( Object obj ) 
    {

        if (obj instanceof QuantityWithValues ) 
	{
            if (
                  super.equivalent ((Component) obj)
                      &&
                  this.getSize().equals(((QuantityWithValues)obj).getSize())
                      &&
                      // FIXME : should iterate over all values an compare
//                  this.getValue().equals(((QuantityWithValues)obj).getValue())
//                      &&
                  this.getMemberList().equals(((ObjectWithQuantities)obj).getMemberList()) // FIXME : need to iterate over members 
               )
            return true;
        }
        return false;
    }


    // Protected 

   /**
     * @return boolean value of whether or not some content was written.
     */
    protected boolean basicXMLWriter (
                                      Hashtable idTable,
                                      Hashtable prefixNamespaceTable,
                                      Writer outputWriter,
                                      String indent,
                                      String newNodeNameString,
                                      boolean indentFirstNode
                                    )
    throws IOException
    {

         // we need to check to see if we are referencing some other Q.
         // IF so, then we WONT print out normally, rather, we will print
         // ourselves out as a referenceQuantity node.
         String id = getId();
         if (id != null && !id.equals("") && idTable != null)
         {
             ObjectWithQuantities idOwner = (ObjectWithQuantities) idTable.get(id);
             if(idOwner != null && idOwner != this)
             {

                Specification spec = Specification.getInstance();
                if( spec.getSerializeRefQuantityStyle() == Constants.REF_QUANTITY_COLLAPSE) 
                {

                   boolean isPretty = spec.isPrettyOutput();

                   if(isPretty && indentFirstNode)
                      outputWriter.write(indent);

                   outputWriter.write("<"+Constants.NodeName.REFERENCE_QUANTITY+" "+Constants.QIDREF_ATTRIBUTE_NAME+"=\""+id+"\"/>");

                   return true;
                } else { // reassign the id of this quantity
                   setId(findUniqueIdName(idTable,id));
                   idTable.put(getId(), this);
                   logger.warn("Reassigning quantity qid from:"+id+" to "+getId()+" to avoid collision of ids");
                }
             }
         }

         // use regular method
         return super.basicXMLWriter(idTable, prefixNamespaceTable, outputWriter, indent, newNodeNameString, indentFirstNode);
    }

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init(int capacity)
    {

       super.init();

       xmlNodeName = Constants.NodeName.ATOMIC_QUANTITY;

       hasMapping = false;

       locatorList = new Vector ();

       ValueContainer data = new ListValueContainerImpl(this, capacity);

       // now initialize XML fields
       // order matters! 
       fieldOrder.add(0, MEMBER_XML_FIELD_NAME);
       fieldOrder.add(SIZE_XML_FIELD_NAME);
//       fieldOrder.add(ACCURACY_XML_FIELD_NAME);
       fieldOrder.add(DATA_XML_FIELD_NAME);

//       fieldHash.put(ACCURACY_XML_FIELD_NAME, new XMLSerializableField( new QuantityContainerImpl("accuracy", false), Constants.FIELD_CHILD_NODE_LIST_TYPE));
       fieldHash.put(DATA_XML_FIELD_NAME, new XMLSerializableField(data, Constants.FIELD_CHILD_NODE_TYPE));
       fieldHash.put(MEMBER_XML_FIELD_NAME, new XMLSerializableField(new QuantityContainerImpl(null, false), Constants.FIELD_CHILD_NODE_TYPE));
       fieldHash.put(SIZE_XML_FIELD_NAME, new XMLSerializableField(new Integer(data.getNumberOfValues()), Constants.FIELD_ATTRIB_TYPE));

       setSize(1);
       updateSize();
    }

    /** init from mapping.
     */
    protected void init (ValueMapping mapping)
    {
       init(1);
       setValueContainer ((ValueContainer) mapping);
       hasMapping = true;
    }

    /** Update the size attribute from the data container.
     * 
     */
    protected void updateSize() {
    	// do nothing. Should always be '1'
//        setSize(new Integer(getValueContainer().getNumberOfValues()));
    }
    
}

