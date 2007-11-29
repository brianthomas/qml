
// CVS $Id$

// TrivialQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.dom.Constant;

/**
 * A restricted type of atomic quantity. The trivial quantity may only 
 * hold a single "unitless", string value, and may not have properties.
 */
// FIXME: Do we need this to inherit XMLSerializableObjectWithFields instead of
// SemanticObject...its not allowed to have any relationships..hrmm. This
// is a problematic class and should probably be deleted..
public class TrivialQuantityImpl 
extends AbstractQuantity
{

	/** No-arg constructor  */
	public TrivialQuantityImpl () { 
		this(null);
	}
	
	public TrivialQuantityImpl (URI uri) { 
		super(uri);
		setXMLNodeName(Constant.NodeName.TRIVIAL_QUANTITY);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.ComponentImpl#setDataType(net.datamodel.qml.DataType)
	 */
	@Override
	public final void setDataType (DataType value) 
	throws IllegalArgumentException
	{
		if (value instanceof StringDataType) {
			super.setDataType(value);
		} else {
			throw new IllegalArgumentException("setDataType other than StringDataType not allowed in Trivial Quantity");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.ComponentImpl#setUnits(net.datamodel.qml.Units)
	 */
	public final void setUnits ( Units value ) 
	throws IllegalArgumentException
	{
		if (value.getString().equals("")) {
			super.setUnits(value);
		} else {
			throw new IllegalArgumentException("setUnits other than Unitless not allowed in Trivial Quantity");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.AbstractQuantity#setValue(java.lang.Byte, net.datamodel.qml.Locator)
	 */
	public final void setValue (Byte obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		throw new IllegalAccessException("Byte data not allowed in Trivial Quantity");
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.AbstractQuantity#setValue(java.lang.Double, net.datamodel.qml.Locator)
	 */
	public final void setValue (Double obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		throw new IllegalAccessException("Double data not allowed in Trivial Quantity");
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.AbstractQuantity#setValue(java.lang.Integer, net.datamodel.qml.Locator)
	 */
	public final void setValue (Integer obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		throw new IllegalAccessException("Integer data not allowed in Trivial Quantity");
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.core.AbstractQuantity#setValue(java.lang.Short, net.datamodel.qml.Locator)
	 */
	public final void setValue (Short obj, Locator loc)
	throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
	{
		throw new IllegalAccessException("Short data not allowed in Trivial Quantity");
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithValues#setValue(java.lang.Float, net.datamodel.qml.Locator)
	 */
	public void setValue(Float obj, Locator loc) throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException {
		throw new IllegalAccessException("Float data not allowed in Trivial Quantity");
	}

	/** Returns true if there were child nodes to handle.
	 * 
	 */
	// FIXME : we are overriding a method in XSSP package, bah!!
	/*
	@Override
	protected boolean handleChildNodes(Map<String,String> idTable, 
			Map<XMLSerializableObject, String> prefixTable, 
			Writer outputWriter, 
			String nodeNameString,
			String indent, 
			List<XMLSerializableObject> childObjs, 
			XMLSerializableField PCDATA)
	throws IOException
	{

		if ( childObjs.size() > 0 )
		{

			Object value = null;

			// deal with child object/list XML fields, if any in our list
			int objs_size = childObjs.size();
			for (int i = 0; i < objs_size; i++) {
				XMLSerializableField field = (XMLSerializableField) childObjs.get(i);

				if (field.getType() == XMLFieldType.CHILD)
				{

					XMLSerializableObject containedObj = (XMLSerializableObject) field.getValue();
					if(containedObj instanceof ValueContainer)
					{
						value = ((ValueContainer) containedObj).getFirstValue();
						break;
					}

				} else {
					// do nothing with other children
				}

			}

			// close the opening tag, print our value
			if (nodeNameString != null && value != null) {
				if(!nodeNameString.equals(""))
					outputWriter.write(">");

				outputWriter.write(value.toString());
				return true;
			}

		}

		return false;
	}
	*/

	// FIXME: we are overriding a method which is in the XSSP package (!!)
	/*
	@Override
	protected void doClosingNodeForChildren (
			String nodeNameString, 
			String indent, boolean hasPCDATA,
			boolean isPrettyOutput,  
			Writer outputWriter
	)
	throws IOException
	{

		// ok, now deal with closing the node
		if (nodeNameString != null && !nodeNameString.equals("")) {

			if(!nodeNameString.equals(""))
				outputWriter.write("</"+nodeNameString+">");
		}

	}
	*/


}


