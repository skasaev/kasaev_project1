import model.XmlFileParameters;
import org.testng.annotations.Test;

public class XmlTests {
    private final static String OLD_XML_NAME = "notebooks.xml";
    private final static String NEW_XML_NAME = "notebooks_changed.xml";
    private XmlTools tools = new XmlTools();

    @Test(description = "Get file, change and save new xml")
    public void test2() {
        XmlFileParameters parameters = tools.getParamsFromXml(OLD_XML_NAME);
        System.out.println("OLD:\n" + parameters);

        parameters = tools.changeParams(parameters);
        System.out.println("NEW:\n" + parameters);

        tools.writeNewParamsToXml(NEW_XML_NAME, parameters);
    }
}
