/**
 * 
 */
package net.datamodel.qml;

import net.datamodel.qml.BaseCase.MyErrorHandler;
import net.datamodel.qml.BaseCase.MyValidator;

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
