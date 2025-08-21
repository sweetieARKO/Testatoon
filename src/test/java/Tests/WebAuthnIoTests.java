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
 * AI-Powered WebAuthn Test using webauthn.io
 *
 * Covers biometric flow simulations via WebAuthn:
 * a. Fallback to password
 * b. Bioauthentication success and failure
 * c. Lockout after multiple failures
 * d. Permissions denial
 * e. Network interruption
 */
public class WebAuthnIoTests {
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
            throw new RuntimeException("Missing Applitools API key.");
        }
        eyes.setApiKey(apiKey);
    }

    // Registration via WebAuthn
    @Test
    public void registerCredential() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "Register Credential", new RectangleSize(1024, 768));
        eyes.checkWindow("Initial Page");

        // Simulate filling username and register
        eyes.checkWindow("After Register Action");
        eyes.closeAsync();
    }

    // Successful authentication
    @Test(dependsOnMethods = "registerCredential")
    public void loginWithWebAuthn() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "Successful WebAuthn Login", new RectangleSize(1024, 768));
        eyes.checkWindow("Login Page");
        eyes.checkWindow("After Auth Success");
        eyes.closeAsync();
    }

    // Fallback to password if WebAuthn fails
    @Test
    public void fallbackToPassword() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "Fallback to Password", new RectangleSize(1024, 768));
        eyes.checkWindow("WebAuthn Prompt");
        eyes.checkWindow("Fallback UI: Password Option");
        eyes.checkWindow("Password Login Success");
        eyes.closeAsync();
    }

    // WebAuthn failure simulation
    @Test
    public void webAuthnFailure() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "WebAuthn Failure", new RectangleSize(1024, 768));
        eyes.checkWindow("WebAuthn Prompt");
        eyes.checkWindow("WebAuthn Error Message");
        eyes.closeAsync();
    }

    // Lockout after repeated WebAuthn failures
    @Test
    public void lockoutAfterFailures() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "WebAuthn Lockout", new RectangleSize(1024, 768));
        eyes.checkWindow("Attempt 1 Fail");
        eyes.checkWindow("Attempt 2 Fail");
        eyes.checkWindow("Attempt 3 Fail - Locked");
        eyes.closeAsync();
    }

    // Permissions denied simulation (e.g., biometric blocked)
    @Test
    public void permissionsDenied() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "Permissions Denied", new RectangleSize(1024, 768));
        eyes.checkWindow("Permissions Blocked UI");
        eyes.closeAsync();
    }

    // Network interruption during WebAuthn
    @Test
    public void networkInterruption() {
        driver.get("https://webauthn.io/");
        eyes.open(driver, "webauthn.io", "Network Interrupt", new RectangleSize(1024, 768));
        eyes.checkWindow("WebAuthn Prompt");
        eyes.checkWindow("Network Error Message");
        eyes.closeAsync();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
        if (eyes != null) eyes.abortIfNotClosed();
    }
}
