package demo.pageObjects;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage {
	
	
	WebDriver driver;
	
	//@FindBy(css="tr.ng-star-inserted th")
	//WebElement orderHistoryData;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover ng-star-inserted']/tbody/tr/th")
	List<WebElement> tableOrderDataColumns;
	
	public OrderHistoryPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement checkOrderExists(String orderID) throws InterruptedException
	{ 
		//String localOrderID = check.getOrderID();
		System.out.println(orderID);
		return tableOrderDataColumns.stream().filter(order -> order.getText().equalsIgnoreCase(orderID)).findFirst().orElse(null);
	}
	

}
