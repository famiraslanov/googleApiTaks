import gmailapi.GmailQuickstart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.ConfirmationPage;
import pages.MainPage;
import pages.NewsLettersPage;
import singletone.Singletone;

public class EuronewsGmailApiTests
{
    private String baseUrl = "https://www.euronews.com/";

    @Before
    public void setUp()
    {
        Singletone.getDriver().get(baseUrl);
        Singletone.getDriver().manage().window().maximize();
    }

    @Test
    public void euroNewsTests()
    {
        MainPage mainPage = new MainPage();
        mainPage.clickAcceptCookiesButton();

        mainPage.clickNewslettersButton();

        NewsLettersPage newsLettersPage = new NewsLettersPage();
        newsLettersPage.clickNewsLettersPlan();
        newsLettersPage.insertEmail("farid.282.282qa@gmail.com", "SecretPass1!");
        Assert.assertTrue("The messages count is not equal to 1",GmailQuickstart.getMessagesCount() == 1);

        Singletone.getDriver().get(GmailQuickstart.getConfirmationLink());
        ConfirmationPage confirmationPage = new ConfirmationPage();
        confirmationPage.clickBackToWebsiteButton();

        mainPage.clickNewslettersButton();
        newsLettersPage.clickSeePreviewButton();
    }

    @After
    public void tearDown()
    {
        if (Singletone.getDriver() != null){
            Singletone.getDriver().quit();
        }
    }
}
