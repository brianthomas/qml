
package net.datamodel.qml.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

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
import com.hp.hpl.jena.util.FileManager;

/**
 * @author thomas
 *
 */
public class TestBuilder extends BaseCase {

	// our trusty logger
	private static final Logger logger = Logger.getLogger(TestBuilder.class);

	protected static String BASE_TEST_RESOURCE_DIR = "src/test/resources/RDF";

	private static final String BaseOntModelUri = "http://test.org/testThings.owl";
//	private static final OntModelSpec modelSpec = OntModelSpec.OWL_MEM;
	private static final OntModelSpec modelSpec = PelletReasonerFactory.THE_SPEC; // dont need a reasoner..we are just counting sub/super classes 

	private static final String testQtyURI = "http://test.org/testOps.owl#quantity1";
	
	protected static String[] testModelFile = { 
		BASE_TEST_RESOURCE_DIR + "/testBuilder1.rdf",
//		BASE_TEST_RESOURCE_DIR + "/testBuilder2.rdf"
	}; 

	// This rdf:type has a special handler we will check for
	private static String SpecialInstanceURI = "http://test.org/testOps.owl#quantity1";

	private static OntModel[] testModels = new OntModel[testModelFile.length];

	private static boolean isSetup = false;
	private static QuantityBuilder builder = null;

	@Override
	protected void setUp() 
	throws Exception 
	{
		super.setUp();

		if (!isSetup) {

			logger.debug("Setting up tests");

			try {
				OntModel model = createOntModel(BaseOntModelUri);
				builder = new QuantityBuilder(model);
			} catch (Exception e) {
				e.printStackTrace();
				//logger.error(e.getMessage());
				// fail(e.getMessage());
			}
			logger.debug(" created builder:"+builder);

			// create the test model query
			try {
				for (int i = 0; i < testModelFile.length; i++) {
					logger.debug(" Create model:"+testModelFile[i]);
					testModels[i] = createOntModel(new File(testModelFile[i]));
					logger.debug(" finished Create model:"+testModelFile[i]);
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

		logger.debug("CreateOntModel uri:"+ontoUri);
		OntModel queryModel = ModelFactory.createOntologyModel(modelSpec);
		FileManager fm = FileManager.get();
		logger.debug("  file manager:"+fm);
		queryModel.add(fm.loadModel(ontoUri, null, "RDF/XML-ABBREV"));
		
		for (Model m : getImports(queryModel)) {
			queryModel.addSubModel(m);
		}
		return queryModel;
	}

	private static List<Model> getImports (OntModel model) {
		List<Model> ml = new Vector<Model>();
		for (Object uri : model.listImportedOntologyURIs()) {
			logger.debug("IMPORTED URI:"+uri.toString());
			Model m =  FileManager.get().loadModel(uri.toString(),null, "RDF/XML");
			ml.add(m);
			if (m instanceof OntModel)
				ml.addAll(getImports((OntModel)m));
		}
		return ml;
	}
	private static OntModel createOntModel (File ontoFile) 
	throws FileNotFoundException 
	{
		logger.debug("CreateOntMOdel w/ file");
		OntModel ontModel = ModelFactory.createOntologyModel(modelSpec);
		logger.debug(" TRY TO READ ONTOFILE:"+ontoFile);
		ontModel.read(new FileInputStream(ontoFile), null);
		logger.debug(" return MODEL:"+ontModel);
		return ontModel;
	}

	public void test1() {
		logger.info("test builder construction");
		assertNotNull(builder);
	}

	public void test2() {
		logger.info("test builder on various RDF serializations of Q");
		

		for (OntModel model: testModels) {
			
			Individual in = model.getIndividual(testQtyURI);
			assertNotNull("got test individual from RDF model",in);
			
			try {
				
				SemanticObject so = builder.createSemanticObject(in);
				assertNotNull("SO from builder is not null",so);
				
				logger.debug("Resulting SO:\n"+so.toXMLString());
				
			} catch (SemanticObjectBuilderException e) {
				fail (e.getMessage());
			}
		}

	}
}
