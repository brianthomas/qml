/**
 * 
 */
package net.datamodel.qml.core;

import java.net.URISyntaxException;

import net.datamodel.qml.URN;

/**
 * @author thomas
 *
 */
public class URNImpl implements URN 
{

	private String scheme;
	private String ssp;
	private String fragment;
	
	/** Constructor of URN using a string representation. 
	 * 
	 * @param stringRep
	 * @throws URISyntaxException
	 */
	public URNImpl (String stringRep) 
	throws URISyntaxException
	{
		// TODO Auto-generated constructor stub
	}
	
	/** Constructor with separate fields for each field in the URN.
	 * 
	 * @param scheme
	 * @param ssp
	 * @throws URISyntaxException
	 */ 
	public URNImpl (String scheme, String ssp)
	throws URISyntaxException
	{
		this(scheme, ssp, (String) null);
	}
	
	/** Constructor with separate fields for each field in the URN.
	 * 
	 * @param scheme
	 * @param ssp
	 * @param fragment
	 * @throws URISyntaxException
	 */ 
	public URNImpl (String scheme, String ssp, String fragment)
	throws URISyntaxException
	{
		this.scheme = scheme;
		this.ssp = ssp;
		this.fragment = fragment;
	}
	
	@Override
	public String toString () {
		// TODO
		return "";
	}

	/* (non-Javadoc)
	 * @see net.datamodel.qml.URN#getScheme()
	 */
	public String getScheme() {
		return scheme;
	}

	/* (non-Javadoc)
	 * @see net.datamodel.qml.URN#getSchemeSpecificPart()
	 */
	public String getSchemeSpecificPart() {
		return ssp;
	}

	/* (non-Javadoc)
	 * @see net.datamodel.qml.URN#getFragment()
	 */
	public String getFragment() {
		return fragment;
	}

}
