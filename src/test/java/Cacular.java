import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Cacular {
    private WebDriver driver;
    private Eyes eyes;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY")); // Use your Applitools API key
    }

    @Test
    public void testLogin() {
        driver.get("https://www.saucedemo.com");
        eyes.open(driver, "SauceDemo", "Login Test", new RectangleSize(1024, 768));
        eyes.checkWindow("Login Page");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        Assert.assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed());

        eyes.checkWindow("Inventory Page");
        eyes.closeAsync();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
        if (eyes != null) eyes.abortIfNotClosed();
    }
}