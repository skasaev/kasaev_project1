package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import pages.elements.*;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    private SelectBlock pagerSelectBlock;

    @Name("Store rating filter block")
    @FindBy(css = "._2uSu7TQsMO")
    private StoreRatingFilterBlock storeRatingFilterBlock;

    @Name("Sorting options")
    @FindBy(css = ".n-filter-sorter")
    private List<HtmlElement> sortingOptions;

    @Name("Product list")
    @FindBy(css = ".n-snippet-card2")
    private List<CatalogItem> catalogItems;

    @Name("Listing spinner")
    @FindBy(css = ".n-filter-applied-results .spin2_progress_yes")
    private HtmlElement listingSpinner;

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

    @Step("Select filter {filterName} = {checkBoxLabelName}")
    public void selectFilterCheckBox(@NonNull final String filterName, @NonNull final String checkBoxLabelName) {
        CheckBoxListBlock filter = filterBlock.getCheckBoxListBlocks()
                .stream()
                .filter(block -> block.exists() && block.getBlockDescription().getText().equalsIgnoreCase(filterName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find filter block " + filterName));
        filter.selectCheckBox(checkBoxLabelName, getSeleniumTools());
    }

    @Step("Filter by {type} price = {price}")
    public void filterByPrice(@NonNull final String price, @NonNull final String type) {
        HtmlElement priceField = type.equalsIgnoreCase("min") ? filterBlock.getPriceFromField() : filterBlock.getPriceToField();
        assertThat("Price field should be displayed", priceField.exists());
        priceField.sendKeys(price);
    }

    @Step("Select number of products per page {optionName}")
    public void selectNumberOfProductsOption(@NonNull final String optionName){
        getSeleniumTools().waitSpinner(listingSpinner);
        assertThat("", pagerSelectBlock.exists());
        pagerSelectBlock.selectOption(optionName);
        getSeleniumTools().waitSpinner(listingSpinner);
    }

    /**
     * set filter by rating
     * @param rating example: "2", "4". "any"
     */
    @Step("Set filter by rating {rating}")
    public void selectRatingFilter(@NonNull final String rating) {
        final String attribute = "qrfrom_" + (rating.equalsIgnoreCase("any") ? "-1" : rating);
        storeRatingFilterBlock.getRatingLabels()
                .stream()
                .filter(label -> label.exists() && label.getAttribute("for").equalsIgnoreCase(attribute))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find filter with rating [" + rating
                        + "], because did not find label attribute " + attribute))
                .click();
    }


    @Step("Select sorting items by {optionName} and direction is {direction}")
    public void sortingBy(@NonNull final String optionName, final String direction) {
        final HtmlElement option = getSortingOptionByName(optionName);
        assertThat("Sorting option [" + optionName +"] should be displayed", option.exists());
        if (!isSortingOptionSelected(optionName, direction)) {
            option.click();
            final String classPart = direction.contains("возрастанию") ? "asc" : "desc";
            if (!option.getAttribute("class").contains(DIRECTION_CLASS_PART + classPart)) {
                option.click();
            }
        }
        getSeleniumTools().waitSpinner(listingSpinner);
        assertThat("Sorting option [" + optionName +"] should be selected",
                isSortingOptionSelected(optionName, direction));
    }

    @Step("Check sorting items by price and {direction}")
    public void verifySortingByPrice(@NonNull final String direction) {
        assertThat("Catalog item list should not be empty", catalogItems, not(empty()));
        final List<Integer> prices = catalogItems
                .stream()
                .map(CatalogItem::getPriceAsNumber)
                .collect(Collectors.toList());
        assertThat("Item price list should not be empty", prices, not(empty()));
        int prevPrice;
        if (direction.contains("возрастанию")) {
            prevPrice = 0;
            for (final Integer price : prices) {
                assertThat("Invalid sort by " + direction + ". List of visible prices: " + prices,
                        price, greaterThanOrEqualTo(prevPrice));
                prevPrice = price;
            }
        } else {
            prevPrice = prices.get(0);
            for (int i = 1; i < prices.size(); i++) {
                final int currentPrice = prices.get(i);
                assertThat("Invalid sort by " + direction + ". List of visible prices: " + prices,
                        currentPrice, lessThanOrEqualTo(prevPrice));
                prevPrice = currentPrice;
            }
        }
    }

    @Step("Select {index} catalog item")
    public void selectCatalogItemByIndex(int index) {
        index = index < 0 ? 0 : index >= catalogItems.size() ? catalogItems.size()-1 : index - 1;
        Link title = catalogItems.get(index).getItemTitle();
        assertThat("Item title should be displayed", title.exists());
        title.click();
    }

    private boolean isSortingOptionSelected(@NonNull final String optionName, final String direction) {
        boolean result = true;
        final HtmlElement option = getSortingOptionByName(optionName);
        assertThat("Sorting option [" + optionName +"] should be displayed", option.exists());
        if (Objects.nonNull(direction)) {
            final String classPart = direction.contains("возрастанию") ? "asc" : "desc";
            final String elementClassDirection = DIRECTION_CLASS_PART + classPart;
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
