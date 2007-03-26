package net.datamodel.qml;

import java.util.List;

public interface MatrixLocator extends Locator {

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
	
    /** Set the location by identified axis list index.
     */
	public void setLocationIndex ( ListQuantity axis, int listIndex ) 
	throws IllegalArgumentException;
	
    /** Determine the location in terms of the listIndex for a particular
     * quantity which is serving as an axis.
     */ 
    public Integer getLocationIndex ( ListQuantity axis ) 
    throws IllegalArgumentException;
    
    /** Utility method to allow determination of the value of the location 
     * in terms of a particular quantity which is serving as an axis.
     */
    public Object getLocationValue ( ListQuantity axis )
    throws IllegalArgumentException;
	
	/** Get the iteration order for the current frame. List may be empty
     *  if no current frame is specified.
     */
    public List<ListQuantity> getIterationOrder ( );
	
}
