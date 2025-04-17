import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class TwitterDirectMessagingTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions action;

    @BeforeClass
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        action = new Actions(driver);

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
    public void testReactingToMessage() throws InterruptedException {
        // Open Messages
        WebElement messagesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#react-root > div > div > div.css-175oi2r.r-1f2l425.r-13qz1uu.r-417010.r-18u37iz > header > div > div > div > div.css-175oi2r.r-1habvwh > div.css-175oi2r.r-15zivkp.r-1bymd8e.r-13qz1uu > nav > a:nth-child(4)")));
        messagesButton.click();
        Thread.sleep(3000);

        // Open conversation
        WebElement messagesUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[1]/div/div/div[2]/section/div/div/div[2]/div/div[1]")));
        messagesUser.click();
        Thread.sleep(3000);

        // Hover over message so react button appears
        WebElement message = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div/div/span"));
        action.moveToElement(message).perform();
        Thread.sleep(1000);

        // Select reaction option
        WebElement reactButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div/div[1]/div/div[1]/div/div/button")));
        reactButton.click();
        Thread.sleep(3000);

        // Select reaction
        WebElement reactionSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div[2]/div/div/div/div/div/button[6]")));
        reactionSelection.click();
        Thread.sleep(3000);

        // Select reaction option
        WebElement reactionOptions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div/div[2]/div[1]/div/div/button")));
        reactionOptions.click();
        Thread.sleep(3000);

        // Select reaction undo
        WebElement reactionUndo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div[2]/div/div[2]/div/div/div/div/div/div[2]/div/div[4]/button")));
        reactionUndo.click();
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void testSendingMessage() throws InterruptedException {
        // Start a new message
        WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/aside/div[2]/div[2]/div/div/div/div/div/div/div[1]/div/div/div/div/div")));
        messageBox.click();
        Thread.sleep(3000);

        // Input message
        action.sendKeys("This is an automated message!").perform();

        // Click send message
        action.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void testReplyingMessage() throws InterruptedException {
        // Hover over message so options button appears
        WebElement message = driver.findElement(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div"));
        action.moveToElement(message).perform();
        Thread.sleep(1000);

        // Select more options button
        WebElement moreOptionsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/div/div/div[1]/div/div/div[2]/div/div[1]/div/div[1]/button")));
        moreOptionsButton.click();
        Thread.sleep(3000);

        // Start reply
        WebElement replyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div[2]/div/div[3]/div/div/div/div[1]")));
        replyButton.click();
        Thread.sleep(3000);

        // Input message
        action.sendKeys("This is an automated reply!");

        // Send message
        action.sendKeys(Keys.ENTER).build().perform();
    }

    @Test(priority = 4)
    public void testEmoji() throws InterruptedException {
        // Open emoji panel
        WebElement emojiButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/aside/div[2]/div[1]/div/button")));
        emojiButton.click();
        Thread.sleep(3000);

        // Select emoji
        action.sendKeys("test").build().perform();
        Thread.sleep(3000);

        WebElement emoji = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"emoji_picker_categories_dom_id\"]/div/div[1]/div/div[2]/div/div/div[1]/div")));
        emoji.click();
        Thread.sleep(3000);

        // Exit emoji panel
        action.sendKeys(Keys.ESCAPE).build().perform();
        Thread.sleep(3000);

        // Send emoji
        action.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(3000);
    }

    @Test(priority = 5)
    public void testSendGif() throws InterruptedException {
        // Open emoji panel
        WebElement gifButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/aside/div[2]/div[1]/button[2]")));
        gifButton.click();
        Thread.sleep(3000);

        // Select gif
        action.sendKeys("matrix hacking").build().perform();
        Thread.sleep(3000);

        WebElement gif = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[3]/div/div[1]/div/button[1]")));
        gif.click();
        Thread.sleep(3000);

        // Send gif
        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/section[2]/div/div/div[2]/div/div/aside/div[2]/button")));
        sendButton.click();
        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
