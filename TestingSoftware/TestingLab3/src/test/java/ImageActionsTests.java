import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

public class ImageActionsTests {
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
    public void uploadImage() {
        Helpers.uploadImage(driver);
    }

    @Test
    public void deleteImage() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Helpers.uploadImage(driver);
        //Remember Name of the newly uploaded image
        String uploadedImageTitle = driver.findElement(By.xpath("//input[@id=\"pb-fullscreen-view-settings-image-title\"]")).getAttribute("value");
        //Delete image
        driver.findElement(By.xpath("//div[@data-original-title=\"Delete\"]")).click();
        driver.findElement(By.xpath("//button[contains(text(), \"Yes, Delete Image\")]")).click();
        //Check name of the previous picture and confirm they are different
        String differentPictureTitle = driver.findElement(By.xpath("//input[@id=\"pb-fullscreen-view-settings-image-title\"]")).getAttribute("value");
        assertNotEquals(uploadedImageTitle, differentPictureTitle);
    }

    @Test
    public void addAndRemoveTags() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Helpers.uploadImage(driver);
        //Click on add tag placeholder
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), \"Add tag...\")]")));
        driver.findElement(By.xpath("//div[contains(text(), \"Add tag...\")]")).click();
        //Type new tag and press enter
        Actions action = new Actions(driver);
        action.sendKeys("New Tag").perform();
        action.sendKeys(Keys.ENTER).perform();
        //Assert tag added
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"pb-link tag-link\"]")));
        assertEquals("New Tag", driver.findElement(By.xpath("//a[@class=\"pb-link tag-link\"]")).getText());
        //Delete tag and assert it got deleted
        driver.findElement(By.xpath("//div[@class=\"css-1b8tjcg multi-select__multi-value__remove\"]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[@class=\"pb-link tag-link\"]")));
    }

    @Test
    public void addImageToNewAlbumAndDeleteAlbum() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Helpers.uploadImage(driver);
        driver.get("https://photobucket.com/");
        //Select image and click move to album
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]")).click();
        driver.findElement(By.xpath("//div[@id=\"move-icon\"]")).click();

        //Set album name and move image to the album
        WebElement createAlbumButton = driver.findElement(By.xpath("//button[contains(text(), \"Create album\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(createAlbumButton));
        createAlbumButton.click();
        driver.findElement(By.xpath("//div[@class=\"album-chooser-albums-btn-container-create\"]/input")).sendKeys("new album");
        driver.findElement(By.xpath("//div[@class=\"album-chooser-albums-btn-container-create\"]/button")).click();

        WebElement moveImageButton = driver.findElement(By.xpath("//button[@class=\"Button-sc-14ro8yz bxMEsd pb-button l  pb-drawer-footer-button \" and contains(text(), Move)]"));
        wait.until(ExpectedConditions.elementToBeClickable(moveImageButton));
        moveImageButton.click();

        //Reload page so the album is visible
        driver.get("https://photobucket.com/");
        //Find new album
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"gallery-album-container\"]//div[@title=\"new album\"]")));
        WebElement newAlbum = driver.findElement(By.xpath("//div[@class=\"gallery-album-container\"]//div[@title=\"new album\"]/../../.."));
        wait.until(ExpectedConditions.elementToBeClickable(newAlbum));
        newAlbum.click();
        //Click delete
        WebElement deleteButton = driver.findElement(By.xpath("//div[@data-original-title=\"Delete\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
        //Confirm delete
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), \"Yes, Delete Album\")]")));
        WebElement confirmDelete = driver.findElement(By.xpath("//button[contains(text(), \"Yes, Delete Album\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmDelete));
        confirmDelete.click();
        wait.until(ExpectedConditions.invisibilityOf(newAlbum));


    }

    @Test
    public void shareImageViaEmail() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Select image and click share
        driver.get("https://photobucket.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]")).click();

        //Select via email
        driver.findElement(By.xpath("//div[@id=\"pb-actionbar-share-button\"]")).click();
        Thread.sleep(1000);
        //Fill form
        driver.findElement(By.xpath("//div[@class=\"SocialButtons-sc-18vscas CcGIN\"]/*[local-name() = \"svg\"]")).click();
        driver.findElement(By.xpath("//input[@placeholder=\"Recipient Name\"]")).sendKeys("Daniil");
        driver.findElement(By.xpath("//input[@placeholder=\"Recipient Email address\"]")).sendKeys("derexdahik@yandex.ru");
        //Click send
        driver.findElement(By.xpath("//div[@class=\"Modal-sc-4lz6wg EmailModal-sc-1v45r8j foCnDt\"]//button[contains(text(), \"Share\")]")).click();
        //Wait for message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), \"Sharing email sent successfully!\")]")));
    }

    @Test
    public void changePictureTitleAndDescription() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.get("https://photobucket.com/");
        //Click image
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]")).click();
        //Click edit
        driver.findElement(By.xpath("//div[@id=\"edit-icon\"]")).click();
        //Change title and description
        driver.findElement(By.xpath("//input[@id=\"pb-fullscreen-view-settings-image-title\"]")).sendKeys("Changed title");
        driver.findElement(By.xpath("//textarea[@id=\"pb-fullscreen-view-settings-image-description\"]")).sendKeys("Changed description");
        //Click outside to save
        driver.findElement(By.tagName("body")).click();
        //Wait for message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), \"OK\")]")));

    }

    @Test
    public void downloadImage(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.get("https://photobucket.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]")));
        //Select image
        driver.findElement(By.xpath("//div[@class=\"Gallery-sc-1bnpdn bQfaqv\"]/div[1]")).click();
        //Click download
        driver.findElement(By.xpath("//div[@id=\"download-icon\"]")).click();
        //Wait for message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), \"OK\")]")));

    }
}
