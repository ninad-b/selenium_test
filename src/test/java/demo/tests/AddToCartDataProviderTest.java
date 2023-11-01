package demo.tests;

import org.testng.annotations.DataProvider;
import java.lang.reflect.*;
import org.testng.annotations.Test;
import java.util.*;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.awt.List;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

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

public class AddToCartDataProviderTest extends InitTests{
	
	
	//String email, pass, productName = "ZARA COAT 3";
	
	@Test(priority=2, dataProvider="getDataForTest")
	public void loginTest(String email, String pass) throws IOException, InterruptedException
	{
		// TODO Auto-generated method stub
		//email = "ninad@testmail.com";
		//pass = "Tester@123";
		productCatalogPage_r = landingPage_r.loginToApplication(email, pass);
		Assert.assertNotNull("Login failed as login returned Null ProductCatalog Object", productCatalogPage_r);
	}
	
	@Test(dependsOnMethods="loginTest", dataProvider="getTestDataFromJson")
	public void addProductToCartTest(HashMap<String, Object> map) throws InterruptedException
	{
		//email = "ninad@testmail.com";
		//pass = "Tester@123";
		//productCatalogPage_r = landingPage_r.loginToApplication(email, pass);	
		productCatalogPage_r.addProductToCart(map.get("product").toString());
		CartPage cartPage_r = productCatalogPage_r.navigateToCartPage();
		CheckoutPage checkoutPage_r = cartPage_r.proceedToCheckout();
		checkoutPage_r.selectCountry("India");
		OrderHistoryPage orderHistoryPage_r = checkoutPage_r.submitOrder();
		String orderID_v = checkoutPage_r.getOrderID();
		Assert.assertTrue(checkoutPage_r.verifyActualMessage("THANKYOU FOR THE ORDER."));
		checkoutPage_r.navigateToOrderHistory();
		Assert.assertNotNull(orderHistoryPage_r.checkOrderExists(orderID_v));
	}
	
	@Test(priority=1,dataProvider="getHashMapDataForTest", groups= {"datap"})
	public void invalidLoginTest(HashMap<String, Object> map) throws InterruptedException
	{
		//email = "testninad@testmail.com";
		//pass = "Tester@123";
		//productCatalogPage_r = landingPage_r.loginToApplication(email, pass);
		System.out.println(map.get("email")+" and "+map.get("password"));
		productCatalogPage_r = landingPage_r.loginToApplication(map.get("email").toString(), map.get("password").toString());
		System.out.println("AddToCartDP : CatalogObject "+productCatalogPage_r);
		Assert.assertNull("AddTestToCartTestDP : Test Failed as ProductCatalog Object returned by login in Not Null", productCatalogPage_r);
	}
	
	@DataProvider
	public Object[][] getDataForTest(Method m){
		switch (m.getName()) {
		case "loginTest": 
			return new Object[][] {{"ninad@testmail.com","Tester@123"}};
		case "invalidLoginTest": 
			return new Object[][] {{"testninad@testmail.com","Tester@123"}};
		case "addProductToCartTest": 
			return new Object[][] {{"ZARA COAT 3"}};
		default:
			return null;
		}
		
	}
	
	@DataProvider
	public Object[][] getHashMapDataForTest(Method m){
		
		//java.util.List<HashMap<String, Object>> mapList = null;
		
		HashMap<String, Object> map1 = new HashMap<>();
		map1.put("email", "ninad@testmail.com");
		map1.put("passowrd", "Tester@123");
		
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("email", "testninad@testmail.com");
		map2.put("password", "Tester@123");
		
		HashMap<String, Object> map3 = new HashMap<>();
		map3.put("productName", "ZARA COAT 3");
		
		switch (m.getName()) {
		case "loginTest": 
			return new Object[][] {{map1}};
		case "invalidLoginTest": 
			return new Object[][] {{map2}};
		case "addProductToCartTest": 
			return new Object[][] {{map3}};
		default:
			return null;
		}
	}
	
	
	@DataProvider
	public Object[][] getTestDataFromJson(Method m) throws IOException{
		
		//java.util.List<HashMap<String, Object>> mapList = null;
		
		java.util.List<HashMap<String, Object>> mapList = getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/demo/data/demoProjectData.json");
		switch (m.getName()) {
		case "loginTest": 
			return new Object[][] {{mapList.get(0)}};
		case "invalidLoginTest": 
			return new Object[][] {{mapList.get(1)}};
		case "addProductToCartTest": 
			return new Object[][] {{mapList.get(2)}};
		default:
			return null;
		}
	}

}
