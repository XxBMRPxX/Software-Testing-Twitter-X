import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;


public class TwitterSearchFunctionality {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception {
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
    public void testSearchSuggestionsAppear() throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.sendKeys("Nature");
        Thread.sleep(2000);

        List<WebElement> suggestions = driver.findElements(By.xpath("//div[@role='listbox']//span"));
        Assert.assertTrue(suggestions.size() > 0, "Search suggestions did not appear.");
    }

    @Test(priority = 2)
    public void testSearchWithEnterKey() throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        Assert.assertTrue(driver.getCurrentUrl().contains("search"), "Search did not navigate to results page.");
    }

    @Test(priority = 3)
    public void testSearchTabsNavigation() throws InterruptedException {
        // Wait for the tab bar to load after search
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//nav")));

        String[] tabNames = {"Top", "Latest", "People", "Media", "Lists"};
        for (String tab : tabNames) {
            try {
                WebElement tabElement = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[text()='" + tab + "']/ancestor::a")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabElement);
                Thread.sleep(500);
                tabElement.click();
                Thread.sleep(6000); // Let results load
                System.out.println("Navigated to tab: " + tab);
            } catch (TimeoutException | NoSuchElementException e) {
                Assert.fail("Failed to navigate to tab: " + tab, e);
            }
        }
    }

    @Test(priority = 4)
    public void testClearSearchInput() throws InterruptedException {
        // Focus the search input
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.click();
        Thread.sleep(1000);

        // Select all and delete
        searchInput.sendKeys(Keys.chord(Keys.COMMAND, "a")); // or Keys.COMMAND for Mac
        searchInput.sendKeys(Keys.DELETE);
        Thread.sleep(2000);

        // Re-check the input value
        String inputValue = searchInput.getAttribute("value");
        Assert.assertEquals(inputValue, "", "Search input was not cleared using Select + Delete.");
    }

    @Test(priority = 5)
    public void testInvalidSearchQuery() throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.clear();
        searchInput.sendKeys("asdkjh1238123zxcv"); // gibberish
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        // Option 1: Look for "No results" message
        List<WebElement> noResultsText = driver.findElements(By.xpath("//*[contains(text(), 'No results')]"));

        // Option 2: Check number of tweets
        List<WebElement> tweets = driver.findElements(By.xpath("//article"));

        boolean noResultsFound = noResultsText.size() > 0 || tweets.size() < 2;
        Assert.assertTrue(noResultsFound, "Unexpected results found for invalid search.");
    }

    @Test(priority = 6)
    public void testNavigateToFGCUProfile() throws InterruptedException {
        // Clear previous search
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Search query']")));
        searchInput.click();
        searchInput.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        searchInput.sendKeys(Keys.DELETE);
        Thread.sleep(1000);

        // Enter new search and wait for suggestions
        searchInput.sendKeys("Florida Gulf Coast University");
        Thread.sleep(2000);

        // Move down twice to highlight the actual FGCU profile
        searchInput.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(500);
        searchInput.sendKeys(Keys.ARROW_DOWN); // second option = profile
        Thread.sleep(500);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(3000); // Wait for profile to load

        // Verify FGCU profile loaded
        WebElement profileHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@data-testid='UserName']//span[contains(text(), 'Florida Gulf Coast University')]")));

        Assert.assertTrue(profileHeader.isDisplayed(), "Did not navigate to FGCU profile.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
