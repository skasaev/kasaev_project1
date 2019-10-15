package pages.elements;

import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

@Getter
public class StoreRatingFilterBlock extends HtmlElement {
    @Name("Rating filter labels")
    @FindBy(css = "label")
    private List<HtmlElement> ratingLabels;
}
