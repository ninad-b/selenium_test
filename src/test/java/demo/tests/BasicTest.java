package demo.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import demo.abstractComponents.CommonUtility;
import demo.pageObjects.LandingPage;
import demo.pageObjects.ProductCatalogPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/client");
		String email = "ninad@testmail.com";
		String pass = "Tester@123";
		String productName = "ZARA COAT 3";
		driver.manage().window().maximize();
		
		LandingPage landingPage_r = new LandingPage(driver);
		ProductCatalogPage productCatalogPage_r = new ProductCatalogPage(driver);
		CommonUtility setup_r = new CommonUtility(driver);
		
		landingPage_r.navigateToLandingPage("https://rahulshettyacademy.com/client");
		landingPage_r.loginToApplication(email, pass);
		setup_r.waitForElementsToBeVisible(By.cssSelector("div.col-md-6.mb-3"));

		productCatalogPage_r.addProductToCart(productName);
		
		setup_r.waitForElementsToBeVisible(By.cssSelector("#toast-container"));
		setup_r.waitForElementsToBeInvisible(By.cssSelector(".ng-animating"));
		
		Thread.sleep(2000);
		
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@routerlink,'cart')]")));
		driver.findElement(By.xpath("//button[contains(@routerlink,'cart')]")).click();
		wt.until(ExpectedConditions.urlContains("/dashboard/cart"));
		driver.findElement(By.xpath("//button[contains(text(), 'Checkout')]")).click();
		
		
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("ind");
		
		List<WebElement> countryList = driver.findElements(By.cssSelector("section.ta-results button"));
		
		WebElement countryToBeSelected = countryList.stream().filter(country -> country.findElement(By.cssSelector("span")).getText().equalsIgnoreCase("India")).findFirst().orElse(null);
		countryToBeSelected.click();
		
		driver.findElement(By.cssSelector("a.action__submit")).click();
		String expectedSuccessMessage = "THANKYOU FOR THE ORDER.";
		Thread.sleep(2000);
		String actualSuccessMessage = driver.findElement(By.cssSelector("table tbody tr td h1.hero-primary")).getText();
		Assert.assertTrue(expectedSuccessMessage.equalsIgnoreCase(actualSuccessMessage));
		
		String orderID = driver.findElement(By.cssSelector("label.ng-star-inserted")).getText().split(" ")[1];
		
		driver.findElement(By.cssSelector("label[routerlink*='myorders']")).click();
		
		By tableColumns = By.xpath("//table[@class='table table-bordered table-hover ng-star-inserted']/tbody/tr/th");
		List<WebElement> OrdersList = driver.findElements(tableColumns);
		System.out.println("Thsi is the current order : "+orderID);
		WebElement orderExists = OrdersList.stream().filter(order -> order.getText().equalsIgnoreCase(orderID)).findFirst().orElse(null);
		if(orderExists != null)
			System.out.println("Order exists in the order list");
		else
			System.out.println("Order does not exists");
		//driver.close();
		
		//driver.findElement(By.xpath( "/following-sibling::td/button[@class='btn btn-primary']"));
	}

}
