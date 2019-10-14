package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Getter
public class CategoryPage extends MainPage {

    @Name("Category page title")
    @FindBy(css = "h1._39qdPorEKz")
    private HtmlElement pageTitle;

    @Name("Sub category link list")
    @FindBy(css = "a._2qvOOvezty")
    private List<HtmlElement> subCategoryLinks;

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that category page title is {title}")
    public void checkTitle(@NonNull final String title) {
        assertThat("Category page title should be displayed", pageTitle.exists());
        assertThat("Category page title differs", pageTitle.getText(), equalToIgnoringCase(title));
    }

    @Step("Select sub-category {subCategoryName} on Category page")
    public void selectSubCategory(@NonNull final String subCategoryName) {
        subCategoryLinks
                .stream()
                .filter(i -> i.exists() && i.getText().equalsIgnoreCase(subCategoryName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find sub category link " + subCategoryName))
                .click();
    }
}
