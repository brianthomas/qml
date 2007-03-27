

// QML Xerces2 DOM QTrackingElementImpl
// CVS $Id$

// QTrackingElementImpl.java Copyright (C) 2005 Brian Thomas,

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

package net.datamodel.qml.support.DOMXerces2;

import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.QMLElement;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/** 
     An implementation of Element based on the Xerces2 ElementNSImpl class.
     We need this so we can keep track of QMLElements (e.g. quantities) that 
     are within the parent document.
 */

public class QTrackingElementImpl extends ElementNSImpl
{
	
	private static final Logger logger = Logger.getLogger(QTrackingElementImpl.class);

   //
   // Constructors
   //

   public QTrackingElementImpl (CoreDocumentImpl doc, String namespaceURI, String qName, String localName )
   throws org.w3c.dom.DOMException
   {
      super (doc, namespaceURI, qName, localName);
      ownerDocument = doc;
   }

   public QTrackingElementImpl (CoreDocumentImpl doc, String namespaceURI, String qName )
   throws org.w3c.dom.DOMException
   {
      super (doc, namespaceURI, qName);
      ownerDocument = doc;
   }

   public QTrackingElementImpl (CoreDocumentImpl doc, String tag)
   {
      super(doc, tag);
      ownerDocument = doc;
   }

   //
   // Other Public Methods
   //

   public Node appendChild (Node newChild) throws DOMException
   {

      newChild = super.appendChild(newChild);

      checkForQuantitiesAdd(newChild);

      return newChild;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException
   {
       Node node = super.insertBefore(newChild,refChild);

       checkForQuantitiesAdd(newChild);

       return node;
   }

   public Node removeChild(Node oldChild) throws DOMException
   {
       Node node = super.removeChild(oldChild);

       checkForQuantitiesRemove(oldChild);

       return node;
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException
   {
       Node node = super.replaceChild(newChild, oldChild);

       checkForQuantitiesAdd(newChild);
       checkForQuantitiesRemove(oldChild);

       return node;
   }

   // Private methods.
   //
   private void checkForQuantitiesAdd (Node node) 
   {

      if (node == null)
          return;

      if (node instanceof QMLElement)
      {
         QMLElement qElem = (QMLElement) node;
         Quantity q = qElem.getQuantity();

         // now tell parent document about this..
         if(getOwnerDocument() instanceof QMLDocumentImpl)
         {
             ((QMLDocumentImpl) getOwnerDocument()).QuantityList.add(q);
             logger.debug("  ** Adding Q to document:"+q);
         }
      }
   }

   private void checkForQuantitiesRemove (Node node)
   {

      if (node == null)
          return;

      if (node instanceof QMLElement)
      {
         QMLElement qElem = (QMLElement) node;
         Quantity q = qElem.getQuantity();

         // now tell parent document about this..
         if(getOwnerDocument() instanceof QMLDocumentImpl)
         {
             ((QMLDocumentImpl) getOwnerDocument()).QuantityList.remove(q);
             logger.debug("  ** Removing Q from document:"+q);
         }

      }
   }


}

