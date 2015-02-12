package net.datamodel.qml;

import java.util.List;

public interface MatrixLocator 
extends Locator 
{

	/** Find the reference frame which the matrix locator is using
	 * to traverse the values of its parent MatrixQuantity.  
	 * 
	 * @return  ReferenceFrame
	 */
	public ReferenceFrame getCurrentReferenceFrame ( );

	/**
	 * Set the ReferenceFrame to use for traversal of the parent
	 * MatrixQuantity. If the frame is "null" 
	 * then list-ordered iteration will be used by the 
	 * locator. 
	 * 
	 * @param frame to set. It may be "null".
	 * @throws ReferenceFrameNotFoundException if the frame of reference doesnt exist in the parent MatrixQuantity.
	 * 
	 */
	public void setCurrentReferenceFrame ( ReferenceFrame frame)
	throws ReferenceFrameNotFoundException;
	
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
