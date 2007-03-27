package net.datamodel.qml.test.create;

import java.io.StringReader;
import java.net.URI;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.Locator;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ListQuantityImpl;
import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.core.ReferenceFrameImpl;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocument;
import net.datamodel.qml.support.QMLElement;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl;
import net.datamodel.qml.test.BaseCase;
import net.datamodel.qml.units.UnitsImpl;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class TestCreate extends BaseCase {

	private static final Logger logger = Logger.getLogger(TestCreate.class);
	private Specification spec = Specification.getInstance();

	/**
	 * @@Overrides
	 */
	public void setUp () {
		super.setUp();
	}

	/** General helper method to construct atomic quantities for testing.
	 * 
	 * @param id
	 * @param units
	 * @param datatype
	 * @param value
	 * @param memberList
	 * @return
	 * @throws SetDataException
	 * @throws IllegalAccessException
	 */
	public static AtomicQuantityImpl createAtomicQuantity (String id, 
			URI uri, Units units, 
			DataType datatype, 
			String value, 
			List memberList)
	throws SetDataException, IllegalAccessException
	{

		AtomicQuantityImpl q = new AtomicQuantityImpl();

		// populate all of the known fields to test if they are working
		// TODO: setId is now auto-matically generated??
//		q.setId(id);
		logger.debug("Got URI:"+uri.toASCIIString());
//		q.setURI(URI);
		q.setUnits(units);
		q.setDataType(datatype);
		q.setValue(value,q.createLocator());

		return (AtomicQuantityImpl) addProperties (q, memberList);
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
// FIXME: setId no longer available because Id gen should be handled by computer
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
// FIXME: setId no longer available because Id gen should be handled by computer
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

	// Tests
	//

	/** Create an atomic quantity and exercise its interface to insure we
	 * get some sane answers.
	 *
	 */
	public void testCreateSimpleAtomicQuantity ( ) {

		// create an atomic quantity
		logger.info("testCreateSimpleAtomicQuantity");

		String id = "atomic1";
		DataType datatype = createStringDataType(4); 
		String URIrep = "URI:no-semantic-value";
		Units units = new UnitsImpl("");
		String value = "data";

		try {
			URI URI = new URI (URIrep);
			AtomicQuantityImpl q = createAtomicQuantity(id, URI, units, datatype, value, new Vector());
			assertNotNull(q);

			// check the string representation
			checkVariousValidXMLRepresentations(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, id, URIrep, units, datatype);

			// check the value of the Atomic Q
			assertEquals("value OK", q.getValue(), value);

		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Create an list quantity and exercise its interface to insure we
	 * get some sane answers.
	 *
	 */ 
	public void testCreateSimpleListQuantity ( ) {

		// create a list quantity
		logger.info("testCreateSimpleListQuantity");

		String URIrep = "URI:no-semantic-value";
		DataType datatype = createStringDataType(5); 
		Units units = new UnitsImpl("");
		List values = new Vector ();

		values.add("data1");
		values.add("data2");
		values.add("data3");

		try {

			URI URI = new URI (URIrep);			
			ListQuantityImpl q = createListQuantity(URI, units, datatype, values, new Vector());

			assertNotNull(q);

			// check the string representation
			checkVariousValidXMLRepresentations(q);

			// check the API a bit
			validateQuantityAPI((Quantity) q, q.getId(), URIrep, units, datatype);

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

		} catch (Exception e) {
			logger.error("test error: "+e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Create an matrix quantity and exercise its interface to insure we
	 * get some sane answers.
	 *
	 */
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

	/** Create a matrix quantity with 2 dimensions and exercise its interface to insure we
	 * get some sane answers.
	 *
	 */
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

	/** Check the XMLrepresentation of the quantity is valid.
	 * 
	 * @param xmlRep
	 */
	private void checkVariousValidXMLRepresentations(Quantity q) 
	throws Exception
	{
		logger.debug("Check valid string representation");

		// Setup the document. We need to make sure all the right prefixes,
		// and schema references exist.
		Hashtable prefixMap = new Hashtable();
		// use the xerces representation
		QMLDocument doc = new QMLDocumentImpl();

		// create the prefix map we want to use
		prefixMap.put("", Constants.QML_NAMESPACE_URI);
		prefixMap.put("xsi",Constants.XML_SCHEMA_INSTANCE_NAMESPACE_URI);

		// set prefix mappings in the document
		doc.setPrefixNamespaceMappings(prefixMap);

		// create a new element
		QMLElement elem = doc.createQMLElementNS (Constants.QML_NAMESPACE_URI, q);
		String schemaLoc = Constants.QML_NAMESPACE_URI+" "+
		testDirectory+"/"+Constants.QML_SCHEMA_NAME;
		elem.setAttribute("xsi:schemaLocation",schemaLoc);
		doc.setDocumentElement(elem);

		// now check various representations
		checkValidXMLRepresentation(doc, false, Constants.VALUE_SERIALIZE_TAGGED);
		checkValidXMLRepresentation(doc, true, Constants.VALUE_SERIALIZE_TAGGED);
		checkValidXMLRepresentation(doc, false, Constants.VALUE_SERIALIZE_SPACE);
		checkValidXMLRepresentation(doc, true, Constants.VALUE_SERIALIZE_SPACE);

	}

	private void checkValidXMLRepresentation(QMLDocument doc, boolean pretty, int type)
	throws Exception
	{
		String xmlRep = doc.toXMLString();
		StringReader sr = new StringReader(xmlRep);

		logger.debug("XMLRepresentation:\n"+xmlRep);

		spec.setPrettyOutput(pretty);
		spec.setSerializeValuesStyle(type);

		assertTrue("Is valid version pretty:"+pretty+" type:"+type, validateSource(new InputSource(sr)));

	}

}
