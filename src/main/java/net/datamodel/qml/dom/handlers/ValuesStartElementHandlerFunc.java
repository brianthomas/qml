// CVS $Id$
// ValuesStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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


import net.datamodel.qml.Quantity;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.xssp.dom.StartElementHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ValuesStartElementHandlerFunc implements StartElementHandler {

	private static final Logger logger = Logger.getLogger(ValuesStartElementHandlerFunc.class);

	public Object action ( XSSPDocumentHandler handler, String namespaceURI, 
			String localName, String qName, Attributes attrs)
	throws SAXException {

		// Allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;


		// set meta-data about values..
		qhandler.ActualValuesAdded = 0; // values counter to "0"
		qhandler.setHasCSVValues(false);  // default is we have tagged values
		qhandler.setHasMultipleValues(true);
		qhandler.setHasValuesInCDATASection(false);
		qhandler.ValuesBuf = new StringBuffer(); // reset the values stringbuffer

		Quantity qV = qhandler.getCurrentQuantity();
		return qV.getValueContainer();

	}
}

