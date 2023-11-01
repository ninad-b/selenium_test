package demo.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.abstractComponents.CommonUtility;

public class LandingPage extends CommonUtility{
	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	/*
	WebElement txtUserEmail, txtUserPassword, btnUserLogin;
	{	
		txtUserEmail=driver.findElement(By.id("userEmail"));
		txtUserPassword=driver.findElement(By.id("userPassword"));
		btnUserLogin=driver.findElement(By.id("login"));
		// .sendKeys("ninad@testmail.com"); .sendKeys("Tester@123");
	}*/
	
	//PageFactory
	@FindBy(id="userEmail")
	WebElement txtUserEmail;
	
	@FindBy(id="userPassword")
	WebElement txtUserPassword;
	
	@FindBy(id="login")
	WebElement btnUserLogin;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement toastMessageContainer;
	
	
	public ProductCatalogPage loginToApplication(String useremail, String password) throws InterruptedException
	{
		txtUserEmail.clear();
		txtUserEmail.sendKeys(useremail);
		Thread.sleep(1000);
		txtUserPassword.clear();
		txtUserPassword.sendKeys(password);
		Thread.sleep(1000);
		btnUserLogin.click();
		System.out.println(toastMessageContainer.getText());
		waitForWebElementToBeVisible(toastMessageContainer);
		if(toastMessageContainer.getText().contains("Incorrect email or password"))
			return null;
		else
			return new ProductCatalogPage(driver);
	}
	
	public void navigateToLandingPage(String urlToNavigate)
	{
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(urlToNavigate);
	}
	
}
