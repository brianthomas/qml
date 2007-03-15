/**
 * 
 */
package net.datamodel.qml.locator;

import net.datamodel.qml.Locator;
import net.datamodel.qml.ObjectWithValues;

import org.apache.log4j.Logger;

/**
 * @author thomas
 */
public abstract class AbstractLocator 
implements Locator 
{

	private static final Logger logger = Logger.getLogger(AbstractLocator.class);

	private ObjectWithValues parent;

	// where in the data container list we are
	protected int listIndex;
	
	// Constructor
	private AbstractLocator () {}
	
	/** Vanilla constructor. */
	public AbstractLocator ( ObjectWithValues parent )
	{
		logger.debug("NEW LOCATOR CREATED");
		if (parent == null) {
			throw new NullPointerException("Cant construct Locator with NULL parent!!");
		}

		setParent(parent);
		reset();
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#getParent()
	 */
	public final ObjectWithValues getParent (  ) {
		return parent;
	}

	protected final void  setParent (ObjectWithValues p) { parent = p; } 

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#getListIndex()
	 */
	public final int getListIndex( ) { return listIndex; }

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#setListIndex(int)
	 */
	public final void setListIndex ( int index ) throws IllegalArgumentException {

		if(index < 0)
			throw new IllegalArgumentException ("setListIndex can't set index to negative value");

		listIndex = index;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#reset()
	 */
	public final void reset () {
		listIndex = 0; // set to first location 
	}


	protected final int getMaxLocation() {
		logger.debug("getMaxLocation called");
		//int maxUtilIndex = parent.getValueContainer().getMaxUtilizedIndex();
		return getParent().getCapacity() -1;
	}


}
