

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

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.datamodel.qml.Component;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.datatype.FloatDataType;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.datatype.VectorDataType;
import net.datamodel.qml.support.handlers.AltValuesContainerEndElementHandlerFunc;
import net.datamodel.qml.support.handlers.AltValuesContainerStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.AtomicQuantityStartElementHandlerFunc;
import net.datamodel.qml.support.handlers.ReferenceFrameStartElementHandlerFunc;
import net.datamodel.soml.support.SOMLDocument;
import net.datamodel.soml.support.SOMLDocumentHandler;
import net.datamodel.xssp.parse.CharDataHandler;
import net.datamodel.xssp.parse.EndElementHandler;
import net.datamodel.xssp.parse.StartElementHandler;
import net.datamodel.xssp.parse.XSSPDocumentHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** 
     Contains the core SAX document handler for the Reader. It also contains
     the basic QML element/charData handlers (as internal classes).
     @version $Revision$
 */
public class QMLDocumentHandler 
extends SOMLDocumentHandler 
{

	private static final Logger logger = Logger.getLogger(QMLDocumentHandler.class);
	
    // 
    // Fields
    //

    // TODO: check that this sort of thing isnt this duplicated at a lower level already?
    private Map<String, Quantity> ReferencedQuantities = new Hashtable<String,Quantity>();

    /** */
    private List<Quantity> CurrentQuantityList = new Vector<Quantity>(); 
    
    /** */
    private List<Locator> CurrentLocatorList = new Vector<Locator>(); // The list of current locators for the current quantities
    
    /** */
    private List<Quantity> ParentQuantityAltValueList = new Vector<Quantity>();

    /** a holder for expected number of values we should parse. */ 
    private List<Integer> ExpectedValues = new Vector<Integer>(); 

    // Sigh. a list of fields which should be private/protected but arent
    // (yet) because Im too lazy to make the proper accessor methods.
    private Component LastComponent; // the last component object we worked on
    
    // Various fields which tell us the state of the values we are 
    // dealing with as we parse along.
    /** */
    // TODO: make this private..add accessors!
    public int ActualValuesAdded = 0; 
    
    /** */
    private boolean AddingAltValues = false;
    
    /** */
    private boolean ValuesInCDATASection = false;
    
    /** */
    private boolean HasCSVValues = false;
    
    /** */
    private boolean HasMultipleValues = false;
    
    /** */
    private boolean HasVectorDataType = false;
    
    /** */
    // TODO: make this private..add accessors!
    public StringBuffer ValuesBuf = null;
    
    //
    // Constuctors
    //
    public QMLDocumentHandler (QMLDocument doc) {
    	this (doc, (Map<String,String>) null);
    }

    public QMLDocumentHandler (QMLDocument doc, Map<String,String> options) {
    	super ((SOMLDocument) doc, options);
    	
    	// init start handlers
        Map<String,StartElementHandler> qmlStartHandlers = new Hashtable<String,StartElementHandler>();
        Map<String,StartElementHandler> mapStartHandlers = new Hashtable<String,StartElementHandler>();

        qmlStartHandlers.put(Constants.NodeTypeName.ALTERN_VALUES, new AltValuesContainerStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new AtomicQuantityStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.AXISFRAME, new ReferenceFrameStartElementHandlerFunc());
        /*
        qmlStartHandlers.put(Constants.NodeTypeName.COMPONENT, new ComponentStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new ObjectWithQuantitesStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.FLOAT_DATATYPE, new FloatDataTypeStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.INTEGER_DATATYPE, new IntegerDataTypeStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.LIST_QUANTITY, new ListQuantityStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.MATRIX_QUANTITY, new MatrixQuantityStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.QUANTITY, new IllegalStartElementHandlerFunc()); // its abstract..never invoked as a node! 
        qmlStartHandlers.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new QuantityContainerStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new RefQuantityStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.STRING_DATATYPE, new StringDataTypeStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new TrivialQuantityStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.VECTOR_DATATYPE, new VectorStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.UNITS, new UnitsStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.VALUE, new ValueStartElementHandlerFunc());
        qmlStartHandlers.put(Constants.NodeTypeName.VALUES, new ValuesStartElementHandlerFunc());
        */

        // FIX: hacked in mapping handlers until separate mapping package is built.
        mapStartHandlers.put(Constants.NodeTypeName.MAP, new mappingStartElementHandlerFunc());
        
        this.addStartElementHandlers(qmlStartHandlers, Constants.QML_NAMESPACE_URI);
        this.addStartElementHandlers(mapStartHandlers, Constants.MAPPING_NAMESPACE_URI);

    	// TODO: init end handlers
        Map<String,EndElementHandler> mapEndHandlers = new Hashtable<String,EndElementHandler>();
        Map<String,EndElementHandler> qmlEndHandlers = new Hashtable<String,EndElementHandler>();

        qmlEndHandlers.put(Constants.NodeTypeName.ALTERN_VALUES, new AltValuesContainerEndElementHandlerFunc());
        /*
        qmlEndHandlers.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.AXISFRAME, new ReferenceFrameEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.COMPONENT, new ComponentEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.LIST_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.MATRIX_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.QUANTITY, new IllegalEndElementHandlerFunc()); // its abstract..never invoked as a node! 
        qmlEndHandlers.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new NullEndElementHandlerFunc()); // metaData 
        qmlEndHandlers.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.FLOAT_DATATYPE, new NullEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.INTEGER_DATATYPE, new NullEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.STRING_DATATYPE, new NullEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new QuantityEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.UNITS, new NullEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.VALUE, new NullEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.VALUES, new ValuesEndElementHandlerFunc());
        qmlEndHandlers.put(Constants.NodeTypeName.VECTOR_DATATYPE, new VectorEndElementHandlerFunc());
        */

       // TODO: hacked in mapping handlers until separate mapping package is built.
       // mapEndHandlers.put(Constants.NodeTypeName.MAP, new NullEndElementHandlerFunc());
        
        this.addEndElementHandlers(qmlEndHandlers, Constants.QML_NAMESPACE_URI);
        this.addEndElementHandlers(mapEndHandlers, Constants.MAPPING_NAMESPACE_URI);

    	// TODO: init chardata handlers
        Map<String,CharDataHandler> mapCharDataHandler = new Hashtable <String,CharDataHandler>();
        Map<String,CharDataHandler> qmlCharDataHandler = new Hashtable <String,CharDataHandler>();

        /*
        qmlCharDataHandler.put(Constants.NodeTypeName.ALTERN_VALUES, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.ATOMIC_QUANTITY, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.AXISFRAME, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.COMPONENT, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.COMPOSITE_QUANTITY, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.FLOAT_DATATYPE, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.INTEGER_DATATYPE, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.LIST_QUANTITY, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.MATRIX_QUANTITY, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.QUANTITY, new IllegalCharDataHandlerFunc()); // its abstract..never invoked as a node! 
        qmlCharDataHandler.put(Constants.NodeTypeName.QUANTITY_CONTAINER, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.REFERENCE_QUANTITY, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.STRING_DATATYPE, new NullCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.TRIVIAL_QUANTITY, new TrivialQuantityCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.UNITS, new UnitsCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.VALUE, new ValueCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.VALUES, new ValuesCharDataHandlerFunc());
        qmlCharDataHandler.put(Constants.NodeTypeName.VECTOR_DATATYPE, new NullCharDataHandlerFunc());
        */

        // FIX: hacked in mapping handlers until separate mapping package is built.
        // mapCharDataHandler.put(Constants.NodeTypeName.MAP, new NullCharDataHandlerFunc());
        
        addCharDataHandlers(qmlCharDataHandler, Constants.QML_NAMESPACE_URI); 
        addCharDataHandlers(mapCharDataHandler, Constants.MAPPING_NAMESPACE_URI); 

    	// TODO: init element associations
        
    }

    //
    // Non-Sax Public Methods
    //
    
    public final void setHasCSVValues (boolean val) { HasCSVValues = val; }
    
    public final boolean hasCSVValues () { return HasCSVValues; }
    
    public final boolean hasVectorDataType() { return HasVectorDataType; }
    
    public final void setHasVectorDataType (boolean val) { HasVectorDataType = val; }
    
    public final void setHasMultipleValues (boolean val) { HasMultipleValues = val; }
    
    public final boolean hasMultipleValues() { return HasMultipleValues; }
    
    public final void setHasValuesInCDATASection(boolean val) { ValuesInCDATASection = val; }
    
    public final boolean hasValuesInCDATASection() { return ValuesInCDATASection; }
    
    public final boolean hasRecordedQuantityWithId(String qIdRef) {
    	return ReferencedQuantities.containsKey(qIdRef);
    }
    public final boolean isAddingAltValues () { return AddingAltValues; }
    
    public final Component getLastComponent () { return LastComponent; }
    
    public final void setLastComponent (Component val) { LastComponent = val; }
    
    public final void setAddingAltValues (boolean value) { AddingAltValues = value; }
    
    public final Quantity getRecordedQuantity (String qIdRef) {
    	return ReferencedQuantities.get(qIdRef);
    }

    /** In order to look for referenced Quantities, we 
     * "record" each that we parse.
     */
    public final void recordQuantity (Quantity q) {

       String QId = q.getId();
       if (!QId.equals(""))
       {
          // add this into the list of quantity objects we have
          ReferencedQuantities.put(QId, q);
       }
    }

    /** Get the current object with quantities we are working on. 
     */
    public final Quantity getCurrentQuantity() {
       if (CurrentQuantityList.size() > 0)
          return CurrentQuantityList.get(CurrentQuantityList.size()-1);
       return null;
    }

    /** Remove the current quantity.
     *  @return Quantity that was removed from the list of "current" quantities.
     */
    public final Quantity unrecordQuantity() 
    {

       Quantity q = (Quantity) CurrentQuantityList.remove(CurrentQuantityList.size()-1);
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
    public final void addExpectedValues(Integer value) {
         ExpectedValues.add(value);
    }

    /** remove the present expected value from our list.
     * @return Integer value of the last expected value.
     */
    public final Integer removeExpectedValues() {
       return (Integer) ExpectedValues.remove(ExpectedValues.size()-1);
    }

    /** Get the number of values we expected for current quantity we worked on.
     */
    public final Integer getCurrentExpectedValues() {
       Integer expected = new Integer(-1);
       if (ExpectedValues.size() > 0)
          expected = (Integer) ExpectedValues.get(ExpectedValues.size()-1);
       return expected;
    }

    /** Gets the last component-compliant object we worked on.
     * This could be some types of Quantity as well as components.
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
    public final Locator getCurrentLocator() {
       Locator lastLoc = (Locator) null;
       if (CurrentLocatorList.size() > 0)
          lastLoc = (Locator) CurrentLocatorList.get(CurrentLocatorList.size()-1);
       return lastLoc;
    }

   /** Remove the current locator.
     *  @return Locator that was removed from the list of "current" quantities.
     */
    private Locator removeCurrentLocator () {
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

    public void addParentQuantityNeedsAltValue (Quantity q) 
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
    		// treat this here rather repeat this code in all Quantity handlers..

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


    /*
     * (non-Javadoc)
     * @see net.datamodel.xssp.parse.XSSPDocumentHandler#endDocument()
     */
    @Override
    public void endDocument()
    throws SAXException
    {
    	
    	super.endDocument();

    	// TODO: is this commented out section used or not?
/*
        if (DoctypeObjectAttributes != null || ForceSetXMLHeaderStuff ) {
            
           // bah, this doesnt belong here
           XMLDeclaration xmlDecl = new XMLDeclaration();
           xmlDecl.setStandalone("no");

           DocumentType doctype = new DocumentType(Quantity);

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

           Quantity.setXMLDeclaration (xmlDecl);
           Quantity.setDocumentType(doctype);
        }

        // Now that it exists, lets
        // set the notation hash for the Quantity structure
        Iterator iter = Notation.iterator();
        while (iter.hasNext()) {
           Hashtable initValues = (Hashtable) iter.next(); 
           if (Quantity.getDocumentType() == null) {
              // force having document type
              Quantity.setDocumentType(new DocumentType(Quantity)); 
           }
           Quantity.getDocumentType().addNotation(new NotationNode(initValues));
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
        // belong to the www.datamodel.net/Quantity namespace. Its not
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

    /** Do special check for dealing with adding quantities. 
     *  This method exists because its easier to deal with adding member
     *  Quantities, AxisFrames, etc in a global fashion rather than repeating code
     *  in each of the Quantity handlers.
     *  At any rate the logic is that if no parent Q exists, then it defaults to 
     *  adding the quantities as QElements in the QMLDocument.
     */
    // FIXME: this amounts to a dedicated handler for all quantities.. need to 
    // implement differently as an actual handler?
    private void startHandlerAddQuantityToParent(String namespaceURI, Quantity q) 
    {

            Quantity currentQ = getCurrentQuantity();
            if(currentQ != null) {

                 // IF its an ReferenceFrame, AND currentQ is a Matrix, we add
                 // it to the axisFrame List (just not yet..), otherwise, we add this new Q
                 // as a member to current Quantity
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
                   //   throw new SAXException("Alternative value not a list Quantity");
                 } else
                   currentQ.addProperty(q); // everything else becomes a "member"

             } else {

                 // Add as a QElement to our document, as appropriate (e.g.
                 // either to current node or as document root).
                Element elem = ((QMLDocument) getDocument()).createQMLElementNS(namespaceURI, q);
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

    //
    // Private Methods
    //
 
    // initialize the associations between element names and complextypes (aka. keys for 
    // the dispatch table for start handler events) 
    // You may ask: why "hardwire" this table?
    // We *could* do this by analysis of the QML.xsd everytime we load the document handler, 
    // but thats probably overkill, and a performance hit that we dont need to take.
    //
    // Optionally, it might be nice to have this table declared in the "Constants" class..that
    // seems better..
    // TODO:!!
    private void initElementTypeAssoc() {

//       Map<String, HandlerInfo> qmlAssoc = new Hashtable<String, HandlerInfo>();

       // QML namespace associations
       // WARNING: you need to specify the QML_NAMESPACE_URI on the HandlerInfo constructor line!!!
       // Besure to modify all of the following!!
       // 
       /*
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
       */

       // set up the associated namespace stuff
//       ElementTypeAssoc.put(Constants.QML_NAMESPACE_URI, qmlAssoc);

    }

    //
    // Internal Classes
    //

    // MAPPING HANDLERS - dont really belong here (!)
    //

    // TODO: we need to put in a separate mapping package..
    class mappingStartElementHandlerFunc implements StartElementHandler {
       public Object action ( XSSPDocumentHandler handler, String namespaceURI, 
                              String localName, String qName, Attributes attrs)
       throws SAXException {
           throw new SAXException("Current package cannot handle mapped values.");
       }
    }

} // End of QMLDocumentHandler class 

