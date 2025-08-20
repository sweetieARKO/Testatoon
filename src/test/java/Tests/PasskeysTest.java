package Tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PasskeysTest {
    private WebDriver driver;
    private Eyes eyes;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        eyes = new Eyes();

        eyes.setApiKey(System.getenv("GImvICL104UieANAJ2XLCbtNcPPsReeOWo6IxlQHX3aaM110"));

}

    @Test
    public void registerWithFaceID() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Registration", new RectangleSize(1024, 768));
        eyes.checkWindow("Landing Page");

        eyes.checkWindow("After FaceID Registration");
    }

    @Test(dependsOnMethods = "registerWithFaceID")
    public void loginWithFaceID() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Login", new RectangleSize(1024, 768));
        eyes.checkWindow("Login Page");

        eyes.checkWindow("After FaceID Login");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();

    }
}


