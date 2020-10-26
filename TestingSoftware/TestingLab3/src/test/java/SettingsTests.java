import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SettingsTests {
    WebDriver driver;

    @BeforeEach
    public void prepare() {
        driver = WebDriverInitializer.init();
        Helpers.login(driver);
    }

    @AfterEach
    public void destruct() {
        driver.quit();
    }

    @Test
    public void changePersonalInformation() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.get("https://photobucket.com/profile/personal_details");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"FormContainer-sc-1ff1ftg hVzQIr\"]")));
        //Fill form
        driver.findElement(By.xpath("//div[@class=\"FormContainer-sc-1ff1ftg hVzQIr\"]//input[@type=\"text\"]")).click();
        Actions action = new Actions(driver);
        action.sendKeys("Daniil").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("Tolstov").perform();
        action.sendKeys(Keys.TAB).perform();
        WebElement goBackOneYearButton = driver.findElement(By.xpath("//button[contains(text(), \"«\")]"));
        for (int i = 0; i < 20; i++) {
            goBackOneYearButton.click();
        }

        driver.findElement(By.xpath("//time[@datetime=\"2000-10-08T00:00:00.000\"]")).click();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("derexdahik@yandex.ru").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("Russia").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("313313").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys(Keys.ENTER).perform();
        //Save changes
        driver.findElement(By.xpath("//button[contains(text(), \"Save changes\")]")).click();
        //Wait for message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), \"OK\")]")));
    }

    @Test
    public void changePassword(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.get("https://photobucket.com/profile/password_email");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"FormContainer-sc-1ff1ftg hVzQIr\"]")));
        //Fill form
        driver.findElement(By.xpath("//input[@type=\"password\"]")).click();
        Actions action = new Actions(driver);
        action.sendKeys("tpo313").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("tpo313").perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("tpo313").perform();
        //Save changes
        driver.findElement(By.xpath("//button[contains(text(), \"Save changes\")]")).click();
        //Wait for message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), \"OK\")]")));
    }
}