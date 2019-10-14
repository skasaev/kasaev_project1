package pages.elements;

import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import tools.SeleniumTools;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ChangeRegionForm extends HtmlElement {

    @Name("Region change form input")
    @FindBy(css = ".input__box  input")
    private HtmlElement regionChangeInput;

    @Name("Region suggest list")
    @FindBy(css = ".region-suggest__list-item")
    private List<HtmlElement> regionSuggestList;

    @Name("Region change confirm button")
    @FindBy(css = "button")
    private Button confirmButton;


    public void enterRegionByCharacterAndSelect(@NonNull final String regionName, int length) {
        assertThat("Region name should no be empty", regionName, not(isEmptyString()));
        length = Math.min(regionName.length(), length);

        assertThat("Input field should exist", regionChangeInput.exists());
        regionChangeInput.click();
        regionChangeInput.clear();
        regionChangeInput.sendKeys(regionName.substring(0, length));
        SeleniumTools.hardSleep(200);//waiting animation

        assertThat("Region suggest list should not be empty", regionSuggestList, not(empty()));
        regionSuggestList
                .stream()
                .filter(item -> item.exists() && item.getText().contains(regionName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find [" + regionName + "] in region suggest list"))
                .click();
        confirmButton.click();
    }
}
