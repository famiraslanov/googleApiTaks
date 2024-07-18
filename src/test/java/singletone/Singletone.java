package singletone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Singletone
{
    private static WebDriver webDriver;

    public static WebDriver getDriver()
    {
        if (webDriver == null){
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }
}
