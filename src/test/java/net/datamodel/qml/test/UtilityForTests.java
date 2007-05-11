/**
 * 
 */
package net.datamodel.qml.test;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import net.datamodel.qml.DataType;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ListQuantityImpl;
import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
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
public class UtilityForTests {

	private static final Logger logger = Logger.getLogger(UtilityForTests.class);

	private UtilityForTests () {}

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
			String value
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

		return q;
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
	public static ListQuantity createListQuantity (
			URI uri, 
			Units units, 
			DataType datatype, 
			List<Object> values 
	)
	throws SetDataException, IllegalAccessException
	{

		ListQuantity q = new ListQuantityImpl(uri);

		// populate all of the known fields to test if they are working
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

		return q;
	}

	public static MatrixQuantity createMatrixQuantity (URI uri, 
			Units units,  DataType datatype, List<Object> values,
			List<ReferenceFrame> frames)
	throws SetDataException, IllegalAccessException
	{

		MatrixQuantity q = new MatrixQuantityImpl (uri);

		// populate all of the known fields to test if they are working
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

		for (ReferenceFrame frame : frames) { q.addReferenceFrame(frame); }
		
		return q;
	}

	/** General helper method to create integer data types.
	 * 
	 * @param length
	 * @param signed
	 * @return
	 */
	public static DataType createIntegerDataType (int length, boolean signed) {
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
	public static boolean validateSrc (InputSource inputsource, String parserName )
	throws Exception 
	{

		try {

			logger.debug("create inputsource/sax parser");

			XMLReader parser = XMLReaderFactory.createXMLReader(parserName);

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
