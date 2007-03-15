
//CVS $Id$

//ScalarDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.xssp.XMLFieldType;

/**
 * Class ScalarDataType
 * A scalar (single-valued) data type. 
 */
abstract public class ScalarDataType extends BaseDataType {

	// Fields

	// The width (total length) of this scalar field, 
	// in characters (Width is stored in this fashion so as 
	// to separate it out from the actual encoding of the text).
	private static final String widthFieldName = "width";

	// Methods

	// Constructors

	// No-arguement Constructor
	public ScalarDataType ( ) { 

		setXMLNodeName(null);

		// now initialize XML fields
		// order matters!
		addField(widthFieldName, null, XMLFieldType.ATTRIBUTE);

	}

	// Accessor Methods

	/**
	 * Determine the width (total length) of this scalar field, in characters.
	 * (Width is stored in this fashion so as to separate it out from 
	 * the actual encoding of the text).
	 */
	public final Integer getWidth (  ) {
		return (Integer) getFields().get(widthFieldName).getValue();
	}

	/**
	 * Set the width (total length) of this scalar field, in characters.
	 * (Width is stored in this fashion so as to separate it out from 
	 * the actual encoding of the text).
	 */
	public final void setWidth ( Integer value ) {
		getFields().get(widthFieldName).setValue(value);
	}

	/** Determine if other units are equivalent to this one.*/
	public boolean equals (Object obj) {
		if (obj instanceof ScalarDataType) {
			if (
					super.equals(obj)
					&&
					this.getWidth().equals( ((ScalarDataType)obj).getWidth())
			)
				return true;
		}
		return false;
	}

	// TODO: implement hashCode!

}

