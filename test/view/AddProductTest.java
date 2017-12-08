package view;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddProductTest {
	WebDriver driver;

	String PRODUCTNAME = "Carrot";
	String PRODUCTDESC = "It's orange and healthy.";
	double productPriceRandom;
	
	String url = "http://localhost:8080/Labs/Controller";

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jordy\\Desktop\\UCLL\\Web2\\chromedriver.exe");
		driver = new ChromeDriver();
		addProduct();
		driver.get(url);
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private void addProduct() {
		// Add new product for the test
		driver.get(url + "?action=addProduct");

		productPriceRandom = (int)(Math.random() * 10 + 1);

		fillOutField("name", PRODUCTNAME);
		fillOutField("description", PRODUCTDESC);
		fillOutField("price", String.valueOf(productPriceRandom));
		
		WebElement button = driver.findElement(By.id("addProduct"));
		button.click();
	}

	private void deleteProduct() {
		// Delete test product
		driver.get(url + "?action=products");
		WebElement deleteLink = findDeleteLink();
		if (deleteLink != null) {
			deleteLink.click();
		}
		
		WebElement deleteButtonConfirm = driver.findElement(By.cssSelector("input[type='submit'"));
		deleteButtonConfirm.click();
	}
	
	public WebElement findDeleteLink() {
		ArrayList<WebElement> rows = (ArrayList<WebElement>)driver.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			ArrayList<WebElement> links = (ArrayList<WebElement>)row.findElements(By.tagName("a"));
			if (links.size() == 2 && links.get(0).getText().equals(PRODUCTNAME)) {
				return links.get(1);
			}
		}
		return null;
	}
	
	public boolean findProduct() {
		ArrayList<WebElement> rows = (ArrayList<WebElement>)driver.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			ArrayList<WebElement> items = (ArrayList<WebElement>)row.findElements(By.tagName("td"));
			if (items.size() >= 3) {
				if (!items.get(0).getText().equals(PRODUCTNAME)) {
					continue;
				}
				if (!items.get(1).getText().equals(PRODUCTDESC)) {
					continue;
				}
				// Strip euro sign
				if (Double.parseDouble(items.get(2).getText().substring(2)) != productPriceRandom) {
					continue;
				}
				return true;	
			}
		}
		return false;
	}

	@After
	public void clean() {
		deleteProduct();
		driver.quit();
	}
	
	@Test
	public void testCorrectProductInfo() {
		driver.get(url + "?action=products");
		assertTrue(findProduct());
	}
}