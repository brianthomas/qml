
// QMLElement Interface 
// CVS $Id$

// QMLElement.java Copyright (C) 2004 Brian Thomas,

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

import java.io.Writer;

import net.datamodel.qml.Quantity;

import org.w3c.dom.Element;

/**
 * QMLElement is a DOM element interface for elements which hold Quantities within.
 */

public interface QMLElement extends Element
{

	/** Write the Element out as an XML string.
	 * 
	 * @return string 
	 */
	public String toXMLString ();

	/** Write the Element out as XML to the indicated Writer.
	 * 
	 */
	public void toXMLWriter (Writer outputWriter) throws java.io.IOException;

	/** Get the Quantity associated with this Element node. 
	 */
	public Quantity getQuantity();

	/* Set the Quantity associated with this Element node. 
	 * @param quantity  the quantity to set
	 */
//	Needed? We should limit this to construction time I think...
//	public void setQuantity(Quantity q);

}

