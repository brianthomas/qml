/**
 * 
 */
package net.datamodel.qml.dom.xerces2;

import net.datamodel.qml.BaseCase;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class BaseXerces2Case 
extends BaseCase 
{
	
	protected static final String docClassName = "net.datamodel.qml.dom.xerces2.QMLDocumentImpl";
	protected static boolean isSetUp = false;
			
	private static final Logger logger = Logger.getLogger(BaseXerces2Case.class);
	
	/**
	 */
	@Override
	public void setUp() {
		logger.debug(" baseXerces2Case setup");
		super.setUp();
		
		if(!isSetUp) {
			try {
				docClass = Class.forName(docClassName); 
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			}
			SaxParserName = "org.apache.xerces.parsers.SAXParser";
			isSetUp = false;
		}
	}
	
}
