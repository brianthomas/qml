
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

import net.datamodel.qml.Component;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;

import org.apache.log4j.Logger;

// TODO: refactor class to implement List interface

/**
 * A quantity which contains a list of one or more values.
 */
public class ListQuantityImpl extends AtomicQuantityImpl 
implements ListQuantity 
{
	private static final Logger logger = Logger.getLogger(ListQuantityImpl.class);
	
    // Constructors

    /** Construct a ListQUantity with no values and
     * default capacity.
     */
    public ListQuantityImpl () { 
    	this(-1); 
    }

    /** Constuct the quantity with a number of pre-allocated locations (capacity) 
     * for the list of values it contains.
     */
    public ListQuantityImpl ( int capacity ) { 
    	super(capacity);
        
        if(capacity < 1)
           capacity = Specification.getInstance().getDefaultValueContainerCapacity();
        
        setXMLNodeName(Constants.NodeName.LIST_QUANTITY);
        setSize(1);
        
    }

    /** Construct this quantity with mapping rather than explicitly holding
     *  values. Values will be generated on demand from the (value) mapping.
     */
    public ListQuantityImpl ( ValueMapping mapping )
    {
    	this(-1);
    	setValueContainer ((ValueContainer) mapping);
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.Byte)
     */
    public final void addValue ( Byte obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.Double)
     */
    public final void addValue ( Double obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.Float)
     */
    public final void addValue ( Float obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.Integer)
     */
    public final void addValue (Integer obj)
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.Short)
     */
    public final void addValue ( Short obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ListQuantity#addValue(java.lang.String)
     */
    public final void addValue ( String obj )
    throws IllegalAccessException, IllegalArgumentException, NullPointerException
    {

       if(obj == null)
          throw new NullPointerException("Can't append null value to list.");

       if (hasMapping())
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

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.AbstractQuantity#getValue(net.datamodel.qml.Locator)
     */
    public final Object getValue ( Locator loc ) 
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

        if (obj instanceof Quantity )
        {
            if (
                  super.equivalent ((Component) obj)
                      &&
                  this.getSize().equals(((Quantity)obj).getSize())
  //FIXME                    &&
  //                this.getValue().equals(((Quantity)obj).getValue())
 //                     &&
//                 this.getQuantities().equals(((ObjectWithQuantities)obj).getQuantities()) // FIXME : need to iterate over members 
               )
            return true;
        }
        return false;
    }

    /** Update the size attribute from the data container.
     */
    @Override 
    protected void updateSize() 
    {
        setSize(new Integer(getValueContainer().getNumberOfValues()));
    }
    
}


