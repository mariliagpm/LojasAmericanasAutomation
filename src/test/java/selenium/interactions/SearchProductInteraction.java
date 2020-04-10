package selenium.interactions;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import selenium.SeleniumTestWrapper;
import selenium.pageobjects.SeachProductPage;

public class SearchProductInteraction extends SeachProductPage {

	private final Logger log = Logger.getLogger(SearchProductInteraction.class);
	
	
	public SearchProductInteraction(final WebDriver driver) {
		super(driver);
	}

	
	public void searchProduct(String product) throws Exception {
		
		try { 
			SeleniumTestWrapper.takeScreenshot();
			sendElemet(produtoBusca, product);
			
			waitToElementAndClick(buttonSearchProduct);
			log.info("Product chosen was "+product);
			
			SeleniumTestWrapper.takeScreenshot();
		} catch (Exception e) {
			log.error("It was not possible to search a product");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}
	
	public void chooseProduct(int index) throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();

			log.info("Product index chosen was "+index);
			WebElement element=driver.findElement(By.xpath("//div[@data-tracker=\"productgrid-main\"]//div/div/div/div[@class=\"product-grid-item ColUI-gjy0oc-0 hFbhrr ViewUI-sc-1ijittn-6 iXIDWU\"]["+index+"]//a"));
			String name = element.findElement(By.xpath("//div[@data-tracker=\"productgrid-main\"]//div/div/div/div[@class=\"product-grid-item ColUI-gjy0oc-0 hFbhrr ViewUI-sc-1ijittn-6 iXIDWU\"]["+index+"]//div//div[2]//section//div[2]//h2")).getText();
			System.out.println("Nome do produto =  "+name);
			waitToElementAndClick(element);
			SeleniumTestWrapper.takeScreenshot();
			String nameProductChosen=productName.getText();
			try {
			Assert.assertEquals(name.toLowerCase(), nameProductChosen.toLowerCase());
			log.info("Product chosen is the right one");
			}
			catch(Exception e){
				log.error("The name of the product is not rigth");
				log.error(e.getMessage());
				log.error(e.getStackTrace());
				throw e;
			}
		} catch (Exception e) {
			log.error("It was not possible to choose a product");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}
	
	
	
	public void sendProductToCart() throws Exception {
		try { 
			SeleniumTestWrapper.takeScreenshot();
			Thread.sleep(5000);
			scroll(getElementPositionX(butttonBuy),getElementPositionY(butttonBuy));
			Thread.sleep(5000);
			waitToElementAndClick(butttonBuy);
			SeleniumTestWrapper.takeScreenshot();
		} catch (Exception e) {
			log.error("It was not possible to send the product to the cart");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}

	

}
