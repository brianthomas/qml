

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

import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.QMLElement;
import net.datamodel.soml.support.DOMXerces2.SOMLElementImpl;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/** An implementation of QMLElement based on the Xerces2 ElementNSImpl class.
 */

public class QMLElementImpl extends SOMLElementImpl 
implements QMLElement
{

	private static final Logger logger = Logger.getLogger(QMLElementImpl.class);

	private static final long serialVersionUID = -4652785809392671264L;
	
	public QMLElementImpl(Quantity q, DocumentImpl doc) 
	throws IOException, NullPointerException 
	{
		super(q, doc);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLElement#getQuantity()
	 */
	public final Quantity getQuantity()  { return (Quantity) getSemanticObject(); }

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#appendChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	/*
	// TODO: doc on overriding this method
	@Override
	public Node appendChild(Node newChild) 
	throws DOMException
	{

		if (newChild instanceof QMLElement) 
		{
			QMLElement qElem = (QMLElement) newChild;
			Quantity q = qElem.getQuantity();

			// Add as a member
			// TODO: this should be handled by SOML element, no?
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
	*/

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#insertBefore(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	/*
	// TODO: notes on overriding this method
	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException
	{

		Node node = null;

		if (newChild instanceof QMLElement)
		{
			node = super.insertBefore(newChild,refChild);
			
			QMLElement qElem = (QMLElement) newChild;
			Quantity q = qElem.getQuantity();

			// Add as a member
			getQuantity().addProperty(q);

		} else {
			throw new DOMException (DOMException.NOT_SUPPORTED_ERR, "Can't remove regular DOM Element from QMLElement object.");
		}

		return node;
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#removeChild(org.w3c.dom.Node)
	 */
	/*
	// TODO: notes on overriding this method
	public Node removeChild(Node oldChild) throws DOMException
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
	*/

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.ParentNode#replaceChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	/*
	// TODO: doc on overriding this method
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException
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
	*/

	/*
	public void toXMLWriter (Writer outputWriter, String indent, boolean doFirstIndent, boolean doLastNewLine)
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
			q.toXMLWriter(idTable, prefixTable, outputWriter, indent, doFirstIndent, doLastNewLine);
		} else 
			q.toXMLWriter(outputWriter, indent, doFirstIndent, doLastNewLine);

	}
	*/

}

