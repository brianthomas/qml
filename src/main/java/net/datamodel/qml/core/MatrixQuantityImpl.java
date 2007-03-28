
// CVS $Id$

// MatrixQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

// code generation timestamp: Tue Apr 20 2004-14:23:33 

package net.datamodel.qml.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.Component;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.support.Constants;
import net.datamodel.soml.Relationship;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.impl.AbstractXMLSerializableObjectList;

import org.apache.log4j.Logger;

/**
 * A quantity which contains a set of one or more values 
 * which may be described by one or more axis (coordinate) 
 * frames.
 */
public class MatrixQuantityImpl 
extends ListQuantityImpl 
implements MatrixQuantity
{
	
	private static final Logger logger = Logger.getLogger(MatrixQuantityImpl.class);

    // Fields
    // 

    private static final String alternValuesFieldName = new String("altValue");
    
    private URI refFrameURN = null;

    // Methods
    //

    // Constructors
    //

    /** No-argument Constructor */
    // TODO: I dont think we want to allow this
    protected MatrixQuantityImpl () {  this(null, -1); }
    
    /** Construct with the indicated uri.
     * 
     * @param uri
     */
    public MatrixQuantityImpl (URI uri) { 
    	this (uri, -1);
    }
    
    /** Construct with some initial capacity.
     * 
     * @param capacity
     */
    public MatrixQuantityImpl (URI uri, int capacity) { 
    	super(uri, capacity);

    	setXMLNodeName (Constants.NodeName.MATRIX_QUANTITY);

    	setValueContainer (new MatrixValueContainerImpl(this));

    	// now initialize XML fields
    	addField(alternValuesFieldName, new AltValuesList(), XMLFieldType.CHILD);

    	try {
    		refFrameURN = new URI(Constants.QML_REF_FRAME_URN);
    	} catch (URISyntaxException e) {
    		String msg = "cant construct quantity..URN syntax bogus in class";
    		logger.error(msg);
    		e.printStackTrace();
    	}

    }

    /** Construct this quantity with mapping rather than explicitly holding
      * values. Values will be generated on demand from the (value) mapping.
     */
    public MatrixQuantityImpl (URI uri, ValueMapping mapping) {
    	this(uri, -1);
    	setValueContainer(mapping);
    }

    // Accessor Methods

    /*
     * Add an object of type ObjectWithQuantities to the List of members in this quantity. 
     * Incorrectly dimensioned AxisFrames are not allowed.
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an ReferenceFrame
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This ReferenceFrame may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @throws IllegalArgumentException if the ReferenceFrame dimensionality is incorrect. 
     * @return boolean value of whether addition was successfull or not.
     * 
     */
    /*
    @Override
    public boolean addMember ( Quantities member) 
    throws IllegalArgumentException, NullPointerException
    {
    	
    	logger.debug("MatrixQ addMember(q) called");

        if (member == null)
           throw new NullPointerException();

        // perform dimensional checks on axisFrame
        if (member instanceof ReferenceFrame) 
        {

           ReferenceFrame axisFrame = (ReferenceFrame) member;

           int axisLocations = axisFrame.getNumberOfAxisLocations();
           if (axisLocations == 0)
              throw new IllegalArgumentException("ReferenceFrame has no locations defined!");

 //       if (axisLocations != getSize().intValue())
// {
// Log.errorln("Reminder to self : check expanding values within Matrix when new frame is added");
//           throw new IllegalArgumentException("ReferenceFrame has different number of locations ["+axisLocations+"] from parent Q ["+getSize().intValue()+"]");
// }
           ReferenceFrameList.add(axisFrame);

        }

        return getQuantities().add(member);

    }
*/

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.MatrixQuantity#addReferenceFrame(net.datamodel.qml.ReferenceFrame)
     */
    public final boolean addReferenceFrame(ReferenceFrame frame) 
    throws IllegalArgumentException 
    {
    	
    	int axisLocations = frame.getNumberOfAxisLocations();
    	if (axisLocations == 0)
    		throw new IllegalArgumentException("ReferenceFrame has no locations defined!");

    	if (axisLocations != getSize().intValue()) {
    		logger.error("Reminder to self : check expanding values within Matrix when new frame is added");
    		throw new IllegalArgumentException("ReferenceFrame has different number of locations ["+axisLocations+"] from parent Q ["+getSize().intValue()+"]");
    	}


    	return this.addRelationship(frame, refFrameURN);
    }
    
    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.MatrixQuantity#getReferenceFrames()
     */
    public final List<ReferenceFrame> getReferenceFrames() {
		List<ReferenceFrame> rList = new Vector<ReferenceFrame>(); 
		// tool through our list of related objects and find ones which
		// match the indicated urn
		for (Relationship rel : getRelationships(refFrameURN)) {;
			rList.add((ReferenceFrame) rel.getTarget());
		}
		return rList;
    }
    
    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.MatrixQuantity#removeReferenceFrame(net.datamodel.qml.ReferenceFrame)
     */
    public boolean removeReferenceFrame(ReferenceFrame frame) {
    	return removeRelationship(refFrameURN, frame);
    }

    /**
     * Add an object of type ListQuantity to represent an equivalent, but
     *  alternative represention of this quantity. The alternative quantity must
     * have the same size (number of values) as the parent quantity that it represents.
     *
     * @throws IllegalArgumentException if either self or a quantity of different size from the parent is passed.
     * @return boolean value of whether addition was successfull or not.
     */
    public final boolean addAltValue ( ListQuantity value  )
    throws IllegalArgumentException
    {
        // can't add ourselves as alternative value of ourselves (!)
        if(value == this)
        {
           throw new IllegalArgumentException ("Ignoring attempt to add self to alternative value list.");
        }

        if(this.getSize().intValue() != value.getSize().intValue())
        {
           throw new IllegalArgumentException ("addAltValue - can't add alternative value quantity which has different size from parent quantity (parent:"+this.getSize()+" vs altV:"+value.getSize()+").");
        }

        return getAltValueList().add(value);
    }

    /**
     * Remove an object of type ListQuantity from the List altvalueVector
     *
     * @return boolean value of whether removeal was successfull or not.
     */
    public final boolean removeAltValue ( ListQuantity value ) {
        return getAltValueList().remove(value);
    }

    /**
     * Get the list of altvalueVector
     *
     * @return List of altvalueVector
     */
    public final List<ListQuantity> getAltValueList () {
    	return ((List<ListQuantity>) getFieldValue(alternValuesFieldName));
    }

    /** Determine equivalence between objects (quantities). Equivalence is the same
      * as 'equals' but without checking that the id fields between both
      * objects are the same.
      * @@Overrides
      */
// FIXME : not testing matrix axes, and etcera
    public boolean equivalent ( Object obj )
    {

        if (obj instanceof Quantity )
        {
            if (
                  super.equivalent ((Component) obj)
                      &&
                  this.getSize().equals(((Quantity)obj).getSize())
// FIXME                     &&
//                  this.getValue().equals(((Quantity)obj).getValue())
//                      &&
//                  this.getQuantities().equals(((ObjectWithQuantities)obj).getQuantities()) // FIXME : need to iterate over members 
               )
            return true;
        }
        return false;
    }
    
	/** Quick internal class to hold all relationships between our object 
	 * and other SO's. 
	 */
	class AltValuesList<ListQuantity> 
	extends AbstractXMLSerializableObjectList
	{ 
		// simply change the node name to "relationship"
		// and set no serialization when its empty 
		AltValuesList() { 
			super("altValues"); 
			this.setSerializeWhenEmpty(false);
		}

		@Override
		public String toString() {
			return this.getClass()+"@"+this.hashCode();
		}

	}

}

