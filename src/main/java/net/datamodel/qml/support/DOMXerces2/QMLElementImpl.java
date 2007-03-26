

// QML Xerces2DOM QMLElementImpl
// CVS $Id$

// QMLElementImpl.java Copyright (C) 2005 Brian Thomas,

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

import net.datamodel.qml.ObjectWithQuantities;
import net.datamodel.qml.XMLSerializableObject;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.XMLSerializableField;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocument;
import net.datamodel.qml.support.QMLElement;
import net.datamodel.qml.support.Specification;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.DocumentImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import java.util.Hashtable;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** 
     An implementation of QMLElement based on the Xerces2 ElementNSImpl class.
 */

public class QMLElementImpl extends ElementNSImpl
implements QMLElement
{
	
	private static final Logger logger = Logger.getLogger(QMLElementImpl.class);

   // 
   // Fields
   //
   ObjectWithQuantities myQuantity = null;

   //
   // Constructors
   //

   // hmm..badness. We expect ObjectWithQuantities interface, then demand (internally) the 
   // XMLSerializableObject interface. This could lead to problems down the
   // line. :P 
   public QMLElementImpl (String namespaceURI, ObjectWithQuantities quantity, DocumentImpl doc) 
   throws IOException,NullPointerException
   {
      super (doc, namespaceURI, ((XMLSerializableObject) quantity).getXMLTagName());
      ((XMLSerializableObject) quantity).setNamespaceURI(namespaceURI);
      setQuantity(quantity);
   }

   // this has same issue as the constructor above
   public QMLElementImpl (ObjectWithQuantities quantity, DocumentImpl doc) 
   throws IOException,NullPointerException
   {
      super (doc, Constants.QML_NAMESPACE_URI, ((XMLSerializableObject) quantity).getXMLTagName());
      setQuantity(quantity);
   }

   //
   // Get/Set Methods 
   //

   public String getAttribute(String name) 
   {
      XMLSerializableField field = ((XMLSerializableObject) getQuantity()).findXMLSerializableField(name);
      if (field != null)
         return field.getValue().toString();
      return null;
   }

   public String getTagName() {
      return ((XMLSerializableObject) getQuantity()).getXMLTagName();
   }

   // hmmm. sense badness here..how to keep these in sync?
   public void setAttribute(String name, String value) 
   {
//      super(name,value);
      ((XMLSerializableObject)getQuantity()).addXMLSerializableField(name,value); 
   }

   public String getAttributeNS(String namespaceURI, String localName) 
   {
       logger.error("getAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public void setAttributeNS ( String uri, String name, String value)
   {
       // not allowed
       logger.error("setAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
   }

   public Attr setAttributeNode ( Attr node) 
   {
//      ((XMLSerializableObject)getQuantity()).addXMLSerializableField(node.getName(),node.getValue()); 
       logger.error("setAttributeNode not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public Attr getAttributeNode(String name) 
   {
       logger.error("getAttributeNode not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public Attr setAttributeNodeNS ( Attr node) 
   {
       logger.error("setAttributeNodeNS not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public Attr getAttributeNodeNS(String namespaceURI, String localName) 
   {
       logger.error("getAttributeNodeNS not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public boolean hasAttribute (String name) 
   {
      XMLSerializableField field = ((XMLSerializableObject) getQuantity()).findXMLSerializableField(name);
      return (field == null) ? false : true;
   }

   public boolean hasAttributeNS (String namespaceURI, String localName) 
   {
       logger.error("hasAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
       return false;
   }

   public void removeAttribute(String name) 
   {
      ((XMLSerializableObject) getQuantity()).removeXMLSerializableField(name);
   }

   public void removeAttributeNS (String namespaceURI, String name) 
   {
       logger.error("removeAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
   }

   public Attr removeAttributeNode (Attr oldAttr) 
   {
       logger.error("removeAttributeNode not allowed for Xerces2DOM.QMLElementImpl");
       return null;
   }

   public ObjectWithQuantities getQuantity() 
   {
      return myQuantity;
   }

   public void setQuantity (ObjectWithQuantities object) 
   throws NullPointerException
   {
      if (object == null) 
         throw new NullPointerException("Can't set QMLElement with null ObjectWithQuantities object pointer");
      else
         myQuantity = object;
   }

   //
   // Other Public Methods
   //

   /** */
   public Node appendChild(Node newChild) 
   throws DOMException
   {

      if (newChild instanceof QMLElement) 
      {
         QMLElement qElem = (QMLElement) newChild;
         ObjectWithQuantities q = qElem.getQuantity();

         // Add as a member
         getQuantity().addProperty(q);

         newChild = super.appendChild(newChild); // needed? 

      } else if (newChild instanceof Comment) 
      {
          logger.warn("Can't append comment node into QMLElement..ignoring.");
          return null;
      } else {
         String tag = newChild.getNodeName();
         throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't append regular DOM Element:"+tag+" to QMLElement object:"+this.getNodeName()+".");
      }

      return newChild;
   }

   /** */
   public Node insertBefore(Node newChild, Node refChild) throws DOMException
   {

      Node node = null;

      if (newChild instanceof QMLElement)
      {
         QMLElement qElem = (QMLElement) newChild;
         ObjectWithQuantities q = qElem.getQuantity();

         // Add as a member
         getQuantity().addProperty(q);

         node = super.insertBefore(newChild,refChild);

      } else {
         throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't remove regular DOM Element from QMLElement object.");
      }

      return node;
   }

   /** */
   public Node removeChild(Node oldChild) throws DOMException
   {

      if (oldChild instanceof QMLElement)
      {
         QMLElement qElem = (QMLElement) oldChild;
         ObjectWithQuantities q = qElem.getQuantity();

         // Add as a member
         getQuantity().removeProperty(q);

         oldChild = super.removeChild(oldChild); 

      } else {
         throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't remove regular DOM Element from QMLElement object.");
      }

      return oldChild;
   }

   /** */
   public Node replaceChild(Node newChild, Node oldChild) throws DOMException
   {
       Node node = super.replaceChild(newChild, oldChild);

       if (oldChild instanceof QMLElement)
       {
          QMLElement qElem = (QMLElement) oldChild;
          ObjectWithQuantities q = qElem.getQuantity();

          // remove member
          getQuantity().removeProperty(q);

       } else {
         throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't insertBefore w/ DOM Element in QMLElement object.");
       }

       if (newChild instanceof QMLElement)
       {
          QMLElement qElem = (QMLElement) newChild;
          ObjectWithQuantities q = qElem.getQuantity();

          // add member
          getQuantity().addProperty(q);

       } else {
         throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't insertBefore w/ DOM Element in QMLElement object.");
       }

       return node;
   }

   /** 
   */
   public String toXMLString () {
      return ((XMLSerializableObject) getQuantity()).toXMLString();
   }

   /** */
   public void toXMLWriter (Writer outputWriter)
   throws java.io.IOException
   {
      toXMLWriter(outputWriter,"",false, false);
   }

   /**
   */
   public void toXMLWriter (Writer outputWriter, String indent)
   throws java.io.IOException
   {
      toXMLWriter(outputWriter, indent, false, false);
   }

   /**
    */
   public void toXMLWriter (Writer outputWriter, String indent, boolean doFirstIndent, boolean doLastNewLine)
   throws java.io.IOException
   {

      // How should we write ourselves out? IF we have a ObjectWithQuantities
      // with an ID, then we need to check the document to see
      // if we write out as an refQuantity node or not. Otherwise..just
      // the normal proceedure is ok.

      ObjectWithQuantities q = getQuantity();

      // check parent document about this..
      if(getOwnerDocument() instanceof QMLDocumentImpl)
      {
         Hashtable idTable = ((QMLDocumentImpl) getOwnerDocument()).getQuantityIdTable();
         Hashtable prefixTable = ((QMLDocumentImpl) getOwnerDocument()).getPrefixNamespaceMappings();
         ((XMLSerializableObject) q).toXMLWriter(idTable, prefixTable, outputWriter, indent, doFirstIndent, doLastNewLine);
      } else 
         ((XMLSerializableObject) q).toXMLWriter(outputWriter, indent, doFirstIndent, doLastNewLine);

   }

   // 
   // Private Methods
   //

}

