package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import utils.PropertiesReader;

public class TestListener implements IRetryAnalyzer {
	private static Logger logger = LogManager.getLogger(TestListener.class);
	static int retries = Integer.parseInt(PropertiesReader.readKey("retries"));
	int retryCount;

//	test case is retried specified number of times and only then gets marked as failed if does not run clean after completing all the attempts
	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < retries) {
			logger.info("retrying failed test case '" + result.getName() + "' " + (retryCount + 1) + " time ...");
			retryCount++;
			return true;
		}
		return false;
	}
}
