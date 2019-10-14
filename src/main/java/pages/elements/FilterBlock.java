package pages.elements;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Getter
public class FilterBlock extends HtmlElement {

    @Name("Check-box list block")
    @FindBy(css = "._3M70uokkTS")
    private List<CheckBoxListBlock> checkBoxListBlocks;

    @Name("Price from input field")
    @FindBy(css = "#glpricefrom")
    private HtmlElement priceFromField;

    @Name("Price from input field")
    @FindBy(css = "#glpriceto")
    private HtmlElement priceToField;


    @Step("Select filter {filterName} = {checkBoxLabelName}")
    public void selectFilterCheckBox(@NonNull final String filterName, @NonNull final String checkBoxLabelName) {
        CheckBoxListBlock filterBlock = checkBoxListBlocks
                .stream()
                .filter(block -> block.exists() && block.getBlockDescription().getText().equalsIgnoreCase(filterName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find filter block " + filterName));
        filterBlock.selectCheckBox(checkBoxLabelName);
    }

    @Step("Filter by {type} price = {price}")
    public void filterByPrice(@NonNull final String price, @NonNull final String type) {
        HtmlElement priceField = type.equalsIgnoreCase("min") ? priceFromField : priceToField;
        assertThat("Price field should be displayed", priceField.exists());
        priceField.sendKeys(price);
    }

}
