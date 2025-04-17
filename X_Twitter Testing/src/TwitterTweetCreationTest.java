package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class TwitterTweetCreationTest {
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

    @Test (priority = 1)
    public void TestTwitterTweetCreation() throws InterruptedException {
        String tweet = "Test tweet " + (int)(Math.random() * 100000);

        // Open Tweet Creation Dialog
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/header/div/div/div/div[1]/div[3]/a")).click();
        Thread.sleep(2000);
        // Enter Post Text
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div")).sendKeys(tweet);
        Thread.sleep(1000);
        // Post Tweet
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div[2]/div[2]/div/div/div/button[2]/div/span/span")).click();
        Thread.sleep(2000);

        // Verify the Tweet Creation Dialog is gone
        By postDialog = By.xpath("//*[@id='layers']/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]");
        boolean dialogGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(postDialog));
        Assert.assertTrue(dialogGone, "Post dialog did not disappear.");
        System.out.println("Assertion passed: Post dialog is closed.");
    }

    @Test (priority = 2)
    public void TestTwitterDeleteTweets() throws InterruptedException {
        driver.get("https://x.com/RatedX_CEN4072");
        WebElement moreButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div[4]/div[1]/div/article/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div[2]/div/div/button")));
        moreButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div[2]/div/div[3]/div/div/div/div[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div[2]/button[1]")).click();
        Thread.sleep(2000);
    }

    @Test (priority = 3)
    public void TestTwitterGrokTweetAnalysis() throws InterruptedException {
        driver.get("https://x.com/RatedX_CEN4072");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("svg.r-4qtqp9.r-yyyyoo.r-dnmrzs.r-bnwqim.r-lrvibr.r-m6rgpd.r-1xvli5t.r-1hdv0qi")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("textarea[placeholder='Ask anything']")).sendKeys("Thank you Grok!");
        Thread.sleep(7000);
        driver.findElement(By.xpath("//*[@id=\'layers\']/div/div[1]/div/div/div/div[2]/div[2]/div/div/div/div/div/div[2]/div[2]/button/div/div")).click();
        Thread.sleep(2000);

        By popupTextLocator = By.xpath("//*[@id='layers']/div/div[1]/div/div/div/div[2]/div[1]/div/div[4]/div/div/div[1]/div[2]/div/div/span");
        WebElement popupText = wait.until(ExpectedConditions.visibilityOfElementLocated(popupTextLocator));
        Assert.assertTrue(popupText.isDisplayed(), "Grok did not respond.");
        System.out.println("Assertion passed: Grok responded.");

}

    @Test (priority = 4)
    public void TestTwitterTweetPicture() throws InterruptedException {
        driver.get("https://x.com/");
        Thread.sleep(2000);
        // Open Tweet Creation Dialog
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/header/div/div/div/div[1]/div[3]/a")).click();
        Thread.sleep(2000);
        // Upload Picture
        //driver.findElement(By.cssSelector("button[aria-label='Add photos or video']")).click();
        Thread.sleep(2000);
        //
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-testid='fileInput']")));
        fileInput.sendKeys("/home/john/School/Spring_2025/Software Testing/Project/Rated_X/src/test/java/Test/eagle.jpg");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div[2]/div[2]/div/div/div/button[2]/div/span/span")).click();
        Thread.sleep(2000);

        // Verify the Tweet Creation Dialog is gone
        By postDialog = By.xpath("//*[@id='layers']/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]");
        boolean dialogGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(postDialog));
        Assert.assertTrue(dialogGone, "Post dialog did not disappear.");
        System.out.println("Assertion passed: Post dialog is closed.");
}

    @Test (priority = 5)
    public void TestTwitterTweetComment() throws InterruptedException {
        String reply = "Reply #" + (int)(Math.random() * 100000);

        // Go to profile page
        driver.get("https://x.com/RatedX_CEN4072");
        Thread.sleep(3000);
        // reply to latest tweet
        driver.findElement(By.cssSelector("button[data-testid='reply']")).click();
        Thread.sleep(2000);
        // enter text for reply
        driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/div[2]/div/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/div/div")).sendKeys(reply);
        Thread.sleep(2000);
        // post reply
        driver.findElement(By.cssSelector("button[data-testid='tweetButton']")).click();

        Thread.sleep(2000);

        // Verify the Reply Dialog is gone
        By postDialog = By.xpath("//*[@id='layers']/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]");
        boolean dialogGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(postDialog));
        Assert.assertTrue(dialogGone, "Post dialog did not disappear.");
        System.out.println("Assertion passed: Post dialog is closed.");
    }




    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
