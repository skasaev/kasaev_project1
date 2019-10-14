package tools;

import io.qameta.allure.Step;
import lombok.NonNull;
import model.XmlFileParameters;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class XmlTools {

    @Step("Get test params from xml {url}")
    public XmlFileParameters getParamsFromXml(@NonNull final String url) {
        assertThat("Xml file url should not be empty", url, not(isEmptyString()));
        XmlFileParameters parameters = new XmlFileParameters();
        try {
            File file = new File("notebooks.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlFileParameters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            parameters = (XmlFileParameters) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    public XmlFileParameters changeParams(@NonNull XmlFileParameters parameters) {
        int newMaxGlobalPrice = parameters.getGlobal().getPrice().getMax() * 10;
        parameters.getGlobal().getPrice().setMax(newMaxGlobalPrice);
        parameters.getManufacturers().getManufacturers().forEach(item -> {
            item.getPriceLimit().setMin(item.getPriceLimit().getMin() * 10);
            item.getPriceLimit().setMax(item.getPriceLimit().getMax() * 10);
        });
        return parameters;
    }

    public void writeNewParamsToXml(@NonNull final String url, @NonNull final XmlFileParameters parameters) {
        assertThat("New xml file url should not be empty", url, not(isEmptyString()));
        try {
            File file = new File(url);
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlFileParameters.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(parameters, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void deleteNewXmlFile(@NonNull final String url) {
        assertThat("New xml file url should not be empty", url, not(isEmptyString()));
        try {
            if (Files.deleteIfExists(Paths.get(url))) {
                System.out.println("NEW FILE DELETED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
