/**
 * 
 */
package net.datamodel.qml.builder;


/** Test the Qbuilder on some astronomy data which uses it.
 * 
 * @author thomas
 *
 */
public class TestBuilderOnAstro 
extends BaseBuilderCase 
{
	
	private static final String BaseOntModelUri = "http://archive.astro.umd.edu/ont/Astronomy.owl";
	
	private static String[] testModelFiles = { 
		BASE_TEST_RESOURCE_DIR + "/cat_7145_t1_qonly.rdf",
	}; 

	@Override
	protected String[] getTestModelFiles() {
		return testModelFiles;
	}

	/*
	public void test1() { do_test1(); }
	
	public void test2() { do_test2(); }
	*/
	
	public void test3() {
		build_test("http://archive.astro.umd.edu/ont/Astronomy.owl#SpiralGalaxy");
	}
	
	@Override
	protected String getBaseOntModelUri() { return BaseOntModelUri; }
	
}
