package runners;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/functionalTests/",tags="@compra", glue = "", plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/Reports/report.html" }, monochrome = true)
public class TestRunner {

	@AfterClass
	public static void writeExtentReport() {
		String separator = File.separator;
		String pathReport = new File("src" + separator + "test" + separator + "resources" + separator
				+ "configfiles" + separator + "extent-config.xml").getAbsolutePath();
		File destDir = new File(pathReport);
		Reporter.loadXMLConfig(destDir);
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("System", System.getProperty("os.name"));
		Reporter.assignAuthor("ToolsQA "+ "  - Marilia Matos");
	}

}
