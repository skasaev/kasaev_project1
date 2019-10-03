import model.XmlFileParameters;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlTests {

    @Test
    public void test2() {
        XmlFileParameters parameters = new XmlFileParameters();
        try {

            File file = new File("notebooks.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlFileParameters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            parameters = (XmlFileParameters) jaxbUnmarshaller.unmarshal(file);
            //System.out.println(parameters);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println("OLD:\n" + parameters);

        int newMaxGlobalPrice = parameters.getGlobal().getPrice().getMax() * 10;
        parameters.getGlobal().getPrice().setMax(newMaxGlobalPrice);

        System.out.println(parameters);

    }
}
