
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

import java.util.List;
import java.util.Vector;

import net.datamodel.qml.AxisFrame;
import net.datamodel.qml.Component;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.SemanticObject;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.locator.MatrixLocatorImpl;
import net.datamodel.qml.support.Constants;

import org.apache.log4j.Logger;

/**
 * A quantity which contains a set of one or more values 
 * which may be described by one or more axis (coordinate) 
 * frames.
 */
public class MatrixQuantityImpl extends ListQuantityImpl 
implements MatrixQuantity
{
	
	private static final Logger logger = Logger.getLogger(MatrixQuantityImpl.class);

    // Fields
    // 

    // axis frames (coordinate frames) of this quantity
    /**
     * @uml.property  name="axisFrameList"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="net.datamodel.qml.AxisFrame"
     */
    List AxisFrameList = null;
//    private static final String AXIS_XML_FIELD_NAME = new String("axesFrames");
    // alternative representations of this quantity
    private static final String ALTERN_XML_FIELD_NAME = new String("altValue");

    // Methods
    //

    // Constructors
    //

    // No-argument Constructor
    public MatrixQuantityImpl ( ) { 
       init();
    }

    /** Construct this quantity with mapping rather than explicitly holding
      * values. Values will be generated on demand from the (value) mapping.
     */
    public MatrixQuantityImpl ( ValueMapping mapping )
    {
       init();
       setValueContainer ((ValueContainer) mapping);
    }

    // Accessor Methods

    /**
     * Add an object of type SemanticObject to the List of members in this quantity. 
     * Incorrectly dimensioned AxisFrames are not allowed.
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an AxisFrame
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This AxisFrame may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @throws IllegalArgumentException if the AxisFrame dimensionality is incorrect. 
     * @return boolean value of whether addition was successfull or not.
     * 
     * @@Overrides
     */
    public boolean addMember ( SemanticObject member) 
    throws IllegalArgumentException, NullPointerException
    {
    	
    	logger.debug("MatrixQ addMember(q) called");

        if (member == null)
           throw new NullPointerException();

        // perform dimensional checks on axisFrame
        if (member instanceof AxisFrame) 
        {

           AxisFrame axisFrame = (AxisFrame) member;

           int axisLocations = axisFrame.getNumberOfAxisLocations();
           if (axisLocations == 0)
              throw new IllegalArgumentException("AxisFrame has no locations defined!");

/*
        if (axisLocations != getSize().intValue())
{
Log.errorln("Reminder to self : check expanding values within Matrix when new frame is added");
//           throw new IllegalArgumentException("AxisFrame has different number of locations ["+axisLocations+"] from parent Q ["+getSize().intValue()+"]");
}
*/
           AxisFrameList.add(axisFrame);

        }

        return getMemberList().add(member);

    }

    /**
     * Remove an object of type SemanticObject from the List of members
     * in this quantity.
     * 
     * @return boolean value of whether removeal was successful or not.
     */
    public boolean removeMember ( SemanticObject value ) {

        if(value == null) 
          return false;

        if(value instanceof AxisFrame) 
           AxisFrameList.remove((AxisFrame) value);

        return getMemberList().remove(value);
    }

    /**
     * Get the list of axisframes in this quantity.
     * @return  List of axisframes
     * @uml.property  name="axisFrameList"
     */
    public List getAxisFrameList ( ) {
        return AxisFrameList;
    }

    /**
     * Add an object of type ListQuantity to represent an equivalent, but
     *  alternative represention of this quantity. The alternative quantity must
     * have the same size (number of values) as the parent quantity that it represents.
     *
     * @throws IllegalArgumentException if either self or a quantity of different size from the parent is passed.
     * @return boolean value of whether addition was successfull or not.
     */
    public boolean addAltValue ( ListQuantity value  )
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
    public boolean removeAltValue ( ListQuantity value  )
    {
        return getAltValueList().remove(value);
    }

    /**
     * Get the list of altvalueVector
     *
     * @return List of altvalueVector
     */
    public List getAltValueList (  ) {
        return (List) ((QuantityContainerImpl) ((XMLSerializableField) fieldHash.get(ALTERN_XML_FIELD_NAME)).getValue()).getQuantityList();
    }

    /**
     * Create a locator for this quantity. This method provided for
     * compliance with SemanticObject interface..atomic quantities don't
     * have more than one location, so it is of little value to use them,
     * if you know you are dealing with an atomic quantity.
     */
    public Locator createLocator ( )
    {
        Locator loc = new MatrixLocatorImpl (this);
        locatorList.add(loc);
        return loc;
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
                      &&
                  this.getMemberList().equals(((SemanticObject)obj).getMemberList()) // FIXME : need to iterate over members 
               )
            return true;
        }
        return false;
    }


    // Protected ops

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init()
    {

       super.init(-1);

       xmlNodeName = Constants.NodeName.MATRIX_QUANTITY;

       AxisFrameList = new Vector();
       // now initialize XML fields
//       fieldOrder.add(3,AXIS_XML_FIELD_NAME);
       fieldOrder.add(ALTERN_XML_FIELD_NAME);

       fieldHash.put(ALTERN_XML_FIELD_NAME, new XMLSerializableField( new QuantityContainerImpl("altValues", false), Constants.FIELD_CHILD_NODE_TYPE));

//       fieldHash.put(AXIS_XML_FIELD_NAME, new XMLSerializableField( new QuantityContainerImpl(null, false), Constants.FIELD_CHILD_NODE_TYPE));

    }

}

