package pages;

import io.qameta.allure.Step;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pages.elements.ProductSpecBlock;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;
import tools.AttachmentTools;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductPage extends MainPage {

    @Name("Product title")
    @FindBy(css = ".n-title__text")
    private HtmlElement productTitle;

    @Name("Product parameters tab list")
    @FindBy(css = ".n-product-tabs__item a")
    private List<Link> prodTabsList;

    @Name("Product spec block")
    @FindBy(css = ".n-product-spec-wrap")
    private ProductSpecBlock productSpecBlock;


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check product title contains manufacturer name {manufacturerName}")
    public void checkProductManufacturer(@NonNull final String manufacturerName) {
        assertThat("Product title should be displayed", getSeleniumTools()
                .waitWithConditionSilent((ExpectedCondition<Boolean>) driver -> productTitle.exists(), 100, 10));
        assertThat("Product title should have manufacturer name",
                productTitle.getText().toUpperCase(), containsString(manufacturerName.toUpperCase()));
    }

    @Step("Select product parameter tab {tabName}")
    public void selectProductTabByName(@NonNull final String tabName) {
        prodTabsList
                .stream()
                .filter(tab -> tab.exists() && tab.getText().equalsIgnoreCase(tabName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Did not find product parameter tab " + tabName))
                .click();
    }

    @Step("Check product spec block is displayed")
    public void checkSpecBlockExist() {
        assertThat("Product spec block should be displayed", productSpecBlock.exists());
        assertThat("Product spec list should not be empty", productSpecBlock.getSpecList(), not(empty()));
    }

    @Step("Attach all product specs to report")
    public void attachAllSpec() {
        AtomicReference<String> specs = new AtomicReference<>("");
        productSpecBlock.getSpecList().forEach(s -> {
            specs.set(specs.get().concat(s.getSpecName().getText().trim() + " : " + s.getSpecValue().getText().trim() + "\n"));
        });
        AttachmentTools.attachData("All product specifications", specs.get());
    }

    @Step("Save specification {specName}")
    public String getSpec(@NonNull final String specName) {
        final String spec = productSpecBlock.getSpecByName(specName);
        AttachmentTools.attachData(specName, spec);
        return spec;
    }
}
