package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import singletone.Singletone;

import java.time.Duration;

public class ConfirmationPage
{
    WebDriverWait wait = new WebDriverWait(Singletone.getDriver(), Duration.ofSeconds(30));
    private final By backToWebsiteButton = By.xpath("//a[contains(text(), 'Back to website')]");

    public void clickBackToWebsiteButton()
    {
        wait.until(ExpectedConditions.elementToBeClickable(backToWebsiteButton));
        Assert.assertTrue("Confirmation page is not opened",Singletone.getDriver().findElement(backToWebsiteButton).isDisplayed());
        Singletone.getDriver().findElement(backToWebsiteButton).click();
    }
}
