/**
 * 
 */
package net.datamodel.qml.builder;

import net.datamodel.soml.builder.SemanticObjectBuilderException;

/**
 * @author thomas
 *
 */
public class QuantityBuilderException 
extends SemanticObjectBuilderException {

	private static final long serialVersionUID = 1968731585843624081L;
	
	public QuantityBuilderException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public QuantityBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public QuantityBuilderException(Throwable cause) {
		super(cause);
	}

}
