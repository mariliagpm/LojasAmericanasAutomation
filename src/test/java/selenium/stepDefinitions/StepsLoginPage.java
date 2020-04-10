package selenium.stepDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import selenium.SeleniumTestWrapper;
import selenium.interactions.LoginInteraction;


public class StepsLoginPage {

	Logger log = Logger.getLogger(StepsLoginPage.class); // Classse de Log
	String browserName = "";
	LoginInteraction  loginInteraction;// Classe responsável
									// pelos steps de
									// entrada na home
									// page do sistema
	
	

	String pathLogFinal = "";

	@And("^I click in the login button$")
	public void i_click_in_the_login_button() throws Throwable {
		loginInteraction = PageFactory.initElements(SeleniumTestWrapper.getDriver(), LoginInteraction.class);
		loginInteraction.clickLoginPage();
	}
	
	@And("^I use my email as \"([^\"]*)\" to login$")
	public void i_use_my_email_as_to_login(String email) throws Throwable {
	    loginInteraction.sendEmail(email);
	}

	@And("^I use my password as \"([^\"]*)\" to login$")
	public void i_use_my_password_as_to_login(String password) throws Throwable {
		   loginInteraction.sendPassword(password);
	}

	@And("^I verify if I am logged$")
	public void i_verify_if_I_am_logged() throws Throwable {
	   loginInteraction.verifyUserLogged();
	}

	@Then("^I click the login button$")
	public void i_click_the_login_button() throws Throwable {
	   loginInteraction.clickLoginButton();
	}
	
	
	@Then("^I verify that I am not logged$")
	public void i_verify_that_I_am_not_logged() throws Throwable {
	    loginInteraction.verifyUserNotLogged();
	}
	

	
}