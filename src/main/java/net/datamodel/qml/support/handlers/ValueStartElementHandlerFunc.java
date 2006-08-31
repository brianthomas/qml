// CVS $Id$
// ValueStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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
import net.datamodel.qml.Quantity;
import net.datamodel.qml.core.ListQuantityImpl;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.qml.support.StartElementHandlerAction;

// Import needed SAX stuff
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ValueStartElementHandlerFunc implements StartElementHandlerAction {
       public Object action ( QMLDocumentHandler handler, String namespaceURI, 
                              String localName, String qName, Attributes attrs)
       throws SAXException {

           Quantity qV = handler.getCurrentQuantity();

           // HACK : we need to return the value container ONLY if
           // its an AtomicQuantity OR if its the only value within a
           // List/Matrix Q (which we can find by checking HasValues).
           // The reason for this is so that the prefix, and xmlnodename
           // of the "value" will be set correctly in the cases when it
           // is to be returned.
           return (!(qV instanceof ListQuantityImpl) || !handler.HasMultipleValues) ? qV.getValueContainer() : null;
       }
}

