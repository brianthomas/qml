// QML Xerces XML DOM Writer 
// CVS $Id$

// XMLWriter.java Copyright (C) 2005 Brian Thomas

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
   original code by : Andy Clark, IBM

*/

// Of general use...but only Xerces2 uses right now
package net.datamodel.qml.support;

import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The Xerces2 DOM writer. Hacked from the Xerces2 sample Writer.java program. 
 *
 * @author Brian Thomas
 *
 * @version $Id$
 */

public class XMLWriter {

    // Fields
    //

    /**
     * the writer.
     * @uml.property  name="outputWriter"
     */
    protected Writer outputWriter = null;

    /**
     * Canonical output.
     * @uml.property  name="isCanonical"
     */
    protected boolean isCanonical = false;
    
    /**
     * Set whether we processing an XML 1.1 document.
     * @uml.property  name="isXML11"
     */
    protected boolean isXML11 = false;

    /**
     * Newline Formatting information
     * @uml.property  name="nEW_LINE"
     */
    private String NEW_LINE = Constants.NEW_LINE;
    /**
     * @uml.property  name="isPretty"
     */
    private boolean isPretty = false;

    //
    // Constructors
    //

    /** Constructor. 
     */
    public XMLWriter (Writer baseWriter)
    {
        this (baseWriter, false);
    }

    /** Full Constructor. 
     */
    public XMLWriter (Writer baseWriter, boolean canonical)
    {
        outputWriter = baseWriter; 
        isCanonical = canonical;
    }

    //
    // Public methods
    //


    /** Writes the specified node, recursively. 
      * Starting indent may be specified, but will not
      * be used if specification is not set to pretty print.
      */
    public void write (Node node, String startIndent) 
    throws java.io.IOException
    {

        Specification spec = Specification.getInstance();
        isPretty = spec.isPrettyOutput();

        baseWrite (node, startIndent);
   
        if (isPretty)
           outputWriter.write (NEW_LINE);

    }

    /** Writes the specified node, recursively. 
     */
    public void write (Node node) 
    throws java.io.IOException
    {
        write (node, "");
    }

    //
    // Protected Methods
    //

    protected void baseWrite (Node node, String indent)
    throws java.io.IOException
    {

        // is there anything to do?
        if (node == null) {
            return;
        }

        short type = node.getNodeType();
        switch (type) {
            case Node.DOCUMENT_NODE: {
                Document document = (Document) node;
                isXML11 = "1.1".equals(getVersion(document));
                if (!isCanonical) {
                    if (isXML11) {
                        outputWriter.write("<?xml version=\"1.1\" encoding=\"UTF-8\"?>");
                        if (isPretty) 
                            outputWriter.write(NEW_LINE);
                    }
                    else {
                        outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                        if (isPretty) 
                            outputWriter.write(NEW_LINE);
                    }
                    outputWriter.flush();
                    baseWrite(document.getDoctype(), indent );
                }
                baseWrite(document.getDocumentElement(), indent );
                break;
            }

            case Node.DOCUMENT_TYPE_NODE: {
                DocumentType doctype = (DocumentType)node;
                outputWriter.write("<!DOCTYPE ");
                outputWriter.write(doctype.getName());
                String publicId = doctype.getPublicId();
                String systemId = doctype.getSystemId();
                if (publicId != null) {
                    outputWriter.write(" PUBLIC '");
                    outputWriter.write(publicId);
                    outputWriter.write("' '");
                    outputWriter.write(systemId);
                    outputWriter.write('\'');
                }
                else if (systemId != null) {
                    outputWriter.write(" SYSTEM '");
                    outputWriter.write(systemId);
                    outputWriter.write('\'');
                }
                String internalSubset = doctype.getInternalSubset();
                if (internalSubset != null) {
                    outputWriter.write(" [");
                    if (isPretty) 
                        outputWriter.write(NEW_LINE);
                    outputWriter.write(internalSubset);
                    outputWriter.write(']');
                }
                outputWriter.write('>');
                if (isPretty) 
                    outputWriter.write(NEW_LINE);
                break;
            }

            case Node.ELEMENT_NODE: {
                if (node instanceof QMLElement)
                {

                    ((QMLElement) node).toXMLWriter(outputWriter);

                } else {
                   if (isPretty) 
                       outputWriter.write(indent);
                   outputWriter.write('<');
                   outputWriter.write(node.getNodeName());
                   Attr attrs[] = sortAttributes(node.getAttributes());
                   for (int i = 0; i < attrs.length; i++) {
                       Attr attr = attrs[i];
                       outputWriter.write(' ');
                       outputWriter.write(attr.getNodeName());
                       outputWriter.write("=\"");
                       normalizeAndPrint(attr.getNodeValue(), true);
                       outputWriter.write('"');
                   }
                   outputWriter.write('>');
                   outputWriter.flush();
   
                   Node child = node.getFirstChild();
                   while (child != null) {
                       baseWrite(child, indent );
                       child = child.getNextSibling();
                   }
                }
                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
                if (isCanonical) {
                   Node child = node.getFirstChild();
                   while (child != null) {
                      baseWrite(child, indent );
                      child = child.getNextSibling();
                   }
                }
                else {
                    outputWriter.write('&');
                    outputWriter.write(node.getNodeName());
                    outputWriter.write(';');
                    outputWriter.flush();
                }
                break;
            }

            case Node.CDATA_SECTION_NODE: {
                if (isCanonical) {
                    normalizeAndPrint(node.getNodeValue(), false);
                }
                else {
                    outputWriter.write("<![CDATA[");
                    outputWriter.write(node.getNodeValue());
                    outputWriter.write("]]>");
                }
                outputWriter.flush();
                break;
            }

            case Node.TEXT_NODE: {
                normalizeAndPrint(node.getNodeValue(), false);
                outputWriter.flush();
                break;
            }

            case Node.PROCESSING_INSTRUCTION_NODE: {
                outputWriter.write("<?");
                outputWriter.write(node.getNodeName());
                String data = node.getNodeValue();
                if (data != null && data.length() > 0) {
                    outputWriter.write(' ');
                    outputWriter.write(data);
                }
                outputWriter.write("?>");
                outputWriter.flush();
                break;
            }
            
            case Node.COMMENT_NODE: {
                if (!isCanonical) {
                    if (isPretty)
                       outputWriter.write(indent);
                    outputWriter.write("<!--");
                    String comment = node.getNodeValue();
                    if (comment != null && comment.length() > 0) {
                        outputWriter.write(comment);
                    }
                    outputWriter.write("-->");
                    if (isPretty)
                       outputWriter.write(NEW_LINE);
                    outputWriter.flush();
                }
            }
        }

        // Minor TODO: allow collapsing of element nodes which 
        // have no children or CDATA/PCDATA within them.
        if (type == Node.ELEMENT_NODE && !(node instanceof QMLElement)) {
            outputWriter.write("</");
            outputWriter.write(node.getNodeName());
            outputWriter.write('>');
            if (isPretty)
                outputWriter.write(NEW_LINE);
            outputWriter.flush();
        }

    } 

    /** Returns a sorted list of attributes. 
     */
    protected Attr[] sortAttributes(NamedNodeMap attrs) 
    {

        int len = (attrs != null) ? attrs.getLength() : 0;
        Attr array[] = new Attr[len];
        for (int i = 0; i < len; i++) {
            array[i] = (Attr)attrs.item(i);
        }
        for (int i = 0; i < len - 1; i++) {
            String name = array[i].getNodeName();
            int index = i;
            for (int j = i + 1; j < len; j++) {
                String curName = array[j].getNodeName();
                if (curName.compareTo(name) < 0) {
                    name = curName;
                    index = j;
                }
            }
            if (index != i) {
                Attr temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
        }

        return array;

    } 

    /** Normalizes and prints the given string. 
     */
    protected void normalizeAndPrint(String s, boolean isAttValue) 
    throws java.io.IOException
    {

        int len = (s != null) ? s.length() : 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            normalizeAndPrint(c, isAttValue);
        }

    }

    /** Normalizes and print the given character. 
     */
    protected void normalizeAndPrint(char c, boolean isAttValue) 
    throws java.io.IOException
    {

        switch (c) {
            case '<': {
                outputWriter.write("&lt;");
                break;
            }
            case '>': {
                outputWriter.write("&gt;");
                break;
            }
            case '&': {
                outputWriter.write("&amp;");
                break;
            }
            case '"': {
                // A '"' that appears in character data 
                // does not need to be escaped.
                if (isAttValue) {
                    outputWriter.write("&quot;");
                }
                else {
                    outputWriter.write("\"");
                }
                break;
            }
            case '\r': {
            	// If CR is part of the document's content, it
            	// must not be printed as a literal otherwise
            	// it would be normalized to LF when the document
            	// is reparsed.
            	outputWriter.write("&#xD;");
            	break;
            }
            case '\n': {
                if (isCanonical) {
                    outputWriter.write("&#xA;");
                    break;
                }
                // else, default print char
            }
            default: {
            	// In XML 1.1, control chars in the ranges [#x1-#x1F, #x7F-#x9F] must be escaped.
            	//
            	// Escape space characters that would be normalized to #x20 in attribute values
            	// when the document is reparsed.
            	//
            	// Escape NEL (0x85) and LSEP (0x2028) that appear in content 
            	// if the document is XML 1.1, since they would be normalized to LF 
            	// when the document is reparsed.
            	if (isXML11 && ((c >= 0x01 && c <= 0x1F && c != 0x09 && c != 0x0A) 
            	    || (c >= 0x7F && c <= 0x9F) || c == 0x2028)
            	    || isAttValue && (c == 0x09 || c == 0x0A)) {
            	    outputWriter.write("&#x");
            	    outputWriter.write(Integer.toHexString(c).toUpperCase());
            	    outputWriter.write(";");
                }
                else {
                    outputWriter.write(c);
                }        
            }
        }
    }

    /** Extracts the XML version from the Document. 
     */
    protected String getVersion (Document document) {
    	if (document == null) {
    	    return null;
    	}
        String version = null;
        Method getXMLVersion = null;
        try {
            getXMLVersion = document.getClass().getMethod("getXmlVersion", new Class[]{});
            // If Document class implements DOM L3, this method will exist.
            if (getXMLVersion != null) {
                version = (String) getXMLVersion.invoke((Object)document, (Object[]) null);
            }
        } 
        catch (Exception e) { 
            // Either this locator object doesn't have 
            // this method, or we're on an old JDK.
        }
        return version;
    } 

}  

