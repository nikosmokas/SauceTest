
package testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
public class CheckoutOverviewValidation extends BaseTest {

    protected static final Logger logger = LogManager.getLogger(CheckoutOverviewValidation.class);

    private final String USERNAME_ID = "user-name";
    private final String PASSWORD_ID = "password";
    private final String LOGIN_BTN_ID = "login-button";
    private final String INVENTORY_ITEM_CLASS = "inventory_item";
    private final String BTN_PRIMARY_CLASS = "btn_primary";
    private final String INV_ITEM_NAME = "inventory_item_name";
    private final String INV_ITEM_PRICE = "inventory_item_price";
    private final int SLEEP_TIMER = 100;


    @BeforeMethod
    public void setUp() {
        logger.info("This is beforeMethod in second test");
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
        logger.info("This is afterMethod in second test");
        // Close WebDriver
        if (driver != null) {
            driver.quit();
             logger.info("Browser closed");
        }
    }

    @Test
    public void testCheckoutOverviewValidation() throws InterruptedException {
        logger.info("Logging in with username = 'standard_user' and password = 'secret_sauce'");
        login("standard_user", "secret_sauce");

        logger.info("Login successful. Adding products...");
        ProductDetails product1 = addProductToCart(0);
        ProductDetails product2 = addProductToCart(1);

        goToCart();
        checkout();
        fillCheckoutForm("Nick", "Mokas", "1996");

        validateCheckoutOverview(product1, product2);
    }

    private void login(String username, String password) throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        driver.findElement(By.id(USERNAME_ID)).sendKeys(username);
        driver.findElement(By.id(PASSWORD_ID)).sendKeys(password);
        driver.findElement(By.id(LOGIN_BTN_ID)).click();
        Thread.sleep(SLEEP_TIMER);
    }

    private ProductDetails addProductToCart(int index) throws InterruptedException {
        List<WebElement> products = driver.findElements(By.className(INVENTORY_ITEM_CLASS));
        WebElement product = products.get(index);
        String productName = product.findElement(By.className(INV_ITEM_NAME)).getText();
        String productPrice = product.findElement(By.className(INV_ITEM_PRICE)).getText();
        product.findElement(By.className(BTN_PRIMARY_CLASS)).click();
        Thread.sleep(SLEEP_TIMER);
        logger.info("Added product: " + productName + " with price: " + productPrice);
        return new ProductDetails(productName, productPrice);
    }

    private void goToCart() throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        driver.findElement(By.className("shopping_cart_link")).click();
        logger.info("Clicking on cart");
    }

    private void checkout() throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        logger.info("Clicking checkout");
        driver.findElement(By.id("checkout")).click();
    }

    private void fillCheckoutForm(String firstName, String lastName, String postalCode) throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        logger.info("Filling in the checkout form");
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();
    }

    private void validateCheckoutOverview(ProductDetails product1, ProductDetails product2) throws InterruptedException {
        Thread.sleep(SLEEP_TIMER);
        String checkoutProduct1Name = driver.findElements(By.className(INV_ITEM_NAME)).get(0).getText();
        String checkoutProduct2Name = driver.findElements(By.className(INV_ITEM_NAME)).get(1).getText();
        String checkoutProduct1Price = driver.findElements(By.className(INV_ITEM_PRICE)).get(0).getText();
        String checkoutProduct2Price = driver.findElements(By.className(INV_ITEM_PRICE)).get(1).getText();

        logger.info("Validating checkoutOverview: Product 1 name and checkoutName: " + product1.getName() + " - " + checkoutProduct1Name);
        logger.info("Validating checkoutOverview: Product 2 name and checkoutName: " + product2.getName() + " - " + checkoutProduct2Name);
        logger.info("Validating checkoutOverview: Product 1 price and checkoutPrice: " + product1.getPrice() + " - " + checkoutProduct1Price);
        logger.info("Validating checkoutOverview: Product 2 price and checkoutPrice: " + product2.getPrice() + " - " + checkoutProduct2Price);

        Assert.assertEquals(checkoutProduct1Name, product1.getName(), "Product 1 name does not match");
        Assert.assertEquals(checkoutProduct2Name, product2.getName(), "Product 2 name does not match");
        Assert.assertEquals(checkoutProduct1Price, product1.getPrice(), "Product 1 price does not match");
        Assert.assertEquals(checkoutProduct2Price, product2.getPrice(), "Product 2 price does not match");

        logger.info("Test 'Checkout Overview Validation' passed");
    }

    private static class ProductDetails {
        private String name;
        private String price;

        public ProductDetails(String name, String price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }
}
