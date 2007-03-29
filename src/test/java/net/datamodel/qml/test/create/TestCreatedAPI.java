package net.datamodel.qml.test.create;

import java.util.Iterator;

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.core.AtomicQuantityImpl;

import org.apache.log4j.Logger;

public class TestCreatedAPI extends BaseCase {

	private static final Logger logger = Logger.getLogger(TestCreatedAPI.class);


	// Tests
	//

	// Create an atomic quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleAtomicQuantity ( ) {

		// create an atomic quantity
		logger.info("testCreateSimpleAtomicQuantity");

		try {
			
			AtomicQuantityImpl q = createSimpleAtomicQuantity(); 
			assertNotNull(q);
			
			// check the value of the Atomic Q
			assertEquals("value OK", q.getValue(), AQvalue);
			
			// Otherwise check the API a bit
			validateQuantityAPI((Quantity) q, URIrep, units, AQdatatype);
			
		} catch (Exception e) {
			
			logger.error("test error: "+e.getMessage());
			// fail(e.getMessage());
			e.printStackTrace();
			
		}
	}

	// Create an list quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimpleListQuantity ( ) {

		// create a list quantity
		logger.info("testCreateSimpleListQuantity");

		ListQuantity q = createSimpleListQuantity(); 
		assertNotNull(q);

		// Order of injection should match our list, iterate thru both and compare
		// nothing has been changed/dropped or inserted erroneously
		Iterator iter = LQvalues.iterator();
		Locator loc = q.createLocator();
		// we have to use the iterator as the limit..because locator iterates
		// up to the capacity of the container, not the last utilized index
		while (loc.hasNext() && iter.hasNext()) 
		{
			String value = (String) iter.next();
			String qval = (String) q.getValue(loc);
			assertEquals("value OK", qval, value);
			loc.next();
		}

		validateQuantityAPI((Quantity) q, URIrep, units, AQdatatype);
	
	}
	
	/*

	// Create an matrix quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimple1DMatrixQuantity ( ) {

		// create a matrix quantity
		logger.info("testCreateSimple1DMatrixQuantity");

		String id = "matrix1";
		DataType datatype = createStringDataType(5); 
		Units units = new UnitsImpl("");
		String URIrep = "URI:no-semantic-meaning";
		List values = new Vector ();
		values.add("data1");
		values.add("data2");
		values.add("data3");

		// create our 1D axis frame
		List axisValues = new Vector ();
		axisValues.add("1");
		axisValues.add("2");
		axisValues.add("4");

		try {

			URI URI = new URI (URIrep);
			// create the axisFrame and (1) member axis/dimension for the matrix
			ReferenceFrame af = new ReferenceFrameImpl();
			ListQuantityImpl axis1 = createListQuantity(URI, new UnitsImpl(""), createIntegerDataType(1, false), axisValues, new Vector() );
			af.addAxis(axis1);

			// Now the members which bbelong to the parent (matrix) will include our
			// axis frame
			List members = new Vector();
			members.add(af);

			MatrixQuantityImpl q = createMatrixQuantity(URI, units, datatype, values, members);
			assertNotNull(q);

			// check the string representation 
			checkVariousValidXMLRepresentations(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, id, URIrep, units, datatype);

			logger.debug("check axisframe");
			List<Quantity> actualMembers = q.getProperties();  
			Iterator aiter = actualMembers.iterator();
			int countAxisFrames = 0;
			while (aiter.hasNext()) {
				Quantity mem = (Quantity) aiter.next();
				if (mem instanceof ReferenceFrame) {
					ReferenceFrame maf = (ReferenceFrame) mem;
					List maxisList = maf.getAxes();
					assertEquals("number of axes in axis frame is 1", maxisList.size(), 1);
					logger.debug("number of axes in frame is:"+maxisList.size());
					countAxisFrames++;
				} else {
					fail ("No non-axis members should exist in simple1DMatrixQ constructed");
				}
			}

			logger.debug("number of axis frames is "+countAxisFrames+" should be 1");
			assertEquals("Number of axis frames is 1", countAxisFrames, 1);

			assertEquals("Number of members is 1", actualMembers.size(), 1);

			// Order of injection should match our list, iterate thru both and compare
			// nothing has been changed/dropped or inserted erroneously
			logger.debug("check values");
			Iterator iter = values.iterator();
			Locator loc = q.createLocator();
			// we have to use the iterator as the limit..because locator iterates
			// up to the capacity of the container, not the last utilized index
			while (loc.hasNext() && iter.hasNext()) 
			{
				String value = (String) iter.next();
				String qval = (String) q.getValue(loc);
				assertEquals("value OK", qval, value);
				loc.next();
			}
			logger.debug("fini");

		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	// Create a matrix quantity with 2 dimensions and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimple2DMatrixQuantity ( ) {

		// create a matrix quantity
		logger.info("testCreateSimple2DMatrixQuantity");

		logger.debug("create basic meta-data");
		String id = "matrix2";
		DataType datatype = createStringDataType(5); 
		Units units = new UnitsImpl("");
		List values = new Vector ();
		String URIrep_no_meaning = "URI:no-semantic-meaning";
		String URIrep_x = "URI:x-position";
		String URIrep_y = "URI:y-position";
		values.add("data1");
		values.add("data2");
		values.add("data3");
		values.add("data4");
		values.add("data5");
		values.add("data6");

		// create our 2D axis frame
		logger.debug("create axisframe");
		List axisValues1 = new Vector ();
		axisValues1.add("1");
		axisValues1.add("2");
		axisValues1.add("4");

		List axisValues2 = new Vector ();
		axisValues2.add("A");
		axisValues2.add("B");

		try {

			URI URI_no = new URI (URIrep_no_meaning);
			URI URI1 = new URI (URIrep_x);
			URI URI2 = new URI (URIrep_y);
			// create the axisFrame and (1) member axis/dimension for the matrix
			ReferenceFrame af = new ReferenceFrameImpl();
			ListQuantityImpl axis1 = createListQuantity( URI1, new UnitsImpl(""), createIntegerDataType(1, false), axisValues1, new Vector() );
			ListQuantityImpl axis2 = createListQuantity( URI2, new UnitsImpl(""), createStringDataType(1), axisValues2, new Vector() );
			af.addAxis(axis1);
			af.addAxis(axis2);

			// Now the members which bbelong to the parent (matrix) will include our
			// axis frame
			List members = new Vector();
			members.add(af);

			MatrixQuantityImpl q = createMatrixQuantity( URI_no, units, datatype, values, members);
			assertNotNull(q);

			// check the string representation 
			logger.debug("check representations.."); 
			checkVariousValidXMLRepresentations(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, id, URIrep_no_meaning, units, datatype);

			logger.debug("check axisframe");
			List actualMembers = q.getProperties();  
			Iterator aiter = actualMembers.iterator();
			int countAxisFrames = 0;
			while (aiter.hasNext()) {
				Quantity mem = (Quantity) aiter.next();
				if (mem instanceof ReferenceFrame) {
					ReferenceFrame maf = (ReferenceFrame) mem;
					List maxisList = maf.getAxes();
					logger.debug("number of axes in frame is:"+maxisList.size());
					assertEquals("number of axes in axis frame is 2", maxisList.size(), 2);
					countAxisFrames++;
				} else {
					fail ("No non-axis members should exist in simple1DMatrixQ constructed");
				}
			}

			logger.debug("number of axis frames is "+countAxisFrames+" should be 1");
			assertEquals("Number of axis frames is 1", countAxisFrames, 1);

			assertEquals("Number of members is 1", actualMembers.size(), 1);

			// Order of injection should match our list, iterate thru both and compare
			// nothing has been changed/dropped or inserted erroneously
			logger.debug("check values");
			Iterator iter = values.iterator();
			Locator loc = q.createLocator();
			// we have to use the iterator as the limit..because locator iterates
			// up to the capacity of the container, not the last utilized index
			while (loc.hasNext() && iter.hasNext()) 
			{
				String value = (String) iter.next();
				String qval = (String) q.getValue(loc);
				assertEquals("value OK", qval, value);
				loc.next();
			}
			logger.debug("fini");

		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	*/

}
