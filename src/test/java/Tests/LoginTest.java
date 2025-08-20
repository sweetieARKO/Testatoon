package Tests;

import Pages.LoginPage;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;
    private Eyes eyes;

    @BeforeTest
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY")); // Use your Ap
    }

    @Test
    public void testLogin() {
        driver.get("https://www.saucedemo.com");
        eyes.open(driver, "SauceDemo", "Login Test");
        eyes.checkWindow("Login Page");
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        eyes.checkWindow("After Login");
        eyes.close();
    }
    @Test
    public void loginWithFaceID() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.simulateFaceID();
        // Optional: verify dashboard or success message
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
