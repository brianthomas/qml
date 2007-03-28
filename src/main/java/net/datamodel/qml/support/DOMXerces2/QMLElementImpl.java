

//QML Xerces2DOM QMLElementImpl
//CVS $Id$

//QMLElementImpl.java Copyright (C) 2005 Brian Thomas,

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

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLElement;
import net.datamodel.xssp.ReferenceableXMLSerializableObject;
import net.datamodel.xssp.XMLSerializableField;
import net.datamodel.xssp.XMLSerializableObject;
import net.datamodel.xssp.XMLSerializableObjectWithFields;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/** An implementation of QMLElement based on the Xerces2 ElementNSImpl class.
 */

public class QMLElementImpl extends ElementNSImpl
implements QMLElement
{

	private static final Logger logger = Logger.getLogger(QMLElementImpl.class);

	private static final long serialVersionUID = -4652785809392671264L;


	Quantity myQuantity = null;

	//
	// Constructors
	//

	// hmm..badness. We expect Quantity interface, then demand (internally) the 
	// XMLSerializableObject interface. This could lead to problems down the
	// line. :P 
	public QMLElementImpl (String namespaceURI, Quantity quantity, DocumentImpl doc) 
	throws IOException,NullPointerException
	{
		super (doc, namespaceURI, ((XMLSerializableObject) quantity).getXMLTagName());
		((XMLSerializableObject) quantity).setNamespaceURI(namespaceURI);
		setQuantity(quantity);
	}

	// this has same issue as the constructor above
	public QMLElementImpl (Quantity quantity, DocumentImpl doc) 
	throws IOException,NullPointerException
	{
		super (doc, Constants.QML_NAMESPACE_URI, ((XMLSerializableObject) quantity).getXMLTagName());
		setQuantity(quantity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#getAttribute(java.lang.String)
	 */
	@Override
	public final String getAttribute(String name) 
	{
		XMLSerializableField field = ((XMLSerializableObjectWithFields) getQuantity()).getXMLSerializableField(name);
		if (field != null)
			return field.getValue().toString();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#getTagName()
	 */
	@Override
	public final String getTagName() {
		return ((XMLSerializableObject) getQuantity()).getXMLTagName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#setAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public final void setAttribute(String name, String value) 
	{
		
		if (!getQuantity().addAttributeField(name, value))
			logger.error("setAttribute not allowed on existing attribute.");
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#getAttributeNS(java.lang.String, java.lang.String)
	 */
	@Override
	public final String getAttributeNS(String namespaceURI, String localName) 
	{
		logger.error("getAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#setAttributeNS(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public final void setAttributeNS ( String uri, String name, String value)
	{
		// not allowed
		logger.error("setAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#setAttributeNode(org.w3c.dom.Attr)
	 */
	@Override
	public final Attr setAttributeNode (Attr node) 
	{
		if (!getQuantity().addAttributeField(node.getName(), node.getValue()))
			logger.error("setAttributeNode not allowed on existing attribute."); 
		// FIXME: return an attribute Node from method 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#getAttributeNode(java.lang.String)
	 */
	@Override
	public final Attr getAttributeNode(String name) 
	{
		logger.error("getAttributeNode not allowed for Xerces2DOM.QMLElementImpl");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#setAttributeNodeNS(org.w3c.dom.Attr)
	 */
	@Override
	public final Attr setAttributeNodeNS ( Attr node) 
	{
		logger.error("setAttributeNodeNS not allowed for Xerces2DOM.QMLElementImpl");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#getAttributeNodeNS(java.lang.String, java.lang.String)
	 */
	@Override
	public final Attr getAttributeNodeNS(String namespaceURI, String localName) 
	{
		logger.error("getAttributeNodeNS not allowed for Xerces2DOM.QMLElementImpl");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#hasAttribute(java.lang.String)
	 */
	@Override
	public final boolean hasAttribute (String name) 
	{
		XMLSerializableField field = ((XMLSerializableObjectWithFields) getQuantity()).getXMLSerializableField(name);
		return (field == null) ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#hasAttributeNS(java.lang.String, java.lang.String)
	 */
	@Override
	public final boolean hasAttributeNS (String namespaceURI, String localName) 
	{
		logger.error("hasAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#removeAttribute(java.lang.String)
	 */
	@Override
	public final void removeAttribute(String name) 
	{
//		((XMLSerializableObject) getQuantity()).removeXMLSerializableField(name);
		if (!getQuantity().removeAttributeField(name))
			logger.error("removeAttributeNode could not remove Attribute node:"+name);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#removeAttributeNS(java.lang.String, java.lang.String)
	 */
	@Override
	public final void removeAttributeNS (String namespaceURI, String name) 
	{
		logger.error("removeAttributeNS not allowed for Xerces2DOM.QMLElementImpl");
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ElementImpl#removeAttributeNode(org.w3c.dom.Attr)
	 */
	@Override
	public final Attr removeAttributeNode (Attr oldAttr) 
	{
		logger.error("removeAttributeNode not allowed for Xerces2DOM.QMLElementImpl");

		// FIXME: need to return an attribute node we removed?
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLElement#getQuantity()
	 */
	public final Quantity getQuantity()  { return myQuantity; }

	/** Set the Quantity which represents this element.
	 * 
	 * @param object
	 * @throws NullPointerException
	 */
	protected final void setQuantity (Quantity object) 
	throws NullPointerException
	{
		if (object == null) 
			throw new NullPointerException("Can't set QMLElement with null Quantity object pointer");
		else
			myQuantity = object;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#appendChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	@Override
	public final Node appendChild(Node newChild) 
	throws DOMException
	{

		if (newChild instanceof QMLElement) 
		{
			QMLElement qElem = (QMLElement) newChild;
			Quantity q = qElem.getQuantity();

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

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#insertBefore(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	@Override
	public final Node insertBefore(Node newChild, Node refChild) throws DOMException
	{

		Node node = null;

		if (newChild instanceof QMLElement)
		{
			QMLElement qElem = (QMLElement) newChild;
			Quantity q = qElem.getQuantity();

			// Add as a member
			getQuantity().addProperty(q);

			node = super.insertBefore(newChild,refChild);

		} else {
			throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't remove regular DOM Element from QMLElement object.");
		}

		return node;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#removeChild(org.w3c.dom.Node)
	 */
	public final Node removeChild(Node oldChild) throws DOMException
	{

		if (oldChild instanceof QMLElement)
		{
			QMLElement qElem = (QMLElement) oldChild;
			Quantity q = qElem.getQuantity();

			// Add as a member
			getQuantity().removeProperty(q);

			oldChild = super.removeChild(oldChild); 

		} else {
			throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't remove regular DOM Element from QMLElement object.");
		}

		return oldChild;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#replaceChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	public final Node replaceChild(Node newChild, Node oldChild) throws DOMException
	{
		Node node = super.replaceChild(newChild, oldChild);

		if (oldChild instanceof QMLElement)
		{
			QMLElement qElem = (QMLElement) oldChild;
			Quantity q = qElem.getQuantity();

			// remove member
			getQuantity().removeProperty(q);

		} else {
			throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't insertBefore w/ DOM Element in QMLElement object.");
		}

		if (newChild instanceof QMLElement)
		{
			QMLElement qElem = (QMLElement) newChild;
			Quantity q = qElem.getQuantity();

			// add member
			getQuantity().addProperty(q);

		} else {
			throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't insertBefore w/ DOM Element in QMLElement object.");
		}

		return node;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLElement#toXMLString()
	 */
	public final String toXMLString () {
		return ((XMLSerializableObject) getQuantity()).toXMLString();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLElement#toXMLWriter(java.io.Writer)
	 */
	public final void toXMLWriter (Writer outputWriter)
	throws java.io.IOException
	{
		toXMLWriter(outputWriter,"",false, false);
	}

	/** */
	private void toXMLWriter (Writer outputWriter, String indent)
	throws java.io.IOException
	{
		toXMLWriter(outputWriter, indent, false, false);
	}

	/** */
	private void toXMLWriter (Writer outputWriter, String indent, boolean doFirstIndent, boolean doLastNewLine)
	throws java.io.IOException
	{

		// How should we write ourselves out? IF we have a Quantity
		// with an ID, then we need to check the document to see
		// if we write out as an refQuantity node or not. Otherwise..just
		// the normal proceedure is ok.

		Quantity q = getQuantity();

		// check parent document about this..
		if(getOwnerDocument() instanceof QMLDocumentImpl)
		{
			Map<String,ReferenceableXMLSerializableObject> idTable = ((QMLDocumentImpl) getOwnerDocument()).getXMLSerializableObjectIdTable();
			Map<String,String> prefixTable = ((QMLDocumentImpl) getOwnerDocument()).getPrefixNamespaceMappings();
			((XMLSerializableObject) q).toXMLWriter(idTable, prefixTable, outputWriter, indent, doFirstIndent, doLastNewLine);
		} else 
			((XMLSerializableObject) q).toXMLWriter(outputWriter, indent, doFirstIndent, doLastNewLine);

	}

}

