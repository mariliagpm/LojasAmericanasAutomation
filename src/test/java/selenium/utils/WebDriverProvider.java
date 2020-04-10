package selenium.utils;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

import selenium.driver.WebDriverBuilder;
import selenium.driver.WebDriverConfig;

public class WebDriverProvider extends TestWatcher {
	private final WebDriverBuilder webDriverBuilder;
	private static WebDriver driver; // driver do browser
	private final static Logger log = Logger.getLogger(WebDriverProvider.class);
	
	public WebDriverProvider(final WebDriverConfig webDriverConfig) {
		this.webDriverBuilder = new WebDriverBuilder(webDriverConfig);
	}

	public WebDriver getDriver(int browser) throws Exception {
		if (driver == null) {// verifica se esta nulo
			driver = WebDriverBuilder.toWebDriver(browser); // construindo o driver
		}
		return driver;
	}

	public static WebDriver getDriver() throws Exception {
		return WebDriverBuilder.getDriver();
	}

	public void disableCookies(boolean cookies) {
		webDriverBuilder.disableCookies(cookies);
	}

	public boolean existsDriver() {
		return driver != null;
	}

	@Override
	protected void starting(final Description description) {
		String methodName = description.getClassName() + "." + description.getMethodName();
		this.webDriverBuilder.setName(methodName);
	}

	public static void finished() throws Exception {
		try {
			if (driver != null) {
				driver.close();
				driver = null;
			}
		} catch (Exception e) {
			log.error("Error when it tried to close the browser");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}
}