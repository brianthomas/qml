
// CVS $Id$

// XMLSerializableObject.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

// code generation timestamp: Tue May 30 2004-13:28:19 

package net.datamodel.qml;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;

import net.datamodel.qml.core.XMLSerializableField;

/**
 * The interface for all XMLSerializable objects.
 */
public interface XMLSerializableObject extends Cloneable {

    // Operations

    /**
     * When a class is serialzed in XML, this gives the name of the XML node to be used.
     * @uml.property  name="xMLNodeName"
     */
    public void setXMLNodeName(String name);

    /**
     * When a class is serialzed in XML, this gives the name of the XML node to be used.
     * @return  String on success, null (String Object) if the node name doesnt exist.
     * @uml.property  name="xMLNodeName"
     */
    public String getXMLNodeName();

    /**
     * When a class is serialzed in XML, this gives the value of the XML namespaceURI to use.
     * @return  String on success, null (String Object) if the value is undefined.
     * @uml.property  name="namespaceURI"
     */
    public String getNamespaceURI (  );

   /**
 * A Hashtable to hold the XML fields.
 * @return  Hashtable with all fields keyed by field name.
 * @uml.property  name="fields"
 */
    public Hashtable getFields ();

    /**
     * A List of the order of the XML fields for a given class/instance.
     * @return  List of fields in the order they will be written in the XML serialization.
     * @uml.property  name="fieldOrder"
     */
    public List getFieldOrder (  );

    /**
     * When a class is serialzed in XML, this sets the value of the XML namespaceURI to use. If the value is "null" then no prefix will be appended to the node name.
     * @uml.property  name="namespaceURI"
     */
    public void setNamespaceURI ( String value );

    public void toXMLFile (String fileName) throws java.io.IOException;

    public String toXMLString ();

    public void toXMLWriter (Writer outputWriter) throws java.io.IOException;

    public void toXMLWriter (Writer outputWriter, String indent) throws java.io.IOException;

    /* This is here for those DOM API which indent/do new line differently (aka "Crimson")
     * so that the output looks right in the output DOM.
     */
    public void toXMLWriter ( Writer outputWriter, String indent, boolean doFirstIndent, 
                              boolean doLastNewLine) throws java.io.IOException;

    public void toXMLWriter ( Hashtable qidTable, Hashtable prefixNamespaceMappingTable, 
                              Writer outputWriter, String indent, boolean doFirstIndent, 
                              boolean doLastNewLine ) throws java.io.IOException;

    public boolean addXMLSerializableField (String name, String value);

    public boolean removeXMLSerializableField (String fieldname );

    public XMLSerializableField findXMLSerializableField (String fieldname );

    /** Make a deep copy of this object.
     */
    public Object clone () throws CloneNotSupportedException;

}

