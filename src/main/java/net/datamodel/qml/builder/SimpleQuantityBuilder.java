
package net.datamodel.qml.builder;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import net.datamodel.qml.DataType;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Locator;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ListQuantityImpl;
import net.datamodel.qml.core.MatrixQuantityImpl;
import net.datamodel.qml.datatype.FloatDataType;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.soml.builder.SemanticObjectBuilder;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.ontology.Individual;

/** Builder package for easing the creation of various types 
 * of Quantities from scratch.
 * 
 * @author thomas
 *
 */
public class SimpleQuantityBuilder 
{

	private static final Logger logger = Logger.getLogger(SimpleQuantityBuilder.class);

	private SimpleQuantityBuilder () { }
	
	/** General helper method to construct atomic quantities.
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
	public static final AtomicQuantityImpl createAtomicQuantity ( 
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
		q.setUnits(units);
		q.setDataType(datatype);

		// set the value 2x, just to test the API, and not for any other reason
		q.setValue(value, q.createLocator());
		q.setValue(value); // will overwrite 

		return q;
	}

	/** General helper method to construct list quantities.
	 * 
	 * @param id
	 * @param units
	 * @param datatype
	 * @param values
	 * @return
	 * @throws SetDataException
	 * @throws IllegalAccessException
	 */ 
	public static final ListQuantity createListQuantity (
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

	/** Create a MatrixQuantity from passed parameters.
	 * 
	 * @param uri
	 * @param units
	 * @param datatype
	 * @param values
	 * @param frames
	 * @return
	 * @throws SetDataException
	 * @throws IllegalAccessException
	 */
	public static final MatrixQuantity createMatrixQuantity (URI uri, 
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

	/** General helper method to create float data types.
	 * 
	 * @param length
	 * @param precision
	 * @param exp
	 * @return
	 */ 
	public static final DataType createFloatDataType (int length, int precision, int exp) {
		FloatDataType dt = new FloatDataType();
		dt.setWidth(new Integer(length));
		dt.setPrecision(new Integer(precision));
		dt.setExponent(exp);
		return dt;
	}
	
	/** General helper method to create integer data types.
	 * 
	 * @param length
	 * @param signed
	 * @return
	 */
	public static final DataType createIntegerDataType (int length, boolean signed) {
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
	public static final StringDataType createStringDataType (int length) {
		StringDataType dt = new StringDataType();
		dt.setWidth(new Integer(length));
		return dt;
	}
	
}
