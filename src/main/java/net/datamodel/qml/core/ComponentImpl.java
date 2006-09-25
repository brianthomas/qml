
// CVS $Id$

// ComponentImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.URN;
import net.datamodel.qml.Units;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.units.UnitsImpl;

import org.apache.log4j.Logger;

/**
 * Class ComponentImpl
 * A component of a vector. This is essentially a quantity without any values.
 */
public class ComponentImpl extends XMLSerializableObjectImpl implements Component {

    // Fields
	private static final Logger logger = Logger.getLogger(ComponentImpl.class);

    /* XML attribute names */
    private static final String DATATYPE_XML_FIELD_NAME = new String("dataType");
    private static final String ID_XML_FIELD_NAME = new String("qid");
//    private static final String IMMUTABLE_XML_FIELD_NAME = new String("immutable");
    private static final String UNITS_XML_FIELD_NAME = new String("units");
    private static final String URN_XML_FIELD_NAME = "URN";
    
    // Methods
    //

    // Constructors

    // No-arguement Constructor
    public ComponentImpl ( ) { 
       init();
    }

    // Accessor Methods

    /**
     * The id of an instance of this class. It should be 
     * unique across all components and quantities within a 
     * given document/object tree.
     */
    public String getId ( ) {
        return (String) ((XMLSerializableField) fieldHash.get(ID_XML_FIELD_NAME)).getValue();
    }
    /**
     * The id of an instance of this class. It should be 
     * unique across all components and quantities within a 
     * given document/object tree.
     */
    public void setId ( String value  ) {
        ((XMLSerializableField) fieldHash.get(ID_XML_FIELD_NAME)).setValue(value);
    }
    
    /*
     *  (non-Javadoc)
     * @see net.datamodel.qml.SemanticObject#getURN()
     */
	public URN getURN() {
		try {
			return new URNImpl ((String) ((XMLSerializableField) fieldHash.get(URN_XML_FIELD_NAME)).getValue());
		} catch (Exception e) {
			logger.error("Invalid URN for object returned.");
			return (URN) null; // shouldnt happen as we only let valid URNs in..
		}
	}

	/** Set the URN, representing the semantic meaning, of this object.
	 * 
	 * @param value of the URN to set
	 */
	public void setURN (URN value) {
		// Take the URN and convert it to a string for storage in object/serialization.
		// Not optimal, but works (for now).
	    ((XMLSerializableField) fieldHash.get(URN_XML_FIELD_NAME)).setValue(value.toString());
	}
	
    /*
     * Whether or not this component/quantity is mutable (changeable).
     */
/*
    public Boolean getImmutable (  ) {
        return (Boolean) ((XMLSerializableField) fieldHash.get(IMMUTABLE_XML_FIELD_NAME)).getValue();
    }
*/
    /*
     * Whether or not this component/quantity is mutable (changeable).
     */
/*
    public void setImmutable ( Boolean value ) {
        ((XMLSerializableField) fieldHash.get(IMMUTABLE_XML_FIELD_NAME)).setValue(value);
    }
*/

    /**
     * Get the value of units
     * 
     * @return the value of units
     */
    public Units getUnits ( ) {
        return (Units) ((XMLSerializableField) fieldHash.get(UNITS_XML_FIELD_NAME)).getValue();
    }

    /**
     * Set the value of units
     * 
     * 
     */
    public void setUnits ( Units value  ) {
        ((XMLSerializableField) fieldHash.get(UNITS_XML_FIELD_NAME)).setValue(value);
    }

    /**
     * Get the value of dataType
     * 
     * @return the value of dataType
     */
    public DataType getDataType (  ) {
        return (DataType) ((XMLSerializableField) fieldHash.get(DATATYPE_XML_FIELD_NAME)).getValue();
    }

    /**
     * Set the value of dataType
     * 
     * 
     */
    public void setDataType ( DataType value  ) {
        ((XMLSerializableField) fieldHash.get(DATATYPE_XML_FIELD_NAME)).setValue(value);
    }

    /**
     * When a class is serialzed in XML, this sets the value of the namespaceURI to use.
     * If the value is "null" then no prefix will be appended to the node name.
     */
    public void setNamespaceURI ( String value ) 
    {
        namespaceURI = value;
        // Cant decide if this is bad or not.. probably any datatype def's are part
        // of that Q's package/namespace and are not independant, so this seems OK.
        getDataType().setNamespaceURI(value);
    }

    // Operations
    //

    /** Determine if this component is exactly the same as the comparison object.
     * All fields, sub-objects are compared between both objects.
     */
// Need to override the hashCode method as well??
    public boolean equals (Object obj) 
    {
        if (obj != null && obj instanceof Component) 
        {
            return this.getDataType().equals(((Component)obj).getDataType()) 
// FIX Q: need to do the Id??
//                      && this.getId().equals((Component)obj.getId());
                      && this.getUnits().equals(((Component)obj).getUnits());
        } 
        return false;
/*
         return new EqualsBuilder().
             append(this.getDataType(), obj.getDataType()).
             append(this.getUnits(), obj.getUnits()).
             isEquals();
*/
    }

    /** Deep copy of this QML object.
        @return an exact copy of this QML object
     */
    public Object clone () throws CloneNotSupportedException 
    {
        ComponentImpl cloneObj = (ComponentImpl) super.clone();
        return cloneObj;
    }

    /** Determine equivalence between objects. Equivalence is the same
      * as 'equals' but without checking that the id or namespaceURN 
      * fields between both objects are the same.
      */
    public boolean equivalent ( Object obj )
    {
 
        if (obj instanceof Component )
        {
            if (
                  this.getDataType().equals(((Component)obj).getDataType())
			&&
                  this.getUnits().equals(((Component)obj).getUnits())
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
    protected void init()
    {
  
      resetFields();
  
      // initialize fields
      xmlNodeName = new String("component");

      // now initialize XML fields
      // order matters! these are in *reverse* order of their
      // occurence in the schema/DTD
      fieldOrder.add(0, DATATYPE_XML_FIELD_NAME);
      fieldOrder.add(0, UNITS_XML_FIELD_NAME);
//     fieldOrder.add(0, IMMUTABLE_XML_FIELD_NAME);
      fieldOrder.add(0, ID_XML_FIELD_NAME);
  
      fieldHash.put(ID_XML_FIELD_NAME, new XMLSerializableField(new String(""), Constants.FIELD_ATTRIB_TYPE));
//      fieldHash.put(IMMUTABLE_XML_FIELD_NAME, new XMLSerializableField(new Boolean(false), Constants.FIELD_ATTRIB_TYPE));
      fieldHash.put(UNITS_XML_FIELD_NAME, new XMLSerializableField(new UnitsImpl(""), Constants.FIELD_CHILD_NODE_TYPE));
      fieldHash.put(DATATYPE_XML_FIELD_NAME, new XMLSerializableField(new StringDataType(), Constants.FIELD_CHILD_NODE_TYPE));
      fieldHash.put(URN_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE));

    }

}

