package net.datamodel.qml.core;

import java.net.URI;
import java.util.List;

import net.datamodel.qml.Constants;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.ValueMapping;

import org.apache.log4j.Logger;


//CVS $Id$

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


/**
 * A quantity which holds one value. The value may be any object.
 * @version $Revision$
 */
public class AtomicQuantityImpl 
extends AbstractQuantity 
{

	private static final Logger logger = Logger.getLogger(AtomicQuantityImpl.class);

	/** no-arg constructor. */
	// I dont think we want to allow this
	protected AtomicQuantityImpl () { this (null, 1); }
	
	/** Construct the Quantity with the indicated URI.
	 * 
	 * @param uri
	 */
	public AtomicQuantityImpl (URI uri) { this(uri, 1); }

	/** Construct a quantity for a given capacity.
	 * 
	 * @param capacity
	 */
	protected AtomicQuantityImpl (URI uri, int capacity) {  
		super (uri, capacity);  
		setXMLNodeName(Constants.NodeName.ATOMIC_QUANTITY);
	}

	/* Construct this quantity with mapping rather than explicitly holding 
	 * values. Values will be generated on demand from the (value) mapping.
	 */
	// Make this protected as there is little sense in creating a mapping
	// when you have 1 value (!)
	protected AtomicQuantityImpl (URI uri, ValueMapping mapping ) 
	{ 
		this(uri, 1);
		setValueContainer ((ValueContainer) mapping);
	}

	/** Set the first (and prehaps only) value of this quantity.
	 * 
	 * @param obj Byte to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( Byte obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}

	/** Set the first (and prehaps only) value of this quantity.
	 * 
	 * @param obj Double to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( Double obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}   

	/**
	 * Set the first (and prehaps only) value of this quantity.
	 * 
	 * @param obj Float to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( Float obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}


	/**
	 * Set the first (and prehaps only) value of this quantity.
	 * @param obj Integer to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( Integer obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}

	/**
	 * Set the first (and prehaps only) value of this quantity.
	 * @param obj Short to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( Short obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}

	/**
	 * Set the first (and prehaps only) value of this quantity.
	 * @param obj String to set the value to. It may be "null".
	 * @throws SetDataException when setting a value that is a quantity.
	 */
	public final void setValue ( String obj )
	throws SetDataException
	{
		try {
			getValueContainer().setFirstValue(obj);
		} catch (IllegalAccessException e) {
			logger.error("Can't set data in quantity because "+e.getMessage());
		}
		updateSize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Byte, net.datamodel.qml.Locator)
	 */
	public final void setValue ( Byte obj, Locator loc) 
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Double, net.datamodel.qml.Locator)
	 */
	public final void setValue ( Double obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Float, net.datamodel.qml.Locator)
	 */
	public final void setValue ( Float obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Integer, net.datamodel.qml.Locator)
	 */
	public final void setValue ( Integer obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Short, net.datamodel.qml.Locator)
	 */
	public final void setValue ( Short obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{

		if (hasMapping())
			throw new IllegalAccessException("setValue() illegal. This quantity has mapping-generated values.");

		getValueContainer().setValue(obj,loc);
		updateSize();
	}

}
