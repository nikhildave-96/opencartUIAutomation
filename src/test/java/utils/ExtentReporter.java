package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ExtentReporter class helps create extent report post execution of the
 * testcases.
 * 
 * @author nikdav
 *
 */
public class ExtentReporter {
	public static ExtentSparkReporter reporter;
	public static ExtentTest extentTest;
	public static ExtentReports extentReport = new ExtentReports();
	private static final Logger logger = LogManager.getLogger(ExtentReporter.class);

	/**
	 * Creating extent test report and configuring report related attributes.
	 * 
	 * @author nikdav
	 */
	public static void generateReport() {
		String reportPath = System.getProperty("user.dir") + "\\test-output\\extent\\" + "extentReport.html";
		reporter = new ExtentSparkReporter(reportPath);
		logger.info("Extent report path: '" + reportPath + "' ...");

		reporter.config().setDocumentTitle("Opencart UI Automation Test Execution Report");
		reporter.config().setReportName("Opencart Test Report");
//		reporter.config().setTheme(Theme.DARK);

		extentReport.attachReporter(reporter);
		extentReport.setSystemInfo("ProjectName", "Opencart Demo");
		extentReport.setSystemInfo("Tester", "Nikhil Dave");
	}

	/**
	 * Captures testcase execution status inside the extent test report.
	 * 
	 * @param result Object of TestNG's ITestResult interface
	 */
	public static void addTestStatus(ITestResult result) {
		String failedTestssPath = System.getProperty("user.dir") + "\\screenshots\\" + result.getName() + ".png";
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - testcase FAILED", ExtentColor.RED));
			extentTest.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(failedTestssPath).build());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - testcase PASSED", ExtentColor.GREEN));
		} else {
			extentTest.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - testcase SKIPPED", ExtentColor.ORANGE));
		}
	}
}
