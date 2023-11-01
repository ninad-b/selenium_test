package demo.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WindowAuthPopupTest {
	
	WebDriver driver;
	WebDriverWait wait;
	String downloadPath="";
	
	@BeforeTest
	public void launchBrowser()
	{
		HashMap<String, Object> chromePrefs = new HashMap<>();
		WebDriverManager.chromedriver().setup();
		ChromeOptions cOptions = new ChromeOptions();
		downloadPath = System.getProperty("usr.dir");
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath+"/testFilesDump");
		cOptions.setExperimentalOption("prefs", chromePrefs);
		driver = new ChromeDriver();
		driver.get("https://cloudconvert.com/txt-to-pdf");
		driver.manage().window().maximize();
		
	}
	
	@Test
	public void uploadFileTest() throws IOException, InterruptedException
	{
		//ChromeOptions cOptions = new ChromeOptions();
		
		driver.findElement(By.cssSelector("button.btn-primary")).click();
		
		Thread.sleep(2000);
		
		///// This executes the executable script file written with AutoIT to select the file 
			Runtime.getRuntime().exec("C:/Users/ninad/OneDrive/Documents/CheckPoint/uploadFile.exe");
			/* Code that is written in mentioned exe is as below,
			 * 		ControlFocus("Open","","Edit1")
			 *		ControlSetText("Open","", "Edit1", "C:\Users\ninad\OneDrive\Documents\TestFileUploadDownload.txt")
			 *		ControlClick("Open","","Button1")
			 * */
		///// This executes the executable script file written with AutoIT to select the file 

		By convertButton = By.xpath("//button/span[contains(text(),'Convert')]");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(convertButton));
		driver.findElement(convertButton).click();
	}
	
	@Test(dependsOnMethods="uploadFileTest")
	public void downloadFileTest() throws InterruptedException
	{
		By downloadButton = By.cssSelector("button.btn-success");
		wait.until(ExpectedConditions.visibilityOfElementLocated(downloadButton));
		driver.findElement(downloadButton).click();
		
		File downloadedFile = new File(downloadPath+"/testFilesDump/TestFileUploadDownload.pdf");
		if(downloadedFile.exists())
		{
			System.out.println(" >> File was downloaded successfully");
			System.out.println(" >> Proceeding to delete to cleanup");
			if(downloadedFile.delete())
			{
				System.out.println(" >> File has been deleted successfully");
				System.out.println(" >> You have reached the limit of conversion");
			}
		}
		Thread.sleep(2000);
		driver.close();
	}

}
