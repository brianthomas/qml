package net.datamodel.qml;

public interface MatrixLocator  extends Locator {

	/**
	 * @return  ReferenceFrame
	 */
	// TODO: create sub-inteface 'MatrixLocator' which adds this
	public ReferenceFrame getCurrentAxisFrame ( );

	// TODO: create sub-inteface 'MatrixLocator' which adds this
	/**
	 * Set the current ReferenceFrame. If the frame is "null" 
	 * then list-ordered iteration will be used by the 
	 * locator. This method does nothing for non-matrix 
	 * locators. 
	 * 
	 * @param frame  to set. It may be "null".
	 */
	public void setCurrentAxisFrame ( ReferenceFrame frame);
	
}
