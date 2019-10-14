package pages.elements;

import io.qameta.allure.Step;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class SelectBlock extends HtmlElement {
    @Name("Open options button")
    @FindBy(css = "button")
    private Button openOptionsButton;

    @Name("Option list")
    @FindBy(css = "option")
    private List<HtmlElement> optionList;

    @Step("Select number of products per page {optionName}")
    public void selectOption(@NonNull final String optionName) {
        if (!isOptionSelected(optionName)) {
            assertThat("Select button should be displayed", openOptionsButton.exists());
            openOptionsButton.click();
            optionList
                    .stream()
                    .filter(o -> o.exists() && o.getText().equalsIgnoreCase(optionName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Did not find option " + optionName))
                    .click();
        }
    }

    public boolean isOptionSelected(@NonNull final String optionName) {
        return optionList.stream().anyMatch(option ->
                option.exists() && option.getText().equalsIgnoreCase(optionName) && option.isSelected());
    }
}
