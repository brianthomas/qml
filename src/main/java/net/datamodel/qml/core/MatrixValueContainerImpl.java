/**
 * 
 */
package net.datamodel.qml.core;


/**
 * @author thomas
 *
 */
public class MatrixValueContainerImpl 
extends ListValueContainerImpl 
{

	/**
	 * @param parent
	 */
	public MatrixValueContainerImpl ( ) { this(-1); }

	/**
	 * @param parent
	 * @param capacity
	 */
	public MatrixValueContainerImpl (int capacity) {
		super(capacity);
		locatorClassName = "net.datamodel.qml.locator.MatrixLocatorImpl";
	}

}
