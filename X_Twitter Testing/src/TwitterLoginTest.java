import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class TwitterLoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void testTwitterLogin() throws InterruptedException {
        driver.get("https://twitter.com/login");
        Thread.sleep(2000);

        // Step 1: Try invalid username
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("RatedX_CEN4073");
        //Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        //Thread.sleep(2000);

        // Step 2: Use valid username
        usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("RatedX_CEN4072"); // Replace with actual valid username
        //Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        //Thread.sleep(2000);

        // Step 3: Enter invalid password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("ThisIsClearlyTheWrongPasswordBecauseOfHowLongItIs");
        //Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        //Thread.sleep(2000);

        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("CEN4072!"); // Replace with actual
        //Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        //Thread.sleep(3000);

        boolean isHomeVisible = wait.until(ExpectedConditions.urlContains("/home"));
        Assert.assertTrue(isHomeVisible, "Login failed or home page not loaded.");
    }


    @Test(priority = 2)
    public void testSearchFollowNotifyLikeFGCU() throws InterruptedException {
        // Step 1: Wait for home page
        wait.until(ExpectedConditions.urlContains("/home"));

        // Step 2: Search for FGCU
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.sendKeys("Florida Gulf Coast University");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000); // Wait for profile page

        // Step 3: Follow the account if not already followed
        List<WebElement> followBtns = driver.findElements(By.xpath("//div[@data-testid='placementTracking']//span[text()='Follow']"));
        if (!followBtns.isEmpty()) {
            followBtns.get(0).click();
            System.out.println("Follow button clicked.");
            Thread.sleep(2000);
        } else {
            System.out.println("Already following or Follow button not found.");
        }

        // Step 4: Turn on notifications
        try {
            WebElement notifyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[2]/div[1]/button")));
            notifyBtn.click();
            System.out.println("Notifications turned on.");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Notification bell not found or already enabled.");
        }

        // Step 5: Like the first post
        try {
            WebElement firstLikeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"id__eq0e95ftlln\"]/div[3]/button/div/div[1]/svg/g/path")));
            firstLikeButton.click();
            System.out.println("First post liked.");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Unable to like the first post.");
        }

        // Step 6: Verify on FGCU profile
        WebElement profileHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Florida Gulf Coast University')]")));
        Assert.assertTrue(profileHeader.isDisplayed(), "FGCU profile page did not load.");
    }




    @Test(priority = 3)
    public void testInvalidLogin() throws InterruptedException {
        driver.get("https://twitter.com/login");
        Thread.sleep(2000);

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("invalid_user");
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        Thread.sleep(2000);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("invalid_pass");
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        Thread.sleep(3000);

        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'could not')]"));
        Assert.assertTrue(error.isDisplayed(), "Error message not displayed for invalid login.");
    }

    @Test(priority = 4)
    public void testForgotPasswordLink() {
        driver.get("https://twitter.com/login");
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Forgot password?"));
        Assert.assertTrue(forgotPasswordLink.isDisplayed(), "Forgot password link is not visible.");
        forgotPasswordLink.click();
    }

    @Test(priority = 5)
    public void testRememberMeCheckboxIfPresent() {
        driver.get("https://twitter.com/login");

        // Just a placeholder — Twitter login may not have a checkbox; adapt if necessary
        boolean rememberMeExists = driver.findElements(By.name("remember_me")).size() > 0;
        System.out.println("Remember Me checkbox exists: " + rememberMeExists);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
