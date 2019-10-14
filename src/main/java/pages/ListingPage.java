package pages;

import io.qameta.allure.Step;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ListingPage extends MainPage {

    @Name("Listing page title")
    @FindBy(css = "h1.title")
    private HtmlElement listingPageTitle;


    public ListingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that Listing page title is {title}")
    public void checkTitle(@NonNull final String title) {
        assertThat("Category page title should be displayed", listingPageTitle.exists());
        assertThat("Category page title differs", listingPageTitle.getText(), equalToIgnoringCase(title));
    }
}
