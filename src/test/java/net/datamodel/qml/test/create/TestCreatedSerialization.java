/**
 * 
 */
package net.datamodel.qml.test.create;

import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.test.create.BaseCase;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class TestCreatedSerialization extends BaseCase {
	
	private static final Logger logger = Logger.getLogger(TestCreatedSerialization.class);
	
	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleAtomicQuantity ( ) {

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

}
