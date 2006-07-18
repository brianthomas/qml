
// CVS $Id$

// TrivialQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.QuantityWithValues;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.XMLSerializableObject;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Constants.NodeName;

/**
 * A restricted atomic quantity. The trivial quantity may only hold a single "unitless", 
 * string value.
 */
public class TrivialQuantityImpl extends AtomicQuantityImpl implements QuantityWithValues {

    // Fields

    // Methods
    //

    // Constructors

    // No-arg constructor 
    public TrivialQuantityImpl ( ) { 
       init(-1);
    }

    /** Constuct the quantity with a number of pre-allocated locations (capacity) 
     * for the list of values it contains.
     */
    public TrivialQuantityImpl ( int capacity ) { 
       if(capacity > 1) 
         throw new IllegalArgumentException("TQ can't have a capacity greater than 1!");
       init(capacity);
    }

    /* Construct this quantity with mapping rather than explicitly holding
      * values. Trivial quantities DONT support this, so DONT use this constructor.
      * @throws IllegalArgumentException if called.
      */
/*
    public TrivialQuantityImpl ( ValueMapping mapping )
    {
       throw new IllegalArgumentException("TQ can't have a mapping!");
    }
*/

    // Accessor Methods

    /**
     * Set the value of dataType. As this is, by definition, restricted to "String", 
     * any other DataType will throw an error. 
     * @throws IllegalArgumentException if any non-String dataType object is passed.
     */
    public void setDataType ( DataType value  ) 
    throws IllegalArgumentException
    {
        if (value instanceof StringDataType) {
           super.setDataType(value);
        } else {
           throw new IllegalArgumentException("setDataType other than StringDataType not allowed in Trivial Quantity");
        }
    }

    /**
     * Set the value of units. As this is, by definition, restricted to "Unitless", any
     * other Units will throw an error. 
     * @throws IllegalArgumentException if any non-empty units object (e.g. "unitless") is passed.
     */
    public void setUnits ( Units value ) 
    throws IllegalArgumentException
    {
        if (value.getString().equals("")) {
           super.setUnits(value);
        } else {
           throw new IllegalArgumentException("setUnits other than Unitless not allowed in Trivial Quantity");
        }
    }

    /**
     * Set the value at the specified location. These type of data are NOT allowed for TrivialQuantities.
     * @param obj Byte value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceedi
ng locations.
     */
    public void setValue (Byte obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Byte data not allowed in Trivial Quantity");
    }

    /**
     * Set the value at the specified location. These type of data are NOT allowed for TrivialQuantities.
     * @param obj Double value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Double obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Double data not allowed in Trivial Quantity");
    }

    /**
     * Set the value at the specified location. These type of data are NOT allowed for TrivialQuantities.
     * @param obj Integer value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Integer obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Integer data not allowed in Trivial Quantity");
    }

    /**
     * Set the value at the specified location. These type of data are NOT allowed for TrivialQuantities.
     * @param obj Short value to set. Value cannot be "null" (use a noDataValue instead).
     * @param loc Locator object to indicate where to set the value.
     * @throws IllegalAccessException when called for mapping-based quantities.
     * @throws IllegalArgumentException or a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
     */
    public void setValue (Short obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Short data not allowed in Trivial Quantity");
    }

    // Operations

    // Protected ops

    /** returns true if there were child nodes to handle */
    protected boolean handleChildNodes(Hashtable idTable, Hashtable prefixTable, Writer outputWriter, String nodeNameString,
                                       String indent, Vector childObjs, XMLSerializableField PCDATA)
    throws IOException
    {

       if ( childObjs.size() > 0 )
       {

          Object value = null;

          // deal with child object/list XML fields, if any in our list
          int objs_size = childObjs.size();
          for (int i = 0; i < objs_size; i++) {
            XMLSerializableField field = (XMLSerializableField) childObjs.get(i);

            if (field.getType() == Constants.FIELD_CHILD_NODE_TYPE)
            {

               XMLSerializableObject containedObj = (XMLSerializableObject) field.getValue();
               if(containedObj instanceof ValueContainer)
               {
                   value = ((ValueContainer) containedObj).getFirstValue();
                   break;
               }

            } else {
               // do nothing with other children
            }

          }

          // close the opening tag, print our value
          if (nodeNameString != null && value != null) {
             if(!nodeNameString.equals(""))
                outputWriter.write(">");

             outputWriter.write(value.toString());
             return true;
          }

        }

        return false;
    }

    protected void doClosingNodeForChildren (String nodeNameString, String indent, boolean hasPCDATA,
                                             boolean isPrettyOutput,  Writer outputWriter)
    throws IOException
    {

          // ok, now deal with closing the node
          if (nodeNameString != null && !nodeNameString.equals("")) {

               if(!nodeNameString.equals(""))
                   outputWriter.write("</"+nodeNameString+">");
          }

    }

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init( int capacity )
    {

       super.init(capacity);

       xmlNodeName = Constants.NodeName.TRIVIAL_QUANTITY;

    }

}


