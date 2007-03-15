

// QMLDocumentHandler.java Copyright (c) 2004 Brian Thomas. All rights reserved.

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

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.datatype.FloatDataType;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.datatype.VectorDataType;
import net.datamodel.qml.support.handlers.AltValuesContainerEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.AltValuesContainerStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.AtomicQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.AxisFrameEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.AxisFrameStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.ComponentEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.ComponentStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.DefaultCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.DefaultElementWithCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.DefaultEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.DefaultStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.FloatDataTypeStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.IllegalCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.IllegalEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.IllegalStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.IntegerDataTypeStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.ListQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.MatrixQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.NullCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.NullEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.ObjectWithQuantitesStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.QuantityContainerStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.QuantityEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.RefQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.StringDataTypeStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.TrivialQuantityCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.TrivialQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.UnitsCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.UnitsStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.ValueCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.ValueStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.ValuesCharDataHandlerFunc;
import net.datamodel.qml.support.handlers.ValuesEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.ValuesStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.VectorEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.VectorStartElementHandlerFunc;
import net.datamodel.soml.support.SOMLDocument;
import net.datamodel.soml.support.SOMLDocumentHandler;
import net.datamodel.xssp.XMLSerializableObject;
import net.datamodel.xssp.parse.CharDataHandler;
import net.datamodel.xssp.parse.EndElementHandler;
import net.datamodel.xssp.parse.StartElementHandler;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

/** 
     Contains the core SAX document handler for the Reader. It also contains
     the basic QML element/charData handlers (as internal classes).
     @version $Revision$
 */
public class QMLDocumentHandler extends SOMLDocumentHandler 
implements LexicalHandler
{

	private static final Logger logger = Logger.getLogger(QMLDocumentHandler.class);
	
    // 
    // Fields
    //

    protected List CurrentNodePath;
    
    protected Vector CurrentNodeList;

    /** */
    protected List<Quantity> CurrentQuantityList = new Vector<Quantity>(); 
    
    /** */
    protected List<Locator> CurrentLocatorList = new Vector<Locator>(); // The list of current locators for the current quantities
    
    /** */
    protected List ParentQuantityAltValueList = new Vector();

    // a counter used for checking adding of multiple values
    /** */
    private List ExpectedValues; // a holder for expected number of values we should parse 

   // public Hashtable ObjWithQuantities = new Hashtable();

    // Sigh. a list of fields which should be private/protected but arent
    // (yet) because Im too lazy to make the proper accessor methods.

    public Component LastComponent; // the last component object we worked on
    
    /** */
    public int ActualValuesAdded;
    
    /** */
    public boolean AddingAltValues;
    
    /** */
    public boolean ValuesInCDATASection;
    
    /** */
    public boolean HasCSVValues;
    
    /** */
    public boolean HasMultipleValues;
    
    /** */
    public boolean HasVectorDataType;
    
    /** */
    public StringBuffer ValuesBuf;
    
    //
    // Constuctors
    //

    /**
     *
     */
    public QMLDocumentHandler (QMLDocument doc)
    {
    	super((SOMLDocument) doc);
       init();
       setDocument(doc);
    }

    public QMLDocumentHandler (QMLDocument doc, Map<String,String> options)
    {
    	super((SOMLDocument) doc, options);
    	
       init();
       Options = options;
       setDocument(doc);
    }

    //
    // Non-Sax Public Methods
    //

    /** In order to look for referenced Quantities, we 
     * "record" each that we parse.
     */
    public void recordQuantity (Quantity q) {

       String QId = q.getId();
       if (!QId.equals(""))
       {
          // add this into the list of quantity objects we have
          ObjWithQuantities.put(QId, q);
       }
    }

    /** Get the current object with quantities we are working on. 
     */
    public Quantity getCurrentQuantity() {
       ObjectWithQuantities lastQ = (ObjectWithQuantities) null;
       if (CurrentQuantityList.size() > 0)
          lastQ = (ObjectWithQuantities) CurrentQuantityList.get(CurrentQuantityList.size()-1);
       return lastQ;
    }

    /** Remove the current quantity.
     *  @return ObjectWithQuantities that was removed from the list of "current" quantities.
     */
    public Quantity unrecordQuantity() 
    {

       ObjectWithQuantities q = (ObjectWithQuantities) CurrentQuantityList.remove(CurrentQuantityList.size()-1);
       // to keep things in sync, we need to remove this too
       if(q != null && q instanceof Quantity)
       {
           removeCurrentLocator();
       }

       return q;

    }

    /** Add an Integer as an expected value. 
     * Expected values are compared to the number of actual values counted
     * at parse time as an added check for correctness.
     */
    public void addExpectedValues(Integer value) {
         ExpectedValues.add(value);
    }

    /** remove the present expected value from our list.
     * @return Integer value of the last expected value.
     */
    public Integer removeExpectedValues() {
       return (Integer) ExpectedValues.remove(ExpectedValues.size()-1);
    }

    /** Get the number of values we expected for current quantity we worked on.
     */
    public Integer getCurrentExpectedValues() {
       Integer expected = new Integer(-1);
       if (ExpectedValues.size() > 0)
          expected = (Integer) ExpectedValues.get(ExpectedValues.size()-1);
       return expected;
    }

    /** Gets the last component-compliant object we worked on.
     * This could be some types of ObjectWithQuantities as well as components.
     */
    public Component getCurrentComponent() {
       Component lastC = LastComponent;
       if (lastC == null)
       {
          Quantity lastQ = getCurrentQuantity();
          if(lastQ != null)
              lastC = (Component) lastQ;
       } 
       return lastC;
    }

    /** Get the locator which belongs to the current Quantity.
     */
    public Locator getCurrentLocator() {
       Locator lastLoc = (Locator) null;
       if (CurrentLocatorList.size() > 0)
          lastLoc = (Locator) CurrentLocatorList.get(CurrentLocatorList.size()-1);
       return lastLoc;
    }

   /** Remove the current locator.
     *  @return Locator that was removed from the list of "current" quantities.
     */
    protected Locator removeCurrentLocator () {
       return (Locator) CurrentLocatorList.remove(CurrentLocatorList.size()-1);
    }


    /** Get the current working parent matrix quantity that alt values
     *  should be added to.
     */
    public MatrixQuantity getCurrentParentQuantityAltValue () {
       MatrixQuantity mq = null;
       if (ParentQuantityAltValueList.size() > 0)
          mq = (MatrixQuantity) ParentQuantityAltValueList.get(ParentQuantityAltValueList.size()-1);
       return mq;
    }

    public void addParentQuantityNeedsAltValue (ObjectWithQuantities q) 
    {
          ParentQuantityAltValueList.add(q);
    }

    public Quantity removeParentQuantityNeedsAltValue () {
          return ParentQuantityAltValueList.remove(ParentQuantityAltValueList.size()-1);
    }

    //
    // SAX methods
    //

    /** StartElement handler.
     */
    @Override
    public void startElement (String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException
    {
    	super.startElement(namespaceURI, localName, qName, attrs);

    	// Treat any special handling here
    	// FIXME : NOT right! we have no assurance that this is the
    	// last object to be created.
    	Object thisObject = getCurrentObject();
    	
    	if (thisObject != null && thisObject instanceof Quantity)
    	{

    		logger.debug(" *** THIS ELEMENT is A QUANTITY "+qName);
    		Quantity q = (Quantity) thisObject;

    		// do special check for dealing with quantities
    		// this is here because its easier to deal with adding member
    		// Quantities, AxisFrames, etc here to prevent repeating code
    		// that defaults to adding QElements here rather than in the Element Handler (?)
    		// I know that it looks bad to have this call here, but I'd rather 
    		// treat this here rather repeat this code in all ObjectWithQuantities handlers..

    		// TODO: make a handler for this...
    		startHandlerAddQuantityToParent(namespaceURI, q);

    		// record this as our "current" quantity
    		CurrentQuantityList.add(q);

    		// also record a locator from it
    		if(q instanceof Quantity)
    			CurrentLocatorList.add(((Quantity) q).createLocator());

    		// -- end new handler for all Q's

    	} 

    }


    public void endDocument()
    throws SAXException
    {
    	
    	super.endDocument();

/*
        if (DoctypeObjectAttributes != null || ForceSetXMLHeaderStuff ) {
            
           // bah, this doesnt belong here
           XMLDeclaration xmlDecl = new XMLDeclaration();
           xmlDecl.setStandalone("no");

           DocumentType doctype = new DocumentType(ObjectWithQuantities);

           // set the values of the DocumentType object appropriately
           if (!ForceSetXMLHeaderStuff) {
              if (DoctypeObjectAttributes.containsKey("sysId")) 
                  doctype.setSystemId((String) DoctypeObjectAttributes.get("sysId")); 
              if (DoctypeObjectAttributes.containsKey("pubId")) 
                 doctype.setPublicId((String) DoctypeObjectAttributes.get("pubId")); 
           } else {
              // we have to guess values
              doctype.setSystemId(Constants.Quantity_DTD_NAME); 
           }

           ObjectWithQuantities.setXMLDeclaration (xmlDecl);
           ObjectWithQuantities.setDocumentType(doctype);
        }

        // Now that it exists, lets
        // set the notation hash for the ObjectWithQuantities structure
        Iterator iter = Notation.iterator();
        while (iter.hasNext()) {
           Hashtable initValues = (Hashtable) iter.next(); 
           if (ObjectWithQuantities.getDocumentType() == null) {
              // force having document type
              ObjectWithQuantities.setDocumentType(new DocumentType(ObjectWithQuantities)); 
           }
           ObjectWithQuantities.getDocumentType().addNotation(new NotationNode(initValues));
        }
*/

    }

  

    /** A little utility program to find the expected size 
     * from a list of attributes.
     */
    static public int findExpectedSize(Attributes attrs, String uri) {
        int expected = -1; // means "dont check, its undetermined"
        // Find the index of the "size" attribute..
        // hrm.. this *might* get us into trouble if ppl start using
        // a qualified attribute "somenamspaceuri:size" which doesn't
        // belong to the www.datamodel.net/ObjectWithQuantities namespace. Its not
        // likely, and, I cant get the namespaced "getIndex" function to
        // work, so this will have to do for now.
        int index = attrs.getIndex(Constants.SIZE_ATTRIBUTE_NAME);

        if(index > 0) {
           String value = attrs.getValue(index);
           expected = Integer.parseInt(value);
        }

        return expected;
    }

    /** A utility function to allow proper setting of value in quantity.
     * [Would not be needed if we had q.setValue(Object, Locator)];
     */
    static public void setValue(Quantity qV, String value, Locator loc)
    throws SAXException
    {
        setValue(qV,qV.getDataType(),value,loc);
    }

   /** A utility function to allow proper setting of value in quantity.
     * [Would not be needed if we had q.setValue(Object, Locator)];
     */
    static public void setValue(Quantity qV, DataType dataType, String value, Locator loc)
    throws SAXException
    {

       // set our value appropriate to data type.
       try {

          if(dataType instanceof StringDataType)
          {
             qV.setValue(value,loc);
          }
          else if(dataType instanceof FloatDataType)
          {
             Double dvalue = new Double(value);
             qV.setValue(dvalue,loc);
          }
          else if(dataType instanceof IntegerDataType)
          {

 //          Integer ivalue = new Integer(value.trim());
             Integer ivalue = Integer.decode(value);
             qV.setValue(ivalue,loc);

          }
          else if(dataType instanceof VectorDataType)
          {
             qV.setValue(value,loc); // treat it like a string
          } else
             throw new SAXException("Can't load object of UNKNOWN datatype.");

       } catch (Exception e) {
             throw new SAXException("Can't set value in quantity :"+e.getMessage());
       }
    }


    //
    // Protected Methods
    //

    // Placeholder to remind me to do some version checking w/ base class
    protected boolean checkDocVersion (String version)
    {
// FIX
      // if(version != qmlVersion) { return false; } else { return true; }
      return false;
    }

    /** Do special check for dealing with adding quantities. 
     *  This method exists because its easier to deal with adding member
     *  Quantities, AxisFrames, etc in a global fashion rather than repeating code
     *  in each of the ObjectWithQuantities handlers.
     *  At any rate the logic is that if no parent Q exists, then it defaults to 
     *  adding the quantities as QElements in the QMLDocument.
     */
    protected void startHandlerAddQuantityToParent(String namespaceURI, Quantity q) 
    {

            Quantity currentQ = getCurrentQuantity();
            if(currentQ != null) {

                 // IF its an ReferenceFrame, AND currentQ is a Matrix, we add
                 // it to the axisFrame List (just not yet..), otherwise, we add this new Q
                 // as a member to current ObjectWithQuantities
                 if(currentQ instanceof MatrixQuantity && q instanceof ReferenceFrame)
                 {
                   // do nothing for now.. we want to wait to populate the ReferenceFrame
                   // so that we can check if its really kosher to addit
                   // ((MatrixQuantity)currentQ).addMember((ReferenceFrame)q);
                 }
                 else if (AddingAltValues)
                 {
                   // do nothing here...we have to populate the child Q with values
                   // before we may addit to the parent..so we wait.
                   //if(q instanceof ListQuantity)
                   //   ParentMatrixQ.addAltValue((ListQuantity)q);
                   //else
                   //   throw new SAXException("Alternative value not a list ObjectWithQuantities");
                 } else
                   currentQ.addMember(q); // everything else becomes a "member"

             } else {

                 // Add as a QElement to our document, as appropriate (e.g.
                 // either to current node or as document root).
                Element elem = getDocument().createQMLElementNS(namespaceURI, q);
                Node current = getCurrentNode();
                if(current != null)
                   current.appendChild(elem);
                else
                   getDocument().setDocumentElement(elem);

             }

    }



    // base type of a complex/simple type decl.
    private static String findBaseType(Element typeDecl, String prefix)
    {
       String base = "";
       // drill down to look for "extension" or "restriction" children
       NodeList children = typeDecl.getChildNodes();
       int nrof_children = children.getLength();
       Node contentElem = (Node) null;
       for (int i=0; i<nrof_children; i++) {
          Node item = children.item(i);
          if(item.getNodeType() == Node.ELEMENT_NODE)
             if(item.getNodeName().equals(prefix+"complexContent")
                || item.getNodeName().equals(prefix+"simpleContent"))
             {
                contentElem = item;
                break;
             }
       }

       if(contentElem != null) {
           NodeList cnodes = contentElem.getChildNodes();
           int nrof_cnodes = cnodes.getLength();
           Element baseElem = (Element) null;
           for (int i=0; i<nrof_cnodes; i++) {
              Node item = cnodes.item(i);
              if(item.getNodeType() == Node.ELEMENT_NODE &&
                  (item.getNodeName().equals(prefix+"extension")
                    || item.getNodeName().equals(prefix+"restriction") ))
              {
                  baseElem = (Element) item;
                  break;
              }
           }

           if(baseElem != null) {
              base = baseElem.getAttribute("base");
           } // else
             //  logger.debug(" complexType:"+typeDecl.getAttribute("name")+" which has no ("+
             //                prefix+"restriction|"+prefix+"extension) grandchild node.");
       } // else
//          logger.info(" Inheriting Schema issue? got complexType:"+typeDecl.getAttribute("name")+" which has no "+prefix+"complexContent or simpleContent child. Schema loader will assign a default handler.");

       return base;
    }

    // set up QML handler associtions w/ schema complexTypes
    @Override
    protected void initStartElementHandlers ()
    {

        Map<String,StartElementHandler> qmlAssoc = new Hashtable<String,StartElementHandler>();
        Map<String,StartElementHandler> mapAssoc = new Hashtable<String,StartElementHandler>();
        Map<String,StartElementHandler> xmlAssoc = new Hashtable<String,StartElementHandler>();

        qmlAssoc.put(Constants.NodeTypeName.ALTERN_VALUES, new AltValuesContainerStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new AtomicQuantityStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.AXISFRAME, new AxisFrameStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPONENT, new ComponentStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new ObjectWithQuantitesStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.FLOAT_DATATYPE, new FloatDataTypeStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.INTEGER_DATATYPE, new IntegerDataTypeStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.LIST_QUANTITY, new ListQuantityStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.MATRIX_QUANTITY, new MatrixQuantityStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY, new IllegalStartElementHandlerFunc()); // its abstract..never invoked as a node! 
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new QuantityContainerStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new RefQuantityStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.STRING_DATATYPE, new StringDataTypeStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new TrivialQuantityStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VECTOR_DATATYPE, new VectorStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.UNITS, new UnitsStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUE, new ValueStartElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUES, new ValuesStartElementHandlerFunc());

        // FIX: hacked in mapping handlers until separate mapping package is built.
        mapAssoc.put(Constants.NodeTypeName.MAP, new mappingStartElementHandlerFunc());

        // generic XML handlers. we can certainly treat simple string and anyURI -based elements 
        xmlAssoc.put("string", new DefaultStartElementHandlerFunc());
        xmlAssoc.put("anyURI", new DefaultStartElementHandlerFunc());

        StartElementHandlers.put(Constants.QML_NAMESPACE_URI, qmlAssoc); 
        StartElementHandlers.put(Constants.MAPPING_NAMESPACE_URI, mapAssoc); 
        StartElementHandlers.put(Constants.XML_SCHEMA_NAMESPACE_URI, xmlAssoc); 

    }

    // set up QML handler associtions w/ schema complexTypes
    protected void initCharDataHandlers()
    {
        Hashtable mapAssoc = new Hashtable();
        Hashtable qmlAssoc = new Hashtable();
        Hashtable xmlAssoc = new Hashtable();

        qmlAssoc.put(Constants.NodeTypeName.ALTERN_VALUES, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.AXISFRAME, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPONENT, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.FLOAT_DATATYPE, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.INTEGER_DATATYPE, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.LIST_QUANTITY, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.MATRIX_QUANTITY, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY, new IllegalCharDataHandlerFunc()); // its abstract..never invoked as a node! 
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.STRING_DATATYPE, new NullCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new TrivialQuantityCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.UNITS, new UnitsCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUE, new ValueCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUES, new ValuesCharDataHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VECTOR_DATATYPE, new NullCharDataHandlerFunc());

       // FIX: hacked in mapping handlers until separate mapping package is built.
        mapAssoc.put(Constants.NodeTypeName.MAP, new NullCharDataHandlerFunc());

        // generic XML handlers. we can certainly treat simple string and anyURI-based elements 
        xmlAssoc.put("string", new DefaultElementWithCharDataHandlerFunc());
        xmlAssoc.put("anyURI", new DefaultElementWithCharDataHandlerFunc());

        CharDataHandlers.put(Constants.QML_NAMESPACE_URI, qmlAssoc);
        CharDataHandlers.put(Constants.MAPPING_NAMESPACE_URI, mapAssoc);
        CharDataHandlers.put(Constants.XML_SCHEMA_NAMESPACE_URI, xmlAssoc);

    }

   // set up QML handler associtions w/ schema complexTypes
    protected void initEndElementHandlers ()
    {
        Hashtable mapAssoc = new Hashtable();
        Hashtable qmlAssoc = new Hashtable();
        Hashtable xmlAssoc = new Hashtable();

        qmlAssoc.put(Constants.NodeTypeName.ALTERN_VALUES, new AltValuesContainerEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.AXISFRAME, new AxisFrameEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPONENT, new ComponentEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.LIST_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.MATRIX_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY, new IllegalEndElementHandlerFunc()); // its abstract..never invoked as a node! 
        qmlAssoc.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new NullEndElementHandlerFunc()); // metaData 
        qmlAssoc.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.FLOAT_DATATYPE, new NullEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.INTEGER_DATATYPE, new NullEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.STRING_DATATYPE, new NullEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.UNITS, new NullEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUE, new NullEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VALUES, new ValuesEndElementHandlerFunc());
        qmlAssoc.put(Constants.NodeTypeName.VECTOR_DATATYPE, new VectorEndElementHandlerFunc());

       // FIX: hacked in mapping handlers until separate mapping package is built.
        mapAssoc.put(Constants.NodeTypeName.MAP, new NullEndElementHandlerFunc());

        // generic XML handlers. we can certainly treat simple string and anyURI-based elements 
        xmlAssoc.put("string", new DefaultEndElementHandlerFunc());
        xmlAssoc.put("anyURI", new DefaultEndElementHandlerFunc());

        EndElementHandlers.put(Constants.QML_NAMESPACE_URI, qmlAssoc); 
        EndElementHandlers.put(Constants.MAPPING_NAMESPACE_URI, mapAssoc); 
        EndElementHandlers.put(Constants.XML_SCHEMA_NAMESPACE_URI, xmlAssoc); 

    }

    protected void InitFromSchema (Attributes attrs) {

           String schema_info = findSchemaLocationFromAttribs(attrs);

           Matcher myMatcher = SchemaLocationPattern.matcher(schema_info);
           if(myMatcher.matches()) {
              String uri = myMatcher.group(1).trim();
              String url = myMatcher.group(2).trim();
              List handlers = LoadSchema (uri,url);
              Iterator iter = handlers.iterator();

              // second pass: try to recover missing handlers
              while (iter.hasNext()) {
                  HandlerMapInfo info = (HandlerMapInfo) iter.next();
                  boolean gotHandler = false;

                  switch (info.type) {
                       case START_HANDLER_TYPE:
                           StartElementHandler shandler = findStartHandler(info.name2,info.uri2);
                           if(shandler != null) {
                               ((Hashtable) StartElementHandlers.get(info.uri1)).put(info.name1,shandler);
                               gotHandler = true;
                           }
                           break;
                       case END_HANDLER_TYPE:
                           EndElementHandler ehandler = findEndHandler(info.name2,info.uri2);
                           if(ehandler != null) {
                               ((Hashtable) EndElementHandlers.get(info.uri1)).put(info.name1,ehandler);
                               gotHandler = true;
                           }
                           break;
                       case CHAR_HANDLER_TYPE:
                           CharDataHandler cdhandler = findCharDataHandler(info.name2,info.uri2,info.mixed);
                           if(cdhandler != null) {
                               ((Hashtable) CharDataHandlers.get(info.uri1)).put(info.name1,cdhandler);
                               gotHandler = true;
                           }
                           break;
                  }

                  if(gotHandler)
                      logger.debug(" ==> Mapping complexType:"+info.name1+"["+info.uri1+"] to"+Constants.NEW_LINE+"       start Handler:"+info.name2+"["+info.uri2+"] for type:"+info.type);
                  else
                  {
                      String handlerType = "start element";
                      if(info.type == 1) handlerType = "end element";
                      if(info.type == 2) handlerType = "char data";
                      logger.error(" ==> Mapping complexType:"+info.name1+"["+info.uri1+"] to"+Constants.NEW_LINE+"       start Handler:"+info.name2+"["+info.uri2+"] for type:"+info.type);
                      logger.error(" ** Can't find "+handlerType+" Handler for complexType:"+info.name1
                                  +"["+info.uri1+"] "+Constants.NEW_LINE
                                  +"       (Missing handler:"+info.name2+"["+info.uri2+"])");
                  }

              }
           }

    }

    // convenience method
    protected List LoadSchema (String uri, String url )
    {
        return LoadSchema (uri, url, true);
    }

    /** Base method for loading handlers from XML schema. Will trace back (and forward)
      * through the indicated schema to identify all the element handlers (and the associated
      * namespaces) which are needed.
      */
    protected List LoadSchema (String uri, String url, boolean warnLoaded )
    {

      List handlers = new Vector();

      // tack in the relative path to our source in the url
      url = RelativePath + url;

      if(LoadedSchema.containsKey(uri))
      {
         String priorUrl = (String) LoadedSchema.get(uri);
         if(priorUrl.equals(url))
         {
//            logger.debug("    returning..already loaded schema:"+uri+" "+url);
            return handlers;
         } else
            logger.info(" H_LOAD_SCHEMA: hmmm..already loaded schema with uri:"+uri
                          +" but this has different URL prior_url:["+priorUrl+
                          "] current_url:[" +url
                          +"] If this is a schema that simply extends the orignal namespace, then things are OK, otherwise, maddess may reign.");
      }
      LoadedSchema.put(uri,url);

      logger.info("H_LOAD_SCHEMA : ["+uri+","+url+"]");

      // parse the schema into a DOM representation
      DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();

      try {

          DocumentBuilder docBuilder = fac.newDocumentBuilder();
          InputSource is = new InputSource (url);
          Document schemaDoc = docBuilder.parse(is);

          // find prefix/namespacesURI pairs for this schema.
          String targetNamespace = "";
          Hashtable schemaPrefixNamespaces = new Hashtable();
          Element root = schemaDoc.getDocumentElement();
          NamedNodeMap attrs = root.getAttributes();
          int at_size = attrs.getLength();
          for (int i = 0; i < at_size; i++) {
             Node item = attrs.item(i);
             String nodeName = item.getNodeName();

             if(nodeName.equals("targetNamespace")) {
                targetNamespace = item.getNodeValue();
             } else {
               Matcher myMatcher = XMLNamespacePrefixPattern.matcher(nodeName);
               if(myMatcher.matches()) {
                  String prefix = myMatcher.group(2).trim();
                  schemaPrefixNamespaces.put(prefix,item.getNodeValue());
               }
             }
          }

          // now gather information about included/imported/redefined sub-schema..
          List imports = new Vector();
          List redefine = new Vector();
          List includes = new Vector();
          Hashtable elements = new Hashtable();
          Map types = new Hashtable();
          Enumeration prefixes = schemaPrefixNamespaces.keys();
          while (prefixes.hasMoreElements()) {
             String prefix = (String) prefixes.nextElement();

             imports.addAll(findElements(schemaDoc,"import",prefix));
             redefine.addAll(findElements(schemaDoc,"redefine",prefix));
             includes.addAll(findElements(schemaDoc,"include",prefix));
             elements.put(prefix, findElements(schemaDoc,"element",prefix));
             types.put(prefix, findElements(schemaDoc,"complexType",prefix));

          }

          // init handlers for INCLUDEd schema..
          Iterator iiter = includes.iterator();
          while (iiter.hasNext()) {
              Element includeElem = (Element) iiter.next();
              List missingImportSchemaHandlers =
                   LoadSchema(uri, includeElem.getAttribute("schemaLocation"), false);
              handlers.addAll(missingImportSchemaHandlers);
          }

          // init handlers for IMPORTed schema..
//          logger.debug(" FOUND "+imports.size()+" imports in schema:"+url);
          Iterator iter = imports.iterator();
          while (iter.hasNext()) {
              Element importElem = (Element) iter.next();
              List missingImportSchemaHandlers =
                   LoadSchema(importElem.getAttribute("namespace"),importElem.getAttribute("schemaLocation"));
              handlers.addAll(missingImportSchemaHandlers);
          }

          // init handlers for REDEFINEd schema..
          // There should ONLY be 1 of these 
          logger.debug(" FOUND "+redefine.size()+" redefines in schema:"+url);
          iter = redefine.iterator();
          while (iter.hasNext()) {
              Element redefineElem = (Element) iter.next();
 // check if redefine namespace is same as our present schema?
              String schemaLoc = redefineElem.getAttribute("schemaLocation");
              logger.debug(" redef namespace : "+targetNamespace+" schema:"+schemaLoc);

              // Q: isnt it more efficient to just to find the reference to these handlers 
              // rather than re-load them afresh again?
              List missingRedefineSchemaHandlers =
                   LoadSchema(targetNamespace,redefineElem.getAttribute("schemaLocation"), false);
              handlers.addAll(missingRedefineSchemaHandlers);

              // Q: do I need to remove the older namespace stuff??
          }

          // init handlers for this schema..
          // lets cheat a little..we have already "by hand" set the element handlers
          // for the QML schema, so no need to go thru this to do it
          Matcher myMatcher = QMLSchemaPattern.matcher(url);
          if(uri.equals(Constants.QML_NAMESPACE_URI) && myMatcher.matches()) {
              logger.debug("   skipping loading element handlers for QML schema.");
              return handlers;
          }

          prefixes = schemaPrefixNamespaces.keys();
          Hashtable complexTypes = new Hashtable();
          while (prefixes.hasMoreElements()) {
             String prefix = (String) prefixes.nextElement();
             logger.debug(" Check input COMPLEXTYPE prefix:"+prefix);
             complexTypes.putAll(getBaseTypesOfComplexTypes((List) types.get(prefix), prefix));
          }

          handlers.addAll(initHandlerAssociations(targetNamespace, schemaPrefixNamespaces, complexTypes));

          //logger.debug(" FOUND "+elements.size()+" elements, "+types.size()+" complexTypes in schema:"+url);
          initElementTypeAssociations(targetNamespace, schemaPrefixNamespaces, elements);

      } catch (IOException e) {
    	  // TODO: this this really a warning??
          logger.warn("Can't create input source for schema parser : "+e.getMessage());
          logger.warn("Using the default QML handlers (there may be further problems related to this...)");
      } catch (SAXException e) {
    	  // TODO: this this really a warning??
          logger.warn("Can't parse schema input source : "+e.getMessage());
          logger.warn("Using the default QML handlers (there may be further problems related to this...)");
      } catch (ParserConfigurationException e) {
    	  // TODO: this this really a warning??
          logger.warn("Can't create schema DOM parser : "+e.getMessage());
          logger.warn("Using the default QML handlers (there may be further problems related to this...)");
      }

      return handlers;

    }


    //
    // Private Methods
    //

    /** called by all constructors. May be used to re-initalize reader. 
     */
    private void init () {

      // assign/init 'globals' (e.g. object fields)
      Options = new Hashtable();  
      myDocument = (QMLDocument) null;

      Notation = new HashSet();
      UnParsedEntity = new Hashtable();
      PrefixNamespaceMapping = new Hashtable();

      CurrentNodePath = new Vector();
      CurrentNodeList = new Vector();
      ParentQuantityAltValueList = new Vector();
      ElementNamespaceURIList = new Vector();

      RelativePath = "";

      // initialize the default parser dispatch tables
      // and element/type associations
      initHandlers();

      LoadedSchema = new Hashtable(); 
      AttemptedSchemaLoad = false;
      AddingAltValues = false;
      ExpectedValues = new Vector();

    }

    private void initHandlers() {
 
      // default handlers
      DefaultHandlers = new Hashtable(); // table of default handlers 
      initDefaultHandlerHashtable();

      // element to complexType association
      ElementTypeAssoc = new Hashtable(); // assoc between element names and handler keys 
      initElementTypeAssoc();

      // start Element
      StartElementHandlers = new Hashtable(); // start node handler
      initStartElementHandlers(); 
    
      // end Element
      EndElementHandlers = new Hashtable(); // end node handler
      initEndElementHandlers();

      // character data 
      CharDataHandlers = new Hashtable(); // charData handler
      initCharDataHandlers();

    }

    private void initDefaultHandlerHashtable () {
       DefaultHandlers.put("startElement", new DefaultStartElementHandlerFunc());
       DefaultHandlers.put("endElement", new DefaultEndElementHandlerFunc());
       DefaultHandlers.put("ignoreCharData", new DefaultCharDataHandlerFunc());
       DefaultHandlers.put("charData", new DefaultElementWithCharDataHandlerFunc());
    }

    // initialize the associations between element names and complextypes (aka. keys for 
    // the dispatch table for start handler events) 
    // You may ask: why "hardwire" this table?
    // We *could* do this by analysis of the QML.xsd everytime we load the document handler, 
    // but thats probably overkill, and a performance hit that we dont need to take.
    //
    // Optionally, it might be nice to have this table declared in the "Constants" class..that
    // seems better..
    private void initElementTypeAssoc() {

       Hashtable qmlAssoc = new Hashtable();
       Hashtable xmlAssoc = new Hashtable();

       // QML namespace associations
       qmlAssoc.put(Constants.NodeName.ALTERN_VALUES, new HandlerInfo(Constants.NodeTypeName.ALTERN_VALUES));
       qmlAssoc.put(Constants.NodeName.ATOMIC_QUANTITY, new HandlerInfo(Constants.NodeTypeName.ATOMIC_QUANTITY));
       qmlAssoc.put(Constants.NodeName.AXISFRAME, new HandlerInfo(Constants.NodeTypeName.AXISFRAME));
       qmlAssoc.put(Constants.NodeName.COMPONENT, new HandlerInfo(Constants.NodeTypeName.COMPONENT));
       qmlAssoc.put(Constants.NodeName.COMPOSITE_QUANTITY, new HandlerInfo(Constants.NodeTypeName.COMPOSITE_QUANTITY));
       qmlAssoc.put(Constants.NodeName.FLOAT_DATATYPE, new HandlerInfo(Constants.NodeTypeName.FLOAT_DATATYPE));
       qmlAssoc.put(Constants.NodeName.INTEGER_DATATYPE, new HandlerInfo(Constants.NodeTypeName.INTEGER_DATATYPE));
       qmlAssoc.put(Constants.NodeName.LIST_QUANTITY, new HandlerInfo(Constants.NodeTypeName.LIST_QUANTITY));
       qmlAssoc.put(Constants.NodeName.MATRIX_QUANTITY, new HandlerInfo(Constants.NodeTypeName.MATRIX_QUANTITY));
       qmlAssoc.put(Constants.NodeName.REFERENCE_QUANTITY, new HandlerInfo(Constants.NodeTypeName.REFERENCE_QUANTITY));
       qmlAssoc.put(Constants.NodeName.STRING_DATATYPE, new HandlerInfo(Constants.NodeTypeName.STRING_DATATYPE));
       qmlAssoc.put(Constants.NodeName.TRIVIAL_QUANTITY, new HandlerInfo(Constants.NodeTypeName.TRIVIAL_QUANTITY));
       qmlAssoc.put(Constants.NodeName.UNITS, new HandlerInfo(Constants.NodeTypeName.UNITS));
       qmlAssoc.put(Constants.NodeName.VECTOR_DATATYPE, new HandlerInfo(Constants.NodeTypeName.VECTOR_DATATYPE));
       qmlAssoc.put(Constants.NodeName.VALUE, new HandlerInfo(Constants.NodeTypeName.VALUE));
       qmlAssoc.put(Constants.NodeName.VALUES, new HandlerInfo(Constants.NodeTypeName.VALUES));

       // Simple string-based elements get the default handler
       xmlAssoc.put("string", new HandlerInfo("string"));
       xmlAssoc.put("anyURI", new HandlerInfo("anyURI"));
 
       // set up the associated namespace stuff
       ElementTypeAssoc.put(Constants.QML_NAMESPACE_URI, qmlAssoc);
       ElementTypeAssoc.put(Constants.XML_SCHEMA_NAMESPACE_URI, xmlAssoc);

    }
    
    /*
    private Hashtable attribListToHashtable ( Attributes attrs ) {

       Hashtable hash = new Hashtable();
       int size = attrs.getLength();
       for (int i = 0; i < size; i++) {
          String name = attrs.getQName(i);
          String value; 
          if ((value = attrs.getValue(i)) != null) 
             hash.put(name, value);
       }
         
       return hash;
    }
    */

    //
    // Internal Classes
    //

    // MAPPING HANDLERS - dont really belong here (!)
    //

    // FIX: we need to put in a separate mapping package..
    class mappingStartElementHandlerFunc implements StartElementHandler {
       public Object action ( XSSPDocumentHandler handler, String namespaceURI, 
                              String localName, String qName, Attributes attrs)
       throws SAXException {
           throw new SAXException("Current package cannot handle mapped values.");
       }
    }

    /** A small class to hold information about ComplexTypes */
    protected class ComplexTypeInfo {
       public String name = "";
       public String base = "";
       public String mixed = "";

       public ComplexTypeInfo (String n, String b, String m) {
         name = n; 
         base = b; 
         mixed = m; 
       }

    }

    /** A small class to hold information about SAX handler to element mappings.
     */
    protected class HandlerInfo {
       public String name = "";
       public String uri = Constants.QML_NAMESPACE_URI;

       public HandlerInfo (String n ) {
         name = n; 
       }

       public HandlerInfo (String n, String u) {
         name = n; uri = u;
       }

    }

   /** A small class to hold information about Mappings between SAX handlers.
     */
   protected class HandlerMapInfo {
       public String name1 = "";
       public String uri1 = Constants.QML_NAMESPACE_URI;
       public String name2 = "";
       public String uri2 = Constants.QML_NAMESPACE_URI;
       public String mixed = "";
       public int type = START_HANDLER_TYPE;

       public HandlerMapInfo (String n1, String u1, String n2, String u2, int t, String mx ) {
         name1 = n1; uri1 = u1;
         name2 = n2; uri2 = u2;
         type = t;
         mixed = mx;
       }

       public HandlerMapInfo (String n1, String u1, String n2, String u2 ) {
         name1 = n1; uri1 = u1;
         name2 = n2; uri2 = u2;
       }

       public HandlerMapInfo (String n1, String u1, String n2, String u2, int t) {
         name1 = n1; uri1 = u1;
         name2 = n2; uri2 = u2;
         type = t;
       }

    }

} // End of QMLDocumentHandler class 

