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

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;

import net.datamodel.qml.Quantity;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * QMLDocument is a DOM-based document interface. It aggregates the common  signature of QML DOM Documents regardless of underlying DOM implementation  (Xerces 2 or Crimson, etc).
 */

public interface QMLDocument extends Document 
{

   public String toXMLString ();
   public void toXMLWriter (Writer outputWriter) throws IOException;
   public void toXMLFile (String fileName) throws IOException;

   public void setDocumentElement (Element elem);

   public QMLElement createQMLElement(Quantity quantity) throws DOMException;

   public QMLElement createQMLElementNS(String namespaceURI, Quantity quantity) throws DOMException;

   /**
 * @uml.property  name="quantityIdTable"
 */
public Hashtable getQuantityIdTable();

   public List getQuantityList(boolean deep);

   public void setPrefixNamespaceMapping(String prefix, String namespaceURI);

   public String getNamespace(String prefix);

   /**
 * @param prefixNamespaceMappings  the prefixNamespaceMappings to set
 * @uml.property  name="prefixNamespaceMappings"
 */
public void setPrefixNamespaceMappings(Hashtable prefixMappings);

   /**
 * Get the prefix mappings for this document.
 * @uml.property  name="prefixNamespaceMappings"
 */
   public Hashtable getPrefixNamespaceMappings();

}

