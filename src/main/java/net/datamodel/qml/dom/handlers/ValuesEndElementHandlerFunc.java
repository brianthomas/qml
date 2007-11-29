// CVS $Id$
// ValuesEndElementqhandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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


package net.datamodel.qml.dom.handlers;

import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.datatype.VectorDataType;
import net.datamodel.qml.dom.Constant;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.xssp.dom.EndElementHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class ValuesEndElementHandlerFunc implements EndElementHandler {

	private static final Logger logger = Logger.getLogger(ValuesEndElementHandlerFunc.class);

	public void action (XSSPDocumentHandler handler )
	throws SAXException
	{


		// Allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;


		Quantity qV = qhandler.getCurrentQuantity();
		qV.setValueCDATASerialization(qhandler.hasValuesInCDATASection());

		if(qhandler.hasCSVValues() || qhandler.hasVectorDataType()) {
			Locator loc = qhandler.getCurrentLocator();
			String[] values = qhandler.ValuesBuf.toString().trim().split(Constant.VALUE_SEPARATOR_STRING);

			int nrof_components = 0;
			if(qhandler.hasVectorDataType()) {
				nrof_components = ((VectorDataType) qV.getDataType()).getComponents().size();
			}

			// How we add this kind of data depends on whether we have a vector,
			// AND, if so, how many components it has.
			if(nrof_components == 1) {

				// simple to do: just like scalar data, but use
				// the components dataType
				Component comp = (Component) ((VectorDataType) qV.getDataType()).getComponents().get(0);
				DataType dataType = comp.getDataType();
				for(int i=0; i<values.length; i++)
				{
					QMLDocumentHandler.setValue(qV,dataType,values[i],loc);
					loc.next();
					qhandler.ActualValuesAdded++;
				}

			}
			else if (nrof_components > 1)
			{

				// for extreme debugging..
				// for(int i=0; i<values.length; i++) logger.debug("Value "+i+":"+values[i]);

				String componentValue = "";
				for(int i=1; i<=values.length; i++)
				{
					componentValue += Constant.VALUE_SEPARATOR_STRING + values[i-1];
					logger.debug("   build vc value is now:["+componentValue+"] i:"+i);
					if((i % nrof_components) == 0 && i >= nrof_components)
					{
						try {
							qV.setValue(componentValue.trim(),loc);
							logger.debug("DID vector component value add:["+componentValue+"] i:"+i+" li:"+loc.getListIndex());
						} catch (Exception e) {
							throw new SAXException("Can't set value in quantity :"+e.getMessage());
						}
						componentValue = "";
						qhandler.ActualValuesAdded++;
						logger.debug(" values added:"+qhandler.ActualValuesAdded);
						loc.next(); // advance the locator
					}
				}

			}
			else // just regular "scalar" data
			{
				DataType dataType = qV.getDataType();
				for(int i=0; i<values.length; i++)
				{
					qhandler.setValue(qV, dataType, values[i], loc);
					qhandler.ActualValuesAdded++;
					loc.next(); // advance the locator
				}
			}

			if(qhandler.hasCSVValues()) {
				qV.setTaggedValuesSerialization(false);
			}

			// reset vector type to false
			qhandler.setHasVectorDataType(false);
		}

		int expected = qhandler.getCurrentExpectedValues().intValue();
		// A Quick check to see if we have correct number of values

		if(expected > -1 && qhandler.ActualValuesAdded != expected) {
			throw new SAXException("Parser Error: got different number of values ("+qhandler.ActualValuesAdded
					+") from expected size ("+expected+"). Is the file values section appropriately formatted?");
		}

		qhandler.setHasMultipleValues(false);

	}
}

