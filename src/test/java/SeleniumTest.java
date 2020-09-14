import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SeleniumTest {

    @Test
    public void testLogin(){
        System.out.println("Entering Test");
        WebDriver browser;
        System.setProperty("webdriver.gecko.driver","C:\\Program Files (x86)\\FirefoxDriver\\geckodriver.exe");
        browser = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(browser, 30);

        browser.get("http://localhost:8080/");
        browser.manage().window().maximize();
        WebDriverWait wait2 = new WebDriverWait(browser, 30);

        WebElement textbox = browser.findElement(By.id("gwt-uid-3"));
        textbox.sendKeys("tim.tim@carlook.de");
        textbox = browser.findElement(By.id("gwt-uid-5"));
        textbox.sendKeys("1");
        textbox = browser.findElement(By.id("Anmeldebutton"));
        WebDriverWait wait3 = new WebDriverWait(browser, 10);
        textbox.click();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        textbox = browser.findElement(By.id("gwt-uid-13"));
        textbox.sendKeys("Korolla");
        assertEquals(browser.getCurrentUrl(), "http://localhost:8080/#!MainVertriebler");
    }
}