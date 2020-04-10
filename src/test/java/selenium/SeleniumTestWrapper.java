package selenium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import selenium.driver.WebDriverConfig;
import selenium.utils.WebDriverProvider;
import utils.DateTimeHelper;

public class SeleniumTestWrapper {

	// Config
	private static WebDriverConfig webDriverConfig = new WebDriverConfig();
	private static WebDriverProvider webDriverProvider = new WebDriverProvider(webDriverConfig);
	static int browserNumber;
	static Logger log = Logger.getLogger(SeleniumTestWrapper.class); // Classse de Log
	private static Scenario sc;
	
	public static WebDriver getDriver(int browser) throws Exception {
		try {
			return webDriverProvider.getDriver(browser);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static WebDriver getDriver() throws Exception {
		return WebDriverProvider.getDriver();
	}
	
	public static Scenario getScenario() throws Exception {
		return sc;
	}
	public static void setScenario(Scenario s) throws Exception {
		sc=s;
	}

	public static void closeDriver() throws Exception {
		WebDriverProvider.finished();
	}

	public WebDriverProvider getWebDriverProvider() {
		return webDriverProvider;
	}

	public static void takeScreenshot() throws WebDriverException, IOException {
		try {
			log.info("Taking a screenshot");
			// tirando screenshot do step
			String separator = File.separator;
			String[] id= sc.getId().split(";");
			String path = new File("target" + separator + "screenshots" + separator + sc.getName().replace(" ", "_").replace("-", "_") +"_"+id[3] + separator)
					.getAbsolutePath();
			File destDir = new File(path);
			if (!destDir.exists())
				destDir.mkdir();
			File destPath = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + sc.getName().replace(" ", "_").replace("-", "_")+"_"+id[3] 
					+ DateTimeHelper.getCurrentDateTime().replace(" ", "_") + ".jpg");
			FileUtils.copyFile(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE), destPath);
			log.info("A screenshot was taken");
			String pathReport = new File("src" + separator + "test" + separator + "resources"
					+ separator + "configfiles" + separator + "extent-config.xml").getAbsolutePath();
			File reportFile = new File(pathReport);
			Reporter.loadXMLConfig(reportFile);
			// This attach the specified screenshot to the test
			Reporter.addScreenCaptureFromPath(destPath.getAbsolutePath());
	
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Error when it tried to take an screenshots");
		}
	}

}
