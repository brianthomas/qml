
// CVS $Id$

// TrivialQuantityImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.core;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.ValueContainer;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.support.Constants;
import net.datamodel.xssp.XMLFieldType;
import net.datamodel.xssp.XMLSerializableField;
import net.datamodel.xssp.XMLSerializableObject;

/**
 * A restricted atomic quantity. The trivial quantity may only hold a single "unitless", 
 * string value.
 */
public class TrivialQuantityImpl 
extends AtomicQuantityImpl 
{

    /** No-arg constructor  */
    public TrivialQuantityImpl () { 
       super(-1);
       setXMLNodeName(Constants.NodeName.TRIVIAL_QUANTITY);
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.ComponentImpl#setDataType(net.datamodel.qml.DataType)
     */
    @Override
    public void setDataType (DataType value) 
    throws IllegalArgumentException
    {
        if (value instanceof StringDataType) {
           super.setDataType(value);
        } else {
           throw new IllegalArgumentException("setDataType other than StringDataType not allowed in Trivial Quantity");
        }
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.ComponentImpl#setUnits(net.datamodel.qml.Units)
     */
    public void setUnits ( Units value ) 
    throws IllegalArgumentException
    {
        if (value.getString().equals("")) {
           super.setUnits(value);
        } else {
           throw new IllegalArgumentException("setUnits other than Unitless not allowed in Trivial Quantity");
        }
    }

  /*
   * (non-Javadoc)
   * @see net.datamodel.qml.core.AtomicQuantityImpl#setValue(java.lang.Byte, net.datamodel.qml.Locator)
   */
    public void setValue (Byte obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Byte data not allowed in Trivial Quantity");
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.AtomicQuantityImpl#setValue(java.lang.Double, net.datamodel.qml.Locator)
     */
    public void setValue (Double obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Double data not allowed in Trivial Quantity");
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.AtomicQuantityImpl#setValue(java.lang.Integer, net.datamodel.qml.Locator)
     */
    public void setValue (Integer obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Integer data not allowed in Trivial Quantity");
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.core.AtomicQuantityImpl#setValue(java.lang.Short, net.datamodel.qml.Locator)
     */
    public void setValue (Short obj, Locator loc)
       throws IllegalAccessException, IllegalArgumentException, NullPointerException, SetDataException
    {
        throw new IllegalAccessException("Short data not allowed in Trivial Quantity");
    }

    // FIXME : we are overriding a method in XSSP package!!
    /** returns true if there were child nodes to handle */
    @Override
    protected boolean handleChildNodes(Hashtable idTable, Hashtable prefixTable, Writer outputWriter, String nodeNameString,
                                       String indent, Vector childObjs, XMLSerializableField PCDATA)
    throws IOException
    {

       if ( childObjs.size() > 0 )
       {

          Object value = null;

          // deal with child object/list XML fields, if any in our list
          int objs_size = childObjs.size();
          for (int i = 0; i < objs_size; i++) {
            XMLSerializableField field = (XMLSerializableField) childObjs.get(i);

            if (field.getType() == XMLFieldType.CHILD)
            {

               XMLSerializableObject containedObj = (XMLSerializableObject) field.getValue();
               if(containedObj instanceof ValueContainer)
               {
                   value = ((ValueContainer) containedObj).getFirstValue();
                   break;
               }

            } else {
               // do nothing with other children
            }

          }

          // close the opening tag, print our value
          if (nodeNameString != null && value != null) {
             if(!nodeNameString.equals(""))
                outputWriter.write(">");

             outputWriter.write(value.toString());
             return true;
          }

        }

        return false;
    }
    
    // FIXME: we are overriding a method which is in the XSSP package (!!)
    @Override
    protected void doClosingNodeForChildren (String nodeNameString, 
    										String indent, boolean hasPCDATA,
                                             boolean isPrettyOutput,  Writer outputWriter)
    throws IOException
    {

          // ok, now deal with closing the node
          if (nodeNameString != null && !nodeNameString.equals("")) {

               if(!nodeNameString.equals(""))
                   outputWriter.write("</"+nodeNameString+">");
          }

    }

}


