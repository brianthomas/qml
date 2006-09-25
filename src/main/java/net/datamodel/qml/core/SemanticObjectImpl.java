
// CVS $Id$

// SemanticObjectImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
import java.net.URI;
import java.util.Hashtable;
import java.util.List;

import net.datamodel.qml.SemanticObject;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;

import org.apache.log4j.Logger;

/**
 * An object which holds quantities (as properties). It may be used 
 * as is or as stub code to create other objects which contain quantities.
 */
public class SemanticObjectImpl extends XMLSerializableObjectImpl 
implements SemanticObject {
	
	private static final Logger logger = Logger.getLogger(SemanticObjectImpl.class);

    // Fields
	private static final String MEMBER_XML_FIELD_NAME = "member";
    private static final String ID_XML_FIELD_NAME = "qid";
    private static final String URI_XML_FIELD_NAME = "uri";
    
//    private static final String IMMUTABLE_XML_FIELD_NAME = new String("immutable");

    /**
     * @uml.property  name="nrofMembers"
     */
    private int nrofMembers;

    // Methods
    //

    // Constructors

    // Construct with a given URI.
    public SemanticObjectImpl ( URI uri ) { 
       init();
       setURI(uri);
    }
    
    // The no-argument Constructor
    public SemanticObjectImpl () {
    	init();
    }

    // Accessor Methods

    /*
     * Whether or not this quantity or component is mutable. 
     * (e.g. it may change meta-data/data within the instance).
     */
/*
    public Boolean getImmutable (  ) {
        return (Boolean) ((XMLSerializableField) fieldHash.get(IMMUTABLE_XML_FIELD_NAME)).getValue();
    }
*/

    /*
     * Whether or not this quantity or component is mutable.
     * (e.g. it may change meta-data/data within the instance).
     */
/*
    public void setImmutable ( Boolean value  ) {
        ((XMLSerializableField) fieldHash.get(IMMUTABLE_XML_FIELD_NAME)).setValue(value);
    }
*/

    /**
     * The id of an instance of this class. It should be unique across all components and quantities within a given document/object tree.
     */
    public String getId (  ) {
        return (String) ((XMLSerializableField) fieldHash.get(ID_XML_FIELD_NAME)).getValue();
    }

    /*
     *  (non-Javadoc)
     * @see net.datamodel.qml.SemanticObject#setId(java.lang.String)
     */ 
    public void setId ( String value  ) {
        ((XMLSerializableField) fieldHash.get(ID_XML_FIELD_NAME)).setValue(value);
    }

    /*
     *  (non-Javadoc)
     * @see net.datamodel.qml.SemanticObject#addMember(net.datamodel.qml.SemanticObject)
     */ 
    public boolean addMember ( SemanticObject value  ) 
    {

       // cant add ourselves as member of ourselves (!)
       if(value == this)
       {
           logger.warn("ignoring attempt to add self to member list");
           return false;
       }
       
       // check if the member already exists
       if (null != getMember(value.getURI()))
       {
    	   throw new IllegalArgumentException("addMember: a member already exists with URI:"+value.getURI());
       }

       return getMemberList().add(value);
    }

    /**
     * Remove an object of type SemanticObject from the List memberVector
     *
     * @return boolean value of whether removal was successful or not.
     */
    public boolean removeMember ( SemanticObject value  ) {
       return getMemberList().remove(value);
    }

    /*
	 *  (non-Javadoc)
	 * @see net.datamodel.qml.SemanticObject#getURI()
	 */
	public URI getURI() {
		try {
			return new URI ((String) ((XMLSerializableField) fieldHash.get(URI_XML_FIELD_NAME)).getValue());
		} catch (Exception e) {
			logger.error("Invalid URI for object returned.");
			return (URI) null; // shouldnt happen as we only let valid URIs in..
		}
	}

	public Quantity getMember(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Get the list of memberVector
     *
     * @return List of memberVector
     */
    public List<SemanticObject> getMemberList (  ) {
        return (List) ((QuantityContainerImpl) ((XMLSerializableField) fieldHash.get(MEMBER_XML_FIELD_NAME)).getValue()).getQuantityList();
    }

    // Operations

    /** Determine equivalence between objects (quantities). Equivalence is the same
	  * as 'equals' but without checking that the id fields between both
	  * objects are the same.
	  * @@Overrides
	  */
	public boolean equivalent ( Object obj )
	{
	
	    if (obj instanceof SemanticObject )
	    {
	        if (
	              this.getMemberList().equals(((SemanticObject)obj).getMemberList()) // FIXME : need to iterate over members 
	           )
	        return true;
	    }
	    return false;
	}

	/** Set the URI, representing the semantic meaning, of this object.
	 * 
	 * @param value of the uri to set
	 */
	protected void setURI (URI value) {
		// Take the URI and convert it to a string for storage in object/serialization.
		// Not optimal, but works (for now).
	    ((XMLSerializableField) fieldHash.get(URI_XML_FIELD_NAME)).setValue(value.toASCIIString());
	}

	/**
     * @return boolean value of whether or not some content was written.
     */
    protected boolean basicXMLWriter (
                                      Hashtable idTable,
                                      Hashtable prefixTable,
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
         if(id != null && !id.equals("") && idTable != null)
         {
             SemanticObject idOwner = (SemanticObject) idTable.get(id);
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
         return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, indentFirstNode);
    }

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init( )
    {

       resetFields();

       xmlNodeName = Constants.NodeName.COMPOSITE_QUANTITY;

       nrofMembers = 0;
       
       // now initialize XML fields
       // order matters! these are in *reverse* order of their
       // occurence in the schema/DTD
       fieldOrder.add(0, MEMBER_XML_FIELD_NAME);
//       fieldOrder.add(0, IMMUTABLE_XML_FIELD_NAME);
       fieldOrder.add(0, ID_XML_FIELD_NAME);

       fieldHash.put(URI_XML_FIELD_NAME, new XMLSerializableField(null, Constants.FIELD_ATTRIB_TYPE ));
       fieldHash.put(ID_XML_FIELD_NAME, new XMLSerializableField(new String(""), Constants.FIELD_ATTRIB_TYPE));
//       fieldHash.put(IMMUTABLE_XML_FIELD_NAME, new XMLSerializableField(new Boolean(false), Constants.FIELD_ATTRIB_TYPE));
       fieldHash.put(MEMBER_XML_FIELD_NAME, new XMLSerializableField(new QuantityContainerImpl(null, false), Constants.FIELD_CHILD_NODE_TYPE));
       
    }

}

