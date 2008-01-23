/**
 * 
 */
package net.datamodel.qml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.TestCase;
import net.datamodel.qml.dom.QMLDocument;
import net.datamodel.qml.dom.QMLElement;
import net.datamodel.qml.dom.QMLReader;
import net.datamodel.qml.dom.Specification;
import net.datamodel.qml.dom.xerces2.QMLDocumentImpl;

import org.apache.log4j.Logger;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/** Has a number of re-usable items for various QML tests.
 * @author thomas
 *
 */
abstract public class BaseCase extends TestCase {
	
	private static final Logger logger = Logger.getLogger(BaseCase.class);
	
	protected static final String schemaDirectory = "docs/schema";
//	protected static final String samplesDirectory = "samples";
	protected static final String samplesDirectory = "docs/examples";
	protected static final String testDirectory = "target/test-samples";
	
	protected static final String unknownURNStr = "urn:qml:unknown-object";
	protected static URI unknownURI = null;
	
	// defaults to use Xerces2
	protected static Class docClass; 
	protected static String SaxParserName = "org.apache.xerces.parsers.SAXParser";
	
	protected static String [] samplefiles = null;
		
	protected void setUp()
	throws Exception
	{
		
		logger.debug("Setup test directory");
		
		logger.debug("mkdir test sample directory: "+testDirectory);
		new File(testDirectory).mkdir();
		
		logger.debug("find sample and schema files");
		samplefiles = new File(samplesDirectory).list(new OnlyXML());
		String [] sampleschemafiles = new File(samplesDirectory).list(new OnlySchema());
		String [] baseschemafiles = new File(schemaDirectory).list(new OnlySchema());
		
		logger.debug("copy over sample and schema files to test directory");
		try {
			copyFiles(samplefiles, samplesDirectory, testDirectory); 
			copyFiles(sampleschemafiles, samplesDirectory, testDirectory); 
			copyFiles(baseschemafiles, schemaDirectory, testDirectory); 
		} catch (Exception e) {
			logger.error("Cant set up tests : "+ e.getMessage());
			e.printStackTrace();
		}
		
		try {
			docClass = Class.forName("net.datamodel.qml.dom.xerces2.QMLDocumentImpl"); 
		} catch (ClassNotFoundException e) {
			logger.error("Can't find Xerces2 version of QMLDocumentImpl : "+ e.getMessage());
			e.printStackTrace();
		}
		
		try {
			unknownURI = new URI (unknownURNStr);
		} catch (URISyntaxException e) { 
			logger.error("Can't construct unknown URI for tests: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public class OnlySchema implements FilenameFilter {
		public boolean accept (File dir, String s) {
			if (s.endsWith(".xsd"))
				return true;
			return false;
		}
	}
	
	public class OnlyXML implements FilenameFilter {
		public boolean accept (File dir, String s) {
			if (s.endsWith(".xml"))
				return true;
			return false;
		}
	}
	
	protected static void checkValidXMLRepresentation(QMLDocument doc, boolean pretty, int type)
	throws Exception
	{
		logger.debug("checkValidXMLRepresentation:");
		
		String xmlRep = doc.toXMLString();
		StringReader sr = new StringReader(xmlRep);

		Specification.getInstance().setPrettyOutput(pretty);
		Specification.getInstance().setSerializeValuesStyle(type);
		
		logger.debug("   Document XML is:\n"+xmlRep);
		
		assertTrue("Is valid version? pretty:"+pretty, UtilityForTests.validateSrc(new InputSource(sr), SaxParserName));

	}
	
	/** Check the XMLrepresentation of the quantity is valid.
	 * 
	 * @param xmlRep
	 */
	public static void checkVariousValidXMLRepresentations ( Quantity q ) 
	throws Exception
	{
		logger.debug("Check valid string representation");

		// use the xerces representation
		QMLDocument doc = new QMLDocumentImpl();
		// TODO : check setting another prefix on the QML namespace.
//		doc.setPrefixNamespaceMapping("q", Constant.QML_NAMESPACE_URI);

		// create a new element, which will be the document root
//		QMLElement elem = doc.createQMLElementNS (Constant.QML_NAMESPACE_URI, q);
		
		// TODO: try it this way as well
//		q.setNamespaceURI(Constant.QML_NAMESPACE_URI); // needed? doesnt it default to this? Check! 
		QMLElement elem = doc.createQMLElement(q);
	
		// set the schema location
		String schemaLoc = Constant.QML_NAMESPACE_URI+" "+testDirectory+"/"+Constant.QML_SCHEMA_NAME;
		logger.debug("Set schema location:"+schemaLoc);
		elem.setAttribute("xsi:schemaLocation",schemaLoc);
		
		// now set the root element
		doc.setDocumentElement(elem);
		
		logger.debug("DOC XML IS:["+doc.toXMLString()+"]");
		
		// now check various representations
		checkValidXMLRepresentation(doc, true, Constant.VALUE_SERIALIZE_TAGGED);
		checkValidXMLRepresentation(doc, false, Constant.VALUE_SERIALIZE_TAGGED);
		checkValidXMLRepresentation(doc, true, Constant.VALUE_SERIALIZE_SPACE);
		checkValidXMLRepresentation(doc, false, Constant.VALUE_SERIALIZE_SPACE);

	}
	
	protected static void copyFiles (String[] files, String sourceDirectory, String destDirectory) 
	throws FileNotFoundException, IOException 
	{
		
		for (int i = 0; i< files.length; i++)
		{
				copyFile(sourceDirectory+"/"+files[i], destDirectory+"/"+files[i]);
		}
		
	}
	
	protected static void copyFile (String infile, String outfile) 
	throws FileNotFoundException, IOException 
	{
		
		// logger.debug(" -- copy "+infile+" to "+outfile);
		BufferedInputStream is = new BufferedInputStream (new FileInputStream (infile));
		BufferedOutputStream os = new BufferedOutputStream (new FileOutputStream (outfile));
		
		int b;
		while (( b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		os.close();
		
	}
	
	/** All purpose XML file validator method, should work with any SAX level 2 
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	protected static boolean validateFile (String filename)
	throws Exception 
	{
		return UtilityForTests.validateSrc(new InputSource(filename), SaxParserName);
	}
	
	/** check basic quantity API on given quantity.
	 * 
	 * @param q
	 * @param URNrep
	 * @param units
	 * @param datatype
	 */
	protected static void validateQuantityAPI (
			Quantity q, 
			Units units, 
			DataType datatype)
	{
	
		logger.debug("- validateQuantityAPI (no values check) id:"+q.getId()); 
		logger.debug("   is id OK? "+q.getId()); 
		assertNotNull("id is not NULL", q.getId());
		assertNotNull("Stored URI is not empty.", q.getRDFTypeURIs().size()>0);
		
		logger.debug("   are units OK?"); 
		assertEquals("units OK", q.getUnits().toString(), units.toString());
		logger.debug("   is datatype OK?"); 
		assertEquals("datatype OK", q.getDataType().toString(), datatype.toString());
		
	}
	
	protected static QMLDocument loadFile (String inputfile ) throws Exception {
		
		logger.debug("Attempting to load file : ["+inputfile+"]");
			
	    // declare the structure
	     QMLDocument doc = (QMLDocument) docClass.newInstance();

	     // create the reader
	     QMLReader r = new QMLReader(doc);
	     
	     //InputSource inputsource = new InputSource(new FileReader(inputfile));
	     //r.parse(inputsource);

	     r.parseFile(inputfile);
	     
/*
	     // set the output specification
	     Specification.getInstance().setPrettyOutput(true);
	     Specification.getInstance().setPrettyOutputIndentation("  ");

	     Writer myWriter = new BufferedWriter(new OutputStreamWriter(System.out));
	     // Writer myWriter = new BufferedWriter(new FileWriter(outputfile));
	     doc.toXMLWriter(myWriter);
	     myWriter.flush();
	     */
	     
	     return doc;

	}
	
	static class MyValidator extends DefaultHandler implements LexicalHandler
	    {

	        private Writer out;
	        // here are all the SAX DocumentHandler methods

	        public void setDocumentLocator (Locator l)
	        {
	        // we'd record this if we needed to resolve relative URIs
	        // in content or attributes, or wanted to give diagnostics.
	        }

	        public void startDTD(String name, String publicId, String systemId)
	        throws SAXException
	        {
	   //        emit("<[DOCTYPE "+name+" "+systemId+" "+publicId+"]>");
	        }

	        public void endDTD() throws SAXException {
	        }

	        public void startEntity(String name) throws SAXException {
	        }

	        public void endEntity(String name) throws SAXException {
	        }

	        public void startCDATA() throws SAXException {
	        }

	        public void endCDATA() throws SAXException {
	        }

	        public void comment(char ch[], int start, int length) throws SAXException {
	        }

	        public void startDocument ()
	        throws SAXException
	        {
	           try {
	               out = new OutputStreamWriter (System.out, "UTF8");
	           } catch (IOException e) {
	              throw new SAXException ("I/O error", e);
	           }
	           // emit ("<?xml version='1.0' encoding='UTF-8'?>\n");
	        }

	        public void endDocument ()
	        throws SAXException
	        {
	           try {
	            // out.write ("\n");
	            out.flush ();
	            out = null;
	           } catch (IOException e) {
	              throw new SAXException ("I/O error", e);
	           }
	        }

	        public void startElement (String tag, AttributeList attrs)
	        throws SAXException
	        {
	/*
	           emit ("<");
	           emit (tag);
	           if (attrs != null) {
	              for (int i = 0; i < attrs.getLength (); i++) {
	                emit (" ");
	                emit (attrs.getName (i));
	                emit ("\"");
	                // XXX this doesn't quote '&', '<', and '"' in the
	                // way it should ... needs to scan the value and
	                // emit '&amp;', '&lt;', and '&quot;' respectively
	                emit (attrs.getValue (i));
	                emit ("\"");
	              }
	            }
	            emit (">");
	*/
	        }

	        public void endElement (String name)
	        throws SAXException
	        {
	/*
	           emit ("</");
	           emit (name);
	           emit (">");
	*/
	        }

	        public void characters (char buf [], int offset, int len)
	        throws SAXException
	        {
	/*
	           // NOTE:  this doesn't escape '&' and '<', but it should
	           // do so else the output isn't well formed XML.  to do this
	           // right, scan the buffer and write '&amp;' and '&lt' as
	           // appropriate.

	           try {
	               out.write (buf, offset, len);
	           } catch (IOException e) {
	               throw new SAXException ("I/O error", e);
	           }
	*/
	        }

	        public void ignorableWhitespace (char buf [], int offset, int len)
	        throws SAXException
	        {
	    /*
	        // this whitespace ignorable ... so we ignore it!

	        // this callback won't be used consistently by all parsers,
	        // unless they read the whole DTD.  Validating parsers will
	        // use it, and currently most SAX nonvalidating ones will
	        // also; but nonvalidating parsers might hardly use it,
	        // depending on the DTD structure.
	    */
	        }

	        public void processingInstruction (String target, String data)
	        throws SAXException
	        {

	        }

	        // helpers ... wrap I/O exceptions in SAX exceptions, to
	        // suit handler signature requirements
	        private void emit (String s)
	        throws SAXException
	        {
	            try {
	               out.write (s);
	               out.flush();
	            } catch (IOException e) {
	               throw new SAXException ("I/O error", e);
	            }
	        }
   } // end class Validator 

    static class MyErrorHandler extends HandlerBase
    {
	        // treat validation errors as fatal
	        public void error (SAXParseException e)
	        throws SAXParseException
	        {
	            throw e;
	        }

	        // dump warnings too
	        public void warning (SAXParseException err)
	        throws SAXParseException
	        {
	            System.out.println ("** Warning"
	                + ", line " + err.getLineNumber ()
	                + ", uri " + err.getSystemId ());
	            System.out.println("   " + err.getMessage ());
	        }
    } // end class MyErrorHandler 
    

}
