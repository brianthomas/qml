/**
 * 
 */
package net.datamodel.qml.core;

import net.datamodel.qml.ObjectWithValues;

/**
 * @author thomas
 *
 */
public class MatrixValueContainerImpl extends ListValueContainerImpl {

	/**
	 * @param parent
	 */
	public MatrixValueContainerImpl (ObjectWithValues parent) {
		this(parent,1);
	}

	/**
	 * @param parent
	 * @param capacity
	 */
	public MatrixValueContainerImpl(ObjectWithValues parent, int capacity) {
		super(parent, capacity);
		locatorClassName = "net.datamodel.qml.locator.MatrixLocator";
	}

}
