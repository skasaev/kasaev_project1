package pages.elements;

import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

@Getter
public class SpecsItem extends HtmlElement {

    @Name("Spec name")
    @FindBy(css = ".n-product-spec__name")
    private HtmlElement specName;

    @Name("Spec value")
    @FindBy(css = ".n-product-spec__value")
    private HtmlElement specValue;
}
