/**
 * 
 */
package net.datamodel.qml.core;

import net.datamodel.qml.MatrixQuantity;


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
	public MatrixValueContainerImpl ( MatrixQuantity parent ) { this(parent, -1); }

	/**
	 * @param parent
	 * @param capacity
	 */
	public MatrixValueContainerImpl (MatrixQuantity parent, int capacity) {
		super(parent, capacity);
		locatorClassName = "net.datamodel.qml.locator.MatrixLocatorImpl";
	}

}
