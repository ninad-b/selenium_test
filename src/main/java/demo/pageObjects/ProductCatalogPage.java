package demo.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.abstractComponents.CommonUtility;

public class ProductCatalogPage extends CommonUtility{
	
	WebDriver driver;
	
	public ProductCatalogPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

	@FindBy(css=".card-body button:last-of-type")
	WebElement productToBeAdded;
	
	By productList = By.cssSelector("div.col-md-6.mb-3");
	By loaderAnimation = By.cssSelector(".ng-animating");
	By successToast = By.cssSelector("#toast-container");
	
	
	public List<WebElement> getProductsList()
	{
		waitForElementsToBeVisible(productList);
		return driver.findElements(productList);
	}
	
	public WebElement retrieveProductToBeSelected(String productName)
	{
		return getProductsList().stream()
		.filter(product -> product.findElement(By.cssSelector("b"))
		.getText().equals(productName)).findFirst().orElse(null);
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		waitForElementsToBeVisible(productList);
		WebElement product = retrieveProductToBeSelected(productName);
		product.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		waitForElementToBeVisible(successToast);
		waitForElementsToBeInvisible(loaderAnimation);
	}
}
