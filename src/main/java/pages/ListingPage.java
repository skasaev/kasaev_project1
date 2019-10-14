package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import pages.elements.FilterBlock;
import pages.elements.StoreRatingFilterBlock;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@Getter
public class ListingPage extends MainPage {

    @Name("Listing page title")
    @FindBy(css = "h1.title")
    private HtmlElement listingPageTitle;

    @Name("Listing filter block")
    @FindBy(css = ".search-layout")
    private FilterBlock filterBlock;

    @Name("Number of profucts per page option block")
    @FindBy(css = ".b-pager__select")
    private HtmlElement pagerSelectBlock;

    @Name("Store rating filter block")
    @FindBy(css = "._2uSu7TQsMO")
    private StoreRatingFilterBlock storeRatingFilterBlock;

    @Name("Sorting options")
    @FindBy(css = ".n-filter-sorter")
    private List<HtmlElement> sortingOptions;

    @Name("Product list")
    @FindBy(css = ".n-snippet-card2")
    private HtmlElement productList;

    private final static String DIRECTION_CLASS_PART = "n-filter-sorter_sort_";
    private final static String SELECTED_CLASS = "n-filter-sorter_state_select";


    public ListingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that Listing page title is {title}")
    public void checkTitle(@NonNull final String title) {
        assertThat("Category page title should be displayed", listingPageTitle.exists());
        assertThat("Category page title differs", listingPageTitle.getText(), equalToIgnoringCase(title));
    }

    @Step("Select sorting by {optionName} and direction is {direction}")
    public void sortingBy(@NonNull final String optionName, final String direction) {
        final HtmlElement option = getSortingOptionByName(optionName);
        assertThat("Sorting option [" + optionName +"] should be displayed", option.exists());
        if (!isSortingOptionSelected(optionName, direction)) {
            option.click();
            if (!option.getAttribute("class").contains(DIRECTION_CLASS_PART + direction.toLowerCase())) {
                option.click();
            }
        }
        assertThat("Sorting option [" + optionName +"] should be selected",
                isSortingOptionSelected(optionName, direction));
    }

    private boolean isSortingOptionSelected(@NonNull final String optionName, final String direction) {
        boolean result = true;
        final HtmlElement option = getSortingOptionByName(optionName);
        assertThat("Sorting option [" + optionName +"] should be displayed", option.exists());
        if (Objects.nonNull(direction)) {
            final String elementClassDirection = DIRECTION_CLASS_PART + direction.toLowerCase();
            result = option.getAttribute("class").contains(elementClassDirection);
        }
        return result && option.getAttribute("class").contains(SELECTED_CLASS);
    }

    private HtmlElement getSortingOptionByName(@NonNull final String optionName) {
        return sortingOptions
                .stream()
                .filter(option -> option.exists() && option.getText().equalsIgnoreCase(optionName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find sorting option " + optionName));
    }
}
