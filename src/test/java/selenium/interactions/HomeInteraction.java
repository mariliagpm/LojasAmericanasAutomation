package selenium.interactions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import selenium.pageobjects.HomePage;

public class HomeInteraction extends HomePage {

	private final Logger log = Logger.getLogger(HomeInteraction.class);
	private Scenario sc;
	
	public HomeInteraction(final WebDriver driver) {
		super(driver);
	}

	
	public void navigateToHomePage() throws Exception {
		try { 
			goToUrl(getTestData("homePage"));//redirecionando pra homepage
			log.info("User is in the Home Page");
		} catch (Exception e) {
			log.error("It was not possible to redirect to HomePage");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}
	
	
	
	
	

	public Scenario getSc() {
		return sc;
	}


	public void setSc(Scenario sc) {
		this.sc = sc;
	}


	

}
