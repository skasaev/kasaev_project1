package pages.elements;

import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

@Getter
public class SelectBlock extends HtmlElement {
    @Name("Open options button")
    @FindBy(css = "button")
    private HtmlElement openOptionsButton;

    @Name("Option list")
    @FindBy(css = "option")
    private List<HtmlElement> optionList;

    public void selectOption(@NonNull final String optionName) {
        if (!isOptionSelected(optionName)) {
            assertThat("Select button should be displayed", openOptionsButton.exists());
            openOptionsButton.click();
            assertThat("", optionList, not(empty()));
            optionList
                    .stream()
                    .filter(o -> o.getText().equalsIgnoreCase(optionName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Did not find option " + optionName))
                    .click();
        }
    }

    private boolean isOptionSelected(@NonNull final String optionName) {
        return optionList.stream().anyMatch(option ->
                option.exists() && option.getText().equalsIgnoreCase(optionName) && option.isSelected());
    }
}
