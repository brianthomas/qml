/**
 * 
 */
package net.datamodel.qml.dom.xerces2.validate;

import net.datamodel.qml.dom.xerces2.BaseXerces2Case;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class DisabledTestValidate extends BaseXerces2Case {
	
	private static final Logger logger = Logger.getLogger(DisabledTestValidate.class);
	
	// TODO
    public void testValidateSamples () throws Exception {
		
		logger.warn("testValidateSamples - TODO");
		assertTrue(true);
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
