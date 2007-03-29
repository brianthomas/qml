
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

import java.net.URI;

import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Units;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.units.UnitsImpl;
import net.datamodel.soml.impl.SemanticObjectImpl;
import net.datamodel.xssp.XMLFieldType;

/**
 * Class ComponentImpl
 * A component of a vector. This is essentially 
 * a structure which looks like a Quantity without any values.
 */
public class ComponentImpl 
extends SemanticObjectImpl
implements Component
{

	// Fields
//	private static final Logger logger = Logger.getLogger(ComponentImpl.class);

	/* XML attribute names */
	private static final String datatypeFieldName = "dataType";
	private static final String unitsFieldName = "units";

	// Methods
	//

	// Constructors
	public ComponentImpl () {
		this (null);
	}
	
	/** Create a Component with indicated URI. 
	 */ 
	public ComponentImpl (URI uri) { 
		super(uri);
		setXMLNodeName("component");

		// initialize XML fields
		addField(datatypeFieldName, new StringDataType(), XMLFieldType.CHILD);
		addField(unitsFieldName, new UnitsImpl(""), XMLFieldType.CHILD);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Component#getUnits()
	 */
	public final Units getUnits ( ) {
		return (Units) getFieldValue(unitsFieldName);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Component#setUnits(net.datamodel.qml.Units)
	 */
	// TODO: make final
	public void setUnits (Units value) { setFieldValue(unitsFieldName, value); }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Component#getDataType()
	 */
	public final DataType getDataType () {
		return (DataType) getFieldValue(datatypeFieldName);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Component#setDataType(net.datamodel.qml.DataType)
	 */
	// TODO: make final
	public void setDataType (DataType value) { setFieldValue(datatypeFieldName, value); }

	// Operations
	//

	/** Determine if this component is exactly the same as the comparison object.
	 * All fields, sub-objects are compared between both objects.
	 */
//	Need to override the hashCode method as well??
	public boolean equals (Object obj) 
	{
		if (obj != null && obj instanceof Component) 
		{
			return this.getDataType().equals(((Component)obj).getDataType()) 
//			FIX Q: need to do the Id??
//			&& this.getId().equals((Component)obj.getId());
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

	// TODO : implement hashCode!
	
	/* Deep copy of this object.
	 *  @return an exact copy of this object
	 */
	/*
	public Object clone () throws CloneNotSupportedException 
	{
		return super.clone();
	}
	*/

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

}

