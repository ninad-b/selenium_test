package demo.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.abstractComponents.CommonUtility;

public class CheckoutPage extends CommonUtility{
	
	WebDriver driver;
	
	
	@FindBy(xpath="//button[contains(text(), 'Checkout')]")
	WebElement btnChekcout;
	
	@FindBy(css="input[placeholder='Select Country']")
	WebElement txtCountry;
	
	@FindBy(css="section.ta-results button")
	List<WebElement> lstCountry;
	
	@FindBy(css="a.action__submit")
	WebElement btnSubmitOrder;
	
	@FindBy(css="table tbody tr td h1.hero-primary")
	WebElement lblActualMessage;
	
	@FindBy(css="label.ng-star-inserted")
	WebElement orderData;
	
	@FindBy(css="label[routerlink*='myorders']")
	WebElement linkOrderHistory;
	
	public CheckoutPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void selectCountry(String countryToBeSelected)
	{
		txtCountry.sendKeys("ind");
		lstCountry.stream().filter(country -> country
				.findElement(By.cssSelector("span")).getText().equalsIgnoreCase(countryToBeSelected))
				.findFirst().orElse(null).click();
	}
	
	public OrderHistoryPage submitOrder()
	{
		btnSubmitOrder.click();
		return new OrderHistoryPage(driver);
	}
	
	public boolean verifyActualMessage(String expectedMessage)
	{
		return lblActualMessage.getText().equals(expectedMessage);
	}
	
	public String getOrderID() throws InterruptedException
	{
		//System.out.println(orderData);
		//System.out.println(orderData.getText().split(" ")[1]);
		return orderData.getText().split(" ")[1];
	}
	
	public String navigateToOrderHistory() throws InterruptedException
	{
		String orderID_v = getOrderID();
		linkOrderHistory.click();
		return orderID_v;
	}
}
