/**
 * 
 */
package net.datamodel.qml.test.xerces2.validate;

import net.datamodel.qml.test.xerces2.BaseXerces2Case;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class TestValidate extends BaseXerces2Case {
	
	private static final Logger logger = Logger.getLogger(TestValidate.class);
	
    public void testValidateSamples () throws Exception {
		
		logger.info("testValidateSamples");
		
		try {
			for (int i = 0; i< samplefiles.length; i++)
			{
				logger.debug("test validate file:"+samplefiles[i]);
				assertTrue ("Document is valid", validateFile(testDirectory+"/"+samplefiles[i]));
			}
			
		} catch (Exception e) {
			fail (e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
    
}
