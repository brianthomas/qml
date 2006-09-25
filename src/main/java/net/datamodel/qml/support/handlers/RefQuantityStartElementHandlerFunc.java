// CVS $Id$
// RefQuantityStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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
import net.datamodel.qml.SemanticObject;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.XMLSerializableObject;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.qml.support.StartElementHandlerAction;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RefQuantityStartElementHandlerFunc implements StartElementHandlerAction {
	
	private static final Logger logger = Logger.getLogger(RefQuantityStartElementHandlerFunc.class);
	
       public Object action ( QMLDocumentHandler handler, String namespaceURI,
                              String localName, String qName, Attributes attrs)
       throws SAXException {

          SemanticObject refQ = null;

          String qIdRef = attrs.getValue(Constants.QIDREF_ATTRIBUTE_NAME);

          //  If there is a reference object, clone it to get the new quantity
          if (qIdRef != null) {

             if (handler.ObjWithQuantities.containsKey(qIdRef)) {

                 try {
                    refQ = (SemanticObject) ((XMLSerializableObject) handler.ObjWithQuantities.get(qIdRef)).clone();

                    if(refQ instanceof Quantity)
                       handler.addExpectedValues(new Integer (((Quantity) refQ).getSize().intValue()));
                    else
                       handler.addExpectedValues(new Integer(0));

                    // record this new Q on the list of quantity objects
                    handler.recordObjectWithQuantities(refQ);

                 } catch (CloneNotSupportedException e) {
                    logger.error("refQuantity couldnt be created, clone failed:"+e.getMessage());
                    e.printStackTrace();
                 }

             } else
                 logger.error("QMLReader got a quantity with qidRef=\""+qIdRef+"\" but no previous quantity has that id! Ignoring. Errors may result later.");
          } else
             logger.error("QMLReader got a refQuantity with NO qidRef! Didn't you validate this file before loading??");

          return refQ;

       }
}

