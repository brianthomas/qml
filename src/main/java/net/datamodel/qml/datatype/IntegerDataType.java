
//CVS $Id$

//IntegerDataType.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.qml.Constant;
import net.datamodel.xssp.XMLFieldType;

import org.apache.log4j.Logger;

/**
 * A scalar integer datatype. May be hexidecimal, octal or decimal in nature.
 */
public class IntegerDataType extends NumberDataType {

	private static final Logger logger = Logger.getLogger(IntegerDataType.class);

	// Fields

	// The type of representation to use for these integers in 
	// the XML serialization. Chioces are "decimal", "hexadecimal" 
	// and "decimal".
	private final static String signedFieldName = "signed";
	private final static boolean DEFAULT_SIGNED = false;

	// Whether or not the integers represented by this data type are signed.
	private final static String inttypeFieldName = "type";

	/** No-arg Constructor.*/ 
	public IntegerDataType ( ) { 
		this(2);
	}
	
	public IntegerDataType (int width) { 
		super(width);

		setXMLNodeName(Constant.NodeName.INTEGER_DATATYPE);

		try {
			setNoDataValue(new Integer(-9));
		} catch (Exception e) { }

		// now initialize XML fields
		// order matters!
		addField(signedFieldName, (String) null, XMLFieldType.ATTRIBUTE);
		addField(inttypeFieldName, new Boolean(DEFAULT_SIGNED), XMLFieldType.ATTRIBUTE);
	}

	// Accessor Methods
	/**
	 * The type of representation to use for these integers in the XML serialization. Chioces are "decimal", "hexadecimal" and "decimal".
	 */
	public final String getType (  ) {
		return (String) getFields().get(inttypeFieldName).getValue(); 
	}

	/**
	 * The type of representation to use for these integers in the XML serialization. Chioces are "decimal", "hexadecimal" and "decimal".
	 */
	public final void setType ( String value  ) {

		if( value != null)
		{
			if(!isValidIntegerType(value) ) {
				logger.warn("Warning: "+value+" is not a valid value for the type attribute, ignoring set request.");
				return;
			}
		}

		getFields().get(inttypeFieldName).setValue(value); 
	}

	/** Determine if the passed string represents a valid integer type.
	 * 
	 * @param strIntegerType
	 * @return
	 */
	public static final boolean isValidIntegerType(String strIntegerType) {
	      String[] integerTypeList = Constant.INTEGER_TYPE_LIST;
	      int stop = integerTypeList.length;
	      for (int i = 0; i < stop; i++) {
	        if (strIntegerType.equals(integerTypeList[i]))
	          return true;
	      }

	      return false;
	}
	      
	/**
	 * Whether or not the integers represented by this data type are signed.
	 */
	public final Boolean getSigned (  ) {
		return (Boolean) getFields().get(signedFieldName).getValue(); 
	}
	/**
	 * Whether or not the integers represented by this data type are signed.
	 */
	public final void setSigned ( Boolean value  ) {
		getFields().get(signedFieldName).setValue(value); 
	}

	/**
	 * The number of bytes this data type represents.
	 */
	@Override
	public int numOfBytes ( ) {
		// TODO: check if this is correct??
		return getWidth().intValue();
	}

	 
	/*
	// Determine if other units are equivalent to these.
	@Override
	public boolean equals (Object obj) 
	{
		if (obj instanceof IntegerDataType) {
			if (
					super.equals(obj)
					&&
					this.getSigned().equals( ((IntegerDataType)obj).getSigned())
					&&
					this.getType().equals( ((IntegerDataType)obj).getType())
			)
				return true;
		}
		return false;
	}
	*/
	// TODO: implement Hashcode!

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.DataType#getFortranFormat()
	 */
	public final String getFortranFormat() {
		return "I"+getWidth();
	}


}

