/**
 * 
 */
package net.datamodel.qml.builder;

import net.datamodel.qml.Quantity;
import net.datamodel.qml.core.AtomicQuantityImpl;
import net.datamodel.soml.SemanticObject;
import net.datamodel.soml.builder.SemanticObjectBuilder;
import net.datamodel.soml.builder.SemanticObjectBuilderException;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author thomas
 *
 */
public class QuantityBuilder 
extends SemanticObjectBuilder 
{
	
	// our trusty logger
	private static final Logger logger = Logger.getLogger(QuantityBuilder.class);
	
	public QuantityBuilder(OntModel model) 
	{
		super(model);
		this.addHandler(Quantity.ClassURI, new QuantityHandler()); 
	}
	
	class QuantityHandler 
	implements SemanticObjectHandler
	{

		public SemanticObject create (SemanticObjectBuilder b, Individual in)
				throws SemanticObjectBuilderException 
		{
			Quantity q = new AtomicQuantityImpl(SemanticObjectBuilder.createURI(in.getURI()));
			// TODO: set units, datatype, value, name, description
			// a loop is probably NOT the way to do this..we should call
			// out specific properties with known rdf:types
			for (StmtIterator si = in.listProperties(); si.hasNext(); ) {
				Statement s = si.nextStatement();
			}
			return q;
		}
		
	}
	
}
