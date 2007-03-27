
// CVS $Id$

//AbstractQuantity.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

//code generation timestamp: Tue Apr 20 2004-14:22:31 

package net.datamodel.qml.core;

import java.net.URI;
import java.util.List;

import net.datamodel.qml.Component;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;
import net.datamodel.qml.XMLSerializableObjectWithValues;
import net.datamodel.qml.support.Constants;
import net.datamodel.xssp.XMLFieldType;

/**
 * A base class for building quantities. 
 * @version $Revision$
 */
abstract class AbstractQuantity 
extends ComponentImpl 
implements Quantity, XMLSerializableObjectWithValues
{

//	private static final Logger logger = Logger.getLogger(AbstractQuantity.class);

	/** whether or not this Quantity's value(s) are generated by mapping. */
	private boolean hasMapping = false;

	// XML attribute names 
	private static final String sizeFieldName = Constants.SIZE_ATTRIBUTE_NAME;
	private static final String dataFieldName = Constants.DATA_FIELD_NAME;
	
	// Constructors

	/** No argument constructor. Values will be explicitly held.
	 */
	public AbstractQuantity(URI uri) { 
		this(uri,1);
	}
	
	/** Construct a quantity for a given capacity.
	 * 
	 * @param capacity
	 */
	protected AbstractQuantity (URI uri, int capacity) { 
		
		super(uri);

		ValueContainer dataContain 
			= new ListValueContainerImpl(this, capacity);

		// now initialize XML fields
		addField(sizeFieldName, new Integer(dataContain.getNumberOfValues()), XMLFieldType.ATTRIBUTE );
		addField(dataFieldName, dataContain, XMLFieldType.CHILD);

		// setSize(capacity); 
		setSize(1); 
		updateSize();
		
	}

	/* Construct this quantity with mapping rather than explicitly holding 
	 * values. Values will be generated on demand from the (value) mapping.
	 */
	// Make this protected as some quantities (like trivial, atomic) wont
	// need it...there is little sense in creating a mapping
	// when you have 1 value (!)
	protected AbstractQuantity ( URI uri, ValueMapping mapping ) 
	{ 
		this(uri, 1);
		setValueContainer ((ValueContainer) mapping);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getCapacity()
	 */
	public final int getCapacity() {
		return getValueContainer().getCapacity(); // only ever one location
	}
	
	// a little accessor to determine if we have mapping
	// This is overkill..probably just use the field instead?
	// but if we did that then the inheriting class could change the value
	// which I think we dont want to allow.
	protected final boolean hasMapping() { return hasMapping; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Quantity#getSize()
	 */
	public final Integer getSize() {
		return (Integer) getFieldValue(sizeFieldName);
	}

	/** Set the number of values witin this quantity. This method will
	 *  allow the user to set the number of values within the quantity. 
	 *  If the size is smaller than the existing one, then some of
	 *  the already allocated values will be removed from the quantity. 
	 *  If it is larger than the current size, new locations will be 
	 *  filled with "noDataValues".
	 * @throws IllegalArgumentException if size is less than 1.
	 */
	protected final void setSize ( Integer value ) 
	throws IllegalArgumentException
	{
		if(value.intValue() > 0)
		{
			getValueContainer().setNumberOfValues(value.intValue());
			setFieldValue(sizeFieldName, value );
		}
	}

	/** Set the number of values witin this quantity. This method will
	 *  allow the user to set the number of values within the quantity. 
	 *  If the size is smaller than the existing one, then some of
	 *  the already allocated values will be removed from the quantity. 
	 *  If it is larger than the current size, new locations will be 
	 *  filled with "noDataValues".
	 *  
	 * @throws IllegalArgumentException if size is less than 1.
	 */
	protected final void setSize (int size) 
	throws IllegalArgumentException
	{
		setSize(new Integer(size)); 
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getLocators()
	 */
	public final List<Locator> getLocators() { 
		return getValueContainer().getLocators(); 
	}
	
	/** get the ValueContainer which this quantity uses
	 * to store its values in.
	 * 
	 * @return
	 */
	public final ValueContainer getValueContainer() {
		return (ValueContainer) getFieldValue(dataFieldName);
	}

	/** Set the value container. All prior value(s) held by 
	 * the quantity will be lost.
	 */
	protected final void setValueContainer ( ValueContainer value ) {
		if (value != null)
		{
			if (value instanceof ValueMapping) {
				hasMapping = true;
			}
			
			setFieldValue(dataFieldName, value);

			// update our size
			setSize(new Integer(getValueContainer().getNumberOfValues()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#setValueCDATASerialization(boolean)
	 */
	public final void setValueCDATASerialization (boolean val) {
		getValueContainer().setValueCDATASerialization(val);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#getValueCDATASerialization()
	 */
	public final boolean getValueCDATASerialization () {
		return getValueContainer().getValueCDATASerialization();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getValue()
	 */
	public final Object getValue() {
		return getValueContainer().getFirstValue();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getValue(net.datamodel.qml.Locator)
	 */
	public Object getValue (Locator loc) 
	throws IllegalArgumentException, NullPointerException
	{
		return getValue();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.String, net.datamodel.qml.Locator)
	 */
	public final void setValue ( String obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}
	

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#createLocator()
	 */
	public final Locator createLocator() {
		return getValueContainer().createLocator(); 
	}

	/* Deep copy of thisQML object.
	 *  @return an exact copy of this object
	 */
	// TODO: review that this is correct
	/*
	public Object clone () throws CloneNotSupportedException
	{
		AbstractQuantity cloneObj = (AbstractQuantity) super.clone();
		cloneObj.hasMapping = this.hasMapping;
		cloneObj.setValueContainer(this.getValueContainer().clone());
		//     cloneObj.data = data.clone();
		return cloneObj;
	}
	*/

	/** Determine equivalence between objects (quantities). Equivalence is the same
	 * as 'equals' but without checking that the id fields between both
	 * objects are the same.
	 */
	@Override
	public boolean equivalent ( Object obj ) 
	{

		if (obj instanceof Quantity ) 
		{
			if (
					super.equivalent ((Component) obj)
					&&
					this.getSize().equals(((Quantity)obj).getSize())
//					&&
// FIXME : should iterate over all values an compare
//					this.getValue().equals(((Quantity)obj).getValue())
//					&&
//					this.getQuantities().equals(((ObjectWithQuantities)obj).getQuantities()) 
					// FIXME : need to iterate over members 
			)
				return true;
		}
		return false;
	}

	// TODO: impelemnt hashCode

	/** Update the size attribute from the data container.
	 * 
	 */
	protected void updateSize() {
		// do nothing. Should always be '1'
//		setSize(new Integer(getValueContainer().getNumberOfValues()));
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#getTaggedValuesSerialization()
	 */
	public final boolean getTaggedValuesSerialization() {
		return getValueContainer().getTaggedValuesSerialization();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#setTaggedValuesSerialization(boolean)
	 */
	public final void setTaggedValuesSerialization(boolean value) {
		getValueContainer().setTaggedValuesSerialization(value);
	}
	

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setCapacity(int)
	 */
	public void setCapacity(int value) throws IllegalAccessException 
	{

		// this is the badness.. its only implemented for listvaluecontainerimpl
		if(getValueContainer() instanceof ListValueContainerImpl)
		{
			getValueContainer().setCapacity(value);
			updateSize();
		} else 
			throw new IllegalAccessException("Can't setCapacity, values container doesn't allow.");

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getNumberOfValues()
	 */
	public int getNumberOfValues() { return getValueContainer().getNumberOfValues(); }
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone(); // TODO Auto-generated method stub
	}

}

