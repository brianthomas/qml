/**
 * 
 */
package net.datamodel.qml.builder;

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.xssp.dom.Specification;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class DisabledTestCreatedSerialization 
extends BaseCase 
{
	
	private static final Logger logger = Logger.getLogger(DisabledTestCreatedSerialization.class);
	
	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleAtomicQuantitySerialization ( ) {

		logger.warn("TODO : reenable tests");
		
		// create an atomic quantity
		logger.info("testCreateSimpleAtomicQuantity - serialization");

		try {
			
			AtomicQuantityImpl q = createSimpleAtomicQuantity();
			
			logger.debug("Test AtomicQ XML output:"+System.getProperty("line.separator")+q.toXMLString());
			
			logger.debug(q.toXMLString());
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
	

	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimple1DMatrixQuantitySerialization ( ) {
		
		// create an matrix quantity
		logger.info("testCreateSimple1DMatrixQ - serialization");

		try {
			
			MatrixQuantity q = createSimple1DMatrixQuantity(); 
			
			Specification.getInstance().setPrettyOutput(true); 
			logger.debug("Test 1D MatrixQ XML output:"+System.getProperty("line.separator")+q.toXMLString());
			
			checkVariousValidXMLRepresentations(q);
			Specification.getInstance().setPrettyOutput(false); 
			
		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			// fail(e.getMessage());
			e.printStackTrace();
		}
		
	}

	// TODO
	public void testCreateSimple2DMatrixQuantitySerialization ( ) {
		logger.error("NEED TO TEST 2D Matrix Represnetation");
	}
	
}
