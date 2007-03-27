package net.datamodel.qml.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import net.datamodel.qml.ObjectWithProperties;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.support.Constants;
import net.datamodel.soml.Relationship;
import net.datamodel.soml.impl.SemanticObjectImpl;

import org.apache.log4j.Logger;

public class AbstractObjectWithProperties 
extends SemanticObjectImpl
implements ObjectWithProperties
{
	
	private static final Logger logger = Logger.getLogger(AbstractObjectWithProperties.class);

	private static URI propertyURN = null;
	
	protected AbstractObjectWithProperties () {
		super();
		
		// TODO: Argh. Isnt there a better way to do this?
		try {
			propertyURN = new URI(Constants.QML_PROPERTY_URN);
		} catch (URISyntaxException e) {
			String msg = "cant construct quantity..URN syntax bogus in class";
			logger.error(msg);
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithProperties#addProperty(net.datamodel.qml.Quantity)
	 */
	public boolean addProperty (Quantity property) {
		return addRelationship(property, propertyURN);
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithProperties#getProperties()
	 */
	public List<Quantity> getProperties() {
		List<Quantity> qList = new Vector<Quantity>(); 
		// tool through our list of related objects and find ones which
		// match the indicated urn
		for (Relationship rel : getRelationships(propertyURN)) {;
			qList.add((Quantity) rel.getTarget());
		}
		return qList;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datamodel.qml.ObjectWithProperties#removeProperty(net.datamodel.qml.Quantity)
	 */
	public boolean removeProperty(Quantity property) {
		return removeRelationship (propertyURN, property);
	}

}
