import io.qameta.allure.Step;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;


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

      XmlFileParameters params = tools.getParamsFromXml(NEW_XML_NAME);
      assertThat("Failed on reading new xml file", params, notNullValue());

      MainPage mainPage = new MainPage(driver);
      mainPage.changeRegion(REGION);
      mainPage.checkRegion(REGION);
      mainPage.selectCategory("Компьютерная техника");

      CategoryPage categoryPage = new CategoryPage(driver);
      categoryPage.checkTitle("Компьютерная техника");
      categoryPage.selectSubCategory("Ноутбуки");

      ListingPage listingPage = new ListingPage(driver);
      listingPage.checkTitle("Ноутбуки");
   }

}
