// CVS $Id$
// MatrixQuantityStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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


package net.datamodel.qml.support.handlers;

import java.net.URI;

import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.qml.support.Utility;
import net.datamodel.xssp.parse.StartElementHandler;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MatrixQuantityStartElementHandlerFunc implements StartElementHandler {
	public Object action ( XSSPDocumentHandler handler, String namespaceURI, 
			String localName, String qName, Attributes attrs)
	throws SAXException {

		URI uri = Utility.getURIFromAttribs(attrs);
		MatrixQuantityImpl matrixQ = new MatrixQuantityImpl(uri);
		matrixQ.setAttributeFields(attrs); // set XML attributes from passed list
		
		// Allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

		// In order to look for referenced Quantities, we "record" this one if it has a qid
		qhandler.recordQuantity(matrixQ);

		int expected = QMLDocumentHandler.findExpectedSize(attrs, Constants.QML_NAMESPACE_URI);
		qhandler.addExpectedValues(new Integer(expected));

		try {
			if(expected > matrixQ.getCapacity())
				matrixQ.setCapacity(expected+1);
		} catch (IllegalAccessException e) {
			// do nothing.. means we tried to raise the capacity of mapping-based valuescontainer
			// which is impossible in this case
		}

		return matrixQ;
	}
}

