package selenium.stepDefinitions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import selenium.SeleniumTestWrapper;
import selenium.interactions.HomeInteraction;
import utils.DateTimeHelper;


public class StepsHomePage {

	HomeInteraction homeInteraction;// Classe responsável
									// pelos steps de
									// entrada na home
									// page do sistema


	
	Logger log = Logger.getLogger(StepsHomePage.class); // Classse de Log
	String browserName = "";
	String pathLogFinal = "";
	
	// HomePage
	@Given("^I open the homepage$") // navigate to homepage
	public void i_open_the_homepage() throws Throwable {
		homeInteraction = PageFactory.initElements(SeleniumTestWrapper.getDriver(), HomeInteraction.class);
		homeInteraction.navigateToHomePage();
	}
	
	@Before("@Chrome")
	public void initiTestingChrome(Scenario s) throws Exception {
		browserName = "Chrome";
		SeleniumTestWrapper.setScenario(s);
		logConfiguration("Chrome");
		log.info("Scenario  " + s.getName() + " has started"); // filling the log
		SeleniumTestWrapper.getDriver(2);
		if(s.getName().length()>38)
		{
			log.info("The name of the scenario is too bigger! It has more than 36 characteres");
			throw new Exception("The name of the scenario is too bigger! It has more than 36 characteres"); 
		}
		log.info("Scenario  " + s.getName() + " is running on Chrome"); // filling the log
		
		
		
	}

	@Before("@Firefox")
	public void initiTestingFirefox(Scenario s) throws Exception {
		browserName = "Firefox";
		SeleniumTestWrapper.setScenario(s);
		logConfiguration("Firefox");
		log.info("Scenario  " + s.getName() + " has started"); // filling the log
		SeleniumTestWrapper.getDriver(1);
		if(s.getName().length()>38)
		{
			log.info("The name of the scenario is too bigger! It has more than 36 characteres");
			throw new Exception("The name of the scenario is too bigger! It has more than 36 characteres"); 
		}
		log.info("Scenario  " + s.getName() + " is running on Firefox"); // filling the log
	
	}

	@After
	public void closeBrowser() throws Exception {
		if (SeleniumTestWrapper.getDriver() != null) {
			// Finalizando o cenário
			Thread.sleep(2000);
			log.info("Finishing " + SeleniumTestWrapper.getScenario().getName() + "");
			String separator = File.separator;
			String[] id= SeleniumTestWrapper.getScenario().getId().split(";");
			String path = new File("target" + separator + "screenshots" + separator + SeleniumTestWrapper.getScenario().getName().replace(" ", "_").replace("-", "_")+"_"+id[3])
					.getAbsolutePath();
			File destDir = new File(path);
			System.out.println("teste  = " +path);
			if (!destDir.exists())
				destDir.mkdir();

			if (SeleniumTestWrapper.getScenario().isFailed()) { // verifica se o cenários não passou
				File destPath = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + SeleniumTestWrapper.getScenario().getName().replace(" ", "_").replace("-", "_")+"_"+id[3]
						+ "_error_" + DateTimeHelper.getCurrentDateTime().replace(" ", "_") + ".jpg");

				FileUtils.copyFile(((TakesScreenshot) SeleniumTestWrapper.getDriver()).getScreenshotAs(OutputType.FILE),
						destPath);
				log.error(SeleniumTestWrapper.getScenario().getName() + " did not passed");
			}
			if (!SeleniumTestWrapper.getScenario().isFailed()) {// cenário passou
				File destPath = new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + SeleniumTestWrapper.getScenario().getName().replace(" ", "_").replace("-", "_")+"_"+id[3]
						+ "_sucess_" + DateTimeHelper.getCurrentDateTime().replace(" ", "_") + ".jpg");

				FileUtils.copyFile(((TakesScreenshot) SeleniumTestWrapper.getDriver()).getScreenshotAs(OutputType.FILE),
						destPath);
  			log.info(SeleniumTestWrapper.getScenario().getName() + " passed");
			}
		
			
			
			if (SeleniumTestWrapper.getDriver() != null) {
				SeleniumTestWrapper.closeDriver();// fechando o browser e driver
				log.info("Browser " + browserName + " was closed");
			}

		}
	}

	public void logConfiguration(String browserName) throws Exception {
		try {
			Thread.sleep(2000);
			String separator = File.separator;
			String nameSc =SeleniumTestWrapper.getScenario().getName().replace(" ", "_");

			String pathLogs = new File("target" + separator + "log" + separator).getAbsolutePath();
			

			File destPathLogs = new File(pathLogs);
			if (!destPathLogs.exists())
				destPathLogs.mkdir();
			String[] id= SeleniumTestWrapper.getScenario().getId().split(";");
			String pathLogsConf = new File("target" + separator + "log" + separator + "confiFiles" + separator)
					.getAbsolutePath();
			File destPathLogsConf = new File(pathLogsConf);
			if (!destPathLogsConf.exists())
				destPathLogsConf.mkdir();

			String path = new File(pathLogsConf + separator + "log4j_" + nameSc+"_"+  id[3]+ "_"+ browserName +"_"+DateTimeHelper.getCurrentDateTime()+".properties").getAbsolutePath();

			File file = new File(path);
			
			Properties properties = new Properties();

			properties.setProperty("log4j.appender.RFILE.File",
					"target" + separator + "log" + separator + nameSc+ "_" + id[3]+ "_"+ browserName +"_"+DateTimeHelper.getCurrentDateTime()+ ".log");

			pathLogFinal = "target" + separator + "log" + separator + nameSc+ "_" + id[3]+"_" + browserName + ".log";
			
			properties.setProperty("log4j.appender.name", "RFILE");
			properties.setProperty("log4j.appender.RFILE", "org.apache.log4j.RollingFileAppender");
			properties.setProperty("log4j.appender.RFILE.threshold", "INFO");
			properties.setProperty("log4j.appender.RFILE.maxFileSize", "25MB");
			properties.setProperty("log4j.appender.RFILE.maxBackupIndex", "100");
			properties.setProperty("log4j.appender.RFILE.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.RFILE.layout.ConversionPattern",
					"%d{yyyy-MM-dd HH:mm:ss} [%-5p] [" + SeleniumTestWrapper.getScenario().getName() + "]["+id[3]+"][%c{1}] - [%M] %m%n");
			properties.setProperty("log4j.rootLogger", "INFO, RFILE,STDOUT");
			properties.setProperty("log4j.appender.name", "STDOUT");
			properties.setProperty("log4j.appender.STDOUT", "org.apache.log4j.ConsoleAppender");
			properties.setProperty("log4j.appender.STDOUT.Target", "System.out");
			properties.setProperty("log4j.appender.STDOUT.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.STDOUT.layout.ConversionPattern",
					"%d{yyyy-MM-dd HH:mm:ss} [%-5p]["+browserName+"][" + SeleniumTestWrapper.getScenario().getName() + "]["+id[3]+"] [%c{1}] - [%M] %m%n");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				properties.store(fos, "FILE LOG4J PROPERTIES:");
				fos.close();
			} catch (IOException ex) {
				log.error(ex.getMessage());
				ex.printStackTrace();
			}

			PropertyConfigurator.configure(path); // configuring the log of the project

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	
}