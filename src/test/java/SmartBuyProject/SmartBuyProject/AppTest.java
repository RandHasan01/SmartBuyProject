package SmartBuyProject.SmartBuyProject;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	String PublicProductName;
	String PublicProductPrice;
	String PublicProductQuantities;
	String Email;
	String Password;

	@BeforeTest
	public void mySetup() {
		driver.get(SmartBuyURL);
		driver.manage().window().maximize();
	}

	@Test(priority = 1, enabled = true)
	public void VerifyHomepageLoadsSuccessfully() {
		WebElement navigationMenus = driver.findElement(By.className("nav-bar__linklist"));
		WebElement banners = driver.findElement(By.id("shopify-section-template--23554639757622__slideshow"));
		WebElement featuredProducts = driver
				.findElement(By.id("shopify-section-template--23554639757622__featured_collection_AyKFpr"));

		Assert.assertEquals(navigationMenus.isDisplayed(), true);
		Assert.assertEquals(banners.isDisplayed(), true);
		Assert.assertEquals(featuredProducts.isDisplayed(), true);

	}

	@Test(priority = 2, enabled = true)
	public void VerifyCategoryNavigation() throws InterruptedException {

		WebElement shopByCategory = driver.findElement(By.linkText("Shop by Category"));
		shopByCategory.click();

		WebElement shopByCategoryItem = driver.findElement(By.cssSelector(".nav-bar__item.is-dropdown-open"));

		List<WebElement> Categories = shopByCategoryItem.findElement(By.id("desktop-menu-0-2"))
				.findElements(By.cssSelector("#desktop-menu-0-2 > li > a.nav-dropdown__link.link"));

		for (int i = 0; i < Categories.size(); i++) {
			System.out.println(Categories.get(i).getText());
		}
		int randomIndexCategory = rand.nextInt(Categories.size());
		WebElement selectedCategory = Categories.get(randomIndexCategory);
		System.out.println("@@@@@@Categories.size@@@@@@@@" + Categories.size());
		String selectedCategoryText = selectedCategory.getText().toLowerCase();
		System.out.println("@@@@@@@" + selectedCategoryText);// BUILT-IN
		Actions actions = new Actions(driver);
		actions.doubleClick(selectedCategory).perform();
		Thread.sleep(2000);

		WebElement categoryName = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".heading.h1")));
		System.out.println(categoryName.getText());// Built-In
		boolean actualCategoryNavigation = categoryName.getText().toLowerCase().contains(selectedCategoryText);
		Assert.assertEquals(actualCategoryNavigation, true);
		Thread.sleep(2000);
	}

	@Test(priority = 3, enabled = true)
	public void VerifySearchResultsAccuracy() {
		WebElement searchField = driver.findElement(By.className("search-bar__input"));
		searchField.sendKeys("iPhone 15");

		WebElement searchButton = driver.findElement(By.className("search-bar__submit"));
		searchButton.click();

		List<WebElement> resultOfSearch = driver.findElements(By.className("product-item__title"));
		System.out.println("@@@@@@@@resultOfSearchSize" + resultOfSearch.size());
		for (int i = 0; i < resultOfSearch.size(); i++) {
			System.out.println(resultOfSearch.get(i).getText());
			Assert.assertEquals(resultOfSearch.get(i).getText().toLowerCase().contains("iphone 15"), true);
		}

	}

	@Test(priority = 4, enabled = true)
	public void productDetailPageAccuracy() {
		List<WebElement> resultOfSearch = driver.findElements(By.className("product-item__title"));
		int randomIndexProduct = rand.nextInt(resultOfSearch.size());
		resultOfSearch.get(randomIndexProduct).click();
		WebElement productName = driver.findElement(By.className("product-meta__title"));
		PublicProductName = productName.getText();
		Assert.assertEquals(productName.isDisplayed(), true);
		WebElement productImg = driver.findElement(By.cssSelector(".product-gallery__carousel-item.is-selected"));
		Assert.assertEquals(productImg.isDisplayed(), true);
		WebElement productPrice = driver.findElement(By.className("price"));
		PublicProductPrice = productPrice.getText();
		System.out.println(PublicProductPrice + "@@@");

		Assert.assertEquals(productPrice.isDisplayed(), true);
		String productAddButton = driver.findElement(By.className("product-form__add-button")).getText();
		Assert.assertEquals(productAddButton.contains("Add to cart") || productAddButton.contains("Sold out"), true);
		WebElement addToWishlistButton = driver.findElement(By.id("vitals-wishlist"));
		Assert.assertEquals(addToWishlistButton.isDisplayed(), true);
	}

	@Test(priority = 5, enabled = true)
	public void addToTheCart() throws InterruptedException {
//		driver.navigate().back();
		int randomQuantities = rand.nextInt(2, 4);
		PublicProductQuantities = Integer.toString(randomQuantities);
		System.out.println(PublicProductQuantities + "!!!!!!!!");
		for (int i = 0; i < randomQuantities; i++) {
			WebElement productAddButton = driver.findElement(By.className("product-form__add-button"));

			productAddButton.click();
			Thread.sleep(2000);

		}
		Thread.sleep(2000);
		String cartCount = driver.findElement(By.className("header__cart-count")).getText();
		Assert.assertEquals(Integer.toString(randomQuantities), cartCount);

	}

	@Test(priority = 6, enabled = true)
	public void ShoppingCartReview() throws InterruptedException {

		WebElement cartButton = driver.findElement(By.className("header__cart-toggle"));
		cartButton.click();
		Thread.sleep(2000);
		WebElement viewCartButton = driver.findElement(By.cssSelector(".button.button--secondary"));
		viewCartButton.click();

		String productNameInTheCart = driver.findElement(By.className("line-item__title")).getText();
		String productPriceInTheCart = driver.findElements(By.cssSelector(".line-item__price")).get(0).getText();
		String productQuantityInTheCart = driver.findElement(By.className("quantity-selector__value"))
				.getDomAttribute("value");
		System.out.println(productQuantityInTheCart + "!!!!!!!");
		System.out.println(productPriceInTheCart + "@@@@");

		Assert.assertTrue(productNameInTheCart.contains(PublicProductName), "have a differance product name");
		Assert.assertTrue(PublicProductPrice.contains(productPriceInTheCart), "have a differance product price");
		Assert.assertTrue(productQuantityInTheCart.equals(PublicProductQuantities),
				"have a differance product quantity");

	}

	@Test(priority = 7, enabled = false)
	public void UpdateCartQuantities() throws InterruptedException {
		driver.navigate().back();
		WebElement cartButton = driver.findElement(By.className("header__cart-toggle"));
		cartButton.click();
		WebElement decreaseButton = driver.findElement(By.xpath("//button[@data-action='decrease-quantity']"));
		WebElement increaseButton = driver.findElement(By.xpath("//button[@data-action='increase-quantity']"));

		if (rand.nextBoolean()) {
			decreaseButton.click();
			System.out.println("Clicked Decrease Button");
		} else {
			increaseButton.click();
			System.out.println("Clicked Increase Button");
		}

	}

	@Test(priority = 8, enabled = false)
	public void RemoveItemsfromCart() {

	}

	@Test(priority = 9, enabled = true)
	public void PromotionsPageAccessibility() {

		WebElement promotions = driver.findElement(By.linkText("Promotions"));
		promotions.click();

		List<WebElement> discountLables = driver.findElements(By.className("product-label"));
		int randomIndexdiscountLable = rand.nextInt(discountLables.size());
		boolean discountLable = discountLables.get(randomIndexdiscountLable).isDisplayed();
		Assert.assertTrue(discountLable);
	}

	@Test(priority = 10, enabled = true)
	public void ContactInformationAvailability() {
		WebElement footer = driver.findElement(By.tagName("footer"));
		String divContactInfo = footer.findElement(By.id("block-footer-0")).getText();
		System.out.println(divContactInfo);
		Assert.assertTrue(divContactInfo.contains("+962 (06) 5809999"), "Phone number is not displayed!");
		Assert.assertTrue(divContactInfo.contains("info@smartbuy.jo"), "Email address is not displayed!");
	}

	@Test(priority = 11, enabled = true)
	public void UserRegistrationProcess() {
		WebElement signupButton = driver
				.findElement(By.cssSelector(".header__action-item-link.hidden-pocket.hidden-lap"));
		signupButton.click();

		WebElement creatAccountButton = driver.findElement(By.linkText("Create your account"));
		creatAccountButton.click();

		WebElement firstNameField = driver.findElement(By.id("customer[first_name]"));
		String firstName = "rand";
		firstNameField.sendKeys(firstName);
		WebElement lasttNameField = driver.findElement(By.id("customer[last_name]"));
		String lastName = "hasan";
		lasttNameField.sendKeys(lastName);
		WebElement emailField = driver.findElement(By.id("customer[email]"));
		String email = firstName + lastName + "@gmail.com";
		Email = email;
		emailField.sendKeys(email);
		WebElement passwordField = driver.findElement(By.id("customer[password]"));
		String password = "Rand@123";
		Password = password;
		passwordField.sendKeys(password);

		WebElement createMyAccountButton = driver.findElement(By.xpath("//button[text()='Create my account']"));
		createMyAccountButton.click();

		WebElement confirmationMessage = driver
				.findElement(By.cssSelector(".header__action-item-title.hidden-pocket.hidden-lap"));
		Assert.assertTrue(confirmationMessage.getText().contains("Hello"), "confirmation Message not appeared");
	}

	@Test(priority = 12, enabled = true)
	public void UserLoginFunctionality() {
		WebElement signupButton = driver
				.findElement(By.cssSelector(".header__action-item-link.hidden-pocket.hidden-lap"));
		signupButton.click();
		WebElement emailField = driver.findElement(By.id("customer[email]"));
		emailField.sendKeys(Email);
		WebElement passwordField = driver.findElement(By.id("customer[password]"));
		passwordField.sendKeys(Password);
		WebElement longinButton = driver.findElement(By.xpath("//button[text()='Login']"));
		longinButton.click();

		WebElement confirmationMessage = driver
				.findElement(By.cssSelector(".header__action-item-title.hidden-pocket.hidden-lap"));
		Assert.assertTrue(confirmationMessage.getText().contains("Hello"), "confirmation Message not appeared");

		Assert.assertTrue(driver.getCurrentUrl().contains("account"));

	}

	@AfterTest
	public void endTest() {

	}

}
