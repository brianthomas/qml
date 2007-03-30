// CVS $Id$
// QuantityEndElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.xssp.parse.EndElementHandler;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

//end handler for all quantities
public class QuantityEndElementHandlerFunc 
implements EndElementHandler 
{
	private static final Logger logger = Logger.getLogger(QuantityEndElementHandlerFunc.class);

	public void action (XSSPDocumentHandler handler )
	throws SAXException {

		logger.debug("Quantities End Handler called");

		// Allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

		// peel off the last quantity, and locator, in the current list
		Quantity q = qhandler.unrecordQuantity();

		// Are we adding altValues? If so, we  should
		// now add this quantity to altvalues section of current parent Q
		if(qhandler.isAddingAltValues())
		/*
			if (q instanceof ListQuantity)
			{
				qhandler.getCurrentParentQuantityAltValue().addAltValue((ListQuantity)q);
			} else { 
			*/
				throw new SAXException("Alternative value not a list Quantity");
			// }

		qhandler.removeExpectedValues();

		logger.debug("Quantity End Handler - FINISH");

	}
	
}

