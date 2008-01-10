/**
 * 
 */
package net.datamodel.qml.builder;

import java.util.Iterator;

import net.datamodel.qml.Constants;
import net.datamodel.qml.DataType;
import net.datamodel.qml.Quantity;
import net.datamodel.qml.SetDataException;
import net.datamodel.qml.Units;
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
import com.hp.hpl.jena.rdf.model.Resource;
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

	private static final String BooleanDataTypeURI = Constants.QML_NAMESPACE_URI+"#BooleanDataType";
	private static final String FloatDataTypeURI = Constants.QML_NAMESPACE_URI+"#FloatDataType";
	private static final String IntegerDataTypeURI = Constants.QML_NAMESPACE_URI+"#IntegerDataType";
	private static final String StringDataTypeURI = Constants.QML_NAMESPACE_URI+"#StringDataType";
	
	private static final String UnitsTypeURI = Constants.QML_NAMESPACE_URI+"#Units";
	
	private static final String hasValueURI = Constants.QML_NAMESPACE_URI+"#value";
	private static final String hasDataTypeURI = Constants.QML_NAMESPACE_URI+"#hasDataType";
	private static final String hasUnitsURI = Constants.QML_NAMESPACE_URI+"#hasUnits";
	private static final String rdfTypeURI = RDF.getURI()+"type";
	private static final String owlSameAsURI = OWL.getURI()+"sameAs";
	
	private static final String dtWidthPropURI = Constants.QML_NAMESPACE_URI+"#width";
	private Property dataTypeWidthProperty = null;
	private static final String dtPrecisionPropURI = Constants.QML_NAMESPACE_URI+"#precision";
	private Property dataTypePrecisionProperty = null;
	private static final String dtExponentPropURI = Constants.QML_NAMESPACE_URI+"#exponent";
	private Property dataTypeExponentProperty = null;
	
	private static final String unitSymbolPropURI = Constants.QML_NAMESPACE_URI+"#symbol";
	private Property unitSymbolProperty = null;

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
		dataTypeExponentProperty = model.getProperty(dtExponentPropURI);
		unitSymbolProperty = model.getProperty(unitSymbolPropURI);
		
	}

	class QuantityHandler 
	implements SemanticObjectHandler
	{

		public SemanticObject create (SemanticObjectBuilder b, Individual in, String rdfType)
		throws SemanticObjectBuilderException 
		{
			logger.info("QuantityHandler called");

			AtomicQuantityImpl q = new AtomicQuantityImpl(SemanticObjectImpl.createURI(rdfType));
			
			// TODO: set name, description
			
			// a loop is probably NOT the way to do this..we should call
			// out specific properties with known rdf:types
			
			for (StmtIterator si = in.listProperties(); si.hasNext(); ) {
				Statement s = si.nextStatement();
				String propUri = s.getPredicate().getURI();
				if (propUri.equals(hasValueURI)) {
					try {
						// this call is kosher because by the Q spec, 
						// all values are held as literals (of xsd:string type) 
						q.setValue(s.getString()); 
					} catch (SetDataException e) { 
						logger.error("Can't set value on quantity uri:"+in.getURI()+" from RDF");
					}
					/*
				} else if (propUri.equals(nameUri)) {
					logger.error(" Cant yet set name!");
				} else if (propUri.equals(descUri)) {
					logger.error(" Cant yet set description!");
					 */
				} else if (propUri.equals(hasDataTypeURI)) {
					q.setDataType(rdfNodeToDataType(s.getObject())); 
				} else if (propUri.equals(hasUnitsURI)) {
					q.setUnits(rdfNodeToUnits(s.getObject())); 
				} else if (propUri.equals(owlSameAsURI)) {
					// pass...for now 
				} else if (propUri.equals(rdfTypeURI)) {
					// pass...for now 
				} else {
					logger.warn("Bad Quantity (uri:"+in.getURI()+") parse: Couldnt figure what to do with child statement:"+s);
				}
			}
			return q;
		}

	}

	protected DataType rdfNodeToDataType (RDFNode vnode) {
		DataType dt = null;
		if(vnode.canAs(Individual.class)) {
			Individual in = (Individual) vnode.as(Individual.class);
			
			logger.debug("trying to get datatype for uri:"+vnode.asNode().getURI());
			logger.debug(" DT node:"+vnode.asNode());
			for (String type : findRDFTypes(in)) {
				logger.debug("Got type:"+type);
			}
			String rdfType = findRDFTypes(in).get(0);
			
			if (rdfType.equals(BooleanDataTypeURI)) {
				// TODO: add real boolean type??
				dt = new IntegerDataType(1);
			} else if (rdfType.equals(FloatDataTypeURI)) {
				int width = in.getProperty(dataTypeWidthProperty).getInt();
				int prec = in.getProperty(dataTypePrecisionProperty).getInt();
				int exp = 1;
				if(in.hasProperty(dataTypeExponentProperty))
					exp = in.getProperty(dataTypeExponentProperty).getInt();
				logger.debug(" GOT width:"+width+" precision:"+prec);
				dt = new FloatDataType(width,prec,exp);
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

	protected Units rdfNodeToUnits (RDFNode vnode) {
		Units units = null;
		if(vnode.canAs(Individual.class)) {
			Individual in = (Individual) vnode.as(Individual.class);
			
			logger.debug("Got unit uri:"+in.getURI());
			for (Iterator i = in.listRDFTypes(true); i.hasNext();) {
				logger.debug("UNIT TYPE : "+i.next());
			}
			for (String type : findRDFTypes(in)) {
				logger.debug("Unit rdf:type => "+type);
			}
			String rdfType = findRDFTypes(in).get(0);
			if (rdfType.equals(UnitsTypeURI)) {
				String symbol =  in.getProperty(unitSymbolProperty).getString();
				units = new UnitsImpl(symbol);
			} else {
				logger.error("CANT HANDLE units:"+rdfType);
				throw new IllegalArgumentException("Cant handle units in RDF of type:"+rdfType);
			}
				
		}
		return units;
	}

}
