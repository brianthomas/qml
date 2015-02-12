// CVS $Id$
// ComponentStartElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
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

import net.datamodel.qml.core.ComponentImpl;
import net.datamodel.qml.datatype.VectorDataType;
import net.datamodel.qml.dom.QMLDocumentHandler;
import net.datamodel.xssp.dom.StartElementHandler;
import net.datamodel.xssp.dom.XSSPDocumentHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ComponentStartElementHandlerFunc implements StartElementHandler {
	public Object action (XSSPDocumentHandler handler, String namespaceURI, String localName,
			String qName, Attributes attrs)
	throws SAXException {

		// allow it to crash if the cast fails
		QMLDocumentHandler qhandler = (QMLDocumentHandler) handler;

		ComponentImpl newComponent = new ComponentImpl();
		newComponent.setAttributeFields(attrs); // set XML attributes from passed list

		VectorDataType vector = (VectorDataType) qhandler.getCurrentObject();

		if(vector == null)
			throw new SAXException("Can't find parent vector to add component to.");

		vector.addComponent(newComponent);

		// record this as the last component we worked on
		qhandler.setLastComponent(newComponent);

		return newComponent;
	}
}
