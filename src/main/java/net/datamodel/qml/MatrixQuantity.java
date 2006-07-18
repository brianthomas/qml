
// CVS $Id$

// MatrixQuantity.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

// code generation timestamp: Tue Apr 20 2004-14:22:31 

package net.datamodel.qml;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * The interface for all Quantities.
 */
// hrmm. there doesnt seem to be a need for a "listQuantity"
// interface, so we just extend "QuantityWithValues" instead.
public interface MatrixQuantity extends ListQuantity {

    // Operations

    /**
     * Get the list of axisframes in this quantity.
     * @return  List of axisframes
     * @uml.property  name="axisFrameList"
     */
    public List getAxisFrameList (  );

    /**
     * Add an object of type ListQuantity to represent an equivalent, but
     *  alternative represention of this quantity. The alternative quantity must
     * have the same size (number of values) as the parent quantity that it represents.
     *
     * @throws IllegalArgumentException if either self or a quantity of different size from the parent is passed.
     * @return boolean value of whether addition was successfull or not.
     */
    public boolean addAltValue ( ListQuantity value ) throws IllegalArgumentException;

    /**
     * Remove an object of type ListQuantityImpl from the List altvalueVector
     *
     * @return boolean value of whether removeal was successfull or not.
     */
    public boolean removeAltValue ( ListQuantity value ); 

    /**
     * Get the list of altvalueVector
     * @return  List of altvalueVector
     * @uml.property  name="altValueList"
     */
    public List getAltValueList ( ); 
}
