package net.datamodel.qml.test.create;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ReferenceFrameImpl;
import net.datamodel.qml.test.UtilityForTests;
import net.datamodel.qml.units.UnitsImpl;
import net.datamodel.soml.ObjectProperty;
import net.datamodel.soml.Property;
import net.datamodel.soml.SemanticObject;

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


	// Create an matrix quantity and exercise its interface to insure we
	// get some sane answers.
	public void testCreateSimple1DMatrixQuantity ( ) {

		// create a matrix quantity
		logger.info("testCreateSimple1DMatrixQuantity");

		try {


			MatrixQuantity q = createSimple1DMatrixQuantity();
			assertNotNull(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, URIrep, units, AQdatatype);

			logger.debug("check reference frame API");
			for (ReferenceFrame r : q.getReferenceFrames())
			{
				List<ListQuantity> maxisList = r.getAxes();
				assertEquals("number of axes in reference frame is 1", maxisList.size(), 1);
				logger.debug("number of axes in frame is:"+maxisList.size());
			}

			logger.debug("number of ref frames is "+q.getReferenceFrames().size()+" should be 1");
			assertEquals("Number of ref frames is 1", q.getReferenceFrames().size(), 1);

			// Order of injection should match our list, iterate thru both and compare
			// nothing has been changed/dropped or inserted erroneously
			logger.debug("check values");
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
		DataType datatype = UtilityForTests.createStringDataType(5); 
		Units units = new UnitsImpl("");

		String URIrep_no_meaning = "URI:no-semantic-meaning";
		String URIrep_x = "URI:x-position";
		String URIrep_y = "URI:y-position";

		List<Object> values = new Vector<Object>();
		values.add("data1");
		values.add("data2");
		values.add("data3");
		values.add("data4");
		values.add("data5");
		values.add("data6");

		// create our 2D axis frame
		logger.debug("create axisframe");
		List<Object> axisValues1 = new Vector<Object>();
		axisValues1.add("1");
		axisValues1.add("2");
		axisValues1.add("4");

		List<Object> axisValues2 = new Vector<Object>();
		axisValues2.add("A");
		axisValues2.add("B");

		try {

			URI URI_no = new URI (URIrep_no_meaning);
			URI URI1 = new URI (URIrep_x);
			URI URI2 = new URI (URIrep_y);
			// create the axisFrame and (1) member axis/dimension for the matrix
			ReferenceFrame frame = new ReferenceFrameImpl(URI_no);
			ListQuantity axis1 = UtilityForTests.createListQuantity( URI1, new UnitsImpl(""), UtilityForTests.createIntegerDataType(1, false), axisValues1 );
			ListQuantity axis2 = UtilityForTests.createListQuantity( URI2, new UnitsImpl(""), UtilityForTests.createStringDataType(1), axisValues2);
			frame.addAxis(axis1);
			frame.addAxis(axis2);

			// Now the members which belong to the parent (matrix) will include our
			// axis frame
			List<ReferenceFrame> members = new Vector<ReferenceFrame>();
			members.add(frame);

			MatrixQuantity q = UtilityForTests.createMatrixQuantity( URI_no, units, datatype, values, members);
			assertNotNull(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, URIrep_no_meaning, units, datatype);

			logger.debug("check refframe");
			List<Property> actualMembers = q.getProperties();  
			int countAxisFrames = 0;
			for(Property prop : actualMembers) {
				if (prop instanceof ObjectProperty)
				{
					SemanticObject mem = ((ObjectProperty) prop).getTarget();
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

}
