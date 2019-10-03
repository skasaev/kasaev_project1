import model.XmlFileParameters;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;


public class Tests {
   private final static String OLD_XML_NAME = "notebooks.xml";
   private final static String NEW_XML_NAME = "notebooks_changed.xml";
   private String url = "https://market.yandex.ru";

   private XmlTools tools = new XmlTools();

   @BeforeSuite
   public void setUp() {
      tools.writeNewParamsToXml(NEW_XML_NAME, tools.changeParams(tools.getParamsFromXml(OLD_XML_NAME)));
   }

   @AfterSuite
   public void deleteNewFile() {
      tools.deleteNewXmlFile(NEW_XML_NAME);
   }

   @Test(testName = "Internet Explorer test")
   public void testForIE() {
      SeleniumConfig seleniumConfig = new SeleniumConfig(false);
      WebDriver driver = seleniumConfig.getDriver();
      driver.get(url);
      assertThat("Title differs", driver.getTitle(), containsString("Яндекс.Маркет"));

      XmlFileParameters params = tools.getParamsFromXml(NEW_XML_NAME);
      assertThat("Failed on reading new xml file", params, notNullValue());

      driver.close();
   }

   @Test(testName = "Headless Chrome test")
   public void testForChrome() {
      SeleniumConfig seleniumConfig = new SeleniumConfig(true);
      WebDriver driver = seleniumConfig.getDriver();
      driver.get(url);
      assertThat("Title differs", driver.getTitle(), containsString("Яндекс.Маркет"));

      XmlFileParameters params = tools.getParamsFromXml(NEW_XML_NAME);
      assertThat("Failed on reading new xml file", params, notNullValue());

      driver.close();
   }

}
