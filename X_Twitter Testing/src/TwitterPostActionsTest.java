import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class TwitterPostActionsTest {
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
        passwordField.sendKeys("CEN4072!"); // Replace with actual valid password
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        Thread.sleep(1000);

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.click();
        searchInput.sendKeys("Rated:X");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ARROW_DOWN); // Skip first result
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ARROW_DOWN); // Reach FGCU profile
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("RatedX"), "Did not navigate to FGCU profile.");
    }

    @Test(priority = 1)
    public void testLikePost() throws InterruptedException {
        driver.findElement(By.cssSelector("button[data-testid='like']")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button[data-testid='unlike']")).click();
        Thread.sleep(2000);
        System.out.println("Post liked/unliked.");
    }

    @Test(priority = 2)
    public void testRetweetPost() throws InterruptedException {
        // Click on the main retweet button on the tweet
        driver.findElement(By.cssSelector("button[data-testid='retweet']")).click();
        Thread.sleep(1000); // slight delay for popup to appear

        // Click on the confirm retweet option in the modal
        try {
            WebElement confirmRetweet = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='menu']//div[@data-testid='retweetConfirm']")));
            confirmRetweet.click();
            System.out.println("Successfully retweeted the post.");
        } catch (TimeoutException e) {
            Assert.fail("Retweet confirmation button did not appear.");
        }
    }

    @Test(priority = 3)
    public void testTwitterTweetComment() throws InterruptedException {
        String reply = "Reply #" + (int)(Math.random() * 100000);
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

    @Test(priority = 4)
    public void testBookmarkPost() throws InterruptedException {
        driver.findElement(By.cssSelector("button[data-testid='bookmark']")).click();
        Thread.sleep(2000);
        System.out.println("Post bookmarked.");
    }

    @Test(priority = 5)
    public void testSharePost() throws InterruptedException {
        WebElement firstTweet = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("article[data-testid='tweet']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstTweet);

        // Click the share button
        WebElement shareBtn = firstTweet.findElement(By.xpath(".//button[@aria-label='Share post']"));
        wait.until(ExpectedConditions.elementToBeClickable(shareBtn)).click();

        // Click "Send via Direct Message"
        WebElement dmButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='menuitem']//span[text()='Send via Direct Message']")));
        dmButton.click();
        Thread.sleep(2000);

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.sendKeys("Juno");
        Thread.sleep(1000);
        searchInput.sendKeys(Keys.ARROW_DOWN);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        // Wait for composer to be ready and type the message
        Thread.sleep(1000); // Optional delay to ensure composer is focused
        Actions actions = new Actions(driver);
        actions.sendKeys("Hey there! Just testing automation 🚀").perform();

        // Click the "Send" button
        driver.findElement(By.cssSelector("button[data-testid='dmComposerSendButton']")).click();
        Thread.sleep(3000);

        System.out.println("Direct Message sent successfully.");
    }




    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
