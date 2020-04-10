package selenium.stepDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.java.en.And;
import selenium.SeleniumTestWrapper;
import selenium.interactions.SearchProductInteraction;


public class StepsSearchProduct {

	Logger log = Logger.getLogger(StepsSearchProduct.class); // Classse de Log
	String browserName = "";
	SearchProductInteraction searchProductInteraction;// Classe responsável
									// pelos steps de
									// entrada na home
									// page do sistema

	String pathLogFinal = "";

	@And("^I search for \"([^\"]*)\"$")
	public void i_search_for(String product) throws Throwable {
		searchProductInteraction = PageFactory.initElements(SeleniumTestWrapper.getDriver(), SearchProductInteraction.class);

	  searchProductInteraction.searchProduct(product);
	}
	
	@And("^I chose a product to buy with the index (\\d+)$")
	public void i_chose_a_product_to_buy_with_the_index(int index) throws Throwable {
		 searchProductInteraction.chooseProduct(index);
	}
	
	@And("^I send it to my cart$")
	public void i_send_it_to_my_cart() throws Throwable {
		
		 searchProductInteraction.sendProductToCart();
	}

	

	
	
}