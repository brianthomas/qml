/**
 * 
 */
package net.datamodel.qml;

import java.util.List;

/**
 * @author thomas
 *
 */
public interface ObjectWithProperties {

	/** Add a property to this object (which is represented as
	 * by a child Quantity).
	 */
	public boolean addProperty (Quantity property);
	
	/** Remove the indicated property of the object (which is represented by
	 * a different, child Quantity). 
	 * 
	 * @param property
	 * @return
	 */
	public boolean removeProperty (Quantity property);
	
	/** Return the list of properties (Quantities) of this object.
	 * 
	 * @return
	 */
	public List<Quantity> getProperties();

}
