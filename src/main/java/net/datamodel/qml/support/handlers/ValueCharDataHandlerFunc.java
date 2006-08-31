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


package net.datamodel.qml.support.handlers;

// import QML stuff
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.CharDataHandlerAction;
import net.datamodel.qml.support.QMLDocumentHandler;

// Import needed SAX stuff
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ValueCharDataHandlerFunc implements CharDataHandlerAction {
       public void action (QMLDocumentHandler handler, char buf [], int offset, int len)
       throws SAXException
       {

          // 1. get our value as a string
          String value = new String (buf, offset, len);

          value = value.trim();

          if(!handler.IgnoreWhitespaceOnlyData || !value.equals(""))
          {

             // 2. get the current quantity
             Quantity qV = handler.getCurrentQuantity();
             Locator loc = handler.getCurrentLocator();

             handler.setValue(qV,value, loc);

             loc.next(); // advance the locator
             handler.ActualValuesAdded++;
         }
      }
}

