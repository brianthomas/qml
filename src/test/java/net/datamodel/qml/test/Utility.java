/**
 * 
 */
package net.datamodel.qml.test;

import java.io.StringReader;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ListQuantityImpl;
import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocument;
import net.datamodel.qml.support.QMLElement;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl;
import net.datamodel.qml.test.BaseCase.MyErrorHandler;
import net.datamodel.qml.test.BaseCase.MyValidator;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author thomas
 *
 */
public class Utility {

	private static final Logger logger = Logger.getLogger(Utility.class);
	
	private static String SaxParserName = "org.apache.xerces.parsers.SAXParser";

	private Utility () {}

	/** General helper method to construct atomic quantities for testing.
	 * 
	 * @param id
	 * @param units
	 * @param datatype
	 * @param value
	 * @param properties
	 * @return
	 * @throws SetDataException
	 * @throws IllegalAccessException
	 */
	public static AtomicQuantityImpl createAtomicQuantity ( 
			URI uri, 
			Units units, 
			DataType datatype, 
			String value, 
			List<Quantity> properties
			)
	throws SetDataException, IllegalAccessException
	{

		logger.debug(" create AQ.");
		logger.debug(" create AQ. Got URI:"+uri.toASCIIString());
		AtomicQuantityImpl q = new AtomicQuantityImpl(uri);

		// populate all of the known fields to test if they are working
		// TODO: setId is now auto-matically generated??
//		q.setId(id);
//		q.setURI(URI);
		q.setUnits(units);
		q.setDataType(datatype);
		
		// set the value 2x, just to test the API, and not for any other reason
		q.setValue(value, q.createLocator());
		q.setValue(value); // will overwrite 

		return (AtomicQuantityImpl) addProperties (q, properties);
	}

	/** General helper method to construct list quantities for testing.
	 * 
	 * @param id
	 * @param units
	 * @param datatype
	 * @param values
	 * @return
	 * @throws SetDataException
	 * @throws IllegalAccessException
	 */ 
	public static ListQuantityImpl createListQuantity (URI uri, Units units, 
			DataType datatype, List values, List memberList)
	throws SetDataException, IllegalAccessException
	{

		ListQuantityImpl q = new ListQuantityImpl(uri);

		// populate all of the known fields to test if they are working
//		FIXME: setId no longer available because Id gen should be handled by computer
//		q.setId(id);
		q.setUnits(units);
		q.setDataType(datatype);

		Iterator iter = values.iterator();
		Locator loc = q.createLocator();
		while (iter.hasNext()) {
			String value = (String) iter.next();
			logger.debug("insert value:"+value+" li:"+loc.getListIndex());
			q.setValue(value,loc);
			loc.next();
		}

		return (ListQuantityImpl) addProperties (q, memberList);
	}

	public static MatrixQuantityImpl createMatrixQuantity (URI uri, 
			Units units,  DataType datatype, List values, List memberList)
	throws SetDataException, IllegalAccessException
	{

		MatrixQuantityImpl q = new MatrixQuantityImpl (uri);

		// populate all of the known fields to test if they are working
//		FIXME: setId no longer available because Id gen should be handled by computer
//		q.setId(id);
		q.setUnits(units);
		q.setDataType(datatype);

		Iterator iter = values.iterator();
		Locator loc = q.createLocator();
		while (iter.hasNext()) {
			String value = (String) iter.next();
			logger.debug("insert value:"+value+" li:"+loc.getListIndex());
			q.setValue(value,loc);
			loc.next();
		}

		return (MatrixQuantityImpl) addProperties (q, memberList);
	}

	/** General helper method to allow adding list of quantities as member of another one.
	 * 
	 * @param q
	 * @param memberList
	 * @return
	 */
	private static Quantity addProperties (Quantity q, 
			List<Quantity> memberList 
	) {
		for (Quantity prop : memberList) { q.addProperty(prop); }
		return q;
	}

	/** General helper method to create integer data types.
	 * 
	 * @param length
	 * @param signed
	 * @return
	 */
	public static IntegerDataType createIntegerDataType (int length, boolean signed) {
		IntegerDataType dt = new IntegerDataType();
		dt.setWidth(new Integer(length));
		dt.setSigned(signed);
		return dt;
	}

	/** General helper method to create string data types.
	 * 
	 * @param length
	 * @return
	 */
	public static StringDataType createStringDataType (int length) {
		StringDataType dt = new StringDataType();
		dt.setWidth(new Integer(length));
		return dt;
	}
	
	/** All purpose validator method, should work with any SAX level 2 
	 * compliant parser.
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	protected static boolean validateSource (InputSource inputsource )
	throws Exception 
	{
		
        //
        // turn it into an in-memory object.
        //

		try {
			
		logger.debug("create inputsource/sax parser");
        
        SAXParserFactory spf = SAXParserFactory.newInstance ();
        SAXParser sp = spf.newSAXParser ();
        XMLReader parser = XMLReaderFactory.createXMLReader(SaxParserName);

		logger.debug("set parser feature sets");
        parser.setFeature("http://xml.org/sax/features/validation", true);
        parser.setFeature("http://apache.org/xml/features/validation/schema", true);
        parser.setFeature("http://xml.org/sax/features/namespaces", true);
        //parser.setFeature("http://xml.org/sax/features/xmlns-uris", true);

        logger.debug(" set parser handlers");
        parser.setContentHandler (new MyValidator ());
        parser.setErrorHandler (new MyErrorHandler ());
        
        logger.debug(" Parsing uri:"+inputsource.getSystemId());
        parser.parse (inputsource);

     } catch (SAXParseException err) {
         String message =  "Failed: ** Parsing error"
             + ", line " + err.getLineNumber ()
             + ", uri " + err.getSystemId () + "\n" + err.getMessage();
            logger.error(message);
            return false;
     	} catch (SAXException e) {
     		Exception   x = e;
     		if (e.getException () != null)
     		{
     			x = e.getException ();
     		}
     		// x.printStackTrace ();
            logger.error(x.getMessage());
            return false;

     	} catch (Throwable t) {
         	t.printStackTrace ();
         	logger.error(" Failed parse:"+t.getMessage());
         	throw new Exception("Failed:"+t.getMessage());
     	}

		return true;
	}
	
}
