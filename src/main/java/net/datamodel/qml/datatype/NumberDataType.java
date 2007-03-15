
//CVS $Id$

//NumberDataFormat.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
 * Class NumberDataFormat
 * A scalar data type which describes some kind of number.
 */
abstract public class NumberDataType extends ScalarDataType 
{

	// Fields
	private final static String infiniteFieldName = "infinite";
	private final static String negativeInfiniteFieldName = "infiniteNegative";
	private final static String notANumberFieldName = "notANumber";
	private final static String disabledFieldName = "disabledValue";
	private final static String overflowFieldName = "overflowValue";
	private final static String underflowFieldName = "overflowValue";

	// Methods
	// Constructors
	/** No-argument Constructor
	 */
	public NumberDataType ( ) 
	{ 
		addField(infiniteFieldName, null, XMLFieldType.ATTRIBUTE);
		addField(negativeInfiniteFieldName, null, XMLFieldType.ATTRIBUTE);
		addField(notANumberFieldName, null, XMLFieldType.ATTRIBUTE);
		addField(disabledFieldName, null, XMLFieldType.ATTRIBUTE);
		addField(overflowFieldName, null, XMLFieldType.ATTRIBUTE);
		addField(underflowFieldName, null, XMLFieldType.ATTRIBUTE);
	}

	// Accessor Methods

	/**
	 * The object which represents the "infinite" value.
	 */
	public final Object getInfinite ( )  {
		return getFields().get(infiniteFieldName).getValue();
	}

	/**
	 * The object which represents the "infinite" value.
	 */
	public final void setInfinite ( Object value  )  {
		getFields().get(infiniteFieldName).setValue(value); 
	}

	/**
	 * The object which represents the "negative infinite" value.
	 */
	public final Object getInfiniteNegative (  ) {
		return getFields().get(negativeInfiniteFieldName).getValue();
	}

	/**
	 * The object which represents the "negative infinite" value.
	 */
	public final void setInfiniteNegative ( Object value  ) {
		getFields().get(negativeInfiniteFieldName).setValue(value); 
	}

	/**
	 * The object which represents the "not a number" value.
	 */
	public final Object getNotANumber (  ) {
		return getFields().get(notANumberFieldName).getValue();
	}

	/**
	 * The object which represents the "not a number" value.
	 */
	public final void setNotANumber ( Object value  ) {
		getFields().get(notANumberFieldName).setValue(value);
	}

	/**
	 * The object which represents the "no data possible (disabled location)" value.
	 */
	public final Object getDisabledValue (  ) {
		return getFields().get(disabledFieldName).getValue();
	}

	/**
	 * The object which represents the "no data possible (disabled location)" value.
	 */
	public final void setDisabledValue ( Object value  ) {
		getFields().get(disabledFieldName).setValue(value);
	}

	/**
	 * The object which represents the "overflow" value.
	 */
	public final Object getOverflowValue (  ) {
		return getFields().get(overflowFieldName).getValue();
	}

	/**
	 * The object which represents the "overflow" value.
	 */
	public final void setOverflowValue ( Object value  ) {
		getFields().get(overflowFieldName).setValue(value);
	}

	/**
	 * The object which represents the "underflow" value.
	 */
	public final Object getUnderflowValue (  ) {
		return getFields().get(underflowFieldName).getValue();
	}

	/**
	 * The object which represents the "underflow" value.
	 */
	public final void setUnderflowValue ( Object value  ) {
		getFields().get(underflowFieldName).setValue(value);
	}

	/** Determine if other units are equivalent to these.
	 */
	@Override
	public boolean equals (Object obj) {
		if (obj instanceof NumberDataType) {
			if (
					super.equals(obj)
					&&
					this.getInfinite().equals( ((NumberDataType)obj).getInfinite())
					&&
					this.getInfiniteNegative().equals( ((NumberDataType)obj).getInfiniteNegative())
					&&
					this.getNotANumber().equals( ((NumberDataType)obj).getNotANumber())
					&&
					this.getDisabledValue().equals( ((NumberDataType)obj).getDisabledValue())
					&&
					this.getOverflowValue().equals( ((NumberDataType)obj).getOverflowValue())
					&&
					this.getUnderflowValue().equals(((NumberDataType)obj).getUnderflowValue())
			)
				return true;
		}
		return false;
	}

	// TODO: implement hashCode

}

