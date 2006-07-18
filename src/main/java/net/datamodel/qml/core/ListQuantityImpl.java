
// CVS $Id$

// ListQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.util.Vector;
import java.util.List;

import net.datamodel.qml.Component;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.QuantityWithValues;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.Constants.NodeName;
import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;

import org.apache.log4j.Logger;
/**
 * A quantity which contains a list of one or more values.
 */
public class ListQuantityImpl extends AtomicQuantityImpl 
implements ListQuantity 
{
	private static final Logger logger = Logger.getLogger(ListQuantityImpl.class);
	
    // Fields

    // Methods
    //

    // Constructors

    // No-arg constructor 
    public ListQuantityImpl ( ) { 
       init(-1);
    }

    /** Constuct the quantity with a number of pre-allocated locations (capacity) 
     * for the list of values it contains.
     */
    public ListQuantityImpl ( int capacity ) { 
       init(capacity);
    }

    /** Construct this quantity with mapping rather than explicitly holding
      * values. Values will be generated on demand from the (value) mapping.
     */
    public ListQuantityImpl ( ValueMapping mapping )
    {
       init(mapping);
    }

    // Accessor Methods

    // Operations

    /** Set the capacity of this quantity to hold values. 
     * @param new_capacity 
     * @throws IllegalAccessException if called on mapping-based quantity.
     */
// I'm conflicted about whether or not this is a good idea...
    public void setCapacity ( int new_capacity ) 
    throws IllegalAccessException
    {

       // this is the badness.. its only implemented for listvaluecontainerimpl
       if(getValueContainer() instanceof ListValueContainerImpl)
       {
          ((ListValueContainerImpl) getValueContainer()).setCapacity(new_capacity);
          updateSize();
       } else 
          throw new IllegalAccessException("Can't setCapacity, values container doesn't allow.");
    } 

    /**
     * Utility method. Append a Byte value onto the current list.
     * @param obj Byte value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Byte obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }

       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Utility method. Append a Double value onto the current list.
     * @param obj Double value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Double obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }

       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Utility method. Append a Float value onto the current list.
     * @param obj Float value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Float obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }
       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Utility method. Append an Integer value onto the current list.
     * @param obj Integer value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Integer obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }

       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Utility method. Append a Short value onto the current list.
     * @param obj Short value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( Short obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }

       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Utility method. Append a String value onto the current list.
     * @param obj String value to append. It may be not "null".
     * @throws IllegalAccessException when called for mapping-based containers.
     * @throws IllegalArgumentException when object is a Quantity.
     * @throws NullPointerException when null parameters are passed.
     */
    public void addValue ( String obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping)
           throw new IllegalAccessException("addValue - has underlying values generated from Mapping. Cant add explicit value.");

       // check if we are full, and if so, expand our array
       if (getValueContainer().getNumberOfValues() == getCapacity())
       {
          int new_capacity = (int)( getCapacity() * Constants.EXPAND_VALUELIST_FACTOR);
          setCapacity(new_capacity);
       }

       Locator loc = createLocator();
       loc.setListIndex(getSize().intValue());

       try {
          setValue(obj, loc);
       } catch (SetDataException e) {
          // shouldnt happen..
          logger.error("developer error? addvalue for listQ got SetDataException :"+e.getMessage());
       }
    }

    /**
     * Get the value at the specified location. For atomic quantities  only one location will exist.
     * @throws IllegalArgumentException when a locator belonging to another quantity is passed.
     * @throws NullPointerException when null parameters are passed.
     * @return Object value at requested location.
     * @@Overrides
     */
    public Object getValue ( Locator loc ) 
        throws IllegalArgumentException, NullPointerException
    {
        return getValueContainer().getValue(loc);
    }
    
    /* Deep copy of this QML object.
        @return an exact copy of this QML object
     */
/*
    public Object clone () throws CloneNotSupportedException
    {
        ListQuantityImpl cloneObj = (ListQuantityImpl) super.clone();
        return cloneObj;
    }
*/


    /** Determine equivalence between objects (quantities). Equivalence is the same
      * as 'equals' but without checking that the id fields between both
      * objects are the same.
      * @@Overrides
      */
// FIXME : not testing listQ values!
    public boolean equivalent ( Object obj )
    {

        if (obj instanceof QuantityWithValues )
        {
            if (
                  super.equivalent ((Component) obj)
                      &&
                  this.getSize().equals(((QuantityWithValues)obj).getSize())
  //FIXME                    &&
  //                this.getValue().equals(((QuantityWithValues)obj).getValue())
                      &&
                  this.getMemberList().equals(((Quantity)obj).getMemberList()) // FIXME : need to iterate over members 
               )
            return true;
        }
        return false;
    }


    // Protected ops

    /** A special protected method used by constructor methods to
     *  conviently build the XML attribute list for a given class.
     */
    protected void init( int capacity )
    {

       super.init(capacity);

       if(capacity < 1)
          capacity = Specification.getInstance().getDefaultValueContainerCapacity();

       myInit();
    }

    protected void init( ValueMapping mapping )
    {

       super.init(-1);

       myInit();
       setValueContainer ((ValueContainer) mapping);

    }

    
    /** Update the size attribute from the data container.
     * @@Overrides 
     */
    protected void updateSize() 
    {
        setSize(new Integer(getValueContainer().getNumberOfValues()));
    }
    
    // Private
    //
    
    private void myInit() {

       xmlNodeName = Constants.NodeName.LIST_QUANTITY;
       setSize(1);

    }

}


