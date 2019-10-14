package pages.elements;

import io.qameta.allure.Step;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

public class StoreRatingFilterBlock extends HtmlElement {
    @Name("Rating filter labels")
    @FindBy(css = "labels")
    private List<HtmlElement> ratingLabels;

    /**
     * set filter by rating
     * @param rating example: "2", "4". "any"
     */

    @Step("Set filter by rating {rating}")
    public void selectRatingFilter(@NonNull final String rating) {
        final String attribute = "qrfrom_" + (rating.equalsIgnoreCase("any") ? "-1" : rating);
        ratingLabels
                .stream()
                .filter(label -> label.exists() && label.getAttribute("for").equalsIgnoreCase(attribute))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find filter with rating [" + rating
                        + "], because did not find label attribute " + attribute))
                .click();
    }
}
