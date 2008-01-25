
package net.datamodel.qml.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.datamodel.qml.Quantity;
import net.datamodel.soml.SemanticObject;
import net.datamodel.soml.builder.SemanticObjectBuilderException;
import net.datamodel.xssp.dom.Specification;

import org.apache.log4j.Logger;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

/**
 * @author thomas
 *
 */
abstract public class BaseBuilderCase 
extends BaseCase 
{

	// our trusty logger
	private static final Logger logger = Logger.getLogger(BaseBuilderCase.class);

	protected static String BASE_TEST_RESOURCE_DIR = "src/test/resources/RDF";
	
//	private static final OntModelSpec modelSpec = OntModelSpec.OWL_MEM;
	private static final OntModelSpec modelSpec = PelletReasonerFactory.THE_SPEC; // dont need a reasoner..we are just counting sub/super classes 

	protected static List<OntModel> testModels = new Vector<OntModel>();
	
	private static boolean isSetup = false;
	private static QuantityBuilder builder = null;

	abstract protected String[] getTestModelFiles();
	
	abstract protected String getBaseOntModelUri();
	
	@Override
	protected void setUp() 
	throws Exception 
	{
		super.setUp();

		if (!isSetup) {

			logger.debug("Setting up tests");

			try {
				OntModel model = createOntModel(getBaseOntModelUri());
				builder = new QuantityBuilder(model);
				builder.setLaxQuantityParsing(true);
			} catch (Exception e) {
				e.printStackTrace();
				//logger.error(e.getMessage());
				// fail(e.getMessage());
			}
			logger.debug(" created builder:"+builder);

			// create the test model query
			String[] tModelFile = getTestModelFiles();
			try {
				for (int i = 0; i < tModelFile.length; i++) {
					logger.debug(" Create model:"+tModelFile[i]);
					testModels.add(createOntModel(new File(tModelFile[i])));
					logger.debug(" finished Create model:"+tModelFile[i]);
				}
			} catch (Exception e) {
				logger.error("error in constructing test rdf models:" + e.getMessage());
				fail("cant run tests:"+e.getMessage());
			}

			// pretty print of debug output
			Specification.getInstance().setPrettyOutput(true);
			
			logger.debug("setup finished");
			isSetup = true;
		}

	}

	/** Build an OntModel for the passed query string.
	 * 
	 * @param queryStr
	 * 
	 * @return OntModel which represents the query 
	 */
	private static OntModel createOntModel (String ontoUri) {
		return createOntModel(ontoUri, new Hashtable<String,Boolean>(), true);
	}
	
	private static OntModel createOntModel (
			String ontoUri, 
			Map<String,Boolean> loaded, 
			boolean loadImports ) 
	{

		logger.debug("CreateOntModel uri:"+ontoUri);
		OntModel queryModel = ModelFactory.createOntologyModel(modelSpec);
		queryModel.setDynamicImports(loadImports);
		
		FileManager fm = FileManager.get();
		logger.debug("  file manager:"+fm);
		queryModel.add(fm.loadModel(ontoUri, null, "RDF/XML-ABBREV"));
		
		return queryModel;
	}
	
	private static OntModel createOntModel (File ontoFile) 
	throws FileNotFoundException 
	{
		logger.debug("CreateOntMOdel w/ file:"+ontoFile);
		OntModel ontModel = ModelFactory.createOntologyModel(modelSpec);
		ontModel.read(new FileInputStream(ontoFile), null);
		return ontModel;
	}

	public void do_test1() {
		logger.info("test builder construction");
		assertNotNull(builder);
	}

	public void do_test2() {
		
		logger.info("test builder on various RDF serializations of Q");
		build_test(Quantity.ClassURI);
		
	}
	
	protected void build_test (String uriToBuild) 
	{
		
		logger.info("test builder on various RDF serializations of "+uriToBuild);
		
		String[] tMF = getTestModelFiles();

		int modelnum = 0;
		for (OntModel model: testModels) {
			
			logger.debug("Test2 running on model:"+modelnum);
			
			Resource bClass = model.getResource(uriToBuild);
			assertNotNull("Model has class to build defined", bClass);
			
			int num_items_in_model = 0;
			for (Iterator i = model.listIndividuals(bClass); i.hasNext(); ) 
			{
				
				Individual in = (Individual) i.next();
				num_items_in_model++;
			
				try {
				
					SemanticObject so = builder.createSemanticObject(in);
					assertNotNull("SO from builder is not null",so);
				
					logger.debug("Resulting SO:\n"+so.toXMLString());
				
				} catch (SemanticObjectBuilderException e) {
					fail (e.getMessage());
				}
			}
			logger.debug("Test QuantityBuilder parsed "+num_items_in_model+" items from model.");
			assertTrue("Model in "+tMF[modelnum]+" has quantities",num_items_in_model > 0);
			modelnum++;
		}

	}
}
