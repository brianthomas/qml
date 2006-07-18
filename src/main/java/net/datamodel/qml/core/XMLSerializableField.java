
// CVS $Id$

// XMLSerializableField.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

package net.datamodel.qml.core;

import java.util.List;
import java.util.Vector;

import net.datamodel.qml.XMLSerializableObject;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Utility;
import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;

import org.apache.log4j.Logger;


  /** XMLSerializableField works in tandem with XMLSerializableObject to 
   *  provide flexible XML serialization in the QML package.
   *
   *  The XMLSerializableField class works as a minimal wrapper around
   *  those objects which are fields of an XMLSerializableObject.
   *
   *  Various types of XMLSerializableFields are supported, and these include 
   *  fields which are either serialized as PCDATA, CDATA, 
   *  child XML nodes or attributes. If the object is to be 
   *  serialized as a child node, it needs to inherit from the
   *  XMLSerializableObject class.
   *
   */
  public class XMLSerializableField implements Cloneable {
	  
	  private static final Logger logger = Logger.getLogger(XMLSerializableField.class);

    // the value is the object that is being held
    /**
     * @uml.property  name="value"
     */
    private Object value;
    // the type tells nature of object and how we 
    // are to serialize it
    /**
     * @uml.property  name="type"
     */
    private String type;

    /** Constructor takes object reference and type.
    */
    // Shouldnt type be an emunerated list from the Constants class?
    // NOT just any arbitrary string can go here.
    public XMLSerializableField (Object obj, String mytype) {

       setValue(obj);

       // minimal check to see if its kosher object
       if(setType(mytype) && obj != null)
          if(mytype == Constants.FIELD_CHILD_NODE_TYPE && !(obj instanceof XMLSerializableObject))
             logger.warn("Can't serialize FIELD_CHILD_NODE_TYPE obj:"+obj+" which doesn't inherit from XMLSerializableObject.");

    }

    /**
     * Set the value of this XMLSerializableField.
     * @uml.property  name="value"
     */
    public void setValue (Object objValue) {
      value = objValue;
    }

    /** Set the type of value held by this XMLSerializableField.
    */
    public boolean setType (String newtype) {
      if ( !Utility.isValidXMLSerializableFieldType(newtype))
      {
         logger.error("Can't setType : passed value is illegal for XMLSerializableField");
         return false;
      }

      // ok, set it
      type = newtype;

      return true;
    }

    /**
     * Get the value of this XMLSerializableField.
     * @uml.property  name="value"
     */
    public Object getValue() {
       return value;
    }

    /**
     * Get the XMLSerializableField value type.
     * @uml.property  name="type"
     */
    public String getType() {
       return type;
    }

    // deep clone this object
    public Object clone () throws CloneNotSupportedException 
    {

      synchronized (this) {
        XMLSerializableField cloneObj = null;
        cloneObj = (XMLSerializableField) super.clone();
        synchronized (cloneObj) {

          // need to deep copy the fields here too
          if (value == null) {
            return cloneObj;
          }
          if (value instanceof String ) {
            cloneObj.value = new String((String) this.value);
            return cloneObj;
         }
         if (value instanceof Integer) {
            cloneObj.value = new Integer(((Integer) this.value).intValue());
            return cloneObj;
         }
         if (value instanceof Double) {
          cloneObj.value = new Double(((Double) this.value).doubleValue());
          return cloneObj;
         }
         if (value instanceof List) {
           cloneObj.value = new Vector(((List) this.value).size());
           int stop = ((List)this.value).size();
           for (int i = 0; i < stop; i ++) {
              //List only contains child classes of XMLSerializableObject
              Object obj = ((List)this.value).get(i);
              ((List)cloneObj.value).add(((XMLSerializableObjectImpl) obj).clone());

            }
            return cloneObj;
         } // synchonized 
        //all other classes are child classes of XMLSerializableObject
        cloneObj.value = ((XMLSerializableObjectImpl) this.value).clone();
        return cloneObj;

      } // synchonized 
    }
  }

} 

