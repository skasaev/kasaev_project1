package tools;

import lombok.Getter;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.concurrent.TimeUnit;

@Getter
public class SeleniumConfig {
    private WebDriver driver;
    private static boolean isHeadless;

    public SeleniumConfig(boolean isHeadlessParam) {
        isHeadless = isHeadlessParam;
        driver = isHeadless ? getDriverForChrome() : getDriverForIE();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private WebDriver getDriverForIE() {
        System.setProperty("webdriver.ie.driver","driver/IEDriverServer.exe");
        InternetExplorerOptions options = new InternetExplorerOptions()
                .destructivelyEnsureCleanSession()
                .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .introduceFlakinessByIgnoringSecurityDomains()
                .withInitialBrowserUrl("");
        return new InternetExplorerDriver(options);
    }

    private WebDriver getDriverForChrome() {
        System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions()
                .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .setHeadless(true)
                .addArguments("test-type")
                .addArguments("disable-infobars")
                .addArguments("chrome.switches", "--disable-extensions")
                .addArguments("--ignore-certificate-errors")
                .addArguments("--disable-notifications");
        return new ChromeDriver(options);
    }

    public static boolean isHeadless() {
        return isHeadless;
    }
}
