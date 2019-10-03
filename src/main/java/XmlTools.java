import model.XmlFileParameters;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlTools {

    public XmlFileParameters getParamsFromXml(String url) {
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
        return parameters;
    }

    public XmlFileParameters changeParams(XmlFileParameters parameters) {
        int newMaxGlobalPrice = parameters.getGlobal().getPrice().getMax() * 10;
        parameters.getGlobal().getPrice().setMax(newMaxGlobalPrice);
        parameters.getManufacturers().getManufacturers().forEach(item -> {
            item.getPriceLimit().setMin(item.getPriceLimit().getMin() * 10);
            item.getPriceLimit().setMax(item.getPriceLimit().getMax() * 10);
        });
        return parameters;
    }

    public void writeNewParamsToXml(String url, XmlFileParameters parameters) {
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
}
