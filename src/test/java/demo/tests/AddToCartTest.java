package demo.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import demo.Components.InitTests;
import demo.abstractComponents.CommonUtility;
import demo.pageObjects.CartPage;
import demo.pageObjects.CheckoutPage;
import demo.pageObjects.LandingPage;
import demo.pageObjects.OrderHistoryPage;
import demo.pageObjects.ProductCatalogPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class AddToCartTest extends InitTests{
	
	
	String email, pass, productName = "ZARA COAT 3";
	
	@Test(priority=2)
	public void loginTest() throws IOException, InterruptedException
	{
		// TODO Auto-generated method stub
		email = "ninad@testmail.com";
		pass = "Tester@123";
		productCatalogPage_r = landingPage_r.loginToApplication(email, pass);
		Assert.assertNotNull("Login failed as login returned Null ProductCatalog Object", productCatalogPage_r);
	}
	
	@Test(dependsOnMethods="loginTest", priority=3)
	public void addProductToCartTest() throws InterruptedException
	{
		//email = "ninad@testmail.com";
		//pass = "Tester@123";
		//productCatalogPage_r = landingPage_r.loginToApplication(email, pass);	
		productCatalogPage_r.addProductToCart(productName);
		CartPage cartPage_r = productCatalogPage_r.navigateToCartPage();
		CheckoutPage checkoutPage_r = cartPage_r.proceedToCheckout();
		checkoutPage_r.selectCountry("India");
		OrderHistoryPage orderHistoryPage_r = checkoutPage_r.submitOrder();
		String orderID_v = checkoutPage_r.getOrderID();
		Assert.assertTrue(checkoutPage_r.verifyActualMessage("THANKYOU FOR THE ORDER."));
		checkoutPage_r.navigateToOrderHistory();
		Assert.assertNotNull(orderHistoryPage_r.checkOrderExists(orderID_v));
	}
	
	@Test(priority=1)
	public void invalidLoginTest() throws InterruptedException
	{
		email = "testninad@testmail.com";
		pass = "Tester@123";
		productCatalogPage_r = landingPage_r.loginToApplication(email, pass);
		System.out.println("AddToCart : CatalogObject "+productCatalogPage_r);
		Assert.assertNull("AddTestToCartTest : Test Failed as ProductCatalog Object returned by login in Not Null", productCatalogPage_r);
	}

}
