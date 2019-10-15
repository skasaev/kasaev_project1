package pages.elements;

import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

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

}
