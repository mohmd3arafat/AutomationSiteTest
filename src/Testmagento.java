import java.awt.Desktop.Action;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Testmagento {

	WebDriver driver = new ChromeDriver();

	String myWebSite = "https://automationteststore.com/";
	String[] firstNames = { "mohammad", "ali", "ahmad", "obada", "hamza", "alaa", "sawsan" };
	String[] LastNames = { "awartani", "safoty", "abu_al_haija", "radaida" };
	String className = "";
	String theUserName = "";

	Random rand = new Random();
	Actions actions = new Actions(driver);
	int randomFname = rand.nextInt(firstNames.length);
	int randomLname = rand.nextInt(LastNames.length);
	int randomNum = rand.nextInt(99999);
	int randomCountry = rand.nextInt(1, 240);
	int randomRegion = rand.nextInt(1, 6);

	String UserFirstName = firstNames[randomFname];
	String UserLastName = LastNames[randomLname];
	String UserEmail = UserFirstName + UserLastName + randomNum + "@gmail.com";
	String LoginName = UserFirstName + UserLastName + randomNum;

	@BeforeTest
	public void setUp() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(myWebSite);
		driver.manage().window().maximize();

	}

	@Test(priority = 1)
	public void signUp() throws InterruptedException {

		driver.get("https://automationteststore.com/index.php?rt=account/create");

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
		selector1.selectByIndex(randomCountry);
		Thread.sleep(3000);
		WebElement CityInput = driver.findElement(By.id("AccountFrm_city"));
		CityInput.sendKeys("capital city");
		WebElement RegionInput = driver.findElement(By.id("AccountFrm_zone_id"));
		Select selector2 = new Select(RegionInput);
		selector2.selectByIndex(randomRegion);
		WebElement ZipCodeInput = driver.findElement(By.id("AccountFrm_postcode"));
		ZipCodeInput.sendKeys("13310");
		WebElement LoginNameInput = driver.findElement(By.id("AccountFrm_loginname"));
		LoginNameInput.sendKeys(LoginName);
		WebElement PassInput = driver.findElement(By.id("AccountFrm_password"));
		PassInput.sendKeys("mohammad@2002");
		WebElement ConfirmPassInput = driver.findElement(By.id("AccountFrm_confirm"));
		ConfirmPassInput.sendKeys("mohammad@2002");
		WebElement CheckBox = driver.findElement(By.id("AccountFrm_agree"));
		CheckBox.click();
		theUserName = LoginName;
		WebElement SubmitButton = driver.findElement(By.xpath("//button[@title='Continue']"));
		SubmitButton.click();

	}

	@Test(priority = 2)

	public void Logout() throws InterruptedException {
		Thread.sleep(2000);
		WebElement hoverLogout = driver.findElement(By.partialLinkText("Welcome back"));
		actions.moveToElement(hoverLogout).perform();
		Thread.sleep(2000);
		WebElement logoutButton = driver.findElement(By.partialLinkText("Logoff"));
		logoutButton.click();
	}

	@Test(priority = 3)
	public void login() {
		driver.get("https://automationteststore.com/index.php?rt=account/login");

		WebElement logName = driver.findElement(By.id("loginFrm_loginname"));
		logName.sendKeys(theUserName);
		WebElement logPass = driver.findElement(By.id("loginFrm_password"));
		logPass.sendKeys("mohammad@2002");
		WebElement loginButton = driver.findElement(By.xpath("//button[@title='Login']"));
		loginButton.click();
	}

	@Test(priority = 4)
	public void addItems() throws InterruptedException {

		String[] UrlArray = { "https://automationteststore.com/index.php?rt=product/category&path=68",
				"https://automationteststore.com/index.php?rt=product/category&path=36",
				"https://automationteststore.com/index.php?rt=product/category&path=43",
				"https://automationteststore.com/index.php?rt=product/category&path=49",
				"https://automationteststore.com/index.php?rt=product/category&path=58",
				"https://automationteststore.com/index.php?rt=product/category&path=52",
				"https://automationteststore.com/index.php?rt=product/category&path=65" };

		int RandomURLselector = rand.nextInt(UrlArray.length);
		driver.get(UrlArray[RandomURLselector]);

		WebElement SubCetegories = driver.findElement(By.cssSelector(".thumbnails.row"));

		int SubCetNum = SubCetegories.findElements(By.cssSelector(".col-md-2.col-sm-2.col-xs-6.align_center")).size();
		int RanSubCet = rand.nextInt(SubCetNum);
		SubCetegories.findElements(By.cssSelector(".col-md-2.col-sm-2.col-xs-6.align_center")).get(RanSubCet).click();
		////////////////////////
		WebElement ItemsInside = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"));

		int ItemsNum = ItemsInside.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).size();
		int RanItem = rand.nextInt(ItemsNum);
		ItemsInside.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).get(RanItem).click();
		///////////////////////
		Thread.sleep(2000);
		WebElement ulList = driver.findElement(By.className("productpagecart"));
		String txt = ulList.getText();
		
		if (txt.equals("Out of Stock")) {
			Thread.sleep(2000);
		    driver.get(myWebSite);
		    System.out.println("sorry the item is out of stock");
		} 
		else {
			WebElement liClass = ulList.findElement(By.tagName("a"));
			className = liClass.getAttribute("class");
			if(className.equals("cart"))
			{
				Thread.sleep(2000);
		    driver.findElement(By.className("cart")).click();
		    }
			else {
				Thread.sleep(2000);
			    driver.findElement(By.className("call_to_order")).click();

			    WebElement fname = driver.findElement(By.id("ContactUsFrm_first_name"));
			    fname.sendKeys(UserFirstName);
			    WebElement email = driver.findElement(By.id("ContactUsFrm_email"));
			    email.sendKeys(UserEmail);
			    WebElement enquiry = driver.findElement(By.id("ContactUsFrm_enquiry"));
			    enquiry.sendKeys("just a test");
			    WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
			    submit.click();
			}	
		}

	}
}
