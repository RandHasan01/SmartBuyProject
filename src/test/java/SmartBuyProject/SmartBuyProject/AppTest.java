package SmartBuyProject.SmartBuyProject;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {

	WebDriver driver = new ChromeDriver();
	String SmartBuyURL = "https://smartbuy-me.com/";
	Random rand = new Random();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	@BeforeTest
	public void mySetup() {
		driver.get(SmartBuyURL);
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void VerifyHomepageLoadsSuccessfully() {
		WebElement navigationMenus = driver.findElement(By.className("nav-bar__linklist"));
		WebElement banners = driver.findElement(By.id("shopify-section-template--23554639757622__slideshow"));
		WebElement featuredProducts = driver
				.findElement(By.id("shopify-section-template--23554639757622__featured_collection_AyKFpr"));

		Assert.assertEquals(navigationMenus.isDisplayed(), true);
		Assert.assertEquals(banners.isDisplayed(), true);
		Assert.assertEquals(featuredProducts.isDisplayed(), true);

	}

	@Test(priority = 2, enabled = false)
	public void VerifyCategoryNavigation() {

		WebElement shopByCategory = driver.findElement(By.linkText("Shop by Category"));
		shopByCategory.click();

		WebElement shopByCategoryItem = driver.findElement(By.cssSelector(".nav-bar__item.is-dropdown-open"));

		List<WebElement> Categories = shopByCategoryItem.findElement(By.id("desktop-menu-0-2"))
				.findElements(By.className("nav-dropdown__item"));

		int randomIndexCategory = rand.nextInt(Categories.size());
		WebElement selectedCategory = Categories.get(randomIndexCategory);
		selectedCategory.click();

		String classValue = selectedCategory.getAttribute("class");
		if (classValue.contains("is-dropdown-open")) {
			selectedCategory.click();
		}

	}

	@Test(priority = 3, enabled = false)
	public void VerifySearchResultsAccuracy() {
		WebElement searchField = driver.findElement(By.className("search-bar__input"));
		searchField.sendKeys("iPhone 15");

		WebElement searchButton = driver.findElement(By.className("search-bar__submit"));
		searchButton.click();

		List<WebElement> resultOfSearch = driver.findElements(By.className("product-item__title"));
		System.out.println(resultOfSearch.size());
		for (int i = 0; i < resultOfSearch.size(); i++) {
			System.out.println(resultOfSearch.get(i).getText());
			Assert.assertEquals(resultOfSearch.get(i).getText().toLowerCase().contains("iphone 15"), true);
		}

	}

	@Test(priority = 4)
	public void test4() {

	}

	@Test(priority = 5)
	public void test5() {

	}

	@Test(priority = 6)
	public void test6() {

	}

	@Test(priority = 7)
	public void test7() {

	}

	@Test(priority = 8)
	public void test8() {

	}

	@Test(priority = 9)
	public void test9() {

	}

	@Test(priority = 10)
	public void test10() {

	}

	@AfterTest
	public void endTest() {

	}

}
