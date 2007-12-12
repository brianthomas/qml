/**
 * 
 */
package net.datamodel.qml.builder;

import net.datamodel.qml.Constants;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.qml.datatype.FloatDataType;
import net.datamodel.qml.datatype.IntegerDataType;
import net.datamodel.qml.datatype.StringDataType;
import net.datamodel.qml.units.UnitsImpl;
import net.datamodel.soml.SemanticObject;
import net.datamodel.soml.builder.SemanticObjectBuilder;
import net.datamodel.soml.builder.SemanticObjectBuilderException;
import net.datamodel.soml.impl.SemanticObjectImpl;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author thomas
 *
 */

public class QuantityBuilder 
extends SemanticObjectBuilder
{

	// our trusty logger
	private static final Logger logger = Logger.getLogger(QuantityBuilder.class);

//	private static final String nameUri = Constants.QML_NAMESPACE_URI+"#name";
//	private static final String descUri = Constants.QML_NAMESPACE_URI+"#description";

	private static final String FloatDataTypeURI = Constants.QML_NAMESPACE_URI+"#FloatDataType";
	private static final String IntegerDataTypeURI = Constants.QML_NAMESPACE_URI+"#IntegerDataType";
	private static final String StringDataTypeURI = Constants.QML_NAMESPACE_URI+"#StringDataType";
	
	private static final String hasValueURI = Constants.QML_NAMESPACE_URI+"#value";
	private static final String hasDataTypeURI = Constants.QML_NAMESPACE_URI+"#hasDataType";
	private static final String hasUnitsURI = Constants.QML_NAMESPACE_URI+"#hasUnits";
	private static final String rdfTypeURI = RDF.getURI()+"type";
	private static final String owlSameAsURI = OWL.getURI()+"sameAs";
	
	private static final String dtWidthPropURI = Constants.QML_NAMESPACE_URI+"#width";
	private Property dataTypeWidthProperty = null;
	private static final String dtPrecisionPropURI = Constants.QML_NAMESPACE_URI+"#precision";
	private Property dataTypePrecisionProperty = null;

	public QuantityBuilder(OntModel model) 
	{
		super(model);
		addHandler(Quantity.ClassURI, new QuantityHandler()); 
		// null these out..we will handle from grabbing the Q in the QuantityHandler
		addHandler(Constants.QML_NAMESPACE_URI+"#Units", getNullHandler()); 
//		addHandler(Constants.QML_NAMESPACE_URI+"#DataType", getNullHandler(); 

		// init property stuff
		dataTypeWidthProperty = model.getProperty(dtWidthPropURI);
		dataTypePrecisionProperty = model.getProperty(dtPrecisionPropURI);
		
	}

	class QuantityHandler 
	implements SemanticObjectHandler
	{

		public SemanticObject create (SemanticObjectBuilder b, Individual in, String rdfType)
		throws SemanticObjectBuilderException 
		{
			logger.info("QuantityHandler called");

			AtomicQuantityImpl q = new AtomicQuantityImpl(SemanticObjectImpl.createURI(rdfType));
			q.setUnits(new UnitsImpl("dude"));
			// TODO: set units, datatype, value, name, description
			// a loop is probably NOT the way to do this..we should call
			// out specific properties with known rdf:types
			for (StmtIterator si = in.listProperties(); si.hasNext(); ) {
				Statement s = si.nextStatement();
				String propUri = s.getPredicate().getURI();
				if (propUri.equals(hasValueURI)) {
					try {
						q.setValue(s.getObject().toString());
					} catch (SetDataException e) { }
					/*
				} else if (propUri.equals(nameUri)) {
					logger.error(" Cant yet set name!");
				} else if (propUri.equals(descUri)) {
					logger.error(" Cant yet set description!");
					 */
				} else if (propUri.equals(hasDataTypeURI)) {
					q.setDataType(rdfNodeToDataType(s.getObject())); 
				} else if (propUri.equals(hasUnitsURI)) {
					logger.error(" Cant yet set units!");
				} else if (propUri.equals(owlSameAsURI)) {
					// pass...for now 
					logger.debug("pass owl:sameAs property");
				} else if (propUri.equals(rdfTypeURI)) {
					// pass...for now 
					logger.debug("pass rdf:type property");
				} else {
					logger.warn("Unused Q statement:"+s);
				}
			}
			return q;
		}

	}

	protected DataType rdfNodeToDataType (RDFNode vnode) {
		DataType dt = null;
		if(vnode.canAs(Individual.class)) {
			Individual in = (Individual) vnode.as(Individual.class);
			String rdfType = findRDFTypes(in).get(0);
			if (rdfType.equals(FloatDataTypeURI)) {
				int width = in.getProperty(dataTypeWidthProperty).getInt();
				int prec = in.getProperty(dataTypePrecisionProperty).getInt();
				logger.debug(" GOT width:"+width+" precision:"+prec);
				dt = new FloatDataType(width,prec);
			} else if (rdfType.equals(StringDataTypeURI)) {
				int width = in.getProperty(dataTypeWidthProperty).getInt();
				dt = new StringDataType(width);
			} else if (rdfType.equals(IntegerDataTypeURI)) {
				int width = in.getProperty(dataTypeWidthProperty).getInt();
				dt = new IntegerDataType(width);
			} else {
				logger.error("CANT HANDLE datatype:"+rdfType);
				throw new IllegalArgumentException("Cant handle datatype in RDF of type:"+rdfType);
			}
				
		}
		return dt;
	}

}
