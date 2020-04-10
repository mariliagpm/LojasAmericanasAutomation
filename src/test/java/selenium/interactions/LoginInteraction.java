package selenium.interactions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import selenium.SeleniumTestWrapper;
import selenium.pageobjects.LoginPage;

public class LoginInteraction extends LoginPage{

	private final Logger log = Logger.getLogger(LoginInteraction.class);
	
	
	public LoginInteraction(final WebDriver driver) {
		super(driver);
	}

	
	public void clickLoginPage() throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();
			waitToElementAndClick(loginLink);
			SeleniumTestWrapper.takeScreenshot();
			waitToElementAndClick(loginButtonEmail);
			log.info("User is in the Home Page");
			SeleniumTestWrapper.takeScreenshot();
		} catch (Exception e) {
			log.error("It was not possible to redirect to HomePage");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}
	
	
	

	public void sendEmail(String email) throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();
			sendElemet(userEmail, email);
			SeleniumTestWrapper.takeScreenshot();
			log.info("User send the email");
		} catch (Exception e) {
			log.error("It was not possible to redirect to HomePage");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
		
	}
	public void sendPassword(String password) throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();
			sendElemet(userPassword, password);
			waitToElementAndClick(loginButton);
			log.info("User send the password");
			SeleniumTestWrapper.takeScreenshot();
		} catch (Exception e) {
			log.error("It was not possible to redirect to HomePage");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
		
	}


	public void verifyUserLogged() throws Exception {
		try {
			SeleniumTestWrapper.takeScreenshot();
			waitForElement(userlink);
			Assert.assertTrue(isElementPresent(userlink));
			Assert.assertTrue(isElementPresent(userAccountLink));
			log.info("User is logged");
			SeleniumTestWrapper.takeScreenshot();
		}
		catch (Exception e) {
			log.error("User is not logged");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}


	public void clickLoginButton() throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();
			waitToElementAndClick(loginButton);
			log.info("User clicked loggin button");
			SeleniumTestWrapper.takeScreenshot();
		} catch (Exception e) {
			log.error("It was not possible to redirect to HomePage");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
		
	}


	public void verifyUserNotLogged() throws Exception {
		try {
		verifyTextIsPresent("E-mail ou senha incorretos");
		log.info("User is not logged");
		SeleniumTestWrapper.takeScreenshot();
		}
		catch (Exception e) {
			log.error("It was not possible to Login");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
		
	}

}
