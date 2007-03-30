
package net.datamodel.qml.locator;

import net.datamodel.qml.Locator;
import net.datamodel.qml.ObjectWithValues;
import net.datamodel.qml.ValueContainer;

import org.apache.log4j.Logger;

/**
 * @author thomas
 */
public abstract class AbstractLocator 
implements Locator 
{

	private static final Logger logger = Logger.getLogger(AbstractLocator.class);

	private ValueContainer parent;

	/** Vanilla constructor. */
	public AbstractLocator ( ValueContainer parent )
	{
		logger.debug("NEW LOCATOR CREATED type:"+this.getClass());
		if (parent == null) {
			throw new NullPointerException("Cant construct Locator with NULL parent!!");
		}

		setParent(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#getParent()
	 */
	public final ValueContainer getParent ( ) {
		return parent;
	}

	// Setting the parent should be at the descresion of the locator
	// and not by the public (in general).
	protected final void setParent (ValueContainer p) { parent = p; } 

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#getListIndex()
	 */
	abstract public int getListIndex();

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#setListIndex(int)
	 */
	abstract public void setListIndex ( int index ) throws IllegalArgumentException;

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.Locator#reset()
	 */
	abstract public void reset ();

	/** Find the index value of the maximum utilized Index in the container
	 * to which this locator belongs.
	 * 
	 * @return
	 */
	protected final int getMaxLocation() {
		logger.debug("getMaxLocation called");
		//int maxUtilIndex = parent.getValueContainer().getMaxUtilizedIndex();
		return getParent().getCapacity() -1;
	}

}
