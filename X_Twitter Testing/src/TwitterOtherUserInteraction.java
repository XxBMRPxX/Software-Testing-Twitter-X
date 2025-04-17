import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class OtherUserInteraction {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Login
        driver.get("https://twitter.com/login");
        Thread.sleep(2000);
        // Username
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("text")));
        usernameField.sendKeys("RatedX_CEN4072"); // Replace with actual valid username
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        // Password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("CEN4072!"); // Replace with actual
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 1)
    public void testNavigateToFGCUProfile() throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.click();
        searchInput.sendKeys("FGCU");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ARROW_DOWN); // Skip first result
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ARROW_DOWN); // Reach FGCU profile
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("fgcu"), "Did not navigate to FGCU profile.");
    }

    @Test(priority = 2)
    public void followAndTurnOnNotifications() throws InterruptedException {
        WebElement followBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-testid='placementTracking']//span[text()='Follow']")));
        followBtn.click();
        Thread.sleep(2000);

        try {
            WebElement notifyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[2]/div[1]/button")));
            notifyBtn.click();
            System.out.println("Notifications turned on.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Notification bell not found or already enabled.");
        }
    }

    @Test(priority = 3)
    public void unfollowUser() throws InterruptedException {
        WebElement followingBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-testid='placementTracking']//span[text()='Following']")));
        followingBtn.click();
        Thread.sleep(2000);
        WebElement confirmUnfollow = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div[2]/button[1]")));
        confirmUnfollow.click();
        Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void muteUser() throws InterruptedException {
        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[1]/div[2]/button[1]")));
        moreBtn.click();
        Thread.sleep(2000);
        WebElement muteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div[2]/div/div[3]/div/div/div/div[3]")));
        muteBtn.click();
        Thread.sleep(4000);
    }

    @Test(priority = 5)
    public void unmuteUser() throws InterruptedException {
        WebElement unmuteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[7]/button")));
        unmuteBtn.click();
        Thread.sleep(2000);
        WebElement confirmUnmute = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div[2]/button[1]")));
        confirmUnmute.click();
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void blockUser() throws InterruptedException {
        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[1]/div[2]/button[1]")));
        moreBtn.click();
        Thread.sleep(2000);
        WebElement blockBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div[2]/div/div[3]/div/div/div/div[4]")));
        blockBtn.click();
        Thread.sleep(2000);
        WebElement confirmBlock = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Block']")));
        confirmBlock.click();
        Thread.sleep(2000);
    }


    @Test(priority = 7)
    public void unblockUser() throws InterruptedException {
        WebElement unblockBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div/div[1]/div[2]/div/div/button/div")));
        unblockBtn.click();
        Thread.sleep(2000);
        WebElement confirmUnblock = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div[2]/button[1]/div/span/span")));
        confirmUnblock.click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

