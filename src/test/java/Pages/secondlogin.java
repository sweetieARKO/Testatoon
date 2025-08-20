package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class secondlogin {
    WebDriver driver;
    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginBtn = By.id("login-button");
    By faceIDBtn = By.id("faceid-button");         // Simulated
    By fingerprintBtn = By.id("fingerprint-button"); // Simulated

    public secondlogin(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }

    public void simulateFaceID() {
        driver.findElement(faceIDBtn).click();
    }

    public void simulateFingerprint() {
        driver.findElement(fingerprintBtn).click();
    }
}
