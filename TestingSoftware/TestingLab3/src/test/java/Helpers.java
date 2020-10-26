import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Helpers {
    public static void login(WebDriver driver) {
        driver.get("https://photobucket.com/");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //Navigate to login page
        WebElement loginButton = driver.findElement(By.xpath("//a[@href=\"/login\"]"));
        loginButton.click();
        //Fill login form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"login-page-container\"]//form//input[@id=\"login-loginfield\"]")));
        WebElement emailInput = driver.findElement(By.xpath("//div[@class=\"login-page-container\"]//form//input[@id=\"login-loginfield\"]"));
        emailInput.sendKeys("derexdahik@yandex.ru");
        WebElement passwordInput = driver.findElement(By.xpath("//div[@class=\"login-page-container\"]//form//input[@type=\"password\"]"));
        passwordInput.sendKeys("tpo313");
        WebElement singInButton = driver.findElement(By.xpath("//div[@class=\"login-page-btn-block\"]/button"));
        //Click Sign in
        singInButton.click();

        //Wait for dashboard to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(., \"Upload Images\")]")));
    }

    public static void uploadImage(WebDriver driver){
        driver.get("https://photobucket.com/");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.findElement(By.xpath("//button[contains(text(),\"Upload Images\")]")).click();
        //Uploading image
        try{
            Thread.sleep(1000);
        }catch (Exception e){}
        driver.findElement(By.xpath("//input[@id=\"pb-upload-select-photo-button\"]")).sendKeys("C:\\Users\\Daniil\\iCloudDrive\\4 kursen\\TestingSoftware\\TestingLab3\\Korg.jpg");
        //confirm
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\"Button-sc-14ro8yz bxMEsd pb-button l  UploadToButton-sc-irlgzw hYpLRP \" and contains(text(), \"Upload to Your Bucket\")]")));
        driver.findElement(By.xpath("//button[@class=\"Button-sc-14ro8yz bxMEsd pb-button l  UploadToButton-sc-irlgzw hYpLRP \" and contains(text(), \"Upload to Your Bucket\")]")).click();
        //Wait for dashboard
        driver.get("https://photobucket.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        //Double click on uplodead picture
        try{
            Thread.sleep(1000);
        }catch (Exception e){}
        Actions action = new Actions(driver);
        WebElement uploadedImage = driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]"));
        action.doubleClick(uploadedImage).perform();

        //check title of uploaded image
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"pb-fullscreen-view-settings-image-title\"]")));
        WebElement title = driver.findElement(By.xpath("//input[@id=\"pb-fullscreen-view-settings-image-title\"]"));
        assertTrue(title.getAttribute("value").startsWith("Korg"));
    }
}
