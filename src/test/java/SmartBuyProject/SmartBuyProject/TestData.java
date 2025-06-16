package SmartBuyProject.SmartBuyProject;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestData {

	WebDriver driver = new ChromeDriver();
	String SmartBuyURL = "https://smartbuy-me.com/";
	Random rand = new Random();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	String PublicProductName;
	String PublicProductPrice;
	String PublicProductQuantities;
	String Email = "randhasan@gmail.com";
	String Password = "Rand@123";

	public void setup() {
		driver.get(SmartBuyURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

}
