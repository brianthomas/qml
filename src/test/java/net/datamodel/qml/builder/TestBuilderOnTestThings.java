/**
 * 
 */
package net.datamodel.qml.builder;

/** Testing Q's serialized in testThings ontology.
 * 
 * @author thomas
 *
 */
public class TestBuilderOnTestThings extends BaseBuilderCase {
	
	private static final String BaseOntModelUri = "http://test.org/testThings.owl";
	
	private static String[] testModelFiles = { 
		BASE_TEST_RESOURCE_DIR + "/testBuilder1.rdf",
		BASE_TEST_RESOURCE_DIR + "/testBuilder2.rdf", 
		BASE_TEST_RESOURCE_DIR + "/testBuilder3.rdf", 
	}; 

	@Override
	protected String[] getTestModelFiles() {
		return testModelFiles;
	}

	public void test1() {
		do_test1();
	}
	
	public void test2() {
		do_test2();
	}

	@Override
	protected String getBaseOntModelUri() { return BaseOntModelUri; }
	
}
