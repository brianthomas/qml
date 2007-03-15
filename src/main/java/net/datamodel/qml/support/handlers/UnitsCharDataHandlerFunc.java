// CVS $Id$
// UnitsCharDataHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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
import net.datamodel.qml.Component;
import net.datamodel.qml.support.CharDataHandler;
import net.datamodel.qml.units.UnitsImpl;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

// Import needed SAX stuff
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UnitsCharDataHandlerFunc implements CharDataHandler {
       public void action (XSSPDocumentHandler handler, char buf [], int offset, int len)
       throws SAXException
       {

          // 1. get our value as a string
          String value = new String (buf, offset, len);
          value = value.trim();

          // 2. get the current quantity
          Component cp = handler.getCurrentComponent();

          // 3. retrive , and set, the value on our units
          ((UnitsImpl) cp.getUnits()).setValue(value);

      }
}

