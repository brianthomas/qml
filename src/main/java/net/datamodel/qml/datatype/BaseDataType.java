
//CVS $Id$

//BaseDataType.java Copyright (c) 2006 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.datatype;

import net.datamodel.qml.DataType;
import net.datamodel.qml.Quantity;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.impl.AbstractXMLSerializableObject;

/**
 * An abstract class for describing the format of the data value in all value-based
 * quantities and component classes (composite quantities do not have data types). 
 */
abstract public class BaseDataType 
extends AbstractXMLSerializableObject 
implements DataType
{
	// Fields

	// The object which represents the "no data available" value.
	private static final String noDataValueFieldName = "noDataValue";

	public BaseDataType ( ) { 
		setNamespaceURI(Quantity.namespaceURI);
		addField(noDataValueFieldName, null, XMLFieldType.ATTRIBUTE);
	}

	/**
	 * The object which represents the "no data available" value.
	 * @return the value of NoDataValue
	 */
	public final Object getNoDataValue (  ) {
		return getFields().get(noDataValueFieldName).getValue();
	}

	/**
	 * The object which represents the "no data available" value.
	 * @param value the object which represents the NoDataValue.
	 * @throws IllegalAccessException if called for some datatypes (e.g. VectorDataType).
	 */
	public final void setNoDataValue ( Object value  ) 
	throws IllegalAccessException
	{
		getFields().get(noDataValueFieldName).setValue(value);
	}

	/**
	 * The number of bytes this data type represents.
	 */
	abstract public int numOfBytes ( );

}
