package SmartBuyProject.SmartBuyProject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

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

public class AppTest extends TestData {
	public void enterCredentials(String email, String password) {
		WebElement emailField = driver.findElement(By.id("customer[email]"));
		emailField.clear();
		emailField.sendKeys(email);

		WebElement passwordField = driver.findElement(By.id("customer[password]"));
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	@BeforeTest
	public void mySetup() {
		setup();
	}

	@Test(priority = 1, enabled = false)
	public void verifyHomepageLoadsSuccessfully() {
		WebElement navigationMenus = driver.findElement(By.className("nav-bar__linklist"));
		WebElement banners = driver.findElement(By.id("shopify-section-template--23554639757622__slideshow"));
		WebElement featuredProducts = driver
				.findElement(By.id("shopify-section-template--23554639757622__featured_collection_AyKFpr"));

		Assert.assertTrue(navigationMenus.isDisplayed(), "Navigation menu is not displayed");
		Assert.assertTrue(banners.isDisplayed(), "Banner not visible");
		Assert.assertTrue(featuredProducts.isDisplayed(), "Featured products not visible");

	}

	@Test(priority = 2, enabled = false, invocationCount = 5)
	public void verifyCategoryNavigation() throws InterruptedException {

		WebElement shopByCategory = driver.findElement(By.linkText("Shop by Category"));
		shopByCategory.click();
		Thread.sleep(2000);
		List<WebElement> Categories = driver
				.findElements(By.cssSelector("#desktop-menu-0-2 > li > a.nav-dropdown__link.link"));

		for (int i = 0; i < Categories.size(); i++) {
			System.out.println("Categories :" + Categories.get(i).getText());
		}
		int randomIndexCategory = rand.nextInt(Categories.size());
		WebElement selectedCategory = Categories.get(randomIndexCategory);
		System.out.println("Categories size :" + Categories.size());
		String selectedCategoryText = selectedCategory.getText().toLowerCase();
		System.out.println("selectedCategory :" + selectedCategoryText);
		String categoryHref = selectedCategory.getDomAttribute("href");
		driver.get(SmartBuyURL + categoryHref);
		WebElement categoryPageHeading = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".heading.h1")));
		String headingText = categoryPageHeading.getText().toLowerCase();
		System.out.println("headingText :" + headingText);

		if (selectedCategoryText.equals("tablets")) {
			Assert.assertTrue(headingText.contains("tablet"),
					"Expected heading to contain 'tablet' but found: " + headingText);
		} else {
			Assert.assertTrue(headingText.contains(selectedCategoryText),
					"Expected heading to contain: " + selectedCategoryText + " but found: " + headingText);
		}
		Thread.sleep(2000);
	}

	@Test(priority = 3, enabled = true)
	public void verifySearchResultsAccuracy() {
		WebElement searchField = driver.findElement(By.className("search-bar__input"));
		searchField.sendKeys("iPhone 15");

		WebElement searchButton = driver.findElement(By.className("search-bar__submit"));
		searchButton.click();
		WebElement productList = driver.findElement(By.className("product-list"));
		Assert.assertTrue(productList.isDisplayed());
		List<WebElement> productsTitle = productList.findElements(By.className("product-item__title"));
		System.out.println("resultOfSearchSize :" + productsTitle.size());
		for (int i = 0; i < productsTitle.size(); i++) {
			System.out.println("results :" + productsTitle.get(i).getText());
			Assert.assertEquals(productsTitle.get(i).getText().toLowerCase().contains("iphone 15"), true);
		}

	}

	@Test(priority = 4, enabled = true) //
	public void productDetailPageAccuracy() {

		WebElement listOfProduct = driver.findElement(By.cssSelector(".product-list.product-list--collection"));

		List<WebElement> products = listOfProduct.findElements(By.className("product-item"));
		int randomIndexProduct = rand.nextInt(products.size());
		WebElement selectedProduct = products.get(randomIndexProduct);
		WebElement productName = selectedProduct.findElement(By.className("product-item__title"));
		String productPrice = selectedProduct.findElement(By.className("price")).getText().replaceAll("[^0-9.]", "");
		String productNameText = productName.getText();
		System.out.println(productPrice + productNameText);
		productName.click();
		String detailPageProductName = driver.findElement(By.className("product-meta__title")).getText();
		String detailPageProductPrice = driver.findElement(By.className("price")).getText().replaceAll("[^0-9.]", "");
		System.out.println(detailPageProductName + detailPageProductPrice);
		
//		List<WebElement> resultOfSearch = driver.findElements(By.className("product-item__title"));
//		int randomIndexProduct = rand.nextInt(resultOfSearch.size());
//		resultOfSearch.get(randomIndexProduct).click();
//		WebElement productName = driver.findElement(By.className("product-meta__title"));
//		PublicProductName = productName.getText();
//		Assert.assertEquals(productName.isDisplayed(), true);
//		WebElement productImg = driver.findElement(By.cssSelector(".product-gallery__carousel-item.is-selected"));
//		Assert.assertEquals(productImg.isDisplayed(), true);
//		WebElement productPrice = driver.findElement(By.className("price"));
//		PublicProductPrice = productPrice.getText();
//		System.out.println("PublicProductPrice :"+PublicProductPrice );
//
//		Assert.assertEquals(productPrice.isDisplayed(), true);
//		String productAddButton = driver.findElement(By.className("product-form__add-button")).getText();
//		Assert.assertEquals(productAddButton.contains("Add to cart") || productAddButton.contains("Sold out"), true);
//		WebElement addToWishlistButton = driver.findElement(By.id("vitals-wishlist"));
//		Assert.assertEquals(addToWishlistButton.isDisplayed(), true);
	}

	@Test(priority = 5, enabled = false)
	public void addProductToTheCart() throws InterruptedException {
		int randomQuantities = rand.nextInt(1, 4);
		PublicProductQuantities = Integer.toString(randomQuantities);
		System.out.println("PublicProductQuantities :" + PublicProductQuantities);
		WebElement productAddButton = driver.findElement(By.className("product-form__add-button"));
		for (int i = 0; i < randomQuantities; i++) {
			productAddButton.click();
			Thread.sleep(2000);

		}
		Thread.sleep(2000);
		String cartCount = driver.findElement(By.className("header__cart-count")).getText();
		Assert.assertEquals(Integer.toString(randomQuantities), cartCount);

	}

	@Test(priority = 6, enabled = false)
	public void shoppingCartReview() throws InterruptedException {

		WebElement cartButton = driver.findElement(By.className("header__cart-toggle"));
		cartButton.click();
		Thread.sleep(2000);
		WebElement viewCartButton = driver.findElement(By.cssSelector(".button.button--secondary"));
		viewCartButton.click();

		String productNameInTheCart = driver.findElement(By.className("line-item__title")).getText();
		String productPriceInTheCart = driver.findElements(By.cssSelector(".line-item__price")).get(0).getText();
		String productQuantityInTheCart = driver.findElement(By.className("quantity-selector__value"))
				.getDomAttribute("value");
		System.out.println("productQuantityInTheCart :" + productQuantityInTheCart);
		System.out.println("productPriceInTheCart :" + productPriceInTheCart);

		Assert.assertTrue(productNameInTheCart.contains(PublicProductName), "have a differance product name");
		Assert.assertTrue(PublicProductPrice.contains(productPriceInTheCart), "have a differance product price");
		Assert.assertTrue(productQuantityInTheCart.equals(PublicProductQuantities),
				"have a differance product quantity");

	}

	@Test(priority = 7, enabled = false)
	public void promotionsPageAccessibility() {

		WebElement promotions = driver.findElement(By.linkText("Promotions"));
		promotions.click();

		Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("promotions"),
				"URL does not contain 'promotions'");
		List<WebElement> products = driver.findElement(By.cssSelector(".product-list.product-list--collection"))
				.findElements(By.className("product-item"));
		int randomIndexProduct = rand.nextInt(products.size());
		WebElement selectedProduct = products.get(randomIndexProduct);
		WebElement discountLable = selectedProduct.findElement(By.className("product-item__label-list"));
		Assert.assertTrue(discountLable.isDisplayed(), "Discount label not found on selected promotion product");

	}

	@Test(priority = 8, enabled = false)
	public void contactInformationAvailability() {
		WebElement footer = driver.findElement(By.tagName("footer"));
		String divContactInfo = footer.findElement(By.id("block-footer-0")).getText();
		System.out.println(divContactInfo);
		Assert.assertTrue(divContactInfo.contains("+962 (06) 5809999"), "Phone number is not displayed!");
		Assert.assertTrue(divContactInfo.contains("info@smartbuy.jo"), "Email address is not displayed!");
	}

	@Test(priority = 9, enabled = false)
	public void UserRegistrationProcess() {
		WebElement signupButton = driver
				.findElement(By.cssSelector(".header__action-item-link.hidden-pocket.hidden-lap"));
		signupButton.click();

		WebElement creatAccountButton = driver.findElement(By.linkText("Create your account"));
		creatAccountButton.click();

		WebElement firstNameField = driver.findElement(By.id("customer[first_name]"));
		firstNameField.sendKeys("rand");
		WebElement lasttNameField = driver.findElement(By.id("customer[last_name]"));
		lasttNameField.sendKeys("hasan");
		enterCredentials(Email, Password);

		WebElement createMyAccountButton = driver.findElement(By.xpath("//button[text()='Create my account']"));
		createMyAccountButton.click();

		WebElement confirmationMessage = driver
				.findElement(By.cssSelector(".header__action-item-title.hidden-pocket.hidden-lap"));
		Assert.assertTrue(confirmationMessage.getText().toLowerCase().contains("hello"),
				"confirmation Message not appeared");
	}

	@Test(priority = 10, enabled = false)
	public void UserLoginFunctionality() {
		WebElement signupButton = driver
				.findElement(By.cssSelector(".header__action-item-link.hidden-pocket.hidden-lap"));
		signupButton.click();
		enterCredentials(Email, Password);
		WebElement longinButton = driver.findElement(By.xpath("//button[text()='Login']"));
		longinButton.click();

		WebElement confirmationMessage = driver
				.findElement(By.cssSelector(".header__action-item-title.hidden-pocket.hidden-lap"));
		Assert.assertTrue(confirmationMessage.getText().contains("Hello"), "confirmation Message not appeared");

		WebElement currentPage = driver.findElement(By.cssSelector(".heading.h1"));
		Assert.assertTrue(currentPage.getText().toLowerCase().contains("my orders"));

		WebElement logoutButton = driver.findElement(By.linkText("Logout"));
		logoutButton.click();

	}

	@Test(priority = 11, enabled = false)
	public void InvalidUserLoginFunctionality() {
		WebElement signupButton = driver
				.findElement(By.cssSelector(".header__action-item-link.hidden-pocket.hidden-lap"));
		signupButton.click();
		enterCredentials(Email, "Rand@1234");
		WebElement longinButton = driver.findElement(By.xpath("//button[text()='Login']"));
		longinButton.click();

		WebElement errorMessage = driver.findElement(By.className("alert"));
		Assert.assertTrue(errorMessage.isDisplayed());

	}

	@Test(priority = 12, enabled = false)
	public void SocialMediaLinks() {
		WebElement footer = driver.findElement(By.tagName("footer"));

		List<WebElement> socialMediaItems = footer.findElements(By.cssSelector("ul.social-media__item-list li"));
		int randomSocialMediaLink = rand.nextInt(socialMediaItems.size());
		socialMediaItems.get(randomSocialMediaLink).click();

		Set<String> handels = driver.getWindowHandles();
		List<String> windowList = new ArrayList<>(handels);
		driver.switchTo().window(windowList.get(1));

		String currentUrl = driver.getCurrentUrl().toLowerCase();
		boolean isCorrectPlatform = currentUrl.contains("facebook") || currentUrl.contains("instagram")
				|| currentUrl.contains("youtube") || currentUrl.contains("x.com") || currentUrl.contains("linkedin");

		boolean isSmartBuyBrand = currentUrl.contains("smartbuy");

		Assert.assertTrue(isCorrectPlatform && isSmartBuyBrand, "The social media link is incorrect: " + currentUrl);

	}

	@AfterTest
	public void endTest() {
		driver.close();

	}

}
