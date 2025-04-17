import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TwitterProfileTest {

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
    public void createExpandedProfile() throws InterruptedException {

        // Click Profile Button
        WebElement profileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[8]")));
        profileButton.click();
        Thread.sleep(3000);

        // Click Edit Profile Button
        WebElement editProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[2]/a")));
        editProfileButton.click();
        Thread.sleep(3000);

        // Click Edit Expanded Profile
        WebElement createdExpandedProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/a[1]")));
        createdExpandedProfileButton.click();
        Thread.sleep(3000);

        // Click Work History Button
        WebElement workHistoryButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div[2]/div[2]/button")));
        workHistoryButton.click();
        Thread.sleep(3000);

        // Edit Work History Title Textbox
        WebElement workHistoryTitleTextbox = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]/label/div/div[2]/div/input"));
        workHistoryTitleTextbox.click();
        workHistoryTitleTextbox.clear();
        workHistoryTitleTextbox.sendKeys("Dummy");
        Thread.sleep(3000);

        // Edit Work History Company Textbox
        WebElement workHistoryCompanyTextbox = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[2]/div/div/div/form/div[1]/div/label/div/div[2]/div/input"));
        workHistoryCompanyTextbox.click();
        workHistoryCompanyTextbox.clear();
        workHistoryCompanyTextbox.sendKeys("FGCU");
        Thread.sleep(3000);

        // Check The "Currently Work Here" Checkbox
        WebElement currentlyWorkHereCheckbox = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[4]/div/label/div/div/input"));
        currentlyWorkHereCheckbox.click();
        Thread.sleep(3000);

        // Select Month Drop Down Menu
        WebElement monthDropDownMenu = driver.findElement(By.xpath("//*[@id=\"SELECTOR_1\"]"));
        Select monthDropDown = new Select(monthDropDownMenu);
        monthDropDown.selectByIndex(4);
        Thread.sleep(3000);

        // Save Changes To Profile
        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/button[2]/div/span/span"));
        saveButton.click();
        Thread.sleep(3000);
    }
    
    @Test(priority = 2)
    public void viewFollowersAndFollowing() throws InterruptedException {

        // Click Profile Button
        WebElement profileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[8]")));
        profileButton.click();
        Thread.sleep(3000);

        // Click On Following
        WebElement followingButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[6]/div[1]/a/span[2]/span")));
        followingButton.click();
        Thread.sleep(3000);

        // Navigate To Verified Followers
        WebElement verifiedFollowersButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[1]/div[1]/div[2]/nav/div/div[2]/div/div[1]/a/div/div/span")));
        verifiedFollowersButton.click();
        Thread.sleep(3000);

        // Navigate To Followers
        WebElement FollowersButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[1]/div[1]/div[2]/nav/div/div[2]/div/div[2]/a")));
        FollowersButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void editProfileBio() throws InterruptedException {

        // Click Profile Button
        WebElement profileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[8]")));
        profileButton.click();
        Thread.sleep(3000);

        // Click Edit Profile Button
        WebElement editProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[2]/a")));
        editProfileButton.click();
        Thread.sleep(3000);

        // Edit Bio
        WebElement profileBio = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[4]/label/div/div[2]/div/textarea")));
        profileBio.click();
        profileBio.clear();
        profileBio.sendKeys("This Is A Bio For The Twitter Account: RatedX_CEN4072");
        Thread.sleep(3000);

        // Save Changes To Profile
        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div/div/div/div[3]/button"));
        saveButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 4)
    public void editProfileLocation() throws InterruptedException {

        // Click Profile Button
        WebElement profileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[8]")));
        profileButton.click();
        Thread.sleep(3000);

        // Click Edit Profile Button
        WebElement editProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[2]/a")));
        editProfileButton.click();
        Thread.sleep(3000);

        // Edit Location
        WebElement profileLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div[5]/label/div/div[2]/div/input")));
        profileLocation.click();
        profileLocation.clear();
        profileLocation.sendKeys("I Am Currently Located At FGCU");
        Thread.sleep(3000);

        // Save Changes To Profile
        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div/div/div/div/div[3]/button"));
        saveButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 5)
    public void screenshotProfilePicture() throws InterruptedException, IOException {

        // Go To Profile Button
        WebElement profileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[8]")));
        profileButton.click();
        Thread.sleep(3000);

        // Click Profile Picture
        WebElement profilePicture = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div[1]/div[2]/div/div[2]/div/a")));
        profilePicture.click();
        Thread.sleep(3000);

        // Screenshot The Profile Photo
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("C:\\Users\\XxBMR\\Desktop\\TestNGScreenshot.png");
        FileHandler.copy(srcFile, destFile);
        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}