//CVS $Id$
//RefQuantityStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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
import net.datamodel.qml.dom.Constant;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.xssp.dom.StartElementHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RefQuantityStartElementHandlerFunc implements StartElementHandler {

	private static final Logger logger = Logger.getLogger(RefQuantityStartElementHandlerFunc.class);

	public Object action ( XSSPDocumentHandler handler, String namespaceURI,
			String localName, String qName, Attributes attrs)
	throws SAXException {

		Quantity refQ = null;

		String qIdRef = attrs.getValue(Constant.QIDREF_ATTRIBUTE_NAME);

		// Allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

		//  If there is a reference object, clone it to get the new quantity
		if (qIdRef != null) {

			// if (qhandler.ObjWithQuantities.containsKey(qIdRef)) {

			if (qhandler.hasRecordedQuantityWithId(qIdRef)) 
			{

				try {

					refQ = (Quantity) qhandler.getRecordedQuantity(qIdRef).clone();

					if(refQ instanceof Quantity) {
						qhandler.addExpectedValues(new Integer (((Quantity) refQ).getSize().intValue()));
					} else {
						qhandler.addExpectedValues(new Integer(0));
					}

					// record this new Q on the list of quantity objects
					qhandler.recordQuantity(refQ);

				} catch (CloneNotSupportedException e) {
					logger.error("refQuantity couldnt be created, clone failed:"+e.getMessage());
					e.printStackTrace();
				}

			} else { 
				logger.error("QMLReader got a quantity with qidRef=\""+qIdRef+"\" but no previous quantity has that id! Ignoring. Errors may result later.");
			}

		} else {
			logger.error("QMLReader got a refQuantity with NO qidRef! Didn't you validate this file before loading??");
		}

		return refQ;

	}
}

