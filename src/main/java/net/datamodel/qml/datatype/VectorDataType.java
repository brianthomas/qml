
// CVS $Id$

// VectorDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.datatype;

import java.util.Iterator;
import java.util.List;

import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.support.Constant;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.impl.AbstractXMLSerializableObject;
import net.datamodel.xssp.impl.AbstractXMLSerializableObjectList;

import org.apache.log4j.Logger;

/**
 * A vector (having one or more components) as a data type.
 */
public class VectorDataType 
extends AbstractXMLSerializableObject 
implements DataType
{

	private static final Logger logger = Logger.getLogger(VectorDataType.class);
	private static final String componentsFieldName = "components";

	// Constructors

	/** No-argument Constructor. */ 
	public VectorDataType ( ) { 

		setXMLNodeName(Constant.NodeName.VECTOR_DATATYPE);

		// order matters! these are in *reverse* order of their
		// occurence in the schema/DTD
		addField(componentsFieldName, new ComponentList(), XMLFieldType.CHILD);

	}

	// Accessor Methods

	/**
	 * The object which represents the "no data available" value.
	 */
	public final Object getNoDataValue () {
		String noDataValue = "";
		Iterator iter = getComponents().iterator();
		while (iter.hasNext()) {
			Component comp = (Component) iter.next();
			noDataValue += Constant.VALUE_SEPARATOR_STRING + comp.getDataType().getNoDataValue().toString(); 
		}
		return noDataValue.trim();
	}

	/**
	 * The object which represents the "no data available" value.
	 * This is actually constructed from the component dataTypes held by 
	 * the vector, therefore it is not kosher to call this method for VectorDataType.
	 * @throws IllegalAccessException if called for VectorDataType.
	 */
	public final void setNoDataValue (Object value) 
	throws IllegalAccessException
	{
		throw new IllegalAccessException("Can't set no data value for vectordatatype. You must set this in each of its components instead.");
	}

	/**
	 * Add an object of type Component to represent a component of this vector.
	 *
	 * @throws NullPointerException if a null reference is passed.
	 * @return boolean value of whether addition was successfull or not.
	 */
	public final boolean addComponent ( Component value )
	throws NullPointerException
	{
		// can't add ourselves as alternative value of ourselves (!)
		if(value == null)
			throw new NullPointerException ();

		return getComponents().add(value);
	}

	/**
	 * Remove an object of type Component from the component List.
	 *
	 * @return boolean value of whether removal was successfull or not.
	 */
	public final boolean removeAltValue ( Component value )
	{
		return getComponents().remove(value);
	}

	/**
	 * Get the list of component objects.
	 * 
	 * @return List of components held by this VectorDataType
	 */
	public final List<Component> getComponents (  ) {
		return (List<Component>) getFieldValue(componentsFieldName);
	}

	/**
	 * The number of bytes this data type represents.
	 */
	public final int numOfBytes ( ) {
		// TODO: FIX  
		logger.error("VectorDataType returns INCORRECT numOfBytes, please fix");
		return -1;
	}

	/** Determine if other units are equivalent to these.
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (obj instanceof VectorDataType) {
			if (
					super.equals(obj)
					&&
					// TODO: FIXME : should iterate thru to find equivalence
					this.getComponents().equals( ((VectorDataType)obj).getComponents())
			)
				return true;
		}
		return false;
	}

	// TODO: add hashCode method

	/** A little class to hold our components.
	 * 
	 */
	private class ComponentList<Component> 
	extends AbstractXMLSerializableObjectList {
		public ComponentList( ) { super(""); }
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.DataType#getFortranFormat()
	 */
	public final String getFortranFormat() {
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		int cntr = 0;
		for (Component comp : getComponents()) {
			if (cntr++ != 0)
				buf.append(", ");
		}
		buf.append("}");
		return buf.toString();
	}

}

