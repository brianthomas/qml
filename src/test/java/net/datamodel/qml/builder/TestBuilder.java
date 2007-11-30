
package net.datamodel.qml.builder;

import org.apache.log4j.Logger;

/**
 * @author thomas
 *
 */
public class TestBuilder extends BaseCase {
	
	// our trusty logger
	private static final Logger logger = Logger.getLogger(TestBuilder.class);

	public void test1() {
//		logger.info("test builder construction");
		logger.warn(" NEED TO IMPLEMENT TEST for QuantityBuilder");
		QuantityBuilder qb = new QuantityBuilder(null);
		assertNotNull(qb);
	}
	
}
