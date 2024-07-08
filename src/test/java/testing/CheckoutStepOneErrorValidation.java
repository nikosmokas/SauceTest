package testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutStepOneErrorValidation extends BaseTest {

    protected static final Logger logger = LogManager.getLogger(CheckoutStepOneErrorValidation.class);
    private String USERNAME_ID = "user-name";
    private String PASSWORD_ID = "password";
    private String LOGIN_BTN_ID = "login-button";
    private String INVENTORY_ITEM_CLASS = "inventory_item";
    private String BTN_PRIMARY_CLASS = "btn_primary";
    private final int SLEEP_TIMER = 100;


     @BeforeMethod
    public void setUp() {
        logger.info("This is beforeMethod in first test");
        // Initialize WebDriver
        logger.info("Setting up a driver.");
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.saucedemo.com/");
        logger.info("ChromeDriver initiated");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("This is afterMethod in first test");
        // Close WebDriver
        if (driver != null) {
            driver.quit();
             logger.info("Browser closed");
        }
    }

    @Test
    public void testCheckoutStepOneErrorValidation() throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);

        // Login
        logger.info("Logging in...");
        login("standard_user", "secret_sauce");

        // Add a product to the cart
        logger.info("Adding a product to the cart...");
        addProductToCart();

        // Go to Cart
        logger.info("Navigating to the cart...");
        goToCart();

        // Click Checkout
        clickCheckout();

        // Attempt to continue with missing fields
        logger.info("Clicking continue without filling in the form...");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(SLEEP_TIMER);

        // Validate error message
        String errorMessage = driver.findElement(By.className("error-message-container")).getText();
        Assert.assertTrue(errorMessage.contains("Error: First Name is required"), "Error message not displayed correctly");
        logger.info("Error message detected, as it should.");

        logger.info("Test 'Checkout Step One Error Validation' passed");
    }

    private void login(String username, String password) throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        driver.findElement(By.id(USERNAME_ID)).sendKeys(username);
        driver.findElement(By.id(PASSWORD_ID)).sendKeys(password);
        driver.findElement(By.id(LOGIN_BTN_ID)).click();
        Thread.sleep(SLEEP_TIMER);
    }

    private void addProductToCart() throws InterruptedException {
        driver.findElement(By.className(INVENTORY_ITEM_CLASS)).click();
        Thread.sleep(SLEEP_TIMER);
        driver.findElement(By.className(BTN_PRIMARY_CLASS)).click();
        Thread.sleep(SLEEP_TIMER);
    }

    private void goToCart() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(SLEEP_TIMER);
    }

    private void clickCheckout() throws InterruptedException {
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(SLEEP_TIMER);
    }

}
