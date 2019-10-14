package tools;

import lombok.NonNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.time.Duration;

public class SeleniumTools {

    private final WebDriver driver;

    public SeleniumTools(@NonNull final WebDriver driver) {
        this.driver = driver;
    }

    public static void hardSleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignored) {
            System.err.println("Thread interruption occurred on Thread.sleep()");;
        }
    }

    public void scrollIntoView(@NonNull final HtmlElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", el);
    }

    public boolean waitWithConditionSilent(@NonNull final ExpectedCondition<?> condition, final long pollingTime,
                                           final int timeInSec) {
        return waitWithConditionSilent(condition, pollingTime, timeInSec, null);
    }

    public boolean waitWithConditionSilent(@NonNull final ExpectedCondition<?> condition, final long pollingTime,
                                           final int timeInSec, final String msg) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, timeInSec);
            wait.ignoring(NoSuchElementException.class);
            wait.ignoring(StaleElementReferenceException.class);
            wait.pollingEvery(Duration.ofMillis(pollingTime));
            wait.withMessage(msg);
            wait.until(condition);
            return true;
        } catch (final TimeoutException e) {
            return false;
        }
    }
}
