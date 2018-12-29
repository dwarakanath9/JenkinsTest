package parallelTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CrossBrowserScript {

	WebDriver driver = null;;

	/**
	 * This function will execute before each Test tag in testng.xml
	 * @param browser
	 * @throws Exception
	 */
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception{
		//Check if parameter passed from TestNG is 'firefox'
		if(browser.equalsIgnoreCase("firefox")){
			//create firefox instance
			System.setProperty("webdriver.gecko.driver",
					"D:\\Dwaraka_backup\\GITWorkSpace\\photon\\Test\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		//Check if parameter passed as 'chrome'
		else if(browser.equalsIgnoreCase("chrome")){
			//set path to chromedriver.exe
			System.setProperty("webdriver.chrome.driver",
					"D:\\Dwaraka_backup\\GITWorkSpace\\photon\\Test\\drivers\\chromedriver.exe");
			//create chrome instance
			driver = new ChromeDriver();
		}
		//Check if parameter passed as 'Edge'
		else if(browser.equalsIgnoreCase("Ie")){
			//set path to Edge.exe
			System.setProperty("webdriver.ie.driver",
					"D:\\Dwaraka_backup\\GITWorkSpace\\photon\\JAVASE\\drivers\\IEDriverServer.exe");//sel2.39


			//create Edge instance
			driver = new InternetExplorerDriver();
		}
		else{
			//If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public  void launchDriver() throws IOException,InterruptedException {
		//driver.manage().window().maximize();
		try {
			driver.get("https://www.nvsp.in/Forms/Forms/form6");
			WebDriverWait wait = new  WebDriverWait(driver, 100);

			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();



			Select select = new Select(driver.findElement(By.xpath("//select[@id='language']")));
			select.selectByValue("en-GB");

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//select[@id='stateList']"))));
			wait.pollingEvery(3, TimeUnit.SECONDS);
			Wait wait1 = new FluentWait<WebDriver>(driver)
					.withTimeout(20,TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);

			Select stateList = new Select(driver.findElement(By.xpath("//select[@id='stateList']")));
			stateList.selectByValue("S01");




			Select distList = new Select(driver.findElement(By.xpath("//select[@id='distList']")));
			distList.selectByValue("13");


			Select acList = new Select(driver.findElement(By.xpath("//select[@id='acList']")));
			acList.selectByValue("165");

			driver.findElement(By.xpath("//input[@id='isNewVoter']")).click();

		}catch(Exception e) {
			e.printStackTrace();
			driver.navigate().refresh();
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Date date = new Date();

			String path = "D:\\Dwaraka_backup\\GITWorkSpace\\photon\\JAVASE\\ScreenCapture\\"+driver.getTitle()+".png";
			File destFile = new File(path);
			FileUtils.copyFile(srcFile, destFile);


		}

	}
	/*@Test
	public void testParameterWithXML() throws InterruptedException{
		driver.get("http://demo.guru99.com/V4/");
		//Find user name
		WebElement userName = driver.findElement(By.name("uid"));
		//Fill user name
		userName.sendKeys("guru99");
		//Find password
		WebElement password = driver.findElement(By.name("password"));
		//Fill password
		password.sendKeys("guru99");
	}*/
}
