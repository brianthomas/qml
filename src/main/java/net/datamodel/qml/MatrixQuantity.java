
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

import java.util.List;

/**
 * The interface for all Matrix Quantities. This is a type of quantity
 * which holds multiple values, which are described by one or more frames
 * of reference.
 */
public interface MatrixQuantity 
extends ListQuantity 
{

    // Operations

    /**
     * Get the list of axisframes in this quantity.
     * @return  List of axisframes
     */
    public List<ReferenceFrame> getReferenceFrames ( );
    
    /** add a frame of reference to the MatrixQuantity.
     * Incorrectly dimensioned ReferenceFrames are not allowed.
     * 
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an ReferenceFrame
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This ReferenceFrame may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @param frame
     * @throws IllegalArgumentException if the number of locations differ between 
     *         what is declared in the ReferenceFrame and the MatrixQuantity
     * @return true if it may add the ReferenceFrame 
     */
    public boolean addReferenceFrame(ReferenceFrame frame)
    throws IllegalArgumentException;
    
    /** remove a frame of reference from the Quantity.
     * 
     * @param frame
     * @return true if it may remove the ReferenceFrame 
     */ 
    public boolean removeReferenceFrame(ReferenceFrame frame);
    
    /**
     * Add an object of type ListQuantity to represent an equivalent, but
     *  alternative represention of this quantity. The alternative quantity must
     * have the same size (number of values) as the parent quantity that it represents.
     *
     * @throws IllegalArgumentException if either self or a quantity of different size from the parent is passed.
     * @return boolean value of whether addition was successfull or not.
     */
    // TODO
    // public boolean addAltValue ( ListQuantity value ) throws IllegalArgumentException;

    /**
     * Remove an object of type ListQuantityImpl from the List altvalueVector
     *
     * @return boolean value of whether removeal was successfull or not.
     */
    // TODO
    // public boolean removeAltValue ( ListQuantity value ); 

    /**
     * Get the list of altvalueVector
     * @return  List of altvalueVector
     */
    // TODO
    // public List getAltValueList ( ); 
}
