package selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SeachProductPage extends Pages {


	
	@FindBy(how = How.ID, using = "h_search-input")
    public WebElement produtoBusca;
	
	
	@FindBy(how = How.ID, using = "h_search-btn")
    public WebElement buttonSearchProduct;
	
	@FindBy(how = How.XPATH , using = "//div[@data-tracker=\"productgrid-main\"]//div/div/div/div[@class=\"product-grid-item ColUI-gjy0oc-0 hFbhrr ViewUI-sc-1ijittn-6 iXIDWU\"][1]//a")
    public WebElement productLink;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"btn-buy\"]/div")
    public WebElement butttonBuy;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"product-name-default\"]")
    public WebElement productName;
	

	
	
	public SeachProductPage(final WebDriver driver) {
		super(driver);
	}

	public void open() {
		super.open();
	}

}
