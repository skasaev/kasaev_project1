package pages.elements;

import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@Getter
public class CatalogItem extends HtmlElement {

    @Name("Item title")
    @FindBy(css = ".n-snippet-card2__title a")
    private Link itemTitle;

    @Name("Item main price")
    @FindBy(css = ".n-snippet-card2__main-price .price")
    private HtmlElement itemMainPrice;

    public int getPriceAsNumber() {
        assertThat("Item main price should be displayed", itemMainPrice.exists());
        final String price = itemMainPrice.getText().replaceAll("[^0-9]*", "");
        assertThat("Item price should be a number", price, not(isEmptyOrNullString()));
        return Integer.parseInt(price);
    }
}
