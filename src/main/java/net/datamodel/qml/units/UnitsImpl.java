
// CVS $Id$

// UnitsImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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



package net.datamodel.qml.units;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.datamodel.qml.Units;
import net.datamodel.qml.support.Constants;
import net.datamodel.xssp.ReferenceableXMLSerializableObject;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.impl.AbstractXMLSerializableObject;

/**
 * A simple units class where units are represented 
 * only as strings. This is provided to give minimal 
 * functionality for quantity units. A more machine 
 * readable representation is desirable.
 */

public class UnitsImpl extends AbstractXMLSerializableObject 
implements Units
{

	// fields
	private static final String valueFieldName = new String("value");

	// Constructors

	// Empty Constructor not allowed 
	private UnitsImpl ( ) { } 

	/** Construct these units with some value.
	 */
	public UnitsImpl ( String value ) { 
		setXMLNodeName(Constants.NodeName.UNITS);
		addField(valueFieldName, value, XMLFieldType.PCDATA); 
	}

	/** Get the value of these units.
	 */
	public String getValue() {
		return (String) getFields().get(valueFieldName).getValue();
	}

	/** Set the value of these units.
	 */
	public void setValue (String value) {
		getFields().get(valueFieldName).setValue(value);
	}

	/** Get a string representation of the value of the units.
	 */
	public String getString () { return getValue(); }

	// Protected Methods
	//

	/**
	 * @return boolean value of whether or not some content was written.
	 */
	protected boolean basicXMLWriter (
			Map<String,ReferenceableXMLSerializableObject> idTable,
			Map<String,String> prefixTable,
			Writer outputWriter,
			String indent,
			String newNodeNameString, 
			boolean doFirstIndent 
	)
	throws IOException
	{
		// DONT write out if its empty (e.g. "unitless")
		if (getString().equals(""))
			return false;

		return super.basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, doFirstIndent);
	}


}
