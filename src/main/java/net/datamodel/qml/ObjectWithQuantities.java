
// CVS $Id$

// ObjectWithQuantities.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.net.URI;
import java.util.List;

/**
 * The interface for all Quantities.
 */
public interface ObjectWithQuantities {

    // Operations

   /**
     * Add an object of type ObjectWithQuantities to the List of member Quantities.
     * The only restrictions on membership are that a quantity may not "own"
     * itself, and only MatrixQuantities and CompositeQuantiites may have AxisFrames.
     * Furthermore, incorrectly dimensioned AxisFrames are not allowed.
     * Correct dimensionality is when the multiple of the numberOfLocations of
     * all the child axes equal that of the parent size. For example, an AxisFrame
     * with "X" and "Y" axes quantities have numberOfLocations of 10 and 30 respectively.
     * This AxisFrame may be added to any quantity which itself has 10 x 30 = 300 locations.
     *
     * @throws IllegalArgumentException if adding self, or a member already exists with the same URI.
     * @throws NullPointerException if attempting to adding an null (!!)
     * @return boolean value of whether addition was successfull or not.
     */
     public boolean addMember (ObjectWithQuantities member) throws IllegalArgumentException, NullPointerException;

    /**
     * Remove a quantity from the member list.
     */
    public boolean removeMember ( ObjectWithQuantities quantity);
    
    /** Retrieve a member which has a matching URI.
     * 
     * @param uri
     * @return
     */
    public Quantity getMember (URI uri);

    /**
     * Get a list of quantities which are members of the given quantity.
     * @uml.property  name="memberList"
     */
    public List getMemberList ( );

    /**
     * Get the id of an instance of this class. It should be unique across all  components and quantities within a given document/object tree.
     * @uml.property  name="id"
     */
    public String getId ( );

   /**
 * The id of an instance of this class. It should be unique across all components and quantities within a given document/object tree.
 * @uml.property  name="id"
 */
    public void setId ( String value );

    /** Get the URI which represents this quantity. URI is used to provide the
     * id of the semantic meaning of the quantity.
     * @return URI of the quantity which represents its semantic meaning.
     */
	public URI getURI();
}

