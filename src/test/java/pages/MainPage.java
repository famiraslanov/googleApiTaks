package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import singletone.Singletone;

public class MainPage
{
    private final By acceptCookiesButton = By.xpath("//button[@id='didomi-notice-agree-button']");
    private final By newsLettersButton = By.xpath("//div[@id='adb-header-newsletters']");
    private final By topTagsLabel = By.xpath("//strong[contains(text(),'TOP TAGS')]");
    private final By newsLettersLabel = By.xpath("//span[contains(text(), 'Our newsletters')]");

    public void clickAcceptCookiesButton()
    {
        Singletone.getDriver().findElement(acceptCookiesButton).click();
    }

    public boolean isPageOpened()
    {
        return Singletone.getDriver().findElement(topTagsLabel).isDisplayed();
    }

    public void clickNewslettersButton()
    {
        Singletone.getDriver().findElement(newsLettersButton).click();
        Assert.assertTrue("The newsletters page is not opened",Singletone.getDriver().findElement(newsLettersLabel).isDisplayed());
    }

}
