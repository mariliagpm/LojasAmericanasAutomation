package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import selenium.configurations.TypedProperties;

public abstract class SeleniumFunctions {

	// reescrevendo algumas funções do selenium

	private final Logger log = Logger.getLogger(SeleniumFunctions.class);
	protected WebDriver driver;

	public SeleniumFunctions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private static final int ELEMENT_WAIT_TIMEOUT_IN_SECONDS = 80;

	private static final int PAGE_WAIT_TIMEOUT_IN_SECONDS = 80;

	TypedProperties testData = new TypedProperties("/configfiles/test_data.properties");

	protected String getTestData(String key) {
		return testData.getValue(key);
	}

	protected boolean isElementPresent(final By by) {
		return this.driver.findElements(by).size() > 0;
	}

	protected boolean isElementPresent(final WebElement element) {
		try {
			element.getTagName();
		} catch (final NoSuchElementException ignored) {
			return false;
		} catch (final StaleElementReferenceException ignored) {
			return false;
		}
		return true;
	}

	protected boolean isElementVisible(final By by) {
		return this.driver.findElement(by).isDisplayed();
	}

	protected boolean isElementVisible(final WebElement element) {
		return element.isDisplayed();
	}

	protected boolean isAnyTextPresent(final By by) {
		final String text = this.driver.findElement(by).getText();
		return StringUtils.isNotBlank(text);
	}

	protected boolean isAnyTextPresent(final WebElement element) {
		final String text = element.getText();
		return StringUtils.isNotBlank(text);
	}

	protected void waitForElement(final WebElement element) {
		this.waitForElement(element, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
	}

	protected void waitForElement(final WebElement element, final int timeoutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitForElement(final By by) {
		waitForElement(by, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
	}

	protected void waitForElement(final By by, final int timeoutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	protected void waitForElementIsInvisible(final By by) {
		final WebDriverWait wait = new WebDriverWait(this.driver, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	protected void waitForTextToAppear(final String textToAppear, final WebElement element) {
		final WebDriverWait wait = new WebDriverWait(this.driver, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
	}

	protected void waitForUrlPart(final String urlPart) {
		final WebDriverWait wait = new WebDriverWait(this.driver, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.urlContains(urlPart));
	}

	protected void waitForUrlToBe(final String url) {
		final WebDriverWait wait = new WebDriverWait(this.driver, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitForPageLoad() {
		waitForPageLoad(PAGE_WAIT_TIMEOUT_IN_SECONDS);
	}

	public void waitForPageLoad(final int timeoutInSeconds) {
		waitForElement(By.tagName("html"), timeoutInSeconds);
	}

	protected String xpathFinder(final String... xpathList) {
		for (final String xpath : xpathList) {
			if (isElementPresent(By.xpath(xpath))) {
				return xpath;
			}
		}
		return null;
	}

	protected void mouseover(final By by) {
		final WebElement element = this.driver.findElement(by);
		final Actions builder = new Actions(this.driver);
		final Action mouseover = builder.moveToElement(element).build();
		mouseover.perform();
	}

	protected void mouseover(final WebElement element) {
		final Actions builder = new Actions(this.driver);
		final Action mouseover = builder.moveToElement(element).build();
		mouseover.perform();
	}

	protected void moveToElement(final By by) {
		mouseover(by);
	}

	protected void moveToElement(final WebElement element) {
		mouseover(element);
	}

	protected void waitAndMoveToElement(final WebElement element) {
		waitForElement(element);
		moveToElement(element);
	}

	protected void waitAndMoveToElement(final By by) {
		waitForElement(by);
		moveToElement(by);
	}

	protected void dragAndDrop(final By by, final int xOffset, final int yOffset) {
		final WebElement ele = this.driver.findElement(by);
		final Actions builder = new Actions(this.driver);
		final Action dragAndDrop = builder.clickAndHold(ele).moveByOffset(xOffset, yOffset).release().build();
		dragAndDrop.perform();
	}

	protected void dragAndDrop(final WebElement element, final int xOffset, final int yOffset) {
		final Actions builder = new Actions(this.driver);
		final Action dragAndDrop = builder.clickAndHold(element).moveByOffset(xOffset, yOffset).release().build();
		dragAndDrop.perform();
	}

	public void switchWindow(final String url) {
		String currentHandle = null;
		final Set<String> handles = this.driver.getWindowHandles();
		if (handles.size() > 1) {
			currentHandle = this.driver.getWindowHandle();
		}
		if (currentHandle != null) {
			for (final String handle : handles) {
				this.driver.switchTo().window(handle);
				if (this.driver.getCurrentUrl().contains(url) && !currentHandle.equals(handle)) {
					break;
				}
			}
		} else {
			for (final String handle : handles) {
				this.driver.switchTo().window(handle);
				if (this.driver.getCurrentUrl().contains(url)) {
					break;
				}
			}
		}
	}

	protected boolean isReadonly(final By by) {
		return Boolean.parseBoolean(this.driver.findElement(by).getAttribute("readonly"));
	}

	protected boolean isReadonly(final WebElement element) {
		return Boolean.parseBoolean(element.getAttribute("readonly"));
	}

	protected Point getElementPosition(final WebElement element) {
		return element.getLocation();
	}

	protected int getElementPositionX(final WebElement element) {
		final Point pos = getElementPosition(element);
		return pos.getX();
	}

	protected int getElementPositionY(final WebElement element) {
		final Point pos = getElementPosition(element);
		return pos.getY();
	}

	protected void backSpaceInputClear(final WebElement element) {
		final int numberOfCharacters = element.getAttribute("value").length();
		for (int i = 0; i <= numberOfCharacters; i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	protected void backSpaceInputClear(final WebElement element, final int numberOfCharacters) {
		for (int i = 0; i <= numberOfCharacters; i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public void scroll(final int x, final int y) {
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		for (int i = 0; i <= x; i = i + 50) {
			js.executeScript("scroll(" + i + ",0)");
		}
		for (int j = 0; j <= y; j = j + 50) {
			js.executeScript("scroll(0," + j + ")");
		}
	}

	protected JavascriptExecutor highlightElementPermanently(final WebElement element) {
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		return js;
	}

	protected void highlightElement(final WebElement element) {
		final String originalStyle = element.getAttribute("style");
		final JavascriptExecutor js = highlightElementPermanently(element);
		js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
	}

	public void zoomPlus() {
		Actions actions = new Actions(this.driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.ADD).perform();
		actions = new Actions(this.driver);
		actions.keyUp(Keys.CONTROL).perform();
	}

	public void zoomMinus() {
		Actions actions = new Actions(this.driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.SUBTRACT).perform();
		actions = new Actions(this.driver);
		actions.keyUp(Keys.CONTROL).perform();
	}

	public void takeScreenshot(final String path) {
		final File scrFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isAlertPresent() {
		try {
			final Alert alert = this.driver.switchTo().alert();
			alert.getText();
		} catch (final NoAlertPresentException nape) {
			return false;
		}
		return true;
	}

	public String getAlertText() {
		final Alert alert = this.driver.switchTo().alert();
		return alert.getText();
	}

	public void acceptAlert() {
		final Alert alert = this.driver.switchTo().alert();
		alert.accept();
	}

	public void dismissAlert() {
		final Alert alert = this.driver.switchTo().alert();
		alert.dismiss();
	}

	public void sendElemet(final WebElement element, String keysToSend) throws Exception {
		try {
			waitForElement(element, 10);
			element.clear();
			element.sendKeys(keysToSend);
		} catch (Exception e) {
			log.error("Error when it tried to fill an element");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}

	protected void waitToElementAndClick(final WebElement element) throws Exception {
		try {
			waitForElement(element);
			final WebDriverWait wait = new WebDriverWait(this.driver, 3000);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();

		} catch (Exception e) {
			log.error("Error when it tried to click an element");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			throw e;
		}
	}

	protected String waitToElementAndGetText(final WebElement element) throws InterruptedException {
		boolean staleElement = true;
		while (staleElement) {
			try {
				element.getText();
				staleElement = false;

			} catch (StaleElementReferenceException e) {
				staleElement = true;
			}
		}
		return element.getText();
	}

	protected void submitElement(final WebElement element) throws InterruptedException {
		waitForElement(element);
		element.submit();
	}

	protected void goToUrl(final String url) {
		driver.get(url);
	}

	protected void verifyUrl(final String url) throws InterruptedException {
		assertEquals(url, driver.getCurrentUrl());
	}

	protected boolean verifyTextIsPresent(final String wordPresent) throws InterruptedException {
		Thread.sleep(3000);
		try {
			assertTrue(driver.getPageSource().contains(wordPresent));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
