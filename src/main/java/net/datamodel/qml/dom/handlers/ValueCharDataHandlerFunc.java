// CVS $Id$
// ValueCharDataHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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

import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.xssp.dom.CharDataHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.xml.sax.SAXException;

public class ValueCharDataHandlerFunc 
implements CharDataHandler 
{
	public void action (XSSPDocumentHandler handler, char buf [], int offset, int len)
	throws SAXException
	{

		// 1. get our value as a string
		String value = new String (buf, offset, len);

		value = value.trim();

		if(!handler.IgnoreWhitespaceOnlyData || !value.equals(""))
		{
			

			// Allow it to crash if the cast fails
			QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

			// 2. get the current quantity
			Quantity qV = qhandler.getCurrentQuantity();
			Locator loc = qhandler.getCurrentLocator();

			QMLDocumentHandler.setValue(qV,value, loc);

			loc.next(); // advance the locator
			qhandler.ActualValuesAdded++;
		}
	}
}

