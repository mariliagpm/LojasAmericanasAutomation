package selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends Pages {
	
	@FindBy(how = How.ID, using = "h_usr-link")
    public WebElement loginLink;
	
	@FindBy(how = How.ID, using = "h_usr-signin")
    public WebElement loginButtonEmail;
	
	@FindBy(how = How.NAME, using = "email")
    public WebElement userEmail;
	
	@FindBy(how = How.NAME, using = "password")
    public WebElement userPassword;
	
	@FindBy(how = How.ID, using = "login-button")
    public WebElement loginButton;
	
	@FindBy(how = How.ID, using = "h_usr-link")
    public WebElement userlink;
	
	@FindBy(how = How.CLASS_NAME, using = "usr-account")
    public WebElement userAccountLink;
	
	
	public LoginPage(final WebDriver driver) {
		super(driver);
	}

	public void open() {
		super.open();
	}

}
