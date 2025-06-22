# SmartBuyProject

Automated UI Testing Suite for the [SmartBuy](https://smartbuy-me.com/) e-commerce website using Selenium WebDriver and TestNG.

---

## Project Overview

This project contains automated test cases implemented in Java with Selenium WebDriver and TestNG framework to validate key functionalities of the SmartBuy e-commerce website.  
The tests cover core user flows such as homepage loading, category navigation, product search, product detail verification, shopping cart operations, promotions page accessibility, user registration/login, and social media links.

---

## Technologies Used

- Java 17+
- Selenium WebDriver
- TestNG
- ChromeDriver
- Maven (project build and dependency management)

---

## Project Structure

- **TestData.java**  
  Base class containing WebDriver setup, common test data like URL, credentials, and driver initialization.

- **AppTest.java**  
  Contains TestNG test cases extending TestData, covering various test scenarios including homepage verification, navigation, search, product detail checks, cart functionality, user login/registration, and footer link validations.

---

## Test Cases Included

1. **verifyHomepageLoadsSuccessfully**  
   Validates navigation menu, banners, and featured products are visible on homepage.

2. **verifyCategoryNavigation**  
   Randomly selects a category from "Shop by Category" and verifies correct page loads.

3. **verifySearchResultsAccuracy**  
   Searches for "iPhone 15" and verifies search results contain the keyword.

4. **productDetailPageAccuracy**  
   Selects a random product, checks product name, price, image, and specifications on detail page.

5. **addProductToTheCart**  
   Adds a random quantity of the selected product to the cart and verifies cart count.

6. **shoppingCartReview**  
   Checks the product details in the cart (name, price, quantity) against selected product.

7. **promotionsPageAccessibility**  
   Navigates to the promotions page and verifies discount labels on products.

8. **contactInformationAvailability**  
   Validates the presence of phone number and email in the website footer.

9. **UserRegistrationProcess** *(disabled)*  
   Tests the user registration workflow (currently disabled).

10. **UserLoginFunctionality**  
    Validates login with valid credentials and verifies user landing page.

11. **InvalidUserLoginFunctionality**  
    Tests login with invalid credentials and verifies error message.

12. **SocialMediaLinks**  
    Randomly clicks on a social media icon in footer and verifies URL correctness.

---
 
