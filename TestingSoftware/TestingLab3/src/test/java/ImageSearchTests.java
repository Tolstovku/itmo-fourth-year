import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImageSearchTests {
    WebDriver driver;

    @BeforeEach
    public void prepare() {
        driver = WebDriverInitializer.init();
    }

    @AfterEach
    public void destruct() {
        driver.quit();
    }

    @Test
    public void searchImage(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        driver.get("https://photobucket.com/");
        //Search for dog image
        driver.findElement(By.xpath("//a[@href=\"/search\"]")).click();
        driver.findElement(By.xpath("//input[@class=\"SearchInput-sc-1td6id kntPsP\"]")).sendKeys("dog");
        driver.findElement(By.xpath("//button[@class=\"Button-sc-14ro8yz bxMEsd pb-button l  search-button \"]")).click();
        //Get first image
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]")).click();
        //Check tag
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img")));
    }
}
