package demo.abstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.pageObjects.CartPage;

public class CommonUtility {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(xpath="//button[contains(@routerlink,'cart')]")
	WebElement btnViewCart;
	
	public CommonUtility(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
	}
	
	public void waitForElementsToBeVisible(By locator)
	{	
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void waitForWebElementToBeVisible(WebElement element)
	{	
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForURLToBeLoaded(String url)
	{	
		wait.until(ExpectedConditions.urlContains(url));
	}
	
	public void waitForElementToBeVisible(By locator)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForElementsToBeInvisible(By locator)
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public void waitForElementsToBeClickable(WebElement webElement)
	{
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}
	
	public CartPage navigateToCartPage()
	{
		waitForElementsToBeClickable(btnViewCart);
		btnViewCart.click();
		return new CartPage(driver);
	}
	
}
