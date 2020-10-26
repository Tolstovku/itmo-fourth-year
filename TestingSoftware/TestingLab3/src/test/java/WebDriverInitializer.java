import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverInitializer {
    public static WebDriver init(){
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Daniil\\iCloudDrive\\4 kursen\\TestingSoftware\\TestingLab3\\chromedriver.exe");
        driver =  new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }
}
