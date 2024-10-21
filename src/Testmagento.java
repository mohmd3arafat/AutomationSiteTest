import java.time.Duration;  // For managing timeouts
import java.util.Random;     // For generating random values
import org.openqa.selenium.By;   // For locating elements
import org.openqa.selenium.WebDriver;  // WebDriver to control the browser
import org.openqa.selenium.WebElement; // To interact with elements on the webpage
import org.openqa.selenium.chrome.ChromeDriver;  // ChromeDriver for Chrome browser
import org.openqa.selenium.interactions.Actions;  // To perform advanced user interactions like hover
import org.openqa.selenium.support.ui.Select;     // To handle dropdowns
import org.testng.Assert;
import org.testng.annotations.BeforeTest; // TestNG annotation for setup method
import org.testng.annotations.Test;       // TestNG annotation for test methods

public class Testmagento {

    // Declare WebDriver and initialize ChromeDriver
    WebDriver driver = new ChromeDriver();

    // Declare website and arrays for random names
    String myWebSite = "https://automationteststore.com/";
    String[] firstNames = { "mohammad", "ali", "ahmad", "obada", "hamza", "alaa", "sawsan" };
    String[] LastNames = { "awartani", "safoty", "abu_al_haija", "radaida" };

    String className = "";  // Will store the class name of "Add to Cart" button
    String theUserName = "";  // Will store the randomly generated login name

    // Create a Random object to generate random values
    Random rand = new Random();
    Actions actions = new Actions(driver); // Actions object to perform hover

    // Generate random indexes for first name, last name, and other fields
    int randomFname = rand.nextInt(firstNames.length);
    int randomLname = rand.nextInt(LastNames.length);
    int randomNum = rand.nextInt(99999);
    int randomCountry = rand.nextInt(1, 240);  // Random country selector
    int randomRegion = rand.nextInt(1, 6);     // Random region selector

    // Combine random values into useful variables
    String UserFirstName = firstNames[randomFname];
    String UserLastName = LastNames[randomLname];
    String UserEmail = UserFirstName + UserLastName + randomNum + "@gmail.com";
    String LoginName = UserFirstName + UserLastName + randomNum;

    // BeforeTest: Sets up the browser before the test
    @BeforeTest
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));  // Implicit wait
        driver.get(myWebSite);   // Open the website
        driver.manage().window().maximize();  // Maximize the window
    }

    // Test 1: Sign up a new user with random details
    @Test(priority = 1)
    public void signUp() throws InterruptedException {

        // Open the signup page
        driver.get("https://automationteststore.com/index.php?rt=account/create");

        // Fill in all required fields with random data
        WebElement FnameInput = driver.findElement(By.id("AccountFrm_firstname"));
        FnameInput.sendKeys(UserFirstName);

        WebElement LnameInput = driver.findElement(By.id("AccountFrm_lastname"));
        LnameInput.sendKeys(UserLastName);

        WebElement EmailInput = driver.findElement(By.id("AccountFrm_email"));
        EmailInput.sendKeys(UserEmail);

        WebElement AddressInput = driver.findElement(By.id("AccountFrm_address_1"));
        AddressInput.sendKeys("Jordan-Amman");

        WebElement CountryInput = driver.findElement(By.id("AccountFrm_country_id"));
        Select selector1 = new Select(CountryInput);
        selector1.selectByIndex(randomCountry);  // Select random country
        Thread.sleep(3000);  // Wait for the country selection

        WebElement CityInput = driver.findElement(By.id("AccountFrm_city"));
        CityInput.sendKeys("capital city");

        WebElement RegionInput = driver.findElement(By.id("AccountFrm_zone_id"));
        Select selector2 = new Select(RegionInput);
        selector2.selectByIndex(randomRegion);  // Select random region

        WebElement ZipCodeInput = driver.findElement(By.id("AccountFrm_postcode"));
        ZipCodeInput.sendKeys("13310");

        WebElement LoginNameInput = driver.findElement(By.id("AccountFrm_loginname"));
        LoginNameInput.sendKeys(LoginName);  // Set the random login name

        WebElement PassInput = driver.findElement(By.id("AccountFrm_password"));
        PassInput.sendKeys("mohammad@2002");

        WebElement ConfirmPassInput = driver.findElement(By.id("AccountFrm_confirm"));
        ConfirmPassInput.sendKeys("mohammad@2002");

        WebElement CheckBox = driver.findElement(By.id("AccountFrm_agree"));
        CheckBox.click();  // Agree to the terms

        theUserName = LoginName;  // Store the username for future login

        WebElement SubmitButton = driver.findElement(By.xpath("//button[@title='Continue']"));
        SubmitButton.click();  // Submit the signup form
        
        String ExpectedResult = driver.findElement(By.className("heading1")).getText();
        String ActualResult = "Your Account Has Been Created!";
        
        Assert.assertEquals(ActualResult.toUpperCase() , ExpectedResult);
    }

    // Test 2: Log out the user
    @Test(priority = 2)
    public void Logout() throws InterruptedException {
        Thread.sleep(2000);  // Wait for page load
        WebElement hoverLogout = driver.findElement(By.partialLinkText("Welcome back"));
        actions.moveToElement(hoverLogout).perform();  // Hover over the welcome message
        Thread.sleep(2000);
        WebElement logoutButton = driver.findElement(By.partialLinkText("Logoff"));
        logoutButton.click();  // Click the logout button
        
        String ExpectedResult = driver.findElement(By.className("heading1")).getText();
        String ActualResult = "Account Logout";
        
        Assert.assertEquals(ActualResult.toUpperCase() , ExpectedResult);
    }

    // Test 3: Log in the user using stored credentials
    @Test(priority = 3)
    public void login() {
        driver.get("https://automationteststore.com/index.php?rt=account/login");  // Go to login page

        WebElement logName = driver.findElement(By.id("loginFrm_loginname"));
        logName.sendKeys(theUserName);  // Enter username

        WebElement logPass = driver.findElement(By.id("loginFrm_password"));
        logPass.sendKeys("mohammad@2002");  // Enter password

        WebElement loginButton = driver.findElement(By.xpath("//button[@title='Login']"));
        loginButton.click();  // Click login button
        
        String ExpectedResult = driver.findElement(By.className("heading1")).getText();
        String ActualResult = "My Account";
        
        Assert.assertEquals(ActualResult.toUpperCase() + " " + UserFirstName , ExpectedResult);
    }

    // Test 4: Add random items to the cart
    @Test(priority = 4)
    public void addItems() throws InterruptedException {

        // Array of product category URLs
        String[] UrlArray = { 
            "https://automationteststore.com/index.php?rt=product/category&path=68",
            "https://automationteststore.com/index.php?rt=product/category&path=36",
            "https://automationteststore.com/index.php?rt=product/category&path=43",
            "https://automationteststore.com/index.php?rt=product/category&path=49",
            "https://automationteststore.com/index.php?rt=product/category&path=58",
            "https://automationteststore.com/index.php?rt=product/category&path=52",
            "https://automationteststore.com/index.php?rt=product/category&path=65" 
        };

        // Select a random category URL
        int RandomURLselector = rand.nextInt(UrlArray.length);
        driver.get(UrlArray[RandomURLselector]);  // Open random category

        // Locate subcategories
        WebElement SubCetegories = driver.findElement(By.cssSelector(".thumbnails.row"));
        int SubCetNum = SubCetegories.findElements(By.cssSelector(".col-md-2.col-sm-2.col-xs-6.align_center")).size();
        int RanSubCet = rand.nextInt(SubCetNum);  // Select random subcategory
        SubCetegories.findElements(By.cssSelector(".col-md-2.col-sm-2.col-xs-6.align_center")).get(RanSubCet).click();

        // Locate items inside the subcategory
        WebElement ItemsInside = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"));
        int ItemsNum = ItemsInside.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).size();
        int RanItem = rand.nextInt(ItemsNum);  // Select random item
        ItemsInside.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).get(RanItem).click();

        // Check if the item is in stock
        Thread.sleep(3000);
        WebElement ulList = driver.findElement(By.className("productpagecart"));
        String txt = ulList.getText();  // Get stock status text

        // If out of stock, return to the homepage
        if (txt.equals("Out of Stock")) {
            Thread.sleep(2000);
            driver.get(myWebSite);
            System.out.println("Sorry, the item is out of stock");
            
            String ActualResult = driver.getCurrentUrl();
            String ExpectedResult = myWebSite;
            
            Assert.assertEquals(ActualResult, ExpectedResult);
        } 
        // If in stock, add to cart or submit inquiry for out-of-cart items
        else {
            WebElement liClass = ulList.findElement(By.tagName("a"));
            className = liClass.getAttribute("class");

            if (className.equals("cart")) {
                Thread.sleep(2000);
                driver.findElement(By.className("cart")).click();  // Add to cart
                
                String ExpectedResult = driver.findElement(By.className("heading1")).getText();
                String ActualResult = "Shopping Cart";
                
                Assert.assertEquals(ActualResult.toUpperCase() , ExpectedResult);
            } else {
                // If item requires inquiry, fill out the form
                Thread.sleep(2000);
                driver.findElement(By.className("call_to_order")).click();

                WebElement fname = driver.findElement(By.id("ContactUsFrm_first_name"));
                fname.sendKeys(UserFirstName);

                WebElement email = driver.findElement(By.id("ContactUsFrm_email"));
                email.sendKeys(UserEmail);

                WebElement enquiry = driver.findElement(By.id("ContactUsFrm_enquiry"));
                enquiry.sendKeys("Just a test");

                WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
                submit.click();  // Submit inquiry form
                
                String ExpectedResult = driver.findElement(By.className("heading1")).getText();
                String ActualResult = "Contact Us";
                
                Assert.assertEquals(ActualResult.toUpperCase() , ExpectedResult);
            }
        }
    }
}
