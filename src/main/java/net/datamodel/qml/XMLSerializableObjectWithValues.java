
package net.datamodel.qml;

import net.datamodel.xssp.ReferenceableXMLSerializableObject;


/** Interface for any object which is serializable in XML and
 * contains 'values'.
 * 
 * @author thomas
 *
 */
public interface XMLSerializableObjectWithValues 
extends ReferenceableXMLSerializableObject, ObjectWithValues
{

	/** Set whether or not we wish to serialize our value 
	 * as acdata section.
	 * 
	 * @param val boolean value of cdata serialization
	 */
	public void setValueCDATASerialization (boolean val);

	/** Determine whether or not we wish to serialize our value as cdata section.
	 * @return boolean value of cdata serialization
	 */
	public boolean getValueCDATASerialization(); 
	
	/** Whether to serialize values as tagged or space-delimited. 
	 */
	public void setTaggedValuesSerialization (boolean value);

	/** Determine whether values are serialized as tagged or space-delimited. 
	 */
	public boolean getTaggedValuesSerialization();

}
