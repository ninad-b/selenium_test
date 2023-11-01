package demo.Components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StackWalker.Option;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.pageObjects.CartPage;
import demo.pageObjects.LandingPage;
import demo.pageObjects.ProductCatalogPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class InitTests {
	public WebDriver driver;
	protected LandingPage landingPage_r;
	protected ProductCatalogPage productCatalogPage_r;
	protected CartPage cartPage_r;
	
	public InitTests() {
		// TODO Auto-generated constructor stub
	}
	
	public WebDriver getDriver() throws IOException
	{
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//demo//resources//GlobalData.properties");
		properties.load(fis);
		
		DesiredCapabilities caps = new DesiredCapabilities();
		
		String browserString = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
		
		//String browserString = properties.getProperty("browser");  
		if(browserString.contains("remote-chrome"))
		{
			caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			driver = new RemoteWebDriver(new URL("http://192.168.1.19:4444"), caps);
			driver.manage().window().maximize();
		}
		else if(browserString.contains("remote-firefox"))
		{
			caps.setCapability(CapabilityType.BROWSER_NAME, "firefox");
			driver = new RemoteWebDriver(new URL("http://192.168.1.19:4444"), caps);
			driver.manage().window().maximize();
		}
		else if(browserString.contains("chrome"))
		{
			ChromeOptions cOptions = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserString.contains("Headless"))
				cOptions.addArguments("Headless");
			driver = new ChromeDriver(cOptions);
			driver.manage().window().setSize(new Dimension(1440, 900));
		}
		else if(browserString.equalsIgnoreCase("firefox"))
		{
			System.out.println("Tesing firefox here");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserString.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if(browserString.equalsIgnoreCase("ie"))
		{
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public List<HashMap<String, Object>> getJsonDataToMap(String filePath) throws IOException
	{
		String jsonStringContent = FileUtils.readFileToString(new File(filePath), 
															StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> mapDataList = mapper.readValue(jsonStringContent, new TypeReference<List<HashMap<String, Object>>>() {});
		
		return mapDataList;
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts =  (TakesScreenshot)driver;
		File fileSource = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"/reports/"+testCaseName+".png");
		FileUtils.copyFile(fileSource, destFile);
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
	}
	
	@BeforeClass(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = getDriver();
		landingPage_r = new LandingPage(driver);
		landingPage_r.navigateToLandingPage("https://rahulshettyacademy.com/client");
		return landingPage_r;
	}
	
	@AfterClass(alwaysRun=true)
	public void closeDriver()
	{
		driver.close();
	}
}
