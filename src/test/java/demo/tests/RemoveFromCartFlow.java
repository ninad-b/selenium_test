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

import demo.Components.DemoRetryListener;
import demo.Components.InitTests;
import demo.abstractComponents.CommonUtility;
import demo.pageObjects.CartPage;
import demo.pageObjects.CheckoutPage;
import demo.pageObjects.LandingPage;
import demo.pageObjects.OrderHistoryPage;
import demo.pageObjects.ProductCatalogPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class RemoveFromCartFlow extends InitTests{
	
	
	String email, pass, productName = "ZARA COAT 3";
	
	@Test(retryAnalyzer=DemoRetryListener.class)
	public void loginTest() throws IOException, InterruptedException
	{
		// TODO Auto-generated method stub
		email = "ninad@testmail.com";
		pass = "Tester@123";
		productCatalogPage_r = landingPage_r.loginToApplication(email, pass);
		Assert.assertNotNull("Login failed as login returned Null ProductCatalog Object", productCatalogPage_r);
	}
	
	@Test(dependsOnMethods="loginTest")
	public void addProductToCartTest() throws InterruptedException
	{	
		productCatalogPage_r.addProductToCart(productName);
		cartPage_r = productCatalogPage_r.navigateToCartPage();
	}
	
	@Test(dependsOnMethods="addProductToCartTest")
	public void removeProductFromCartTest() throws InterruptedException
	{
		Assert.assertTrue(cartPage_r.verifyRemoveProduct());
	}

}
