
// CVS $Id$

// VectorDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.util.Iterator;
import java.util.List;

import net.datamodel.qml.Component;
import net.datamodel.qml.BaseDataType;
import net.datamodel.qml.core.QuantityContainerImpl;
import net.datamodel.qml.core.XMLSerializableField;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Constants.NodeName;

/**
 * A vector (having one or more components) as a data type.
 */
public class VectorDataType extends BaseDataType 
{

    // Fields
    // 
    private static final String COMPONENTS_XML_FIELD_NAME = new String("components");

    // Methods

    // Constructors

    // No-argument Constructor
    public VectorDataType ( ) { 
       init();
    }

    // Accessor Methods

    /**
     * The object which represents the "no data available" value.
     */
    public Object getNoDataValue (  ) {
        String noDataValue = "";
        Iterator iter = getComponentList().iterator();
        while (iter.hasNext()) {
           Component comp = (Component) iter.next();
           noDataValue += Constants.VALUE_SEPARATOR_STRING + comp.getDataType().getNoDataValue().toString(); 
        }
        return noDataValue.trim();
    }

    /**
     * The object which represents the "no data available" value.
     * This is actually constructed from the component dataTypes held by 
     * the vector, therefore it is not kosher to call this method for VectorDataType.
     * @throws IllegalAccessException if called for VectorDataType.
     */
    public void setNoDataValue ( Object value  ) 
    throws IllegalAccessException
    {
        throw new IllegalAccessException("Can't set no data value for vectordatatype. You must set this in each of its components instead.");
    }

    /**
     * Add an object of type Component to represent a component of this vector.
     *
     * @throws NullPointerException if a null reference is passed.
     * @return boolean value of whether addition was successfull or not.
     */
    public boolean addComponent ( Component value )
    throws NullPointerException
    {
        // can't add ourselves as alternative value of ourselves (!)
        if(value == null)
           throw new NullPointerException ();

        return getComponentList().add(value);
    }

    /**
     * Remove an object of type Component from the component List.
     *
     * @return boolean value of whether removal was successfull or not.
     */
    public boolean removeAltValue ( Component value )
    {
        return getComponentList().remove(value);
    }

    /**
     * Get the list of component objects.
     * 
     * @return List of components held by this VectorDataType
     */
    public List getComponentList (  ) {
        return (List) ((QuantityContainerImpl) ((XMLSerializableField) fieldHash.get(COMPONENTS_XML_FIELD_NAME)).getValue()).getQuantityList();
    }

    /**
     * The number of bytes this data type represents.
     */
    public int numOfBytes ( ) {
        // FIX
        return -1;
    }

    /** Determine if other units are equivalent to these.
      * @@Overrides
      */
    public boolean equals (Object obj)
    {
        if (obj instanceof VectorDataType) {
            if (
                 super.equals(obj)
                      &&
// FIXME : should iterate thru to find equivalence
                 this.getComponentList().equals( ((VectorDataType)obj).getComponentList())
               )
            return true;
        }
        return false;
    }

    // 
    // Protected Methods
    //

    /** Special protected method used by constructor methods to
        conviently build the XML attribute list for a given class.
     */
    protected void init( )
    {

      resetFields();

      xmlNodeName = Constants.NodeName.VECTOR_DATATYPE;

      // order matters! these are in *reverse* order of their
      // occurence in the schema/DTD
      fieldOrder.add(0, COMPONENTS_XML_FIELD_NAME);

      fieldHash.put(COMPONENTS_XML_FIELD_NAME, new XMLSerializableField( new QuantityContainerImpl(null, false), Constants.FIELD_CHILD_NODE_TYPE));

    }

}

