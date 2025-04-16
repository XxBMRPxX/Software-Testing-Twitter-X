import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class TwitterNotificationTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws InterruptedException {

        // Chrome WebDriver
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
        Thread.sleep(3000);
    }


    @Test(priority = 1)
    public void testTopNotification() throws InterruptedException {
        WebElement notificationsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[3]")));
        notificationsButton.click();
        Thread.sleep(3000);

        WebElement topNotification = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div/div/div[3]/section/div/div/div[1]"));
        topNotification.click();
        Thread.sleep(3000);

        WebElement gotItConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/div/div[2]/div[2]/button")));
        gotItConfirmation.click();
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void testBottomNotification() throws InterruptedException {
        WebElement notificationsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[3]")));
        notificationsButton.click();
        Thread.sleep(3000);

        // Scroll Down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(3000);

        // Bottom Notification
        WebElement bottomNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div/div/div[3]/section/div/div/div[27]/div[1]/div/article")));
        bottomNotification.click();
        Thread.sleep(3000);

        WebElement gotItConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/div/div[2]/div[2]/button")));
        gotItConfirmation.click();
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void testVerifiedNotifications() throws InterruptedException {

        WebElement notificationsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[3]")));
        notificationsButton.click();
        Thread.sleep(3000);

        WebElement verifiedButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div/div/div[1]/div[1]/div[2]/nav/div/div[2]/div/div[2]/a")));
        verifiedButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 4)
    public void testMentionNotifications() throws InterruptedException {

        WebElement notificationsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[3]")));
        notificationsButton.click();
        Thread.sleep(3000);

        WebElement verifiedButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div/div/div[1]/div[1]/div[2]/nav/div/div[2]/div/div[3]/a")));
        verifiedButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 5)
    public void testNotificationSettings() throws InterruptedException {

        // Click Notifications Tab
        WebElement notificationsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[3]")));
        notificationsButton.click();
        Thread.sleep(3000);

        // Click Notification Settings Button
        WebElement settingsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div/div/div[1]/div[1]/div[1]/div/div/div/div/div[2]/a")));
        settingsButton.click();
        Thread.sleep(3000);

        // Click Filters Button
        WebElement settingsFilterButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/a[1]")));
        settingsFilterButton.click();
        Thread.sleep(3000);

        // Click Quality Filter Checkbox
        WebElement qualityFilterCheckbox = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div[2]/div/div/label/div/div[2]/input"));
        qualityFilterCheckbox.click();
        Thread.sleep(3000);

        // Go Back
        WebElement backButton = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[1]/div/div/div/div/div[1]/button"));;
        backButton.click();
        Thread.sleep(3000);

        // Click Preferences Button
        WebElement preferencesButton = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/a[2]"));
        preferencesButton.click();
        Thread.sleep(3000);

        // Click Email Preferences
        WebElement emailPreferencesButton = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/a[2]"));;
        emailPreferencesButton.click();
        Thread.sleep(3000);

        // Click Direct Messages Notification Checkbox
        WebElement directMessagesCheckbox = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/div[5]/div/label/div/div[2]/input"));
        directMessagesCheckbox.click();
        Thread.sleep(3000);

        // Click Daily Radio Button
        WebElement dailyRadioButton = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/div[7]/div/label[2]/div/div[2]/input"));
        dailyRadioButton.click();
        Thread.sleep(3000);

        // Click Post Performance Notification Checkbox
        WebElement postPerformanceCheckbox = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/div[8]/div/label/div/div[2]/input"));
        postPerformanceCheckbox.click();
        Thread.sleep(3000);

        // Email Notification Switch
        WebElement toggleEmailNotification = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div[2]/div/div[1]/div/label/div[1]/div[2]/input"));
        toggleEmailNotification.click();
        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}