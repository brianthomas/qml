/*
 * DataSet Validator
 *
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.ext.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;


/**
 *
 *  A validator to validate dataset xml files:
 *
 *    general xml file:
 *      (1) xml file is well-formed;
 *      (2) xml file is consistent with dtd;
 *      (3) check if data files defined in entities exists;
 *   
 *    dataset file specific:
 *      (4) check if adc-name is in entities and data file names;
 *      (5) check if a file defined in entity is a zip or gzip file
 *      (6) check if No. of hrefs is consistent with 
 *          the No. of unparsed external entities
 *
 *    xdf specific: (to-be-implemented)
 *      (7) check consistency between dataFormat and actual data
 *      (8) check read block
 *      (9) check checksum
 *
 * @version $Id$
 *  
 */

public class Validator
    extends DefaultHandler
    implements LexicalHandler
{

    //
    // Constants
    //

    // feature ids

    /** Namespaces feature id (http://xml.org/sax/features/namespaces). */
    protected static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

    /** Validation feature id (http://xml.org/sax/features/validation). */
    protected static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

    /** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
    protected static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

    /** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
    protected static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

    // property ids

    /** Lexical handler property id (http://xml.org/sax/properties/lexical-handler). */
    protected static final String LEXICAL_HANDLER_PROPERTY_ID = "http://xml.org/sax/properties/lexical-handler";

    // default settings

    /** Default parser name. */
    protected static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

    /** Default namespaces support (true). */
    protected static final boolean DEFAULT_NAMESPACES = true;

    /** Default validation support (false). */
    protected static final boolean DEFAULT_VALIDATION = true;

    /** Default Schema validation support (true). */
    protected static final boolean DEFAULT_SCHEMA_VALIDATION = true;

    /** Default Schema full checking support (false). */
    protected static final boolean DEFAULT_SCHEMA_FULL_CHECKING = false;

    /** Default Entity and Data file naming convention check (false). */
    protected static final boolean DEFAULT_ENTITY_NAME_CHECK = false;

    /** Default data file compression check (true). */
    protected static final boolean DEFAULT_COMPRESSION_CHECK = true;

    /** Default debug mode (false). */
    protected static final boolean DEFAULT_DEBUG_MODE = false;

    //
    // Data
    //

    /** debug mode */
    protected static boolean debug = DEFAULT_DEBUG_MODE;

    /** No. of href */
    protected static int hrefNo = 0;

    /** No. of unparsed external entities */
    protected static int entityNo = 0;

    /** xml file to be parsed */
    protected static String xmlfile;

    /** check naming convention */
    protected boolean checkNamingConvention;

    /** check file compression */
    protected boolean checkCompression;

    //
    // Constructors
    //

    /** Default constructor. */
    public Validator() {
    } // <init>()


    //
    // Public methods
    //


    /** Set if check naming convention */
    public void setCheckNamingConvention(boolean namecheck) {
	checkNamingConvention = namecheck;
    } 

    /** Set if check naming convention */
    public void setCheckCompression(boolean compressionCheck) {
	checkCompression = compressionCheck;
    } 

    //
    // ContentHandler methods
    //

    /** Start document. */
    public void startDocument() throws SAXException {
    } // startDocument()

    /** Processing instruction. */
    public void processingInstruction(String target, String data)
        throws SAXException {
    } // processingInstruction(String,String)

    /** Start element. */
    public void startElement(String uri, String local, String raw,
                             Attributes attrs) throws SAXException {
	if (attrs.getIndex("href") != -1)
	    hrefNo++;

    } // startElement(String,String,String,Attributes)

    /** Characters. */
    public void characters(char ch[], int start, int length)
        throws SAXException {
    } // characters(char[],int,int);

    /** Ignorable whitespace. */
    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException {
    } // ignorableWhitespace(char[],int,int);

    /** End element. */
    public void endElement(String uri, String local, String raw)
        throws SAXException {
    } // endElement(String)

    //
    // ErrorHandler methods
    //

    /** Warning. */
    public void warning(SAXParseException ex) throws SAXException {
        printError("Warning", ex);
    } // warning(SAXParseException)

    /** Error. */
    public void error(SAXParseException ex) throws SAXException {
        printError("Error", ex);
    } // error(SAXParseException)

    /** Fatal error. */
    public void fatalError(SAXParseException ex) throws SAXException {
        printError("Fatal Error", ex);
        throw ex;
    } // fatalError(SAXParseException)

    //
    // LexicalHandler methods
    //

    /** Start DTD. */
    public void startDTD(String name, String publicId, String systemId)
        throws SAXException {
    } // startDTD(String,String,String)

    /** End DTD. */
    public void endDTD() throws SAXException {
    } // endDTD()

    /** Start entity. */
    public void startEntity(String name) throws SAXException {
    } // startEntity(String)

    /** End entity. */
    public void endEntity(String name) throws SAXException {
    } // endEntity(String)

    /** Start CDATA section. */
    public void startCDATA() throws SAXException {
    } // startCDATA()

    /** End CDATA section. */
    public void endCDATA() throws SAXException {
    } // endCDATA()

    /** Comment. */
    public void comment(char ch[], int start, int length) throws SAXException {
	//
    } // comment(char[],int,int)


    //
    // DTDHandler
    //
    public void unparsedEntityDecl(String name,
				   String publicId,
				   String systemId,
				   String notationName)
	throws SAXException
    {
	entityNo++;

	File entityFile = null;

	// java IO and systemId were handled differently in
        // sdk 1.3 and 1.4
	/* for SDK1.4; supports URL
*/
	try {
	    entityFile = new File (new URI(systemId));

	    if (!entityFile.exists()) {
		System.err.println("[Error] " + xmlfile + ": " + systemId +
		", defined in entity, does not exist!!");
		return;
	    }
	} catch (URISyntaxException e) {
	    System.err.println("[Error] " + xmlfile + ": " + systemId + ", URISyntaxException: " + e.getMessage());
            return;
	}

	// for jdk 1.3 and 1.4
/*
	if (systemId.startsWith("file:///"))
	    entityFile = new File (systemId.substring(7));
	else
	    entityFile = new File (systemId);
    
	if (!entityFile.exists()) {
	    System.err.println("[Error] " + xmlfile + ": " + systemId + 
			       ", defined in entity, does not exist!!");
	    return;
	}
*/

	// check if a data file defined in entity is compressed
	// only check zip and gzip
	// .Z by compress is not checked ???
	if (checkCompression) {
	    try {
		new GZIPInputStream (new FileInputStream (entityFile));
		System.err.println("[Error] " + xmlfile + ": " + systemId + ", defined in entity, is a gzip file!!");
	    } catch (FileNotFoundException fe) {
		// already checked above
	    } catch (IOException ie) {
		// to be catched by next try {} block
	    }
	    
	    try {
		new ZipFile(entityFile);
		System.err.println("[Error] " + xmlfile + ": " + systemId + ", defined in entity, is a zip file!!");
	    } catch (ZipException ze) {
		// expected
	    } catch (IOException ie) {
		System.err.println("[Error] " + xmlfile + ": IOException on file: " + systemId);
	    }
	}

	// check names of entities and data files
        if (checkNamingConvention) {
	    // Ideally: Journal begins with J
	    //          Catalog begins with C

	    String root, rootplus;
	    int dotPos=xmlfile.indexOf(".xml");
	    if (dotPos != -1)
		root=xmlfile.substring(0,dotPos);
            else
		root=xmlfile;

	    int plus = root.indexOf('+');
	    if (plus != -1) {
		StringBuffer sb = new StringBuffer(root);
		rootplus = sb.replace(plus,plus+1,"_plus_").toString();
	    } else
		rootplus=root;

	    if (name.indexOf(rootplus) == -1 && name.indexOf(root) == -1) {
		System.err.println("[Warning] " + xmlfile + 
		": entity, " + name + ", does not have ADC name pre-appended");
	    }
	    if (systemId.indexOf(root) == -1 && systemId.indexOf(rootplus) == -1) {
		System.err.println("[Warning] " + xmlfile + 
		": file, " + systemId + ", does not have ADC name pre-appended");
	    }
	}
    } // unparsedEntityDecl()


    //
    // Protected methods
    //

    /** Prints the error message. */
    protected void printError(String type, SAXParseException ex) {

        System.err.print("[");
        System.err.print(type);
        System.err.print("] ");
	String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1)
                systemId = systemId.substring(index + 1);
            System.err.print(systemId);
        }
        System.err.print(": Line " + ex.getLineNumber() + ", " +
			 "Column " + ex.getColumnNumber() + ": ");
        System.err.println(ex.getMessage());
        System.err.flush();

    } // printError(String,SAXParseException)

    //
    // Main
    //

    /** Main program entry point. */
    public static void main(String argv[]) {

        // is there anything to do?
        if (argv.length == 0) {
            printUsage();
            System.exit(1);
        }

        // variables
        Validator validator = null;
        XMLReader parser = null;
        boolean namespaces = DEFAULT_NAMESPACES;
        boolean validation = DEFAULT_VALIDATION;
        boolean schemaValidation = DEFAULT_SCHEMA_VALIDATION;
        boolean schemaFullChecking = DEFAULT_SCHEMA_FULL_CHECKING;
	boolean namecheck = DEFAULT_ENTITY_NAME_CHECK;
	boolean compressionCheck = DEFAULT_COMPRESSION_CHECK;

        // process arguments
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.startsWith("-")) {
                String option = arg.substring(1);
                if (option.equals("p")) {
                    // get parser name
                    if (++i == argv.length) {
                        System.err.println("error: Missing argument to -p option.");
                    }
                    String parserName = argv[i];

                    // create parser
                    try {
                        parser = XMLReaderFactory.createXMLReader(parserName);
                    }
                    catch (Exception e) {
			System.err.println("error: Unable to instantiate parser ("+parserName+")");
                    }
                    continue;
                }
                if (option.equalsIgnoreCase("n")) {
                    namespaces = option.equals("n");
                    continue;
                }
                if (option.equalsIgnoreCase("v")) {
                    validation = option.equals("v");
                    continue;
                }
                if (option.equalsIgnoreCase("s")) {
                    schemaValidation = option.equals("s");
                    continue;
                }
                if (option.equalsIgnoreCase("f")) {
                    schemaFullChecking = option.equals("f");
                    continue;
                }
                if (option.equalsIgnoreCase("u")) {
                    namecheck = option.equals("u");
                    continue;
                }
                if (option.equalsIgnoreCase("c")) {
                    compressionCheck = option.equals("c");
                    continue;
                }
                if (option.equalsIgnoreCase("d")) {
                    debug = option.equals("d");
                    continue;
                }
		if (option.equals("h")) {
                    printUsage();
                    continue;
                }
            }
       
            xmlfile=arg;

            // use default parser?
            if (parser == null) {

                // create parser
                try {
                    parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
                }
                catch (Exception e) {
                    System.err.println("error: Unable to instantiate parser ("+DEFAULT_PARSER_NAME+")");
                }
            }

            // set parser features
            try {
                parser.setFeature(NAMESPACES_FEATURE_ID, namespaces);
            }
            catch (SAXException e) {
                System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
            }
            try {
                parser.setFeature(VALIDATION_FEATURE_ID, validation);
            }
            catch (SAXException e) {
                System.err.println("warning: Parser does not support feature ("+VALIDATION_FEATURE_ID+")");
            }
            try {
                parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, schemaValidation);
            }
            catch (SAXNotRecognizedException e) {
                // ignore
            }
            catch (SAXNotSupportedException e) {
                System.err.println("warning: Parser does not support feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");
            }
            try {
                parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, schemaFullChecking);
            }
            catch (SAXNotRecognizedException e) {
                // ignore
            }
            catch (SAXNotSupportedException e) {
                System.err.println("warning: Parser does not support feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");
            }

            // setup validator
            if (validator == null) {
                validator = new Validator();
            }

            // set parser
            parser.setContentHandler(validator);
            parser.setErrorHandler(validator);
            parser.setDTDHandler(validator);
	    
            try {
                parser.setProperty(LEXICAL_HANDLER_PROPERTY_ID, validator);
            }
            catch (SAXException e) {
                // ignore
            }

            // parse file
	    validator.setCheckNamingConvention(namecheck);
	    validator.setCheckCompression(compressionCheck);

            try {
                parser.parse(xmlfile);

		// check consistency between href and data files defined
		// in entities
		if (hrefNo != entityNo) {
		    System.err.println("[Warning] " + xmlfile + ": " +
				       "No. of hrefs (" + hrefNo +
				       ") != No. of entities (" + 
				       entityNo + ")");
		}
            }
            catch (SAXParseException e) {
		System.err.println("error: SAXParseException occurred - " +e.getMessage());
		if (debug)
		    e.printStackTrace(System.err);		
            } catch (java.net.UnknownHostException e) {
		System.err.println("error: java.net.UnknownHostException: " + e.getMessage());
		if (debug)
		    e.printStackTrace(System.err);
	    }
            catch (Exception e) {
                System.err.println("error: Parse exception occurred - "+e.getMessage());
		if (debug) {
		    if (e instanceof SAXException) {
			e = ((SAXException)e).getException();
		    }
		    e.printStackTrace(System.err);
		}
            }
        }

    } // main(String[])

    //
    // Private static methods
    //

    /** Prints the usage. */
    private static void printUsage() {

        System.err.println("\nUsage: java Validator (options) uri ...");
        System.err.println();

        System.err.println("Options:");
        System.err.println("  -p name  Select parser by name.");
        System.err.println("  -n | -N  Turn on/off namespace processing.");
        System.err.println("  -v | -V  Turn on/off validation.");
        System.err.println("  -s | -S  Turn on/off Schema validation support.");
        System.err.println("           NOTE: Not supported by all parsers.");
        System.err.println("  -f | -F  Turn on/off Schema full checking.");
        System.err.println("           NOTE: Requires use of -s and not supported by all parsers.");
        System.err.println("  -u | -U  Turn on/off entity naming convention check for uniqueness.");
        System.err.println("  -c | -C  Turn on/off data file compression check: zip, gzip.");
        System.err.println("  -d | -D  Turn on/off debug mode.");
        System.err.println("  -h       This help screen.");
        System.err.println();

        System.err.println("Defaults:");
        System.err.println("  Parser:     "+DEFAULT_PARSER_NAME);
        System.err.print("  Namespaces: ");
        System.err.println(DEFAULT_NAMESPACES ? "on" : "off");
        System.err.print("  Validation: ");
        System.err.println(DEFAULT_VALIDATION ? "on" : "off");
        System.err.print("  Schema:     ");
        System.err.println(DEFAULT_SCHEMA_VALIDATION ? "on" : "off");
        System.err.print("  Schema full checking:     ");
        System.err.println(DEFAULT_SCHEMA_FULL_CHECKING ? "on" : "off");
        System.err.print("  Entity naming convention check:  ");
        System.err.println(DEFAULT_ENTITY_NAME_CHECK ? "on" : "off");
        System.err.print("  href file Compression checking:  ");
        System.err.println(DEFAULT_COMPRESSION_CHECK ? "on" : "off");
        System.err.print("  Debug:      ");
        System.err.println(DEFAULT_DEBUG_MODE ? "on" : "off");

    }
}

