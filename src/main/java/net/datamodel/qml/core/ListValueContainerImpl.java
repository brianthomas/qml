
// CVS $Id$

// ListValueContainerImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.dom.Constant;
import net.datamodel.qml.dom.Specification;
import net.datamodel.xssp.ReferenceableXMLSerializableObject;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.XMLSerializableField;
import net.datamodel.xssp.dom.Utility;
import net.datamodel.xssp.impl.AbstractXMLSerializableObject;

import org.apache.log4j.Logger;

/**
 * Container class for values held explicitly (e.g. not in a mapping algorithm).
 * 
 * Right now, we are allowing any old Java object as values. It may be that we
 * will want to be more restrictive (e.g. only String, int/Integer, float/Float, etc)
 * objects to be allowed to be held by this class. In this fashion, we can 
 * automatically not have to worry about the user passing us wierd objects to
 * be held by the parent ObjectWithValues..and allows us in the future 
 * to check that the passed information conforms to the declared dataType of 
 * the parent ObjectWithValues.
 */
public class ListValueContainerImpl 
extends AbstractXMLSerializableObject 
implements ValueContainer
{

	private static final Logger logger = Logger.getLogger(ListValueContainerImpl.class);

	/** A list of 'active' locators which point to this container. 
	 */
	private List<Locator> locatorList = new Vector<Locator>();

	// XML attribute name 
	protected static final String valuesFieldName = "values";

	// couple of arrays to store the actual values
	private Object[] valueList;
	private byte [] isNoDataValue;

	/** The present capacity of this container.
	 */
	private int capacity;

	/** The maximum index which is a utilized location.
	 */
	protected int maxUtilizedIndex;
	
	private Quantity parent;
	
	/** Name of the locator class we will use.
	 */
	protected String locatorClassName = "net.datamodel.qml.locator.ListLocatorImpl";

	/** */
	private boolean cdataSerialization = false;

	/** */
	private boolean taggedValuesSerialization = true;

	/** Vanilla constructor. Will create a list with default capacity
	 * (Specification.getDefaultValueContainerCapacity() 
	 */
	public ListValueContainerImpl (Quantity parent) { this(parent, -1); }

	/** Constuct the container with a number of pre-allocated capacity of the list.
	 */
	public ListValueContainerImpl ( Quantity parent, int capacity) 
	{ 
		super("values");
		
		logger.debug("New ListValueContainerImpl with capacity of:"+capacity);
		
		if (parent == null) 
			throw new NullPointerException("Cant create data container with null parent Quantity");
		
		this.parent = parent;
		
		if(capacity < 1)
			capacity = Specification.getInstance().getDefaultValueContainerCapacity();
		
		// TODO: a better way? Code badness..This is onlY here to kludge basicXMLWriter 
		// into seeing we have pcdata
		addField("PCDATA", "", XMLFieldType.PCDATA);

		resetValues(capacity);
		
	}

	/**
	 * Get the maximum value of the list index which has been used.
	 * @return  int
	 */
	// TODO: determine if this should be protected or in interface
	public final int getMaxUtilizedIndex () {
		return maxUtilizedIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Byte, net.datamodel.qml.Locator)
	 */
	public final void setValue (Byte obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Double, net.datamodel.qml.Locator)
	 */
	public final void setValue (Double obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Float, net.datamodel.qml.Locator)
	 */
	public final void setValue (Float obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Integer, net.datamodel.qml.Locator)
	 */
	public final void setValue (Integer obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Short, net.datamodel.qml.Locator)
	 */
	public final void setValue (Short obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.String, net.datamodel.qml.Locator)
	 */
	public final void setValue (String obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		setValue((Object) obj, locator);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getValue(net.datamodel.qml.Locator)
	 */
	public final Object getValue ( Locator locator ) 
	throws IllegalArgumentException, NullPointerException
	{

		if(locator == null)
		{
			throw new NullPointerException("getValue passed null locator.");
		}

		// quick check to see if the locator is kosher, however
		if (locator.getParent() != this)
		{
			throw new  IllegalArgumentException("Can't getValue with locator "+locator+" does not belong to this container:"+this);
		}

		logger.debug("ValueContainer getValue has value:"+valueList[locator.getListIndex()]+" at list index"+locator.getListIndex()); 

		return valueList[locator.getListIndex()];

	}

	/*
	 * Get a list of all values held by the container.
	 * @return List of values held by this ObjectWithValues.
	 */
	public final List<Object> getValues ()
	{
		logger.debug("ListValueContainer getValueList() returns"+valueList.toString());
		return java.util.Arrays.asList(valueList);
	}

	/**
	 * Set the capacity of this container. Calling this method will re-initialize 
	 * the values held by the container list. 
	 * 
	 * @param new_capacity  the new capacity of the container.
	 * @throws IllegalArgumentException  if a value of 0 or less is passed.
	 * @uml.property  name="capacity"
	 */
	public final void setCapacity (int new_capacity) 
	throws IllegalArgumentException
	{

		if(capacity <= 0) 
			throw new IllegalArgumentException("setCapacity() value <= 0 is illegal.");

		byte[] newBytes = new byte[new_capacity];
		Object[] newObjs = new Object[new_capacity];

		int size = (capacity < new_capacity) ? capacity : new_capacity;
		for (int i = 0; i < size; i++) {
			newBytes[i] = isNoDataValue[i];
			newObjs[i] = valueList[i];
		}

		isNoDataValue = newBytes;
		valueList = newObjs;

		capacity = new_capacity;

		// since we could have trimmed off some values, we
		// need to insure this is recorded properly
		if(maxUtilizedIndex > new_capacity)
			maxUtilizedIndex = new_capacity;

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getCapacity()
	 */
	public final int getCapacity () { return capacity; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ValueContainer#getNumberOfValues()
	 */
	public final int getNumberOfValues() { return maxUtilizedIndex + 1; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ValueContainer#setNumberOfValues(int)
	 */
	public final void setNumberOfValues (int value ) 
	throws IllegalArgumentException
	{

		if(value < 1)
			throw new IllegalArgumentException("setNumberOfValues() < 1 is illegal.");
		if(value > capacity)
			throw new IllegalArgumentException("setNumberOfValues() value > number of locations in list.");

		maxUtilizedIndex = value -1;

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ValueContainer#setFirstValue(java.lang.Object)
	 */
	public final void setFirstValue ( Object obj)
	throws IllegalAccessException
	{

		if (obj == null) {
			isNoDataValue[0] = 1;
		} else { 
			isNoDataValue[0] = 0;
		}

		if( maxUtilizedIndex < 0 )
			maxUtilizedIndex = 0;

		valueList[0] = obj;

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ValueContainer#getFirstValue()
	 */
	public final Object getFirstValue ( ) { return valueList[0]; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#createLocator()
	 */
	public final Locator createLocator ( ) {
		
		logger.debug("CreateLocator called for "+this+" w/ locClassName:"+locatorClassName);
		
		// Locator loc = new ListLocatorImpl (this);
		Locator loc = null;
		try {
			Class locatorClass = Class.forName(locatorClassName);
			Class[] paramType = { this.getClass() };
			Constructor locConst = locatorClass.getDeclaredConstructor(paramType);
			loc = (Locator) locConst.newInstance( new Object[] {this});
		} catch (Exception e) {
			// shouldnt happen, but..
			logger.error(e.getClass()+" : "+e.getMessage());
			e.printStackTrace();
		}
		locatorList.add(loc);
		return loc;
	}

	/*
	 * Make a deep copy of this Data object. 
	 */
	// TODO: for now we want only exact copies..this may never get done...need to review need. 
	/*
	public Object clone() {
		ListValueContainerImpl cloneObj = null;
		try {
			cloneObj = (ListValueContainerImpl) super.clone();
			synchronized (this) {
				synchronized (cloneObj) {
//					FIX? what else?
					cloneObj.locatorList = new Vector<Locator>();
					cloneObj.parent = parent;
				}
			}
		} catch (CloneNotSupportedException e) {

		}
		return cloneObj;
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#setValueCDATASerialization(boolean)
	 */
	public final void setValueCDATASerialization (boolean value) {
		cdataSerialization = value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#getValueCDATASerialization()
	 */
	public final boolean getValueCDATASerialization ( ) {
		return cdataSerialization;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#setTaggedValuesSerialization(boolean)
	 */
	public final void setTaggedValuesSerialization (boolean value) {
		taggedValuesSerialization = value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.XMLSerializableObjectWithValues#getTaggedValuesSerialization()
	 */
	public final boolean getTaggedValuesSerialization ( ) {
		return taggedValuesSerialization;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.xssp.impl.AbstractXMLSerializableObject#basicXMLWriter(java.util.Map, java.util.Map, java.io.Writer, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	protected final boolean basicXMLWriter (
			Map<String,ReferenceableXMLSerializableObject> idTable,
			Map<String,String> prefixTable,
			Writer outputWriter,
			String indent,
			String newNodeNameString, 
			boolean doFirstIndent
	)
	throws IOException
	{

		// serialize it
		// If there are fewer than 2 values, serialize with NO node name
		// so that only the child "value" will appear instead of <values><value>..</value></values>
		if(getNumberOfValues() < 2 && newNodeNameString == null)
			return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, "", doFirstIndent);
		else
			return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, doFirstIndent );

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.xssp.impl.AbstractXMLSerializableObject#handleChildNodes(java.util.Map, java.util.Map, java.io.Writer, java.lang.String, java.lang.String, java.util.Vector, net.datamodel.xssp.XMLSerializableField)
	 */
	@Override
	protected boolean handleChildNodes (
				Map<String,ReferenceableXMLSerializableObject> idTable, 
				Map<String,String> prefixTable, 
				Writer outputWriter, 
				String nodeNameString, 
				String indent, 
				Vector childObjs, 
				XMLSerializableField PCDATA 
	) 
	throws IOException
	{
		
		logger.debug("ListValueContainer special handleChildNodes called");

		Specification spec = Specification.getInstance();
		boolean writeTaggedValues = true;
		int serialize_style = spec.getSerializeValuesStyle();
		if(serialize_style == Constant.VALUE_SERIALIZE_SPACE) 
			writeTaggedValues = false;
		else if(serialize_style == Constant.VALUE_SERIALIZE_TAGGED 
				|| serialize_style == Constant.VALUE_SERIALIZE_CONTAINER )
			writeTaggedValues = taggedValuesSerialization;

		// close the opening tag
		if (nodeNameString != null && !nodeNameString.equals(""))
		{
			outputWriter.write(">");

			//         if (Specification.getInstance().isPrettyOutput() && writeTaggedValues)
			//         {
			//             String newindent = indent + spec.getPrettyOutputIndentation();
			//             outputWriter.write(Constant.NEW_LINE+newindent);
			//         }
		}

		Object noDataValue = parent.getDataType().getNoDataValue();
		String noDataValueStr = Utility.entifyString(noDataValue.toString());

		// we write tagged values if requested OR we only have ONE value
		// (the latter is because we set the master nodeName to "", so its
		// not being written out :P
		if(writeTaggedValues || maxUtilizedIndex == 0) 
		{

			String prefix = Utility.getPrefix(parent.getNamespaceURI(), prefixTable);

			String nodeName = Constant.TAGGED_DATA_NODE_NAME;
			if(prefix != null && !prefix.equals("") && nodeName != null)
				nodeName = prefix + ":" + nodeName;

			for (int i = 0; i <= maxUtilizedIndex; i++) 
			{

				outputWriter.write("<"+nodeName+">");

				if(cdataSerialization)
					outputWriter.write("<![CDATA[");

				Object value = valueList[i];
				if(isNoDataValue[i] == 1 || value == null)
					outputWriter.write(noDataValueStr);
				else
					outputWriter.write(Utility.entifyString(value.toString()));

				if(cdataSerialization)
					outputWriter.write("]]>");

				outputWriter.write("</"+nodeName+">");

			} 

		} else { 

			String separator = Constant.VALUE_SEPARATOR_STRING;

			if(cdataSerialization)
				outputWriter.write("<![CDATA[");

			for (int i = 0; i <= maxUtilizedIndex; i++) 
			{

				Object value = valueList[i];
				if(isNoDataValue[i] == 1 || value == null)
					outputWriter.write(noDataValueStr);
				else
					outputWriter.write(Utility.entifyString(value.toString()));

				if(i != maxUtilizedIndex)
					outputWriter.write(separator);
			} 

			if(cdataSerialization)
				outputWriter.write("]]>");

		}

		return true;
	}

	/** Reset the values within the container. 
	 */
	private void resetValues (int numOfLocations) 
	{

		isNoDataValue = new byte[numOfLocations];
		valueList = new Object[numOfLocations];

		capacity = numOfLocations;
		maxUtilizedIndex = -1;

	}

	/**
	 * Set the value at the specified location.
	 * @param Object value to set. Value cannot be "null" (use a noDataValue instead).
	 * @param Locator object to indicate where to set the value.
	 * @throws IllegalAccessException when called for mapping-based containers.
	 * @throws IllegalArgumentException or a locator belonging to another ObjectWithValues is passed.
	 * @throws NullPointerException when null parameters are passed.
	 * @throws SetDataException when setting a value at a location would require inserting null values in preceeding locations.
	 */
	protected final void setValue (Object obj, Locator locator)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (obj == null || locator == null)
		{
			throw new NullPointerException("Error: setValue - was passed a null object/locator parameter.");
		}

		if (locator.getParent() != this)
		{
			throw new  IllegalArgumentException("Can't setValue with locator "+locator+" does not belong to parent\n object:"+this+" it belongs to:"+locator.getParent());
		}

		// ugh. Java wont let us set an unpopulated location. Check
		// to see size (number of populated locations) in order to
		// know which method to call
		int index = locator.getListIndex();

		if (obj == null) {
			isNoDataValue[index] = 1;
		} else {
			isNoDataValue[index] = 0;
		}

		if( index > maxUtilizedIndex)
			maxUtilizedIndex = index;

		if ( index > getCapacity())
			setCapacity(index * 2);

		valueList[index] = obj;

	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getLocators()
	 */
	public final List<Locator> getLocators() { return locatorList; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#getValue()
	 */
	public final Object getValue() { return getFirstValue(); }


}

