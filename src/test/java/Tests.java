import lombok.NonNull;
import model.XmlFileParameters;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.CategoryPage;
import pages.ListingPage;
import pages.MainPage;
import pages.ProductPage;
import tools.AttachmentTools;
import tools.ListComparator;
import tools.SeleniumConfig;
import tools.XmlTools;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static tools.TestTools.getDiagonalOrWeight;


public class Tests {
   private final static String OLD_XML_NAME = "notebooks.xml";
   private final static String NEW_XML_NAME = "notebooks_changed.xml";
   private final static String URL = "https://market.yandex.ru";

   private XmlTools tools = new XmlTools();
   private WebDriver driver;
   private Map<String, byte[]> screenShotsMap = new HashMap<>();
   private Map<String, List<Float>> specsMap = new LinkedHashMap<>();
   private SoftAssert softAssert = new SoftAssert();

   @BeforeSuite
   public void setUp() {
      tools.writeNewParamsToXml(NEW_XML_NAME, tools.changeParams(tools.getParamsFromXml(OLD_XML_NAME)));
   }

   @AfterSuite
   public void deleteNewFile() {
      tools.deleteNewXmlFile(NEW_XML_NAME);
   }

   @AfterTest
   public void afterTest() {
      driver.close();
   }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                AttachmentTools.namedScreenShot(driver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

      MainPage mainPage = new MainPage(driver);
      mainPage.getToUrl(URL, "Яндекс.Маркет");
      mainPage.changeRegion("Санкт-Петербург");
      mainPage.checkRegion("Санкт-Петербург");
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

           listingPage.selectFilterCheckBox("Производитель", manufacturer.getName());
           final int maxPrice = Math.min(manufacturer.getPriceLimit().getMax(), params.getGlobal().getPrice().getMax());
           listingPage.filterByPrice(String.valueOf(maxPrice), "max");
           listingPage.filterByPrice(String.valueOf(manufacturer.getPriceLimit().getMin()), "min");

           assertThat("Pager select block should be displayed", listingPage.getPagerSelectBlock().exists());
           //listingPage.selectNumberOfProductsOption("Показывать по 12");

           assertThat("Store rating filter block should be displayed", listingPage.getStoreRatingFilterBlock().exists());
           listingPage.selectRatingFilter(params.getGlobal().getExcludedVendors().getRating());

           assertThat("Sorting option list should not be empty", listingPage.getSortingOptions(), not(empty()));
           listingPage.sortingBy("по цене", "по убыванию");
           listingPage.verifySortingByPrice("по убыванию");
           listingPage.selectCatalogItemByIndex(3);

           ProductPage productPage = new ProductPage(driver);
           productPage.checkProductManufacturer(manufacturer.getName());
           productPage.selectProductTabByName("Характеристики");
           productPage.checkSpecBlockExist();
           try {
               screenShotsMap.put(manufacturer.getName(), productPage.getScreenShot());
           } catch (IOException e) {
               e.printStackTrace();
           }
           productPage.attachAllSpec();

           List<Float> savedSpecs = new ArrayList<>();

           float diag = getDiagonalOrWeight(productPage.getSpec("Экран"));
           assertThat("Notebook diagonal differs", diag, greaterThan(0f));
           savedSpecs.add(diag);

           float weight = getDiagonalOrWeight(productPage.getSpec("Вес"));
           softAssert.assertTrue(weight > 0f, "Notebook weight should be greater than 0");
           savedSpecs.add(weight);

           specsMap.put(manufacturer.getName(), savedSpecs);

           driver.get("https://market.yandex.ru/catalog--noutbuki/54544/");
       });

       final String maxDiagonalNotebook = specsMap.entrySet()
               .stream()
               .max(Comparator.comparing(Map.Entry::getValue, new ListComparator()))
               .get()
               .getKey();
       AttachmentTools.attachImage("Notebook with max diagonal", screenShotsMap.get(maxDiagonalNotebook));
       softAssert.assertAll();
   }

}
