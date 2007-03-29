
package net.datamodel.qml.test.create;

import java.net.URI;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.DataType;
import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.Units;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.test.Utility;
import net.datamodel.qml.units.UnitsImpl;

/**
 * @author thomas
 *
 */
public class BaseCase 
extends net.datamodel.qml.test.BaseCase 
{
	
	protected final String URIrep = "urn:no-semantic-value";
	protected final String AQvalue = "data";
	protected final List<Object> LQvalues = new Vector<Object>();
	protected final Units units = new UnitsImpl("");
	protected final DataType AQdatatype = Utility.createStringDataType(4); 
		
	@Override
	public void setUp() {
		super.setUp();
		LQvalues.add("data1");
		LQvalues.add("data2");
		LQvalues.add("data3");
	}

	// create a AQ for Creation testing
	protected AtomicQuantityImpl createSimpleAtomicQuantity () 
	{

		AtomicQuantityImpl q = null;
		try {
			
			URI uri = new URI (URIrep);
			q = Utility.createAtomicQuantity(uri, units, AQdatatype, AQvalue);
			
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
			
			URI uri = new URI (URIrep);
			q = Utility.createListQuantity(uri, units, AQdatatype, LQvalues);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}
	
	

}
