
package net.datamodel.qml.builder;

import java.net.URI;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.MatrixQuantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.core.ReferenceFrameImpl;
import net.datamodel.qml.units.UnitsImpl;

/**
 * @author thomas
 *
 */
public class BaseCase 
extends net.datamodel.qml.BaseCase 
{
	
	protected URI noSemanticURI = null;
	protected final String URIrep = "urn:no-semantic-value";
	protected final String AQvalue = "data";
	protected final List<Object> LQvalues = new Vector<Object>();
	protected final List<Object> AxisValues = new Vector<Object>();
	protected final Units units = new UnitsImpl("");
	protected final DataType AQdatatype = QuantityBuilder.createStringDataType(4); 
	protected ReferenceFrame MQrefFrame = null;
		
	@Override
	public void setUp() {
		super.setUp();
		try {
			noSemanticURI = new URI (URIrep);
			
			MQrefFrame = new ReferenceFrameImpl(noSemanticURI);
	
			LQvalues.add("data1");
			LQvalues.add("data2");
			LQvalues.add("data3");

			// create our 1D axis frame
			AxisValues.add("1");
			AxisValues.add("2");
			AxisValues.add("4");

			// create the axisFrame and (1) member axis/dimension for the matrix
			ListQuantity axis1 = QuantityBuilder.createListQuantity(noSemanticURI, units, QuantityBuilder.createIntegerDataType(1, false), AxisValues );
			MQrefFrame.addAxis(axis1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// create a AQ for Creation testing
	protected AtomicQuantityImpl createSimpleAtomicQuantity () 
	{

		AtomicQuantityImpl q = null;
		try {
			q = QuantityBuilder.createAtomicQuantity(noSemanticURI, units, AQdatatype, AQvalue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}
	
	// create a LQ for Creation testing
	protected ListQuantity createSimpleListQuantity () 
	{

		ListQuantity q = null;
		try {
			q = QuantityBuilder.createListQuantity(noSemanticURI, units, AQdatatype, LQvalues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}
	
	// create a 1D MatrixQ for creation testing
	protected MatrixQuantity createSimple1DMatrixQuantity () 
	{

		MatrixQuantity q = null;
		try {
			List<ReferenceFrame> frames = new Vector<ReferenceFrame>(); 
			frames.add(MQrefFrame);
			q = QuantityBuilder.createMatrixQuantity(noSemanticURI, units, AQdatatype, LQvalues, frames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}

}
