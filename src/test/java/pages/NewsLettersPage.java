package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import singletone.Singletone;

import java.security.Signature;
import java.time.Duration;

public class NewsLettersPage
{
    private final By selectNewsLettersButtons = By.xpath("//label[contains(text(), 'Select this newsletter')]");
    private final By emailInput = By.xpath("//input[@type='email']");
    private final By submitButton = By.xpath("//input[@value='Continue']");
    private final By passwordInput = By.xpath("//input[@placeholder='Enter your password *']");
    private final By createAccountButton = By.xpath("//input[@value='Create my account']");
    private final By seePreviewButton = By.xpath("//a[contains(@href, 'green_previews')]");
    WebDriverWait wait = new WebDriverWait(Singletone.getDriver(), Duration.ofSeconds(10));

    public void clickNewsLettersPlan()
    {
        Assert.assertTrue("An email form has not appeared at the bottom of the page",Singletone.getDriver().findElement(emailInput).isDisplayed());
        Singletone.getDriver().findElements(selectNewsLettersButtons).get(1).click();
    }

    public void insertEmail(String email, String password)
    {
        Singletone.getDriver().findElement(emailInput).sendKeys(email);
        Singletone.getDriver().findElement(submitButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
        Singletone.getDriver().findElement(createAccountButton).click();

    }

    public void clickSeePreviewButton()
    {
        Singletone.getDriver().findElement(seePreviewButton).click();
    }
}
