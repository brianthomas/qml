/**
 * 
 */
package net.datamodel.qml.test.create;

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.core.AtomicQuantityImpl;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class TestCreatedSerialization extends BaseCase {
	
	private static final Logger logger = Logger.getLogger(TestCreatedSerialization.class);
	
	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleAtomicQuantitySerialization ( ) {

		// create an atomic quantity
		logger.info("testCreateSimpleAtomicQuantity - serialization");

		try {
			
			AtomicQuantityImpl q = createSimpleAtomicQuantity(); 
			
			logger.debug("Test AtomicQ XML output:"+System.getProperty("line.separator")+q.toXMLString());
			
			checkVariousValidXMLRepresentations(q);
			
		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			// fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleListQuantitySerialization ( ) {

		// create an atomic quantity
		logger.info("testCreateSimpleListQuantity - serialization");

		try {
			
			ListQuantity q = createSimpleListQuantity(); 
			
			logger.debug("Test ListQ XML output:"+System.getProperty("line.separator")+q.toXMLString());
			
			checkVariousValidXMLRepresentations(q);
			
		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			// fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
