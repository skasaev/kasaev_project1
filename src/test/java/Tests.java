import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class Tests {
   private SeleniumConfig seleniumConfig = new SeleniumConfig();
   private String url = "https://market.yandex.ru";

   @BeforeSuite
   public void setUp() {
      seleniumConfig.getDriver().get(url);
   }

   @AfterSuite
   public void tearDown() {
      seleniumConfig.getDriver().close();
   }

   @Test
   public void test1() {
      seleniumConfig.getDriver().get(url);
      assertThat("Title differs", seleniumConfig.getDriver().getTitle(), containsString("Яндекс.Маркет"));
   }


}
