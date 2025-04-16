import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

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

        // Step 1: Enter username
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("RatedX_CEN4072"); // Use your actual valid username
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();

        // Step 2 (Optional): If Twitter asks for email or phone confirmation
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement emailField = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
            emailField.sendKeys("jmdominguez8203@eagle.fgcu.edu");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[text()='Next']")).click();
        } catch (TimeoutException e) {
            // No email confirmation step, continue to password
        }

        // Step 3: Enter password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("CEN4072!"); // Use actual password
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();

        // Step 4: Assert login success
        boolean isHomeVisible = wait.until(ExpectedConditions.urlContains("/home"));
        Assert.assertTrue(isHomeVisible, "Login failed or home page not loaded.");
    }

    @Test(priority = 2)
    public void testTwitterLogout() throws InterruptedException {
        // Step 1: Click on the profile icon to open the logout menu
        WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='react-root']/div/div/div[2]/header/div/div/div/div[2]/div/button/div[2]/div/div[2]/div/div/div/span")));
        Thread.sleep(1000);
        profileButton.click();

        // Step 2: Click the "Log out" option from the dropdown
        WebElement logoutOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div[2]/div/div[2]/div/div/div/div/div/a[2]/div[1]/div")));
        Thread.sleep(1000);
        logoutOption.click();

        // Step 3: Confirm logout from the popup
        WebElement confirmLogoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div[2]/button[1]/div/span/span")));
        Thread.sleep(1000);
        confirmLogoutButton.click();

        // Step 4: Validate that we are back on the login page
        boolean isLoginPage = wait.until(ExpectedConditions.urlContains("/logout"));
        Assert.assertTrue(isLoginPage, "Logout failed or login page not reached.");

        //Step 5: Return to login page
        WebElement SignInButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div[1]/div[1]/div/div[3]/div[4]/a/div/span/span")));
        Thread.sleep(1000);
        SignInButton.click();
    }

    @Test(priority = 3)
    public void testInvalidLogin() throws InterruptedException {

        // Step 1: Try invalid username
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("NotARealUser34072");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();

        // Step 2: Enter valid username after failure
        usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.clear(); // Clear the previous entry
        usernameField.sendKeys("RatedX_CEN4072");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();

        // Step 3: Enter invalid password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("invalid_pass");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();

        // Step 4: Assert error message is displayed
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'could not') or contains(text(),'Wrong Password')]")
        ));
        Assert.assertTrue(error.isDisplayed(), "Error message not displayed for invalid login.");
    }


    @Test(priority = 4)
    public void testForgotPasswordLink() throws InterruptedException {
        // Click "Forgot password?"
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Forgot password?']/ancestor::button")
        ));
        Assert.assertTrue(forgotPasswordLink.isDisplayed(), "Forgot password link is not visible.");
        forgotPasswordLink.click();



        // Click "Next" button
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/div/div/div/button/div")
        ));
        nextButton.click();
    }


    @Test(priority = 5)
    public void testSignUpButton() throws InterruptedException {
        driver.get("https://twitter.com/login");

        // Wait for the "Don't have an account? Sign up" section to load
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Sign up']")));
        Thread.sleep(1000);

        signUpButton.click();
        Thread.sleep(2000);

        // Optional: wait and verify that you're now on the SignUp page
        boolean isSignUpPage = wait.until(ExpectedConditions.urlContains("/i/flow/signup"));
        Assert.assertTrue(isSignUpPage, "Sign Up page did not load as expected.");
    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
