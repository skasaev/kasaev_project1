package model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Getter
public class Manufacturers implements BaseModel {
    private List<Manufacturer> manufacturers;

    @XmlElement(name = "Manufacturer")
    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
