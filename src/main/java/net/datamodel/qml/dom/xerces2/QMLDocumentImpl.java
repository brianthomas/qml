
//QML DOM QMLDocumentImpl
//CVS $Id$

//QMLDocumentImpl.java Copyright (C) 2005 Brian Thomas,

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

package net.datamodel.qml.dom.xerces2;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.Quantity;
import net.datamodel.qml.dom.Constant;
import net.datamodel.qml.dom.QMLDocument;
import net.datamodel.qml.dom.QMLElement;
import net.datamodel.soml.ObjectProperty;
import net.datamodel.soml.Property;
import net.datamodel.soml.dom.DOMXerces2.SOMLDocumentImpl;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;

/** 
 *  Read any XML Document into a specialized DOM document -- QMLDocumentImpl.
 */

//Based on Xerces DocumentImpl class.
public class QMLDocumentImpl extends SOMLDocumentImpl 
implements QMLDocument
{

	private static final Logger logger = Logger.getLogger(QMLDocumentImpl.class);
	
	private static final long serialVersionUID = 2635262244735617568L;

	/** Create a QMLDocument. This implementation will automatically set up
	 * the basic default namespace mappings for target namespace 
	 * (set to {@link Constant.QML_NAMESPACE_URI})
	 * and "xsi" equal to {@link Constant.XML_SCHEMA_INSTANCE_NAMESPACE_URI}.
	 * Both of these settings may be overridden after construction using
	 * the {@link net.datamodel.xssp.parse.XSSPDocument#setPrefixNamespaceMapping(java.lang.String, java.lang.String)} method.
	 */
	public QMLDocumentImpl() {
		super();
		setPrefixNamespaceMapping("", Constant.QML_NAMESPACE_URI);
		setPrefixNamespaceMapping("xsi", Constant.XML_SCHEMA_INSTANCE_NAMESPACE_URI);
	}
	
	// TODO: remove when done testing.
	private void dumpUserData () {
		logger.debug("DOC userData is:");
		for (Object key : userData.keySet()) {
			logger.debug("key:"+key+" : "+userData.get(key)+" class: "+key.getClass());
		}
	}
		
	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLDocument#getQuantities(boolean)
	 */
	public final List<Quantity> getQuantities (boolean deep) 
	{
		List<Quantity> qList = new Vector<Quantity>(); 

		for (Object key : userData.keySet()) {
			if (key instanceof QMLElement)
			{
				QMLElement qElem = (QMLElement) key;
				qList.add(qElem.getQuantity()); 
				if (deep)
					qList.addAll(findQuantities(qElem.getQuantity()));
			}
		}

		return qList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.CoreDocumentImpl#createElement(java.lang.String)
	 */
	// TODO: evaluate need for tracking element..
	/*
	@Override
	public final Element createElement (String tagName) throws DOMException
	{

		ElementImpl retval = null;

		try {
			retval = new QTrackingElementImpl(this, tagName );
		} catch (DOMException e) {
			// tailor QTrackingElement message?
			throw new DOMException(DOMException.INVALID_STATE_ERR, e.getMessage());
		}
		return retval;
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.CoreDocumentImpl#createElementNS(java.lang.String, java.lang.String)
	 */
	// TODO: evaluate need for tracking element..
	/*
	@Override
	public final Element createElementNS (String uri, String tagName) 
	throws DOMException
	{

		ElementNSImpl retval = null;

		try {
			retval = new QTrackingElementImpl(this, uri, tagName);
		} catch (DOMException e) {
			// tailor QTrackingElement message?
			throw new DOMException(DOMException.INVALID_STATE_ERR, e.getMessage());
		}
		return retval;
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see org.apache.xerces.dom.CoreDocumentImpl#createElementNS(java.lang.String, java.lang.String, java.lang.String)
	 */
	// TODO: evaluate need for tracking element..
	/*
	@Override
	public final Element createElementNS (String uri, String qName, String lName) 
	throws DOMException
	{
		ElementNSImpl retval = null;

		try {
			retval = new QTrackingElementImpl(this, uri, qName, lName);
		} catch (DOMException e) {
			// tailor QTrackingElement message?
			throw new DOMException(DOMException.INVALID_STATE_ERR, e.getMessage());
		}

		return retval;
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLDocument#createQMLElement(net.datamodel.qml.Quantity)
	 */
	public final QMLElement createQMLElement(Quantity quantity) 
	throws DOMException
	{

		QMLElementImpl qElem = null;
		try {
			qElem = new QMLElementImpl(quantity, this);
		} catch (IOException e) {
			// dunno if this is the right error code.. but what the hell
			throw new DOMException(DOMException.INVALID_STATE_ERR, e.getMessage());
		}
		dumpUserData();
		return qElem;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.support.QMLDocument#createQMLElementNS(java.lang.String, net.datamodel.qml.Quantity)
	 */
	public final QMLElement createQMLElementNS(String namespaceURI, Quantity quantity)
	throws DOMException
	{

		QMLElementImpl qElem = null;
		try {
			quantity.setNamespaceURI(namespaceURI);
			qElem = new QMLElementImpl(quantity, this);
		} catch (IOException e) {
			// dunno if this is the right error code.. but what the hell
			throw new DOMException(DOMException.INVALID_STATE_ERR, e.getMessage());
		}
		dumpUserData();
		return qElem;

	}
	
	@Override
	public String getNamespaceURI() { return Constant.QML_NAMESPACE_URI; }

	@Override
	public String getSchemaName() { return Constant.QML_SCHEMA_NAME; }


	// insert prefix mappings in root element
	// TODO: need to override?
	/*
	private void insertPrefixMappings() 
	{

		Element root = getDocumentElement();
		logger.debug("START insertPrefixMappings");
		if(root != null) {

			logger.debug(" root not null insertPrefixMappings");
			// first check if the table is empty, if so, try to simulate one
			// based on the quantitie that we find.
			if (PrefixNamespaceMappingHashtable.isEmpty()) 
			{
				logger.warn("No prefix namespace table in document...(rootNode:"+root.getTagName()+")...doing slow auto-generation technique.");

				// first add in the necessaries
				PrefixNamespaceMappingHashtable.put("xsi",Constant.XML_SCHEMA_INSTANCE_NAMESPACE_URI);
				// PrefixNamespaceMappingHashtable.put("",Constant.QML_NAMESPACE_URI);

				// pre-add in the root element namespace as the default
				// this overrides, and is simpler/faster than the following chunk of 
				// commented out code
				String rootURI = root.getNamespaceURI();
				if (rootURI != null) {
					logger.debug(" ROOT Namepsace URI is :"+rootURI);
					PrefixNamespaceMappingHashtable.put("",rootURI);
				} 

				// get the root namespace (old way) 
				// if(root instanceof XMLSerializableObject) {
				//    XMLSerializableObject so = (XMLSerializableObject) root;
				//    logger.debug("Generation of namespace table from Quantity:"+so.getXMLNodeName());
				//    PrefixNamespaceMappingHashtable.putAll( 
				//                 AbstractXMLSerializableObject.generatePrefixNamespaceTable(so, PrefixNamespaceMappingHashtable));
				// }

				for (Quantity q : getQuantities(true)) { // go deep?
					if(q instanceof XMLSerializableObject) {
						XMLSerializableObject so = (XMLSerializableObject) q;
						logger.debug("Generation of namespace table from Quantity:"+so.getXMLTagName());
						PrefixNamespaceMappingHashtable.putAll( 
								Utility.generatePrefixNamespaceTable(so, PrefixNamespaceMappingHashtable));
						
					}
				}
				logger.debug("* Finished Auto-Generation of namespace table.");
			}

			if(root instanceof QMLElement) {
//				Quantity q = ((QMLElement) root).getQuantity(); 
//				XMLSerializableObject xmlq = (XMLSerializableObject) q;
				for (String prefix : PrefixNamespaceMappingHashtable.keySet()) {
					String namespaceURI = (String) PrefixNamespaceMappingHashtable.get(prefix);
					String name = "xmlns";
					if(!prefix.equals(""))
						name = "xmlns:"+prefix;
					// FIXME: cant add field!!
					logger.error("Cant addSerializableField in QMLDocImpl!");
//					xmlq.addXMLSerializableField (name, namespaceURI);
					logger.debug("   "+name+"="+namespaceURI);
				}

			} else {

				for (String prefix : PrefixNamespaceMappingHashtable.keySet()) {
					String namespaceURI = (String) PrefixNamespaceMappingHashtable.get(prefix);
					String name = "xmlns";
					Attr attrib = createAttribute(name);
					if(!prefix.equals(""))
					{
						name = "xmlns:"+prefix;
						attrib = createAttributeNS(namespaceURI, name, prefix);
					}
					attrib.setValue(namespaceURI);
					root.setAttributeNode(attrib);
					logger.debug("   "+name+"="+namespaceURI);
				}
			}

		}
		logger.debug("FINISH insertPrefixMappings");
	}

	// remove prefix mappings in root element
	private void removePrefixMappings() 
	{
		Element root = getDocumentElement();
		if(root != null) {

			if(root instanceof QMLElement) {
//				Quantity q = ((QMLElement) root).getQuantity();
//				XMLSerializableObject xmlq = (XMLSerializableObject) q;
				for (String prefix : PrefixNamespaceMappingHashtable.keySet()) {
					String name = "xmlns";
					if(!prefix.equals(""))
						name = "xmlns:"+prefix;
					// FIXME: cant remove field!!
					logger.error("Cant removeSerializableField in QMLDocImpl!");
//					xmlq.removeXMLSerializableField(name);
				}

			} else {

				for (String prefix : PrefixNamespaceMappingHashtable.keySet()) {
					String name = "xmlns";
					if(!prefix.equals(""))
						name = "xmlns:"+prefix;
					root.removeAttribute(name);
				}
			}
		}
	}
	*/

	/* (re-)build a hashtable of all Q's with qIds. 
	 * Repeat ids in q's are ignored
	 */
	// TODO: needed?
	/*
	private void updateQuantityIdTable() 
	{
		List deepList = getQuantities(true);
		QuantityIdTable = new Hashtable<String,ReferenceableXMLSerializableObject>();
		Iterator iter = deepList.iterator();
		while(iter.hasNext()) {
			Quantity q = (Quantity) iter.next();
			if(q.getId() != null) {
				if(!QuantityIdTable.containsKey(q.getId()))
					QuantityIdTable.put(q.getId(),q);
			}
		}
	}
	*/

	/** Find all the quantities of the passed Q. This will include
	 * a recursive traversal for all of the sub-Quantities of the 
	 * target which are properties (and properties of properties, and
	 * so on).
	 * 
	 * @param target
	 * @return
	 */
	private List<Quantity> findQuantities (Quantity target)
	{
		List<Quantity> qList = new Vector<Quantity>();
		// troll thru our relationships looking for objects which
		// are Quantities
		for (Property r : target.getProperties()) 
		{
			
			if (r instanceof ObjectProperty 
					&& ((ObjectProperty)r).getTarget() instanceof Quantity)
			{
				Quantity q = (Quantity) ((ObjectProperty)r).getTarget();
				qList.add(q);
				qList.addAll(findQuantities(q));
			}
		}
		return qList;
	}

}

