
// CVS $Id$

// QMLReader.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import net.datamodel.soml.support.SOMLReader;

import org.xml.sax.InputSource;

/** This class is used to create QMLDocument from QML files/streams.
 */
public class QMLReader
extends SOMLReader
{
	
    //
    // Constructor methods 
    //

    /** Constructor. Pass a QMLDocument to use as the structure to read 
     *  information into. Note that if the passed QMLDocument has
     *  prior information in it, it will remain *unless* overridden by 
     *  conflicting information from the input source. 
     */
    public QMLReader (QMLDocument doc)  { super (doc); }

   /** Get the (QML) Document that the QMLReader will parse an InputSource into. 
     *  @return QMLDocument that results from the parsing.
    */
    @Override
    public QMLDocument getDocument()  {
       return (QMLDocument) super.getDocument();
    }

    /** Parse an InputSource into a QMLDocument object.
     *  @return QMLDocument that results from the parsing.
     */
    @Override
    public QMLDocument parse (InputSource inputsource) 
    throws java.io.IOException
    {
        return (QMLDocument) super.parse(inputsource);
    }

    /** Utility method to parse from file into a QMLDocument.
     *  @return QMLDocument that results from the parsing.
     */
    @Override
    public QMLDocument parseFile (String file)
    throws java.io.IOException
    {
    	return (QMLDocument) super.parseFile(file);
    }

    /** Utility method to parse from a string into a QMLDocument.
     *  @return QMLDocument
     */
    @Override
    public QMLDocument parseString (String XMLContent)
    throws java.io.IOException
    {

    	return (QMLDocument) super.parseString(XMLContent);
    }

} // end QMLReader class

