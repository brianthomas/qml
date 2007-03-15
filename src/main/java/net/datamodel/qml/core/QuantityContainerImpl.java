
// CVS $Id$

// QuantityContainerImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.core;

import java.io.Writer;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.ObjectWithQuantities;
import net.datamodel.qml.support.Constants;

/**
 * This is a simple container class which holds quantities.
 * It is used internally by other quantities to hold a list of
 * one or more quantities which will be serialized as a group
 * of child objects.
 *
 * It is not intended for external (outside of the ObjectWithQuantities package) 
 * use as it can't be cast as a ObjectWithQuantities (this is intentional..this 
 * class models containers which aren't seperable from the parent 
 * quantity).
 *
 * For most people, you should use a CompositeQuantity instead.
 */
public class QuantityContainerImpl extends AbstractXMLSerializableObject {

    // FIELDS
    private static final String QLIST_XML_FIELD_NAME = new String("qList");

    // should we go ahead an serialize empty objects? The default
    // is "no".
    /**
     * @uml.property  name="serializeIfEmpty"
     */
    protected boolean serializeIfEmpty;

    // Methods
    //

    // Constructors

    // No-argument Constructor set to "private" to prevent use.
    private QuantityContainerImpl ( ) 
    {
       // init("");
    }

    /** Construct container with a particular XML nodename.
     */
    public QuantityContainerImpl (String nodeName )
    {
       init(nodeName, true);
    }

    /** Construct container with a particular XML nodename. Allow control
     * over whether or not it will be serialized to XML if it is empty.
     */
    public QuantityContainerImpl (String nodeName , boolean serializeIfEmpty) 
    {
       init(nodeName, serializeIfEmpty);
    }

    // Accessor Methods

    /** Set whether or not to serialize this object IF no quantities
     * are contained within it. Containers which have one or more quantities
     * are always serialized regardless of the setting of this field.
     * @return boolean value of whether to serialize or not empty containers.
     */ 
    public void setSerializeWhenEmpty ( boolean value) 
    {
        serializeIfEmpty = value;
    }

    /** Determine whether or not to serialize this object IF no quantities
     * are contained within it. Containers which have one or more quantities
     * are always serialized regardless of the setting of this field.
     * @return boolean value of whether to serialize or not empty containers.
     */
    public boolean getSerializeWhenEmpty ( ) 
    {
        return serializeIfEmpty;
    }

    /**
     * Add a quantity to this container. 
     * @param quantity 
     * @return boolean value of whether addition was successful or not. 
     */
    public boolean addQuantity ( ObjectWithQuantities quantity) 
    {
    	return getQuantityList().add(quantity);    
    }

    /**
     * Remove a quantity to from this container. 
     * @param quantity
     * @return boolean value of whether removal was successful or not. 
     */
    public boolean removeQuantity ( ObjectWithQuantities quantity) {
    	return getQuantityList().remove(quantity);    
    }

    /**
     * Get the list of quantities in this container. 
     * @return List  
     */
    public List getQuantityList ( ) {
       return (List) ((XMLSerializableField) fieldHash.get(QLIST_XML_FIELD_NAME)).getValue();
    }

    // Protected

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
    throws java.io.IOException
    {

       // if our list is empty, we dont serialize out if so directed
       if(getQuantityList().size() == 0 && !serializeIfEmpty)
           return false; 

       // serialize it
       return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, doFirstIndent );

    }

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init(String nodeName, boolean serialize )
    {

       resetFields();

       xmlNodeName = nodeName;

       serializeIfEmpty = serialize;

       fieldOrder.add(0, QLIST_XML_FIELD_NAME);

       fieldHash.put(QLIST_XML_FIELD_NAME, new XMLSerializableField( new Vector(), Constants.FIELD_CHILD_NODE_LIST_TYPE));

    }

}

