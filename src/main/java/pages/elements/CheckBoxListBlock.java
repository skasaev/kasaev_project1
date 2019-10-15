package pages.elements;

import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import tools.SeleniumTools;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Getter
public class CheckBoxListBlock extends HtmlElement {

    @Name("Block description")
    @FindBy(css = "legend")
    private HtmlElement blockDescription;

    @Name("Checkbox list")
    @FindBy(css = "li label")
    private List<HtmlElement> checkBoxList;

    @Name("Expand list button")
    @FindBy(css = "button")
    private Button expandButton;

    @Name("Search checkbox field")
    @FindBy(css = "input[name='Поле поиска']")
    private HtmlElement searchField;

    @Name("Filter spinner")
    @FindBy(css = "._1Or6hY8Y3c")
    private HtmlElement filterSpinner;

    public void selectCheckBox(@NonNull final String checkBoxLabelName, @NonNull final SeleniumTools seleniumTools) {
        assertThat("Checkbox name should not be empty", checkBoxLabelName, not(isEmptyString()));
        assertThat("Expand button should be display", expandButton.exists());
        expandButton.click();
        assertThat("Search field should be displayed", searchField.exists());
        searchField.sendKeys(checkBoxLabelName);
        seleniumTools.waitSpinner(filterSpinner);

        checkBoxList
                .stream()
                .filter(i -> i.exists() && i.getText().equalsIgnoreCase(checkBoxLabelName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find checkbox with label " + checkBoxLabelName))
                .click();
    }

}
