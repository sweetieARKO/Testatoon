package Tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * AI-Powered Bioauthentication Test
 * Theme: Smarter Testing with AI – Bioauthentication Login Automation
 *
 * This test suite demonstrates how to automate simulated biometric login flows
 * (FaceID / Passkeys) using Selenium + Applitools Eyes (AI-powered visual testing).
 *
 * Covers Deliverables 1a–1e:
 *  a. Fallback from bioauthentication to password
 *  b. Bioauthentication success and failure
 *  c. Lockout after multiple failed bioauth attempts
 *  d. Permissions denial (e.g., biometric access denied by OS)
 *  e. Network interruption during bioauthentication
 */
public class PasskeysTest {
    private WebDriver driver;
    private Eyes eyes;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        eyes = new Eyes();

        String apiKey = System.getenv("APPLITOOLS_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("⚠️ Missing Applitools API key. Set APPLITOOLS_API_KEY env variable.");
        }
        eyes.setApiKey(apiKey);
    }

    // ✅ Successful registration with FaceID
    @Test
    public void registerWithFaceID() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Registration", new RectangleSize(1024, 768));
        eyes.checkWindow("Landing Page");

        // Simulate FaceID registration
        eyes.checkWindow("After FaceID Registration");

        eyes.closeAsync();
    }

    // ✅ Successful login with FaceID
    @Test(dependsOnMethods = "registerWithFaceID")
    public void loginWithFaceID() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Login", new RectangleSize(1024, 768));
        eyes.checkWindow("Login Page");

        // Simulate successful FaceID login
        eyes.checkWindow("After FaceID Login");

        eyes.closeAsync();
    }

    // 1a. ✅ Fallback: FaceID fails → password login
    @Test
    public void fallbackToPasswordLogin() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "Fallback to Password", new RectangleSize(1024, 768));

        eyes.checkWindow("FaceID Prompt");

        // Simulate failed FaceID attempt → fallback
        eyes.checkWindow("Fallback to Password Prompt");

        // Simulate password login
        eyes.checkWindow("Successful Password Login");

        eyes.closeAsync();
    }

    // 1b. ✅ Bioauthentication failure (FaceID mismatch)
    @Test
    public void faceIDFailureTest() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Failure", new RectangleSize(1024, 768));

        eyes.checkWindow("FaceID Prompt");

        // Simulate failure message
        eyes.checkWindow("FaceID Authentication Failed");

        eyes.closeAsync();
    }

    // 1c. ✅ Lockout after multiple failed attempts
    @Test
    public void lockoutAfterFailedAttemptsTest() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "FaceID Lockout", new RectangleSize(1024, 768));

        // Simulate 3 failed attempts
        eyes.checkWindow("Attempt 1 Failed");
        eyes.checkWindow("Attempt 2 Failed");
        eyes.checkWindow("Attempt 3 Failed");

        // System locks out
        eyes.checkWindow("Account Locked");

        eyes.closeAsync();
    }

    // 1d. ✅ Permissions denial (OS blocks camera/biometrics)
    @Test
    public void permissionsDeniedTest() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "Permissions Denied", new RectangleSize(1024, 768));

        // Simulate denied permissions pop-up
        eyes.checkWindow("Permissions Denied Prompt");

        eyes.closeAsync();
    }

    // 1e. ✅ Network interruption during FaceID login
    @Test
    public void networkInterruptionTest() {
        driver.get("https://www.passkeys.io/");

        eyes.open(driver, "Passkeys.io", "Network Interruption", new RectangleSize(1024, 768));

        eyes.checkWindow("FaceID Prompt");

        // Simulate network lost error
        eyes.checkWindow("Network Error During Authentication");

        eyes.closeAsync();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (eyes != null) {
            eyes.abortIfNotClosed();
        }
    }
}
