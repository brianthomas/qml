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

// import QML stuff
import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.qml.support.StartElementHandlerAction;

// Import needed SAX stuff
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MatrixQuantityStartElementHandlerFunc implements StartElementHandlerAction {
       public Object action ( QMLDocumentHandler handler, String namespaceURI, 
                              String localName, String qName, Attributes attrs)
       throws SAXException {

          MatrixQuantityImpl matrixQ = new MatrixQuantityImpl();
          matrixQ.setFields(attrs); // set XML attributes from passed list

          // In order to look for referenced Quantities, we "record" this one if it has a qid
          handler.recordQuantity(matrixQ);

          int expected = handler.findExpectedSize(attrs, Constants.QML_NAMESPACE_URI);
          handler.addExpectedValues(new Integer(expected));

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

