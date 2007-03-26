// CVS $Id$
// AxisFrameEndElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.support.QMLDocumentHandler;
import net.datamodel.soml.impl.SemanticObjectImpl;
import net.datamodel.xssp.parse.EndElementHandler;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

import org.xml.sax.SAXException;


public class AxisFrameEndElementHandlerFunc implements EndElementHandler 
{
       public void action (XSSPDocumentHandler handler )
       throws SAXException {

   		// let it bomb if the cast doesnt go right
   		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

          // peel off the last quantity, which should be our axisFrame
          Quantity q = qhandler.unrecordQuantity();
          Quantity cq = qhandler.getCurrentQuantity();

          if(q instanceof ReferenceFrame && cq instanceof MatrixQuantity)
          {
        	  
              ((MatrixQuantity)cq).addReferenceFrame(q); 
              
          } else if (cq instanceof SemanticObjectImpl) {
             // do nothing..we already added it as some other 
        	 // type of relationship
          } else
              throw new SAXException("Ugh. ReferenceFrame can't be found..bad parse.");

          handler.removeExpectedValues();

       }
}

