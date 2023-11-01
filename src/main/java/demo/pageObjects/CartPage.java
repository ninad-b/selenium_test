package demo.pageObjects;

import org.checkerframework.checker.signedness.qual.SignedPositiveFromUnsigned;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.abstractComponents.CommonUtility;

public class CartPage extends CommonUtility
{
	
	WebDriver driver;
	
	@FindBy(xpath="//button[contains(text(), 'Checkout')]")
	WebElement btnChekcout;
	
	@FindBy(css=".btn-danger")
	WebElement btnRemoveItemFromCart;
	
	@FindBy(css="div[class=ng-star-inserted] h1")
	WebElement lblNoProductsText;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public CheckoutPage proceedToCheckout()
	{
		waitForURLToBeLoaded("/dashboard/cart");
		btnChekcout.click();
		return new CheckoutPage(driver);
	}
	
	public boolean verifyRemoveProduct()
	{
		btnRemoveItemFromCart.click();
		waitForWebElementToBeVisible(lblNoProductsText);
		System.out.println(lblNoProductsText.getText());
		return lblNoProductsText.getText().equalsIgnoreCase("No Products in your cart !");
	}
}
