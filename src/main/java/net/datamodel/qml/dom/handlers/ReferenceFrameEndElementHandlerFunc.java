// CVS $Id$
// ReferenceFrameEndElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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

import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.soml.impl.SemanticObjectImpl;
import net.datamodel.xssp.dom.EndElementHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.xml.sax.SAXException;

public class ReferenceFrameEndElementHandlerFunc implements EndElementHandler 
{
       public void action (XSSPDocumentHandler handler )
       throws SAXException {

   		// let it bomb if the cast doesnt go right
   		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

          // peel off the last ref frame
          ReferenceFrame r = qhandler.unrecordReferenceFrame();
          Quantity cq = qhandler.getCurrentQuantity();

          if(cq instanceof MatrixQuantity)
          {
              ((MatrixQuantity) cq).addReferenceFrame(r); 
          } else if (cq instanceof SemanticObjectImpl) {
             // do nothing..we already added it as some other 
        	 // type of relationship
          } else
              throw new SAXException("Ugh. ReferenceFrame can't be found..bad parse.");

          qhandler.removeExpectedValues();

       }
}

