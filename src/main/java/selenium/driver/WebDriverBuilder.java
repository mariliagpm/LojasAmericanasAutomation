package selenium.driver;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;




public class WebDriverBuilder {

	private String name;
	private final WebDriverConfig webDriverConfig;
	private String userAgent;
	private boolean disableCookies;
	private final static Logger log = Logger.getLogger(WebDriverBuilder.class);
	
	private static String ipServer = "localhost:4444";

	private static WebDriver driver; // webdriver que vai ser usado nos testes

	static DesiredCapabilities capability;
	
	public WebDriverBuilder(WebDriverConfig webDriverConfig) {
		this.webDriverConfig = webDriverConfig;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void disableCookies(boolean cookies) {
		this.disableCookies = cookies;
	}

	public static WebDriver getDriver() throws Exception {
		if(driver==null)
			throw new Exception("Browser was not chosen");
		return driver;
	}

	public static WebDriver toWebDriver(int browser) throws Exception {
		
		
		String separator = File.separator;// Separador de arquivo do SistemaOperacional
		String downloadDirectory = new File(
				"src" + separator + "test" + separator + "resources" + separator + "downloads").getAbsolutePath();
		switch (browser) {
		case 1:// escolhendo o firefox

			try {
				
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("start-maximized"); 
				options.addArguments("enable-automation"); 
				options.addArguments("--no-sandbox"); 
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-browser-side-navigation"); 
				options.addArguments("--disable-gpu"); 
				options.merge(capability);
				capability = DesiredCapabilities.firefox();
				capability.setCapability("marionette", true);
				options.merge(capability);
				driver = new  FirefoxDriver(options); 
				driver.manage().window().maximize();
				log.info("Firefox was chosen");
				return driver;
			} catch (Exception e) {
				log.error("It was not possible to execute Firefox");
				log.error(e.getMessage());
				throw e;
			}

			

		case 2:// escolhendo o chrome
			try {
				
				
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized"); 
				options.addArguments("enable-automation"); 
				options.addArguments("--no-sandbox"); 
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-browser-side-navigation"); 
				options.addArguments("--disable-gpu"); 
				driver = new ChromeDriver(options); 
				driver.manage().window().maximize();
				log.info("Chrome was chosen");
				return driver;
			} catch (Exception e) {
				log.error("It was not possible to execute Chrome");
				log.error(e.getMessage());
				throw e;
			}
		
		default:
			try {
				ChromeOptions optionsC = new ChromeOptions();
				optionsC.addArguments("--start-maximized");
				optionsC.addArguments("use-fake-ui-for-media-stream");
				optionsC.addArguments("--window-size=1920,1080");

				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadDirectory);
				optionsC.setExperimentalOption("prefs", chromePrefs);
				optionsC.merge(capability);

				capability = DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setCapability(ChromeOptions.CAPABILITY, optionsC);
				driver = new RemoteWebDriver(new URL("http://" + ipServer + "/wd/hub"), capability);
				log.info("Chrome was chosen");
				return driver;
			} catch (Exception e) {
				log.error("It was not possible to execute Chrome");
				log.error(e.getMessage());
				throw e;
			}
		}
	}

	public static void setDriver(WebDriver driver) {
		WebDriverBuilder.driver = driver;
	}

	
}
