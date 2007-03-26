// CVS $Id$

// QMLDocument.java Copyright (C) 2004 Brian Thomas


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

package net.datamodel.qml.support;

import java.util.List;

import net.datamodel.qml.Quantity;
import net.datamodel.soml.support.SOMLDocument;

import org.w3c.dom.DOMException;

/**
 * QMLDocument is a DOM-based document interface. It aggregates the common  signature of QML DOM Documents regardless of underlying DOM implementation  (Xerces 2 or Crimson, etc).
 */

public interface QMLDocument extends SOMLDocument 
{


	/** Create an XML element node which represents the passed Quantity.
	 * 
	 * @param quantity
	 * @return
	 * @throws DOMException
	 */
	public QMLElement createQMLElement (Quantity quantity) 
	throws DOMException;

	/** Create a namespaced XML element node which represents the passed Quantity
	 * under the indicated namespaceURI.
	 * 
	 * @param namespaceURI
	 * @param quantity
	 * @return
	 * @throws DOMException
	 */
	public QMLElement createQMLElementNS (
			String namespaceURI, 
			Quantity quantity) 
	throws DOMException;

	/** Return a list of Quantities which are represented in the document.
	 * 
	 * @param deep
	 * @return
	 */
	public List<Quantity> getQuantities(boolean deep);

}

