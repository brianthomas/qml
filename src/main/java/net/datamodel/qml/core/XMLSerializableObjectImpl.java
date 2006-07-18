
// CVS $Id$

// XMLSerializableObjectImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.XMLSerializableObject;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

/**
 * Underlying class for most QML package objects,
 * this class enables flexible XML serialization for all classes which need it.
 *
 * This class manages the storage, retrieval
 * and writing out the XML-serializable fields of various QML classes.
 *
 * The 2 parts that make this work in XMLSerializableObjectImpl are
 * 1) a lookup table of key/value pairs in fieldHash and 2) a
 * list containing the proper order of the fields.
 *
 * A separate XMLSerializableField class manages the aspects of these (child) fields.
 *
 */
public class XMLSerializableObjectImpl implements XMLSerializableObject
{

	private static final Logger logger = Logger.getLogger(XMLSerializableObjectImpl.class);
	
    // Fields

    /**
     * When a class is serialzed in XML, this gives the name of the XML  node to be used. Clearly, not all QML classes have a defined node name  (ie map to the QML DTD/Schema). The XMLSerializableObjectImpl class is an example of this case.
     * @uml.property  name="xmlNodeName"
     */
    protected String xmlNodeName;

    /**
     * Record the namspaceURI for this object. If its left blank, the document is left to determine the actual namespaceURI.
     * @uml.property  name="namespaceURI"
     */ 
    protected String namespaceURI;

    /**
     * A Hashtable to hold the aggregate objects of a given instance of a  given class.
     * @uml.property   name="fieldHash"
     * @uml.associationEnd   qualifier="constant:java.lang.String net.datamodel.qml.core.XMLSerializableField"
     */ 
    protected Hashtable fieldHash;

    /**
     * A List to store the order of the XML fields.
     * @uml.property  name="fieldOrder"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
     */
    protected List fieldOrder;

    // Methods
    //

    // Constructors

    /** The no-argument constructor. Set to protected to prevent
      * using it without inheriting by another class.
      */
    protected XMLSerializableObjectImpl ( ) { 
       resetFields();
    }

    // Accessor Methods
    // 

    /**
     * When a class is serialzed in XML, this gives the name of the XML node to be used.
     * @return String on success, null (String Object) if the node name doesnt exist.
     */
    public String getXMLNodeName (  ) {
        return xmlNodeName;
    }

   /**
     * When a class is serialzed in XML, this gives the name of the XML node to be used.
     */
    public void setXMLNodeName ( String name  ) 
    {
        if(!name.equals(""))
           xmlNodeName = name;
    }

    /**
     * When a class is serialzed in XML, this gives the value of the XML namespaceURI to use.
     * @return  String on success, null (String Object) if the value is undefined.
     * @uml.property  name="namespaceURI"
     */
    public String getNamespaceURI (  ) {
        return namespaceURI;
    }

    /**
     * When a class is serialzed in XML, this sets the value of the XML namespaceURI to use. If the value is "null" then no prefix will be appended to the node name.
     * @uml.property  name="namespaceURI"
     */
    public void setNamespaceURI ( String value ) {
        namespaceURI = value;
    }

    /**
     * A Hashtable to hold the XML fields.
     * 
     * @return Hashtable with all fields keyed by field name.
     */
    public Hashtable getFields () {
        return fieldHash;
    }

    /**
     * A List of the order of the XML fields for a given class/instance.
     * @return  List of fields in the order they will be written in the XML serialization.
     * @uml.property  name="fieldOrder"
     */
    public List getFieldOrder (  ) {
        return fieldOrder;
    }

    // 
     // A List to store the order of the XML fields.
     //
   // public void setAttribOrder ( List value  ) {
    //    fieldOrder = value;
    //}

    /** Get a specific XMLSerializableField by its name. 
     */
    public XMLSerializableField getField (String fieldName) {
       XMLSerializableField obj = (XMLSerializableField) fieldHash.get(fieldName);
       return obj;
    }

  /** Set an field to the given value. If the field doesn't
    * exist already, an error is thrown.
    */
  public void setField (String name, String value) {

     if (this.fieldHash.containsKey(name)) {

// FIX
/*
        String type = ((XMLSerializableField) this.fieldHash.get(name)).getType();
        if(type.equals(Constants.INTEGER_TYPE))
           // convert string to proper Integer
           ((XMLSerializableField) this.fieldHash.get(name)).setValue(new Integer(value));
        else if(type.equals(Constants.DOUBLE_TYPE))
           // convert string to proper Double
           ((XMLSerializableField) this.fieldHash.get(name)).setValue(new Double(value));
        else // string or Object
*/
           ((XMLSerializableField) this.fieldHash.get(name)).setValue(value);

     } else { // its an add operation
        logger.error("Cannot set XML field:"+name+" as it doesnt exist, use addXMLSerializableField() instead.");
     }

  }

    /** Set the XMLfields of this object using the passed (XML/SAX) Attributes.
        A field will be appended to the existing list of XML fields if
        that field doesnt already exist, otherwise, the value of the existing
        field is overridden by this method.
     */
    public void setFields (Attributes attrs) {
  
       synchronized (fieldHash) {
         // set object fields from an Attributes Object
         if (attrs != null) {
            // whip thru the list, setting each value
            int size = attrs.getLength();
            for (int i = 0; i < size; i++) {
               String name = attrs.getQName(i);
               String value = attrs.getValue(i); // yes, Attributes can only return strings
  
               // set it as appropriate to the type
               if (name != null && value != null) {
                  if (this.fieldHash.containsKey(name)) {
                     setField(name,value);
                  } else {
                     addXMLSerializableField(name,value);
                  }
               }
  
            }
         }
       } //synchronize
    }

    // Operations
    //

    // Public ops

    /** Appends on an XML (string-based) field into the object.
        @return true if successfull.
     */
    public boolean addXMLSerializableField (String name, String value) {
       return addXMLSerializableField(name, (Object) value, Constants.FIELD_ATTRIB_TYPE);
    }

    /** A utility method to write this object out to the indicated file. The 
        file will be clobbered by the output, so it is advisable to check for 
        the existence of the file *before* using this method if you are worried 
        about losing prior information.
        @return File to be written to.
    */
    public void toXMLFile (String filename)
    throws IOException
    {
  
        // open file writer
        Writer fileout = new BufferedWriter (new FileWriter(filename));
        // FileWriter fileout = new FileWriter(filename);
        toXMLWriter(fileout);
        fileout.close();
    }

    /** A utility method to write this object out (serialize) as an XML string. 
        @return String of the XML serialization of this object.
    */
    public String toXMLString ()
    {
  
       // hurm. Cant figure out how to use BufferedWriter here. fooey.
       Writer outputWriter = (Writer) new StringWriter();
       try {
          // we use this so that newline *isnt* appended onto the last element node
          basicXMLWriter(null, null, outputWriter, "", null, true);
       } catch (IOException e) {
          // weird. Out of memorY?
          logger.error("Cant got IOException for toXMLWriter() method within toXMLString()."+e.getMessage());
          e.printStackTrace();
       }
  
       return outputWriter.toString();
  
    }

    /**
     * @return Writer for output of XML serialization of this class. 
     */
    public void toXMLWriter (
                                Writer outputWriter
                            )
    throws IOException
    {
       toXMLWriter(null, null, outputWriter, "", null, true, true);
    }

    /**
     * @return Writer for output of XML serialization of this class. 
     */
    public void toXMLWriter (
                                Writer outputWriter,
                                String indent
                             )
    throws IOException
    {
       toXMLWriter(null, null, outputWriter, indent, null, true, true );
    }

    /**
     * @return Writer for output of XML serialization of this class.
     */
    public void toXMLWriter (
                                Writer outputWriter,
                                String indent, 
                                boolean doFirstIndent, 
                                boolean doLastNewLine
                            )
    throws IOException
    {
       toXMLWriter(null, null, outputWriter, indent, null, doFirstIndent, doLastNewLine );
    }

    public void toXMLWriter (
                                Hashtable idTable,
                                Hashtable prefixTable,
                                Writer outputWriter,
                                String indent,
                                boolean doFirstIndent,
                                boolean doLastNewLine
                            )
    throws IOException
    {
       toXMLWriter(idTable, prefixTable, outputWriter, indent, null, doFirstIndent, doLastNewLine );
    }

    // Protected Ops
    //

   /** The full-blown version.
     */
    protected void toXMLWriter (
                                 Hashtable idTable,
                                 Hashtable prefixTable,
                                 Writer outputWriter,
                                 String indent,
                                 String newNodeNameString,
                                 boolean doFirstIndent,
                                 boolean doLastNewLine
                             )
    throws java.io.IOException
    {

       // well, if we haven't got a prefix table to reference, we should 
       // create one
       if(prefixTable == null)
           prefixTable = generatePrefixNamespaceTable(this, null);

       boolean wroteContent =
           basicXMLWriter(idTable, prefixTable, outputWriter, indent, newNodeNameString, doFirstIndent);
       if (doLastNewLine && Specification.getInstance().isPrettyOutput() && wroteContent )
          outputWriter.write(Constants.NEW_LINE);
    }

    /**
     * @return boolean value of whether or not some content was written.
     */
    protected boolean basicXMLWriter (
                                        Hashtable idTable,
                                        Hashtable prefixNamespaceTable,
                                        Writer outputWriter,
                                        String indent,
                                        String newNodeNameString,
                                        boolean indentFirstNode
                                     )
    throws IOException
    {

      //while writing out, fieldHash should not be changed
      synchronized (fieldHash) {
  
        Specification spec = Specification.getInstance();
        String nodeNameString = this.xmlNodeName;
  
        // Setup. Sometimes the name of the node we are opening is different from
        // that specified in the classNodeName (*sigh*)
        if (newNodeNameString != null) nodeNameString = newNodeNameString;
  
        // set prefix
        if(namespaceURI != null && nodeNameString != null && !nodeNameString.equals("")) 
        {
           String xmlNodePrefix = getXMLNodePrefix(prefixNamespaceTable);
           if(xmlNodePrefix.length() > 0)
              nodeNameString = xmlNodePrefix + ":" + nodeNameString;
        }

        // 1. Open this node, print its simple XML fields
        //    and determine the new indentation from whether or not we have a node name
        String newIndent = indent;
        if (nodeNameString != null) {
  
          if (spec.isPrettyOutput() && indentFirstNode )
            outputWriter.write(indent); // indent node if desired
  
          if(!nodeNameString.equals(""))
             outputWriter.write("<" + nodeNameString);   // print opening statement

          newIndent = indent + spec.getPrettyOutputIndentation();

        } 
  
        // 2. Print out XML fields which are attributes, and tally up 
        //    other types of fields we have in this class.

        Vector childObjs = new Vector();
        XMLSerializableField PCDATA = null;

        Iterator iter = fieldOrder.iterator();
        while (iter.hasNext()) {
          String name = (String) iter.next();
          XMLSerializableField obj = (XMLSerializableField) fieldHash.get(name);

          // all non-null strings we write out
          if(obj.getValue() != null)
            if(obj.getType() == Constants.FIELD_ATTRIB_TYPE)
	    {
	       String value = obj.getValue().toString();
               // our default: don't write out "false" values
               if(value.equals("") || value.equals("false"))
               {
                  // do nothing
               } else {
 
                  if( name.equals(Constants.SIZE_ATTRIBUTE_NAME) && 
                      value.equals("1") &&
                      !spec.printSizeAttribOfOne()) 
                  {
                     // do nothing
                  } else {
                     outputWriter.write(" " + name + "=\"");
                     // entification slows things down(?), should we use?
                     outputWriter.write(entifyString(value));
                     outputWriter.write("\"");
                  }
               }
            } else 
            if(obj.getType() == Constants.FIELD_CHILD_NODE_TYPE
               || obj.getType() == Constants.FIELD_CHILD_NODE_LIST_TYPE )
            {
                childObjs.add(obj);
            } else 
            if(obj.getType() == Constants.FIELD_PCDATA_TYPE
               || obj.getType() == Constants.FIELD_CDATA_TYPE 
               || obj.getType() == Constants.FIELD_TAGGED_CDATA_TYPE 
               || obj.getType() == Constants.FIELD_TAGGED_PCDATA_TYPE )
            {
               PCDATA = obj;
            }
        }
  

        // 3. Print out Node PCData|CData, List or Child Nodes as specified 
        //    by the type of XML field. The way this stuff occurs will also 
        //    affect how we close the node.

        if(handleChildNodes(idTable, prefixNamespaceTable, outputWriter, nodeNameString, newIndent, childObjs, PCDATA)) 
        {
            doClosingNodeForChildren(nodeNameString, indent, (PCDATA != null), spec.isPrettyOutput(), outputWriter );
        } 
        else {
          if (nodeNameString != null && !nodeNameString.equals("")) {
                  // close this node
                  outputWriter.write("/>");
          }
  
        }

        return (nodeNameString != null || PCDATA != null ); //||  childObjs.size() > 0 );

      } // synchonize 

    }

    /** Deep copy of this QML object.
        @return an exact copy of this QML object
     */
    public Object clone () throws CloneNotSupportedException {
  
       //shallow copy for fields
       XMLSerializableObjectImpl cloneObj = (XMLSerializableObjectImpl) super.clone();
  
       // deep copy the attriOrder
       cloneObj.fieldOrder = new Vector ();
       int stop = this.fieldOrder.size();
       for (int i = 0; i < stop; i++) {
          cloneObj.fieldOrder.add(new String((String) this.fieldOrder.get(i)));
       }
  
       // XMLSerializableFields Clone, deep copy
       synchronized (fieldHash) {
         cloneObj.fieldHash = new Hashtable();
         Enumeration keys = this.fieldHash.keys();
         while (keys.hasMoreElements()) {
           String key = (String) keys.nextElement();
           XMLSerializableField XMLSerializableFieldValue = (XMLSerializableField) this.fieldHash.get(key);
           cloneObj.fieldHash.put(key, XMLSerializableFieldValue.clone());
         }
       }

       cloneObj.xmlNodeName  = this.xmlNodeName;
       cloneObj.namespaceURI = this.namespaceURI;

       return cloneObj;

    }

    protected void doClosingNodeForChildren (String nodeNameString, String indent, boolean hasPCDATA,
                                             boolean isPrettyOutput,  Writer outputWriter)
    throws IOException
    {

          // ok, now deal with closing the node
          if (nodeNameString != null && !nodeNameString.equals("")) {

               if (isPrettyOutput && !hasPCDATA )
                  outputWriter.write(indent);

               if(!nodeNameString.equals(""))
                   outputWriter.write("</"+nodeNameString+">");
          }

    }

    /** Format a string so it may be proper XML. Basically, cannonical XML expects
        entities for such characters as quotation, apostrophe, lessthan, greater
        than signs and ampersands. 
    */
    protected String entifyString ( String text)
    {
  
         StringBuffer newStringBuf = new StringBuffer();
         StringCharacterIterator iter = new StringCharacterIterator(text);
  
         for(char c = iter.first(); c != CharacterIterator.DONE; c = iter.next())
         {
  
            switch (c) {
               // do what "Canonical XML" expects
               case '<':  newStringBuf.append("&lt;"); continue;
               case '>':  newStringBuf.append("&gt;"); continue;
               case '&':  newStringBuf.append("&amp;"); continue;
               case '\'': newStringBuf.append("&apos;"); continue;
               case '"':  newStringBuf.append("&quot;"); continue;
  /*
               // newline specific stuff, needed? 
               case '\n': newStringBuf.append("&#010;"); continue;
               case '\r': newStringBuf.append("&#013;"); continue;
  */
               // all other characters
               default:   newStringBuf.append(c); continue;
            }
  
         }
  
         return newStringBuf.toString();
    }

    protected String getXMLNodePrefix (Hashtable prefixTable) {

       String prefix = "";
       if(namespaceURI != null && prefixTable != null) 
       {
          if( prefixTable.containsValue(namespaceURI) )
          {
            // ugh. now we traverse the hash table looking for match..slow..
            Enumeration keys = prefixTable.keys();
            while (keys.hasMoreElements()) {
               String test = (String) keys.nextElement();
               logger.debug("getXMLNodePrefix tests uri:"+test);
               if( ((String) prefixTable.get(test)).equals(namespaceURI))
               {
                 prefix = test;
                 break;
               }
            }
          } else 
{
            logger.error("getXMLNodePrefix : can't find prefix for namespaceURI:["+namespaceURI+"] in passed prefixTable.");
   dumpHashTable(prefixTable);
}
       }

       return prefix; 
    }

// FIX
private void dumpHashTable(Hashtable table)
{

    logger.error(" DUMP Table has :");
    Enumeration keys = table.keys();
    while (keys.hasMoreElements()) {
       Object key = keys.nextElement();
       logger.error(" * key:["+key.toString()+"] value:["+table.get(key).toString()+"]");
    }

}

    /** A little convenience method to save coding time elsewhere.
        This method initializes the QML fields of an object from a
        passed Hashtable.
        Hashtable key/value pairs coorespond to the class QML field
        names and their desired values.
    */
    protected void hashtableInitXMLSerializableFields (Hashtable InitXMLSerializableFieldTable)
    {
  
      Object field;
      Object obj;
      synchronized (fieldHash) {
        int size = fieldOrder.size();
        for (int i = 0; i < size; i++)
        {
          field  = fieldOrder.get(i);
          obj = InitXMLSerializableFieldTable.get(field);
  
          // only if object exists
          if (obj != null) {
            XMLSerializableField toRemove = (XMLSerializableField) fieldHash.remove(field);
            fieldHash.put(field, new XMLSerializableField(obj, toRemove.getType()));
          }
  
        }
      }//synchronize
  
    }

    // a little utility method to reuse code
    protected String objectListToXMLWriter( Hashtable idTable, Hashtable prefixTable, 
                                            Writer outputWriter, List objectList, String indent)
    throws java.io.IOException
    {

       Iterator iter = objectList.iterator(); // Must be in synchronized block
       while (iter.hasNext()) {
            XMLSerializableObjectImpl containedObj = (XMLSerializableObjectImpl) iter.next();
            objectToXMLWriter(idTable, prefixTable, outputWriter, containedObj, indent);
       }

       return indent;
    }

    // another utility method to reuse code
    protected void objectToXMLWriter (Hashtable idTable, Hashtable prefixTable, Writer outputWriter, XMLSerializableObjectImpl obj, String indent)
    throws java.io.IOException
    {
       if (obj != null) { // can happen if we pass a non-kosher object
          obj.toXMLWriter(idTable, prefixTable, outputWriter, indent, null, true, true );
       } else
          logger.error("Can't serialize non-XMLSerializableObjectImpl field. Ignoring.");
    }

    /** Reset (clear) the XMLSerializableFields in a given instance of this class.
     */
    protected void resetFields() {
       // clear out arrays, etc
       fieldHash  = new Hashtable(Constants.INIT_ATTRIBUTE_HASH_SIZE);
       fieldOrder = new Vector ();
    }


    /** returns true if there were child nodes to handle */
    protected boolean handleChildNodes(Hashtable idTable, Hashtable prefixTable, Writer outputWriter, String nodeNameString, 
                                       String indent, Vector childObjs, XMLSerializableField PCDATA) 
    throws IOException
    {

       if ( childObjs.size() > 0 || PCDATA != null )
       {

          // close the opening tag
          if (nodeNameString != null) {
            if(!nodeNameString.equals(""))
               outputWriter.write(">");
            if ((Specification.getInstance().isPrettyOutput()) && (PCDATA == null))
               outputWriter.write(Constants.NEW_LINE);
          }

          // deal with child object/list XML fields, if any in our list
          int objs_size = childObjs.size();
          for (int i = 0; i < objs_size; i++) {
            XMLSerializableField field = (XMLSerializableField) childObjs.get(i);

            if (field.getType() == Constants.FIELD_CHILD_NODE_LIST_TYPE)
            {
               List objectList = (List) field.getValue();
               indent = objectListToXMLWriter(idTable, prefixTable, outputWriter, objectList, indent);
            }
            else if (field.getType() == Constants.FIELD_CHILD_NODE_TYPE)
            {

               XMLSerializableObjectImpl containedObj = (XMLSerializableObjectImpl) field.getValue();
               objectToXMLWriter(idTable, prefixTable, outputWriter, containedObj, indent);

            } else {
               // error: weird type, actually shouldnt occur. Is this needed??
               throw new IOException("Weird error: unknown XML field type for owned field:"+field);
            }

          }

          // print out PCDATA, if any
          // presumably we will have to add stuff for mapping-based
          // containers here (or override this method in mapping class).
          if(PCDATA != null)  {

             String type = PCDATA.getType();

             if(type == Constants.FIELD_CDATA_TYPE || type == Constants.FIELD_PCDATA_TYPE)
             {

                boolean isCDATA = (type == Constants.FIELD_CDATA_TYPE);

                if(isCDATA)
                   outputWriter.write("<![CDATA[");

                // lists need to be space or tag separated
                // FIX : only space seperation currently implemented
                if (PCDATA.getValue() instanceof List)
                {

                   List pcdata = (List) PCDATA.getValue();
                   Iterator pciter = pcdata.iterator();
                   while (pciter.hasNext())
                   {
                      Object obj = pciter.next();
                      if (obj != null)
                      {
                        outputWriter.write(entifyString(obj.toString()));
                        // append single space as a separator for now
                        if(pciter.hasNext())
                           outputWriter.write(" ");
                      }
                   }

                } else
                   outputWriter.write(entifyString(PCDATA.getValue().toString()));

                if(isCDATA)
                   outputWriter.write("]]>");

            } else {  // Tagged

                boolean isCDATA = (type == Constants.FIELD_TAGGED_CDATA_TYPE);
                if (PCDATA.getValue() instanceof List)
                {
                   List data = (List) PCDATA.getValue();

                   if(data.size() > 0)
                   {
                     Iterator diter = data.iterator();
                     while (diter.hasNext())
                     {
                       Object obj = diter.next();
                       if (obj != null)
                         {
                                   outputWriter.write("<"+Constants.TAGGED_DATA_NODE_NAME+">");
                             if(isCDATA)
                                outputWriter.write("<![CDATA[");
                                   outputWriter.write(entifyString(obj.toString()));
                             if(isCDATA)
                                outputWriter.write("]]>");
                                   outputWriter.write("</"+Constants.TAGGED_DATA_NODE_NAME+">");
                         }
                     }

                   } else
                        outputWriter.write("<"+Constants.TAGGED_DATA_NODE_NAME+"/>");


                } else
                    logger.error("Developer error : Can't Tagged data from non-List");

             }

          }

          return true;
       }

       return false;
    }

    // Private ops

    /** Appends on an XML field into the object.
        @return: true if successfull.
    */

    // private for now, I dont see the need to allow users to add 'object' or 'number'
    // or etc. type XML fields other than 'string'
    private boolean addXMLSerializableField (String name, Object value, String type) {
  
      if (!this.fieldHash.containsKey(name)) {
  
         this.fieldOrder.add(name);
  
         //set up the field hashtable key with the default initial value
         this.fieldHash.put(name, new XMLSerializableField(value, type));
  
         return true;
  
      } else {
  
         logger.warn("Cannot addXMLSerializableField("+name+") as it already exists, use setField() instead.");
         return false;
  
      }
  
    }

    public boolean removeXMLSerializableField (String name ) {
      if (fieldHash.containsKey(name)) {
         fieldOrder.remove(name);
         fieldHash.remove(name);
         return true;
      }
      return false;
    }

    public XMLSerializableField findXMLSerializableField ( String name ) 
    {
      if (fieldHash.containsKey(name)) 
         return (XMLSerializableField) fieldHash.get(name);
      return null;
    }


   // find unique id name within a idtable of objects
    protected String findUniqueIdName( Hashtable idTable, String baseIdName)
    {

       StringBuffer testName = new StringBuffer(baseIdName);

       while (idTable.containsKey(testName.toString())) {
           testName.append("0"); // isnt there something better to append here??
       }

       return testName.toString();

    }

    // do a cursory search for namespaces in child objects
    // creating arbitrary prefixes as we go
    public static Hashtable generatePrefixNamespaceTable (XMLSerializableObject myObj, Hashtable prefixNamespaces)
    {

       if(prefixNamespaces == null)
            prefixNamespaces = new Hashtable();
 
       //int nrofPrefixes = prefixNamespaces.size();

       logger.debug(" starting values of namespace: ");
       java.util.Collection pvalues = prefixNamespaces.values();
       Object[] obs = pvalues.toArray();
       for (int i=0; i<obs.length; i++) {
       	    logger.debug(" * "+obs[i].toString());
       }

       // this object gets the root prefix
       String objURI = myObj.getNamespaceURI();
       logger.debug(" OBJ NAMESPACE IS:"+objURI);
       if(myObj.getNamespaceURI() != null && !prefixNamespaces.containsValue(myObj.getNamespaceURI()))
       {
           String prefix = findPrefix(prefixNamespaces.size()); 
           prefixNamespaces.put(prefix,myObj.getNamespaceURI());
           logger.debug("   added object targetnamespace -> "+prefix+":"+myObj.getNamespaceURI());
       }
       
       prefixNamespaces = findPrefixesOfChildObjects(myObj, prefixNamespaces);

       return prefixNamespaces;
    } 
    
    private static Hashtable findPrefixesOfChildObjects(XMLSerializableObject parentObj, Hashtable prefixNamespaces) 
    {
    	
        List childObjs = findChildObjs(parentObj); 
        Iterator iter = childObjs.iterator();
        while (iter.hasNext()) {
           Object testObj = iter.next();
           if(testObj != null && testObj instanceof XMLSerializableField) 
           {
         	  XMLSerializableField testField = (XMLSerializableField) testObj;
         	  if (testField.getType() == Constants.FIELD_CHILD_NODE_LIST_TYPE) {
         		  Iterator citer = ((List) testField.getValue()).iterator();
         		  while (citer.hasNext()) {
         			  XMLSerializableObject childObj = (XMLSerializableObject) citer.next();
         			  prefixNamespaces = checkAddFieldChildObjPrefix(parentObj, childObj, prefixNamespaces);
         		  }
         	  } else { 
         		  // single object
         		  XMLSerializableObject childObj = (XMLSerializableObject) ((XMLSerializableField) testObj).getValue();
         		  prefixNamespaces = checkAddFieldChildObjPrefix(parentObj, childObj, prefixNamespaces);
         	  }
  
           }
        }
    	return prefixNamespaces;
    }
       
    private static Hashtable checkAddFieldChildObjPrefix (XMLSerializableObject parentObj, 
    		XMLSerializableObject childObj, Hashtable prefixNamespaces) 
    {
     	  String childNamespaceURI = childObj.getNamespaceURI();
    	  if(childNamespaceURI != null) 
    	  { 
            logger.debug("   check: "+childNamespaceURI);
            if(parentObj.getNamespaceURI() == null || !parentObj.getNamespaceURI().equals(childNamespaceURI)) {
               // add in prefix-namspaceURI pair if it doesnt exist already
               if(!prefixNamespaces.containsValue(childNamespaceURI))
               {
                  String prefix = findPrefix(prefixNamespaces.size()+1); 
                  prefixNamespaces.put(prefix,childNamespaceURI);
                  logger.debug(" -- ADDED");
               }
            }
            logger.debug(" ");
         }
    	 return prefixNamespaces;
    }

    private static String findPrefix (int index) {

       String prefix = "";

       while(index > 25) {
          index -= 25;
          prefix += findPrefix(index);
       }

       char[] val = new char[1];
       val[0] = (char) (index+97); // we may have xsi and default namespace already in table so thats 95+2 as starting index
       prefix += new String (val);

       return prefix;
    }

    public static List findChildObjs (XMLSerializableObject myObj) {
 
       List childObjs = new Vector();
       Iterator iter = myObj.getFieldOrder().iterator();
       while (iter.hasNext()) {
          String name = (String) iter.next();
          logger.debug(" found child obj:"+name);
          XMLSerializableField obj = (XMLSerializableField) myObj.getFields().get(name);

          // all non-null strings we write out
          if(obj.getValue() != null)
            if(obj.getType() == Constants.FIELD_CHILD_NODE_TYPE
               || obj.getType() == Constants.FIELD_CHILD_NODE_LIST_TYPE )
            {
                childObjs.add(obj);
            }
       }

       return childObjs;

    }

}
