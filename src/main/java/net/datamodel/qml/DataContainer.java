
// CVS $Id$

// DataContainer.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

/**
 * Interface DataContainer  The inteface for all data containers used by quantities.
 */
public interface DataContainer {
    // Methods
    // Accessor Methods
    // Operations

    /**
     * Create a locator for this class.
     */
    public Locator createLocator ( );
    
    /**
     * Get the value at the specified location. For atomic quantities  only one location will exist.
     */
    public Object getValue ( Locator loc) throws IllegalArgumentException;
        
    /**
     * Get the first value. For atomic quantities, this is the only value which exists.
     * @uml.property  name="value"
     */
    public Object getValue ( );
    
    /** 
     * Return the total number of values within this data container.
     */
    public int getSize();

}

