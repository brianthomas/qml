
//CVS $Id$

//StringDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.Constants;

/**
 * A (scalar) string datatype.
 */
public class StringDataType extends ScalarDataType {

	/** No-arg Constructor */ 
	public StringDataType ( ) { 
		this(0);
	}
	
	public StringDataType (int width) 
	{ 
		super(width);

		setXMLNodeName(Constants.NodeName.STRING_DATATYPE);

		try {
			setNoDataValue("");
		} catch (Exception e) { }

	}

	/**
	 * The number of bytes this data type represents.
	 */
	public final int numOfBytes ( ) {
		// TODO: fix.. need to check for encoding..
		return getWidth().intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.DataType#getFortranFormat()
	 */
	public final String getFortranFormat() {
		return "A"+getWidth();
	}
	
	/** Determine if other datatypes are equivalent to these.
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (obj instanceof StringDataType) {
			if (
					super.equals(obj)
			)
				return true;
		}
		return false;
	}
	// TODO: implement hashCode!

}

