import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

@Getter
public class SeleniumConfig {
    private WebDriver driver;

    public SeleniumConfig() {
        System.setProperty("webdriver.ie.driver","./driver\\IEDriverServer.exe");
        InternetExplorerOptions options = new InternetExplorerOptions()
                .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .destructivelyEnsureCleanSession()
                .introduceFlakinessByIgnoringSecurityDomains()
                .withInitialBrowserUrl("");
        driver = new InternetExplorerDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

//    static {
//        System.setProperty("webdriver.gecko.driver", findFile("geckodriver.mac"));
//    }

//    static private String findFile(String filename) {
//        String paths[] = {"", "bin/", "target/classes"};
//        for (String path : paths) {
//            if (new File(path + filename).exists())
//                return path + filename;
//        }
//        return "";
//    }
}
