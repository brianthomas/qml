
// DataType.java Copyright (c) 2006 Brian Thomas. All rights reserved.

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

package net.datamodel.qml;

/**
 * An interface describing the format of the data value in all value-based
 * quantities and component classes.
 *
 * @author thomas
 *
 */
public interface DataType extends XMLSerializableObject {
    
    /**
     * The object which represents the "no data available" value.
     * @throws IllegalAccessException if called for some datatypes (e.g. VectorDataType).
     */
    public void setNoDataValue ( Object value  )  throws IllegalAccessException; 
    
    /**
     * The object which represents the "no data available" value.
     */
    public Object getNoDataValue (  );

    /** Determine if other datatypes are equal to this one. 
     *  @@Overrides
     */
    public boolean equals (Object obj);

    /**
     * The number of bytes this data type represents.
     */
    public int numOfBytes ( );
    
}
