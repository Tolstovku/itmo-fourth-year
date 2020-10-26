import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.util.Random;

public class LoginAndRegistrationTests {
    private static WebDriver driver;

    @BeforeEach
    public void prepare(){
        driver  = WebDriverInitializer.init();
    }

    @AfterEach
    public void destruct(){
        driver.quit();
    }

    @Test
    public void loginSuccessful() {
        Helpers.login(driver);
    }

    @Test
    public void registrationSuccessful() throws InterruptedException {
        driver.get("https://photobucket.com/");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //Navigate to registration form
        WebElement signUpButton = driver.findElement(By.xpath("//a[@href=\"/signup?step=PLANS\"]"));
        signUpButton.click();
        signUpButton = driver.findElement(By.xpath("//button[contains(text(), \"Sign Up\")]"));
        signUpButton.click();
        WebElement continueButton = driver.findElement(By.xpath("//button[contains(text(), \"Continue\")]"));
        continueButton.click();

        Random rd = new Random();
        //Random email of 6 letters
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randonSequence = rd .nextInt(CHARACTERS.length());
            generatedString.append(CHARACTERS.charAt(randonSequence));
        }

        String randomChars = generatedString.toString();
        String email = randomChars + "@shitmail.me";

        //Fill firm
        driver.findElement(By.xpath("//form//input[@id=\"signup-email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//form//input[@id=\"signup-username\"]")).sendKeys(randomChars);
        driver.findElement(By.xpath("//form//input[@id=\"signup-password\"]")).sendKeys(randomChars);

        driver.findElement(By.xpath("//label[@for=\"userAgreement\"]")).click();
        //Click create account
        driver.findElement(By.xpath("//button[contains(text(), \"Create Account\")]")).click();
        //Wait for greetings message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), \"Thank you for signing up!\")]")));
    }
}

