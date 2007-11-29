/**
 * 
 */
package net.datamodel.qml.dom.xerces2.load;

import net.datamodel.qml.dom.xerces2.BaseXerces2Case;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class TestLoad extends BaseXerces2Case 
{
	
	private static final Logger logger = Logger.getLogger(TestLoad.class);
	
	// Attempt to simply load all of the test samples in the samples directory
	// TODO
	public void testLoadSamples () throws Exception {
		
		logger.error("testLoadSamples - TODO");
		assertTrue(true);
		
	/*
		logger.info("testLoadSamples");
		try {
			for (int i = 0; i< samplefiles.length; i++)
			{
				QMLDocument doc = loadFile(testDirectory+"/"+samplefiles[i]);
				assertNotNull("Document reference exists",doc);
			}
		} catch (Exception e) {
			fail (e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	*/
	}
	
	// A test of being able to read, and write the sample.
	// We don't compare the 2 file contents because there may have been
	// some dropped XML comments from the initial parse (which is OK). 
	// TODO
	public void testLoadAndWriteSamples () throws Exception {
		
		logger.warn("testLoadAndWriteSamples - TODO");
		assertTrue(true);
		
	/*
		logger.info("testLoadAndWriteSamples");
		// set the output specification
	    Specification.getInstance().setPrettyOutput(true);
	    Specification.getInstance().setPrettyOutputIndentation("  ");
		
		try {
			for (int i = 0; i< samplefiles.length; i++)
			{
				File outputfile = new File(testDirectory+"/tmp.xml");
				QMLDocument doc = loadFile(testDirectory+"/"+samplefiles[i]);
				
				logger.debug("Attempting to load/write file : ["+samplefiles[i]+"]");
				
				assertNotNull("Document reference exists", doc);

			    Writer myWriter = new BufferedWriter(new FileWriter(outputfile));
			    doc.toXMLWriter(myWriter);
			    // myWriter.flush();
			    myWriter.close();
			     
				assertTrue("can write file", outputfile.canWrite());
				assertTrue( "File has non-zero extent", outputfile.length() > 0);
				assertTrue ("Output document is valid", validateFile (testDirectory+"/tmp.xml"));
				
				// clean up		
				
				outputfile.delete();
			}
		} catch (Exception e) {
			fail (e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	*/
	}
	
	// Test our ability to get consistent results from loading and reloading
	// files. We compare between the products of the first and second loadings.
	// TODO
	public void testMulitLoadAndWriteSamples () throws Exception {
		
		logger.warn("testMultiLoadAndWriteSamples - TODO");
		assertTrue(true);
		/*
		logger.info("testMultiLoadAndWriteSamples");
		
		// set the output specification
		Specification.getInstance().setPrettyOutput(true);
		Specification.getInstance().setPrettyOutputIndentation("  ");
		
		try {
			for (int i = 0; i< samplefiles.length; i++)
			{
				File outputfile = new File(testDirectory+"/tmp.xml");
				QMLDocument doc = loadFile(testDirectory+"/"+samplefiles[i]);
				String firstload = "";
				String secondload = "";
				
				logger.debug("Attempting to multi load/write file : ["+samplefiles[i]+"]");
				
				assertNotNull("initial document reference exists", doc);
				
			    Writer myWriter = new BufferedWriter(new FileWriter(outputfile));
			    StringWriter stringWriter1 = new StringWriter();
			    Writer bsw1 = new BufferedWriter(stringWriter1);
			    doc.toXMLWriter(myWriter);
			    doc.toXMLWriter(bsw1);
			    // myWriter.flush();
			    myWriter.close();
			     
				assertTrue("can write initial file", outputfile.canWrite());
				assertTrue( "initial file has non-zero extent", outputfile.length() > 0);
				
				// now try to read the temp file
				doc = loadFile(testDirectory+"/tmp.xml");
				
				assertNotNull("second document reference exists", doc);
				
			    StringWriter stringWriter2 = new StringWriter();
			    Writer bsw2 = new BufferedWriter(stringWriter2);
			    doc.toXMLWriter(bsw2);
			    
			    stringWriter1.flush();
			    stringWriter2.flush();
			    
			    // logger.debug( "first string:"+stringWriter1.getBuffer().toString());
			    // logger.debug( "second string:"+stringWriter2.getBuffer().toString());
			    
				assertEquals("Document content is the same", 
						stringWriter1.getBuffer().toString(), stringWriter2.getBuffer().toString());
				
				// clean up
				outputfile.delete();
			}
		} catch (Exception e) {
			fail (e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	*/
	}
	
}