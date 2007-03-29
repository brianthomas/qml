
// CVS $Id$

// ReferenceFrameImpl.java Copyright (c) 2004 Brian Thomas. All rights reserved.

/* LICENSE

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

*/

/* AUTHOR

   Brian Thomas  (baba-luu@earthlink.net)
   

*/

// code generation timestamp: Tue Apr 20 2004-14:22:31 

package net.datamodel.qml.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.ListQuantity;
import net.datamodel.qml.ReferenceFrame;
import net.datamodel.soml.SemanticObject;
import net.datamodel.soml.impl.SemanticObjectImpl;

import org.apache.log4j.Logger;

/**
 * This special quantity is a description of a frame of reference for 
 * accessing another quantity. Each axis is assumed to describe 
 * an orthogonal dimension within the Axis Frame, and thus the number 
 * of axes (size of the axis Frame) is the same as the number 
 * of dimensions within the frame.
 * @version $Revision$
 */
public class ReferenceFrameImpl 
extends SemanticObjectImpl
implements ReferenceFrame {

	private static final Logger logger = Logger.getLogger(ReferenceFrameImpl.class);

	/** The string value of the URI describing which Axes (all are ListQuantities)
	 * the ReferenceFrame "owns". 
	 */
	public final String RelatedAxisURIStrValue = "urn:qml:related-ref-axis";
	
	/** The string value of the URI describing which ReferenceFrames 
	 * 'own' the indicated Axis (ListQuanity).
	 */
	public final String RelatedRefFrameURIStrValue = "urn:qml:related-ref-frame";
	
	private URI relatedAxisURI = null; 
	private URI relatedRefFrameURI = null; 
	
    public ReferenceFrameImpl (URI uri) {
    	super(uri);
    	setXMLNodeName("axisFrame");
    	
    	try {
    		relatedAxisURI = new URI(RelatedAxisURIStrValue); 
    		relatedRefFrameURI = new URI(RelatedRefFrameURIStrValue); 
    	} catch (URISyntaxException e) {
    		logger.error("Cant construct Reference Frame. One or more URIS are bogus"+e.getMessage()); 
    		e.printStackTrace();
    	}
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#addAxis(net.datamodel.qml.ListQuantity)
     */
    public boolean addAxis (ListQuantity axis) {
    	
    	// check we arent re-adding an existing axis
    	List<ListQuantity> existing = getAxes();
    	if (existing.contains(axis)) {
    		logger.warn("ReferenceFrame won't add axis. It already contains it!"); 
    		return false;
    	}
    	
    	// add relationships in both objects
    	boolean success = axis.addRelationship(this, relatedRefFrameURI);
    	if (success)
    		return addRelationship(axis, relatedAxisURI); 
    	
    	return false; // if we get here we had a problem setting up the relationships 
    	
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#removeAxis(net.datamodel.qml.ListQuantity)
     */
    public boolean removeAxis ( ListQuantity axis )
    {
    	
    	// check we arent re-adding an existing axis
    	List<ListQuantity> existing = getAxes();
    	if (!existing.contains(axis)) {
    		logger.warn("ReferenceFrame won't remove axis is doesnt have!"); 
    		return false;
    	}
    	
//    	 add relationships in both objects
    	boolean success = axis.removeRelationship(relatedRefFrameURI, this);
    	if (success)
    		return removeRelationship(relatedAxisURI, axis); 
    	
    	// TODO: should we throw an exception instead??
    	return false; // if we get here we had a problem removing the relationships 
    }
    
    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#getAxes()
     */
    public List<ListQuantity> getAxes ( ) {
    	List<ListQuantity> axes = new Vector<ListQuantity>();
    	List<SemanticObject> related = this.getRelatedSemanticObjects(relatedAxisURI);  
    	for (SemanticObject so : related) {
    		// Let it be possible for the cast to fail (e.g. no run time check). Shouldnt 
    		// fail if the package is working as advertised, however.
    		axes.add((ListQuantity) so); // should always be of type ListQuantity 
    	}
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.datamodel.qml.ReferenceFrame#getNumberOfAxisLocations()
     */
    public int getNumberOfAxisLocations() {
    	// TODO
    	/*
        Iterator iter = getAxes().iterator();
        int number = 1;
        while (iter.hasNext()) {
           Quantity q = (Quantity) iter.next();
           number = number*q.getSize().intValue();
        }
        return number;
        */
        return -1;
    }

}

