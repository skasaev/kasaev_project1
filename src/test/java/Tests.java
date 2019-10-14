import lombok.NonNull;
import model.XmlFileParameters;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.CategoryPage;
import pages.ListingPage;
import pages.MainPage;
import tools.AttachmentTools;
import tools.XmlTools;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class Tests {
   private final static String OLD_XML_NAME = "notebooks.xml";
   private final static String NEW_XML_NAME = "notebooks_changed.xml";
   private final static String URL = "https://market.yandex.ru";
   private final static String REGION = "Санкт-Петербург";

   private XmlTools tools = new XmlTools();
   private WebDriver driver;

   @BeforeSuite
   public void setUp() {
      tools.writeNewParamsToXml(NEW_XML_NAME, tools.changeParams(tools.getParamsFromXml(OLD_XML_NAME)));
   }

   @AfterSuite
   public void deleteNewFile() {
      tools.deleteNewXmlFile(NEW_XML_NAME);
   }

   @AfterTest
   public void afterTest() throws IOException {
      AttachmentTools.namedScreenShot(driver);
      driver.close();
   }

   @Test(testName = "Internet Explorer test")
   public void testForIE() {
      test(false);
   }

   @Test(testName = "Headless Chrome test")
   public void testForChrome() {
      test(true);
   }

   private void test(boolean isHeadless) {
      SeleniumConfig seleniumConfig = new SeleniumConfig(isHeadless);
      driver = seleniumConfig.getDriver();
      driver.get(URL);
      assertThat("Title differs", driver.getTitle(), containsString("Яндекс.Маркет"));

      MainPage mainPage = new MainPage(driver);
      mainPage.changeRegion(REGION);
      mainPage.checkRegion(REGION);
      mainPage.selectCategory("Компьютерная техника");

      CategoryPage categoryPage = new CategoryPage(driver);
      categoryPage.checkTitle("Компьютерная техника");
      categoryPage.selectSubCategory("Ноутбуки");

      ListingPage listingPage = new ListingPage(driver);
      listingPage.checkTitle("Ноутбуки");

      XmlFileParameters params = tools.getParamsFromXml(NEW_XML_NAME);
      assertThat("Failed on reading new xml file", params, notNullValue());

      checkByParams(params);
   }

   private void checkByParams(@NonNull final XmlFileParameters params) {
       params.getManufacturers().getManufacturers().forEach(manufacturer -> {
           ListingPage listingPage = new ListingPage(driver);
           assertThat("Filter block should be displayed", listingPage.getFilterBlock().exists());

           listingPage.getFilterBlock().selectFilterCheckBox("Производитель", manufacturer.getName());
           final int maxPrice = Math.min(manufacturer.getPriceLimit().getMax(), params.getGlobal().getPrice().getMax());
           listingPage.getFilterBlock().filterByPrice(String.valueOf(maxPrice), "max");
           listingPage.getFilterBlock().filterByPrice(String.valueOf(manufacturer.getPriceLimit().getMin()), "min");

           assertThat("Pager select block should be displayed", listingPage.getPagerSelectBlock().exists());
           listingPage.getPagerSelectBlock().selectOption("Показывать по 12");

           assertThat("Store rating filter block should be displayed", listingPage.getStoreRatingFilterBlock().exists());
           listingPage.getStoreRatingFilterBlock().selectRatingFilter(params.getGlobal().getExcludedVendors().getRating());

           assertThat("Sorting option list should not be empty", listingPage.getSortingOptions(), not(empty()));
           listingPage.sortingBy("по цене", "по убыванию");
           listingPage.verifySortingByPrice("по убыванию");

           driver.get("https://market.yandex.ru/catalog--noutbuki/54544/");
       });
   }

}
