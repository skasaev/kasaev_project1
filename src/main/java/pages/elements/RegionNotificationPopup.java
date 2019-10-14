package pages.elements;

import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

@Getter
public class RegionNotificationPopup extends HtmlElement {
    @Name("Close button")
    @FindBy(css = "img.image_name_close")
    private HtmlElement closeButton;
}
