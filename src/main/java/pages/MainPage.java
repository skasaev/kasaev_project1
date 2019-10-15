package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pages.elements.ChangeRegionForm;
import pages.elements.RegionNotificationPopup;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import tools.AttachmentTools;
import tools.SeleniumConfig;
import tools.SeleniumTools;


import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Getter
public class MainPage {

    @Name("Region field on header")
    @FindBy(css = ".header2-menu__item_type_region")
    private HtmlElement regionField;

    @Name("Region change form")
    @FindBy(css = ".region-select-form")
    private ChangeRegionForm regionChangeForm;

    @Name("Category link list")
    @FindBy(css = ".n-w-tab a")
    private List<HtmlElement> categoryLinks;

    @Name("Regin notification popup")
    @FindBy(css = ".popup2_region_notification.popup2_visible_yes")
    private RegionNotificationPopup regionPopup;

    private final WebDriver driver;
    private final SeleniumTools seleniumTools;

    public MainPage(final WebDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
        this.driver = driver;
        this.seleniumTools = new SeleniumTools(driver);
    }

    @Step("Open page {pageTitle} by url {url}")
    public void getToUrl(@NonNull final String url, @NonNull final String pageTitle) {
        driver.get(url);
        assertThat("Title differs", driver.getTitle(), containsString(pageTitle));

        if (!SeleniumConfig.isHeadless()) {

        }

    }

    @Step("Change region to {regionName}")
    public void changeRegion(@NonNull final String regionName) {
        assertThat("Region field on header should be visible", regionField.exists());
        if (!regionField.getText().equalsIgnoreCase(regionName)) {
            regionField.click();
            assertThat("Region change from should be opened", regionChangeForm.exists());
            regionChangeForm.enterRegionByCharacterAndSelect(regionName, 3);
        }
    }

    @Step("Check that the region name is {regionName}")
    public void checkRegion(@NonNull final String regionName) {
        assertThat("Region field should be equal to " + regionName,
                seleniumTools.waitWithConditionSilent((ExpectedCondition<Boolean>) driver ->
                        regionField.exists() && regionField.getText().equalsIgnoreCase(regionName), 500, 10));
        closeRegionPopup();
    }

    @Step("Select category {categoryName} on Main page")
    public void selectCategory(@NonNull final String categoryName) {
        categoryLinks
                .stream()
                .filter(link -> link.exists() && link.getText().equalsIgnoreCase(categoryName))
                .findFirst().orElseThrow(() -> new AssertionError("Did not find category link " + categoryName))
                .click();
    }

    @Step("Get screen shoot")
    public byte[] getScreenShot() throws IOException {
        return AttachmentTools.namedScreenShot(driver);
    }

    private void closeRegionPopup() {
        if (regionPopup.exists()) {
            assertThat("Region popup close button should be displayed", regionPopup.getCloseButton().exists());
            regionPopup.getCloseButton().click();
            assertThat("Region notification popup should be closed",
                    seleniumTools.waitWithConditionSilent((ExpectedCondition<Boolean>) driver -> !regionPopup.exists(), 500, 5));

        }

    }
}
